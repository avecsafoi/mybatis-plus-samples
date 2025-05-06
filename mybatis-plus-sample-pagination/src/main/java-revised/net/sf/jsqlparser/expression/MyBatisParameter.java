package net.sf.jsqlparser.expression;

import net.sf.jsqlparser.parser.ASTNodeAccessImpl;

public class MyBatisParameter extends ASTNodeAccessImpl implements Expression {
    
    private String name;
    
    public MyBatisParameter(String name) { super(); this.name = name; }
    
    @Override
    public <T, S> T accept(ExpressionVisitor<T> expressionVisitor, S context) { return null; }
    
    @Override
    public String toString() { return "#{%s}".formatted(name); }
}
