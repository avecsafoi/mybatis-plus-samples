/*
 *    Copyright 2009-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.scripting.xmltags;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.IPage.PageType;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserGlobal;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.MyBatisParameter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.ParenthesedStatement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SetOperationList;

/**
 * @author Clinton Begin
 */
public class DynamicSqlSource implements SqlSource {
    
    private final Configuration configuration;
    private final SqlNode       rootSqlNode;
    
    private static final Pattern P1 = Pattern.compile("#\\{([^,]+)[^\\}]*\\}");
    private static final Pattern P2 = Pattern.compile(":xb(\\d+)");
    
    public DynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) { this.configuration = configuration; this.rootSqlNode = rootSqlNode; }
    
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        rootSqlNode.apply(context);
        SqlSourceBuilder            sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?>                    parameterType   = parameterObject == null ? Object.class : parameterObject.getClass();
        Map.Entry<String, IPage<?>> pe              = ParameterUtils.findPageEntry(parameterObject);
        IPage<?>                    page            = pe == null ? null : pe.getValue();
        String                      name            = pe == null ? null : pe.getKey();
        String                      sql             = context.getSql();
        
        if (page != null && !page.isFirst() && page.pageType() == PageType.ORDERS) {
            
            Matcher            m = P1.matcher(sql);
            LinkedList<String> l = new LinkedList<>();
            StringBuilder      b = new StringBuilder();
            
            int i = 0, j = 0;
            for (; m.find(i); i = m.end()) { l.add(m.group()); b.append(sql.substring(i, m.start())); b.append(":xb").append(j++); }
            if (i < sql.length()) b.append(sql.substring(i));
            sql = b.toString();
            
            Select selectBody = parseSql(sql);
            if (selectBody instanceof PlainSelect plainSelect) {
                addWhere(plainSelect, page, name);
                sql = plainSelect.toString();
            } else if (selectBody instanceof SetOperationList setOperationList) {
                for (Select select : setOperationList.getSelects()) addWhere(select.getPlainSelect(), page, name);
                sql = setOperationList.toString();
            }
            b = new StringBuilder();
            m = P2.matcher(sql);
            for (i = 0, j = 0; m.find(i); i = m.end()) { b.append(sql.substring(i, m.start())).append(l.get(j++)); }
            if (i < sql.length()) b.append(sql.substring(i));
            sql = b.toString();
        }
        Map<String, Object> bindings  = context.getBindings();
        SqlSource           sqlSource = sqlSourceParser.parse(sql, parameterType, bindings);
        BoundSql            boundSql  = sqlSource.getBoundSql(parameterObject);
        bindings.forEach(boundSql::setAdditionalParameter);
        return boundSql;
    }
    
    private void addWhere(PlainSelect select, IPage<?> page, String name) {
        if (page.orders().isEmpty()) return;
        Expression    ex = null;
        AndExpression ax = null, a = null;
        int           i  = 0, z = page.orders().size();
        for (OrderItem o : page.orders()) {
            Column           c = new Column(o.getColumn());
            String           n = "%sorders[%d].value".formatted(name == null ? "" : name + ".", i);
            MyBatisParameter p = new MyBatisParameter(n);
            Expression       w = o.isAsc() ? new GreaterThan(c, p) : new MinorThan(c, p);
            if (++i < z) { a = new AndExpression(new EqualsTo(c, p), null); w = new ParenthesedExpressionList<>(new OrExpression(w, a)); }
            if (ax != null) ax.setRightExpression(w);
            ax = a;
            if (ex == null) ex = w;
        }
        Expression wx = select.getWhere();
        if (wx != null) { if (!(wx instanceof ParenthesedStatement)) wx = new ParenthesedExpressionList<>(wx); ex = new AndExpression(wx, ex); }
        select.setWhere(ex);
    }
    
    private Select parseSql(String sql) {
        try {
            return (Select) JsqlParserGlobal.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
            return null;
        }
    }
}