package MBPackage;

import java.time.LocalDateTime;

/**
 * Created by diogo on 16/03/2018.
 */
public class Booking {

    private String id;
    private String vehicle_id;
    private String first_name;
    private String last_name;
    private LocalDateTime pickup_date;
    private LocalDateTime created_at;
    private LocalDateTime cancelled_at;
    private String cancelled_reason;

    public Booking(String id, String vehicle_id, String first_name, String last_name, LocalDateTime pickup_date, LocalDateTime created_at) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.pickup_date = pickup_date;
        this.created_at = created_at;
    }

    public Booking() {
    }
}
