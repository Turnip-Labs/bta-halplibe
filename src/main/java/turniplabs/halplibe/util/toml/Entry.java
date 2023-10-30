package turniplabs.halplibe.util.toml;

public class Entry<T> {
    T t;
    boolean string = false;

    public Entry(T t) {
        this.t = t;
        string = t instanceof String;
    }

    public T getT() {
        return t;
    }

    public String toString(String key) {
        return key + " = " + this;
    }

    @Override
    public String toString() {
        if (string) {
            return "\"" + t.toString() + "\"";
        }
        return t.toString();
    }
}
