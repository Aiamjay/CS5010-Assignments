package Utils;

import intervals.Interval;
import tree.Operand;
import tree.Operator;
import tree.TreeNode;

import java.util.*;
import java.util.function.Function;

public final class Utils {
    private final static String ADD = "+";
    private final static String SUB = "-";
    private final static String MUL = "*";
    private final static String DIV = "/";

    /**
     * Operators for numerical calculation.
     */
    public static final Set<String> NUMERICAL_OPERATORS = new HashSet<>(Arrays.asList(ADD, SUB, MUL, DIV));

    /**
     * Calculation for numbers.
     *
     * @param operator operator, + - * /
     * @param operand1 first operand
     * @param operand2 second operand
     * @return the result
     */
    public static Double numericalCalculate(String operator, Double operand1, Double operand2) {
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

    private static final String UNION = "U";
    private static final String INTERSECTION = "I";
    public static Set<String> INTERVAL_OPERATORS = new HashSet<>(Arrays.asList(UNION, INTERSECTION));

    public static Interval intervalCalculate(String operator, Interval operand1, Interval operand2) {
        switch (operator) {
            case UNION:
                return operand1.union(operand2);
            case INTERSECTION:
                return operand1.intersect(operand2);
            default:
                throw new IllegalArgumentException("Invalid operator for intervals");
        }
    }

    /**
     * This function parse expression string to expression tree, with specific type for operand and string type data for
     * operator, which means for operator treenode, it would store string type data, and R type for operand.
     *
     * @param expression     the string expression to be parsed.
     * @param transformer    transform the string to certain data type.
     * @param validOperators valid operators for specific kind of expression tree.
     * @param <R>            Type for Operand.
     * @return root node of the expression tree.
     */
    public static <R> TreeNode parsingString(String expression,
                                             Function<String, R> transformer,
                                             Set<String> validOperators) {
        Scanner scanner = new Scanner(expression.trim());
        Stack<TreeNode> operands = new Stack<>();
        while (scanner.hasNextLine()) {
            String current = scanner.next();
            if (validOperators.contains(current)) {
                TreeNode right = operands.pop();
                TreeNode left = operands.pop();
                operands.add(new Operator(current, left, right));
            } else {
                operands.add(new Operand<R>(transformer.apply(current)));
            }
        }
        if (operands.size() != 1) {
            throw new RuntimeException("Something wrong happened in string parsing function.");
        }
        return operands.pop();
    }
}
