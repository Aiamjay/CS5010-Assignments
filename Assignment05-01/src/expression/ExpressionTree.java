package expression;

import tree.GroupNode;
import tree.LeafNode;
import tree.TreeNode;
import tree.TriFunction;

import java.util.*;


public class ExpressionTree implements Expression<String> {
    /*
     * This class is used to represent the expression tree, and it will use tree structure for implementation.
     */

    private final static String ADD = "+";
    private final static String SUB = "-";
    private final static String MUL = "*";
    private final static String DIV = "/";

    public static final Double numericalCalculate(String operator, Double operand1, Double operand2) {
        switch (operator) {
            case ADD:
                return operand1 + operand2;
            case SUB:
                return operand1 - operand2;
            case MUL:
                return operand1 * operand2;
            case DIV:
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator in expression.");
        }
    }

    static TriFunction<String, Double, Double, Double> calculate = new TriFunction<String, Double, Double, Double>() {
        @Override
        public Double apply(String arg1, Double arg2, Double arg3) {
            if (arg2 == null) {
                return Double.parseDouble(arg1);
            }
            return numericalCalculate(arg1, arg2, arg3);
        }
    };

    static TriFunction<String, String, String, String> inOrder = (a, b, c) -> {
        String left = b == null ? "" : "(" + b;
        String right = c == null ? "" : (c.charAt(0) == '-' ? "(" + c + ")" : c) + ")";
        return left + a + right;
    };

    static TriFunction<String, String, String, String> preOrder = (a, b, c) -> {
        return b == null ? (a.charAt(0) == '-' ? "(" + a + ")" : a) : "(" + a + " " + b + " " + c + ")";
    };

    /**
     * Operator set.
     */
    public static Set<String> OPERATORS = new HashSet<>(Arrays.asList(ADD, SUB, MUL, DIV));

    private TreeNode<String> root;

    /**
     * This constructor takes an expression in postfix form and parse it and create the expression tree accordingly.
     *
     * @param expression the postfix form of the expression string.
     */
    public ExpressionTree(String expression) throws Exception {
        Scanner scanner = new Scanner(expression);
        Stack<TreeNode<String>> operands = new Stack<>();
        while (scanner.hasNextLine()) {
            String current = scanner.next();
            if (OPERATORS.contains(current)) {
                TreeNode<String> right = operands.pop();
                TreeNode<String> left = operands.pop();
                operands.add(new GroupNode(current, left, right));
            } else {
                operands.add(new LeafNode<String>(current));
            }
        }
        scanner.close();
        if (operands.size() != 1) {
            throw new Exception("something went wrong ");
        }
        root = operands.pop();
    }

    @Override
    public String infix() {
        return this.root.reduce(inOrder);
    }

    @Override
    public double evaluate() {
        return this.root.reduce(calculate);
    }

    @Override
    public String schemeExpression() {
        return this.root.reduce(preOrder);
    }
}
