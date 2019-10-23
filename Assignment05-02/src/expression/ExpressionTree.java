package expression;

import Utils.Utils;
import tree.TreeNode;
import tree.TriFunction;

import java.util.function.Function;

import static Utils.Utils.NUMERICAL_OPERATORS;
import static Utils.Utils.numericalCalculate;

public class ExpressionTree implements Expression {
    private static final String TAG = "ExpressionTree";
    private TreeNode root;

    private static final TriFunction<String, Double, Double, Double> calculate =
            (arg1, arg2, arg3) -> numericalCalculate(arg1, arg2, arg3);

    private static final TriFunction<String, String, String, String> inOrder =
            (arg1, arg2, arg3) -> "(" + arg2 + arg1 + (arg3.charAt(0) == '-' ? "(" + arg3 + ")" : arg3) + ")";

    private static final TriFunction<String, String, String, String> preOrder =
            (arg1, arg2, arg3) -> "(" + arg1 + " " + arg2 + " " + arg3 + ")";

    public ExpressionTree(String expression) {
        this.root = Utils.parsingString(expression, string2Double, NUMERICAL_OPERATORS);
    }

    private static final Function<String, Double> string2Double = (str) -> {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid character in expression string");
        }
    };

    @Override
    public double evaluate() {
        // note it's not elegant to cast Object to double, because root data type vary.
        return (double) this.root.reduce(calculate);
    }

    @Override
    public String infix() {
        TreeNode<String> node = this.root.map(e -> e.toString());
        return node.reduce(inOrder);
    }

    @Override
    public String schemeExpression() {
        TreeNode<String> node = this.root.map(e -> e.toString());
        return node.reduce(preOrder);
    }
}
