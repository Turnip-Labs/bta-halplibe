package turniplabs.halplibe.util.toml;

public class CommentedEntry<T> extends Entry<T> {
    String comment;

    public String getComment() {
        return comment;
    }

    public CommentedEntry(String comment, T t) {
        super(t);
        this.comment = comment;
    }

    public T getT() {
        return t;
    }

    @Override
    public String toString(String key) {
        StringBuilder res = new StringBuilder();
        for (String s : comment.split("\n"))
            res.append("# ").append(s).append("\n");
        res.append(super.toString(key));
        return res.toString();
    }
}
