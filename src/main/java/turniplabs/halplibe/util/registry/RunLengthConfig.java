package turniplabs.halplibe.util.registry;

import turniplabs.halplibe.util.toml.Toml;

import java.util.ArrayList;
import java.util.List;

public class RunLengthConfig {
    public Reservation[] reservations;

    Toml config;

    public RunLengthConfig(Toml toml, int requested) {
        this.config = toml;

        ArrayList<Integer> sortedKeys = new ArrayList<>();
        for (String orderedKey : toml.getOrderedKeys()) {
            sortedKeys.add(
                    Integer.parseInt(orderedKey.substring(3))
            );
        }
        sortedKeys.sort(Integer::compare);

        int reserved = 0;
        ArrayList<Reservation> reservations1 = new ArrayList<>();
        for (Integer sortedKey : sortedKeys) {
            String run = toml.get("run" + sortedKey, String.class);
            if (run.contains(",")) {
                String[] split = run.split(",");
                int start = Integer.parseInt(split[0].trim());
                int end = Integer.parseInt(split[1].trim());

                reserved += end - start + 1;
                reservations1.add(new Reservation(start, end));
            } else {
                int index = Integer.parseInt(run);
                reservations1.add(new Reservation(index, index));
            }
        }
        if (reserved < requested) {
            reservations1.add(new Reservation());
        }
        this.reservations = reservations1.toArray(new Reservation[0]);
    }

    public void write(List<Reservation> collectedReservations) {
        for (String orderedKey : config.getOrderedKeys())
            config.remove(orderedKey);

        int reserve = 0;
        for (Reservation collectedReservation : collectedReservations) {
            if (collectedReservation.start == collectedReservation.end)
                config.addEntry("run" + reserve, "" + collectedReservation.start);
            else
                config.addEntry("run" + reserve, collectedReservation.start + ", " + collectedReservation.end);
            reserve++;
        }
    }

    public void register(RunReserves reserves) {
        for (Reservation reservation : this.reservations) {
            if (reservation.reserved)
                reserves.reservations.add(reservation);
        }
    }
}
