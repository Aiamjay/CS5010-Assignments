package intervals;

import com.sun.istack.internal.NotNull;
import tree.GroupNode;
import tree.LeafNode;
import tree.TreeNode;
import tree.TriFunction;

import java.util.*;

public class IntervalTree implements Intervals {

    private static final TriFunction<String, Interval, Interval, Interval> calculate = (arg1, arg2, arg3) -> {
        if (arg2 == null) {
            return toInterval(arg1);
        }
        return intervalCalculate(arg1, arg2, arg3);
    };

    @Override
    public Interval evaluate() {
        return this.root.reduce(calculate);
    }

    private static final String UNION = "U";
    private static final String INTERSECTION = "I";
    private static Set<String> OPERATORS = new HashSet<>(Arrays.asList(UNION, INTERSECTION));

    private static final Interval toInterval(String expression) {
        int start = Integer.parseInt(expression.substring(0, expression.indexOf(',')));
        int end = Integer.parseInt(expression.substring(expression.indexOf(',') + 1));
        return new Interval(start, end);
    }

    private static final Interval intervalCalculate(String operator, Interval operand1, Interval operand2) {
        switch (operator) {
            case UNION:
                return operand1.union(operand2);
            case INTERSECTION:
                return operand1.intersect(operand2);
            default:
                throw new IllegalArgumentException("Invalid operator for intervals");
        }
    }

    private TreeNode<String> root;

    public IntervalTree(@NotNull String expression) throws Exception {
        Scanner scanner = new Scanner(expression);
        Stack<TreeNode<String>> operands = new Stack<>();
        while (scanner.hasNextLine()) {
            String current = scanner.next();
            if (OPERATORS.contains(current)) {
                TreeNode<String> right = operands.pop();
                TreeNode<String> left = operands.pop();
                operands.add(new GroupNode(current, left, right));
            } else {
                operands.add(new LeafNode<>(current));
            }
        }
        scanner.close();
        if (operands.size() != 1) {
            throw new Exception("something went wrong ");
        }
        root = operands.pop();
    }

}
