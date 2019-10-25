package tree;

import java.util.function.Function;

public class Operator<T> extends AbstractTreeNode<T> {
  private TreeNode left;
  private TreeNode right;

  /**
   * Constructor for operator.
   *
   * @param data  data .
   * @param left  left node.
   * @param right right node.
   */
  public Operator(T data, TreeNode left, TreeNode right) {
    super(data);
    this.left = left;
    this.right = right;
  }

  /**
   * Get the left child of current node.
   *
   * @return left children node.
   */
  @Override
  public TreeNode<T> getLeftChild() {
    return this.left;
  }

  /**
   * Get the right children of current node.
   *
   * @return right children node.
   */
  @Override
  public TreeNode<T> getRightChild() {
    return this.right;
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
    return new Operator<R>(transform.apply(this.data),
            this.left.map(transform),
            this.right.map(transform));
  }

  /**
   * Reduce function take a TriFunction as parameter and then reduce the tree structure.
   *
   * @param combiner TriFunction applied to tree node.
   * @param <R>      the type of result.
   * @return reduced result.
   */
  @Override
  public <R> R reduce(TriFunction<T, R, R, R> combiner) {
    // note although it's not elegant to cast type from Object to R,\
    // note vase child nodes may has different data types.
    return combiner.apply(this.data,
            (R) this.left.reduce(combiner),
            (R) this.right.reduce(combiner));
  }

  @Override
  public boolean isLeaf() {
    return false;
  }
}
