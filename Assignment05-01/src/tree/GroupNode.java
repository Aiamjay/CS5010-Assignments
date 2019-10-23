package tree;

import java.util.function.Function;

public class GroupNode<T> extends AbstractTreeNode<T> {
    protected TreeNode<T> left;
    protected TreeNode<T> right;

    public GroupNode(T data, TreeNode<T> left, TreeNode<T> right) {
        super(data);
        this.right = right;
        this.left = left;
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
        return new GroupNode<>(transform.apply(this.data),
                this.left.map(transform),
                this.right.map(transform));
    }


    @Override
    public <R> R reduce(TriFunction<T, R, R, R> combiner) {
        return combiner.apply(this.data, this.left.reduce(combiner),
                this.right.reduce(combiner));
    }
}
