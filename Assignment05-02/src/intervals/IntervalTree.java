package intervals;

import Utils.Utils;
import tree.TreeNode;
import tree.TriFunction;

import java.util.function.Function;

import static Utils.Utils.*;

public class IntervalTree implements Intervals {
    @Override
    public Interval evaluate() {
        return (Interval) this.root.reduce(calculate);
    }

    private static final String TAG = "ExpressionTree";
    private TreeNode root;

    private static final TriFunction<String, Interval, Interval, Interval> calculate =
            (arg1, arg2, arg3) -> intervalCalculate(arg1, arg2, arg3);

    public IntervalTree(String expression) {
        this.root = Utils.parsingString(expression, string2Interval, INTERVAL_OPERATORS);
    }

    private static final Function<String, Interval> string2Interval = (str) -> {
        int index = str.indexOf(',');
        if (index == -1) {
            throw new IllegalArgumentException("Invalid interval expression!");
        }
        return new Interval(Integer.parseInt(str.substring(0, index)), Integer.parseInt(str.substring(index + 1)));
    };
}
