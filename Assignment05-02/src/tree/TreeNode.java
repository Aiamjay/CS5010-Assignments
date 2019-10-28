package tree;

import java.util.function.Function;

/**
 * Binary Tree Data Structure.
 *
 * @param <T> data type
 */
public interface TreeNode<T> {
  /**
   * Get the left child of current node.
   *
   * @return left child
   */
  TreeNode<T> getLeftChild();

  /**
   * Get the right child of current node.
   *
   * @return right child
   */
  TreeNode<T> getRightChild();

  /**
   * Transform Treenode with type T to TreeNode with type R.
   *
   * @param transform transform function.
   * @param <R>       The target type.
   * @return TreeNode with type R.
   */
  <R> TreeNode<R> map(Function<T, R> transform);

  /**
   * Reduce function take a TriFunction as parameter and then reduce the tree structure.
   *
   * @param combiner TriFunction applied to tree node.
   * @param <R>      the type of result.
   * @return the reduced result.
   */
  <R> R reduce(TriFunction<T, R, R, R> combiner);

  /**
   * Get the data stored in TreeNode.
   *
   * @return the data treenode carrying.
   */
  T getData();

  /**
   * Return current tree is leaf or not.
   *
   * @return leaf or not
   */
  boolean isLeaf();
}