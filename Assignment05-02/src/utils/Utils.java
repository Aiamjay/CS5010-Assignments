package utils;

import intervals.Interval;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

import tree.Operand;
import tree.Operator;
import tree.TreeNode;


/**
 * There are some common functions for tree node. Including numerical calculation and interval
 * calculation.
 */
public final class Utils {
  private static final String ADD = "+";
  private static final String SUB = "-";
  private static final String MUL = "*";
  private static final String DIV = "/";

  /**
   * Operators for numerical calculation.
   */
  public static final Set<String> NUMERICAL_OPERATORS =
          new HashSet<>(Arrays.asList(ADD, SUB, MUL, DIV));

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
  /**
   * Operators for intervals.
   */
  public static Set<String> INTERVAL_OPERATORS = new HashSet<>(Arrays.asList(UNION, INTERSECTION));

  /**
   * Calculation for intervals.
   *
   * @param operator the operator, U or I
   * @param operand1 operand one
   * @param operand2 operand two
   * @return result
   */
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
   * This function parse expression string to expression tree, with specific type for operand and
   * string type data for operator, which means for operator treenode, it would store string type
   * data, and R type for operand.
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
      throw new IllegalArgumentException("Something wrong happened in string parsing function.");
    }
    return operands.pop();
  }
}