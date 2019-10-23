package tests;

import expression.ExpressionTree;
import org.junit.Test;

import java.util.Scanner;

public class TestForExpressionTree {

    @Test
    public void testExpressionConstructor() throws Exception {
        String case1 = "1 2 +";
        String case2 = "1.2 5.4 *     -4.5 +";
        Scanner scanner = new Scanner(case2);
        ExpressionTree tree = new ExpressionTree(case2);
        System.out.println(tree.infix());
        System.out.println(tree.schemeExpression());
        System.out.println(tree.evaluate());
    }
}
