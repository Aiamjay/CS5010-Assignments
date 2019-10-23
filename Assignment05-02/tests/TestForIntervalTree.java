import intervals.IntervalTree;
import org.junit.Test;

public class TestForIntervalTree {

    @Test
    public void testConstructor() {
        String case1 = "1,4 2,5 U";
        IntervalTree tree = new IntervalTree(case1);
        System.out.println(tree.evaluate());
        String case2 = "-4,4 2,5 U  -1,4 I ";
        tree = new IntervalTree(case2);
        System.out.println(tree.evaluate());
        String case3 = "3,7 2,6 4,10 I U";
        tree = new IntervalTree(case3);
        System.out.println(tree.evaluate());
        String case4 = "3,10 5,12 U 4,4 I";
        tree = new IntervalTree(case4);
        System.out.println(tree.evaluate());
    }
}
