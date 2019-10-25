package tree;

import java.util.function.Function;

public class Operand<T> extends AbstractTreeNode<T> {
  /**
   * Constructor for Operand.
   *
   * @param data data
   */
  public Operand(T data) {
    super(data);
  }

  /**
   * Return current tree is leaf or not.
   *
   * @return leaf or not
   */
  @Override
  public boolean isLeaf() {
    return true;
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
    return new Operand<>(transform.apply(this.data));
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
    return (R) this.data;
  }
}
