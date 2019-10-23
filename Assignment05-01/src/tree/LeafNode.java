package tree;

import java.util.function.Function;

public class LeafNode<T> extends AbstractTreeNode<T> {

    public LeafNode(T data) {
        super(data);
    }

    @Override
    public <R> TreeNode<R> map(Function<T, R> transform) {
        return new LeafNode<R>(transform.apply(this.data));
    }

    @Override
    public <R> R reduce(TriFunction<T, R, R, R> combiner) {
        return combiner.apply(this.data, null, null);
    }
}
