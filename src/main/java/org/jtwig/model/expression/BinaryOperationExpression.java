package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.expression.operation.BinaryOperator;
import org.jtwig.model.position.Position;
import org.jtwig.util.ErrorMessageFormatter;
import org.jtwig.value.JtwigValue;

public class BinaryOperationExpression extends InjectableExpression {
    private final Expression leftOperand;
    private final BinaryOperator operator;
    private final Expression rightOperand;

    public BinaryOperationExpression(Position position, Expression leftOperand, BinaryOperator operator, Expression rightOperand) {
        super(position);
        this.leftOperand = leftOperand;
        this.operator = operator;
        this.rightOperand = rightOperand;
    }

    public Expression getLeftOperand() {
        return leftOperand;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public Expression getRightOperand() {
        return rightOperand;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        return operator.calculator().calculate(context, getPosition(), leftOperand, rightOperand);
    }

    @Override
    public Expression inject(Expression expression) {
        if (leftOperand instanceof InjectableExpression) {
            Expression inject = ((InjectableExpression) leftOperand).inject(expression);
            return new BinaryOperationExpression(getPosition(), inject, operator, rightOperand);
        } else {
            throw new CalculationException(ErrorMessageFormatter.errorMessage(getPosition(), "Invalid expression, expecting a valid injectable one"));
        }
    }
}
