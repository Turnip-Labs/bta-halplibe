package turniplabs.halplibe.util.registry;

import turniplabs.halplibe.util.registry.error.RequestOutOfBounds;

import java.util.ArrayList;
import java.util.List;

public class IdSupplier {
    RunReserves reserves;
    RunLengthConfig cfg;
    int max;

    /* the list of reservations used by the code requesting the ids */
    ArrayList<Reservation> reservations = new ArrayList<>();
    /* if this is true, the reservations will get optimized when being written */
    boolean hasUnreserved = false;

    public IdSupplier(RunReserves reserves, RunLengthConfig cfg, int max) {
        this.reserves = reserves;
        this.cfg = cfg;
        this.max = max;

        if (cfg.reservations[0].reserved) {
            reservations.add(cfg.reservations[0]);

            reservationStart = cfg.reservations[0].start;
            reservationEnd = cfg.reservations[0].end;
        }

        for (Reservation reservation : cfg.reservations) {
            if (!reservation.reserved) {
                hasUnreserved = true;
                break;
            }
        }
    }

    /* information about the reservation currently being filled */
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

        if (hasUnreserved) {
            // group newly added reservations with existing ones if possible
            List<Reservation> result = new ArrayList<>();
            Reservation prev = null;
            for (Reservation reservation : reservations) {
                if (prev == null) {
                    prev = reservation;
                    result.add(reservation);
                } else {
                    if (prev.end + 1 == reservation.start) {
                        result.remove(result.size() - 1);
                        result.add(new Reservation(prev.start, reservation.end));
                    } else {
                        result.add(reservation);
                    }
                    prev = reservation;
                }
            }

            return result;
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
