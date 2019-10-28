package tree;

import java.util.function.Function;

/**
 * Abstract data structure for tree node.
 * @param <T> data type.
 */
public abstract class AbstractTreeNode<T> implements TreeNode<T> {

  protected T data;

  /**
   * Constructor for AbstractTreeNode.
   *
   * @param data data
   */
  public AbstractTreeNode(T data) {
    this.data = data;
  }

  /**
   * Get the left children.
   *
   * @return left children
   */
  @Override
  public TreeNode<T> getLeftChild() {
    return null;
  }

  /**
   * Get the right children.
   *
   * @return right children
   */
  @Override
  public TreeNode<T> getRightChild() {
    return null;
  }

  /**
   * Transform Treenode with type T to TreeNode with type R.
   *
   * @param transform transform function.
   * @param <R>       The target type.
   * @return TreeNode with type R.
   */
  @Override
  public <R> TreeNode<R> map(Function<T, R> transform) {
    return null;
  }

  /**
   * Reduce function take a TriFunction as parameter and then reduce the tree structure.
   *
   * @param combiner TriFunction applied to tree node.
   * @param <R>      the type of result.
   * @return
   */
  @Override
  public <R> R reduce(TriFunction<T, R, R, R> combiner) {
    return null;
  }

  /**
   * Get the data stored in TreeNode.
   *
   * @return the data treenode carrying.
   */
  @Override
  public T getData() {
    return this.data;
  }
}