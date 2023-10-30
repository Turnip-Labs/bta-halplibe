package turniplabs.halplibe.util.registry;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RunReserves {
    ArrayList<Reservation> reservations = new ArrayList<>();

    // arg: min
    // result: a run with at least the specified length
    Function<Integer, Integer> idFinder;
    // args: id, min
    // result: the length of the run, or min if it's longer than min
    BiFunction<Integer, Integer, Integer> runLengthFinder;

    public RunReserves(Function<Integer, Integer> idFinder, BiFunction<Integer, Integer, Integer> runLengthFinder) {
        this.idFinder = idFinder;
        this.runLengthFinder = runLengthFinder;
    }

    public boolean isReserved(int id) {
        for (Reservation reservation : reservations) {
            if (!reservation.reserved) continue;

            if (reservation.start <= id && reservation.end >= id)
                return true;
        }

        return false;
    }
}
