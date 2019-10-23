import static org.junit.Assert.assertEquals;

import intervals.Interval;
import intervals.IntervalTree;
import org.junit.Test;

public class TestForIntervalTree {

    @Test
    public void testConstructor() throws Exception {
        String case1 = "1,4 2,5 U";
        String case2 = "-4,4 2,5 U   -1,4  I";
        IntervalTree tree = new IntervalTree(case1);
        Interval result = new Interval(1, 5);
        assertEquals(result, tree.evaluate());
    }
}
