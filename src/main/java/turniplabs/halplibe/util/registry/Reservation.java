package turniplabs.halplibe.util.registry;

public class Reservation {
    int start, end;
    boolean reserved;

    public Reservation(int start, int end) {
        this.start = start;
        this.end = end;
        reserved = true;
    }

    public Reservation() {
        start = -1;
        end = -1;
        reserved = false;
    }
}
