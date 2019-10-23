package tree;

import java.util.function.Function;

public class Operator<T> extends AbstractTreeNode<T> {
    private TreeNode left;
    private TreeNode right;

    public Operator(T data, TreeNode left, TreeNode right) {
        super(data);
        this.left = left;
        this.right = right;
    }

    @Override
    public TreeNode<T> getLeftChild() {
        return this.left;
    }

    @Override
    public TreeNode<T> getRightChild() {
        return this.right;
    }

    @Override
    public <R> TreeNode<R> map(Function<T, R> transform) {
        return new Operator<R>(transform.apply(this.data), this.left.map(transform), this.right.map(transform));
    }

    @Override
    public <R> R reduce(TriFunction<T, R, R, R> combiner) {
        // note although it's not elegant to cast type from Object to R, vase child nodes may has different data types.
        R left = (R) (this.left.isLeaf() ? this.left.getData() : this.left.reduce(combiner));
        R right = (R) (this.right.isLeaf() ? this.right.getData() : this.right.reduce(combiner));
        return combiner.apply(this.data, left, right);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
