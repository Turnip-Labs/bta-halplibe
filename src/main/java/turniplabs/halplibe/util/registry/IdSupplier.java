package turniplabs.halplibe.util.registry;

import turniplabs.halplibe.util.registry.error.RequestOutOfBounds;

import java.util.ArrayList;
import java.util.List;

public class IdSupplier {
    RunLengthConfig cfg;
    RunReserves reserves;

    ArrayList<Reservation> reservations = new ArrayList<>();
    int max;

    public IdSupplier(RunReserves reserves, int max, RunLengthConfig cfg) {
        this.reserves = reserves;
        this.max = max;
        this.cfg = cfg;

        if (cfg.reservations[0].reserved) {
            reservations.add(cfg.reservations[0]);

            reservationStart = cfg.reservations[0].start;
            reservationEnd = cfg.reservations[0].end;
        }
    }

    int reservationId = 0;
    int current = 0;
    int done = 0;

    int reservationStart = -1;
    int reservationEnd = -1;

    /**
     * Finds the next available id
     * If you remove a block/item, it should currently just be replaced with a call to this to prevent id shifting
     *
     * @return the id
     */
    public int next() {
        Reservation workingIn = cfg.reservations[reservationId];
        if (
                workingIn.reserved &&
                        workingIn.start + current > workingIn.end
        ) {
            reservationId++;
            current = 0;
            workingIn = cfg.reservations[reservationId];
            if (workingIn.reserved)
                reservations.add(workingIn);

            reservationStart = workingIn.start;
            reservationEnd = workingIn.end;
        }
        if (!workingIn.reserved) {
            if (
                    reservationStart == -1 ||
                            reservationStart + current >= reservationEnd
            ) {
                if (reservationStart != -1) {
                    reservations.add(new Reservation(reservationStart, reservationStart + current - 1));
                }

                reservationStart = reserves.idFinder.apply(0);
                reservationEnd = reservationStart + reserves.runLengthFinder.apply(reservationStart, max - done);
                current = 0;
            }
        }

        if (done > max) {
            throw new RequestOutOfBounds("A mod has gotten more ids than it has requested.");
        }

        done++;
        if (done == max) {
            int id = reservationStart + current++;
            cfg.write(calculateRuns());
            return id;
        }
        return reservationStart + current++;
    }

    public List<Reservation> calculateRuns() {
        if (!cfg.reservations[reservationId].reserved) {
            if (reservationStart != -1 && current != 0) {
                reservations.add(new Reservation(reservationStart, reservationStart + current - 1));
                reservationId = -1;
                reservationStart = -1;
                reservationEnd = -1;
                current = 0;
            }
        }

        return reservations;
    }

    /**
     * Ensures that in the current registry run has at least {@param amount} ids remaining
     * Used for blocks/items that look for hardcoded ids, such as furnaces which look for id+1 for active and id-1 for inactive
     *
     * @param amount the amount of ids to ensure
     */
    public void ensureFree(int amount) {
        Reservation workingIn = cfg.reservations[reservationId];
        if (workingIn.reserved) return;

        int remaining = reservationEnd - (reservationStart + current);
        System.out.println(remaining);
        if (remaining >= amount) {
            return;
        }

        if (reservationStart != -1) {
            reservations.add(new Reservation(reservationStart, reservationStart + current - 1));
        }

        reservationStart = reserves.idFinder.apply(amount);
        reservationEnd = reservationStart + reserves.runLengthFinder.apply(reservationStart, max - done);
        current = 0;
    }
}
