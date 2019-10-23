import expression.ExpressionTree;
import org.junit.Test;

public class TestForExpression {

    @Test
    public void testConstructor() {
        String case1 = "-1 -2 +";
        String case2 = "-2 -3 -4";
        ExpressionTree tree = new ExpressionTree(case1);
        System.out.println(tree.evaluate());
        System.out.println(tree.infix());
        System.out.println(tree.schemeExpression());
        String case3 = "1.2 5.4 *     -4.5 +";
        tree = new ExpressionTree(case3);
        System.out.println(tree.evaluate());
        System.out.println(tree.infix());
        System.out.println(tree.schemeExpression());
        String case4 = "-1";
        tree = new ExpressionTree(case4);
        System.out.println(tree.evaluate());
        System.out.println(tree.infix());
        System.out.println(tree.schemeExpression());
    }
}
