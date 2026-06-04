package ch.noseryoung.restaurant;

import ch.noseryoung.restaurant.domain.reservation.Reservation;
import ch.noseryoung.restaurant.domain.reservation.ReservationRepository;
import ch.noseryoung.restaurant.domain.table.RestaurantTable;
import ch.noseryoung.restaurant.domain.table.TableRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Log4j2
public class DataInitializer implements CommandLineRunner {

    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public DataInitializer(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<RestaurantTable> tables;
        if (tableRepository.count() == 0) {
            log.info("No tables found. Initializing realistic test tables...");

            RestaurantTable t1 = RestaurantTable.builder().numSeats(2).build();
            RestaurantTable t2 = RestaurantTable.builder().numSeats(4).build();
            RestaurantTable t3 = RestaurantTable.builder().numSeats(4).build();
            RestaurantTable t4 = RestaurantTable.builder().numSeats(6).build();
            RestaurantTable t5 = RestaurantTable.builder().numSeats(8).build();
            RestaurantTable t6 = RestaurantTable.builder().numSeats(2).build();

            tables = tableRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6));
            log.info("Initialized 6 restaurant tables.");
        } else {
            log.info("Tables already exist in the database.");
            tables = tableRepository.findAll();
        }

        if (reservationRepository.count() == 0 && !tables.isEmpty()) {
            log.info("No reservations found. Initializing sample reservations...");

            RestaurantTable t1 = tables.get(0);
            RestaurantTable t2 = tables.size() > 1 ? tables.get(1) : t1;

            LocalDateTime tomorrow18 = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime tomorrow20 = tomorrow18.plusHours(2);
            
            LocalDateTime tomorrow19 = LocalDateTime.now().plusDays(1).withHour(19).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime tomorrow21 = tomorrow19.plusHours(2);

            Reservation r1 = Reservation.builder()
                    .tables(java.util.Set.of(t1))
                    .start(tomorrow18)
                    .end(tomorrow20)
                    .numberOfPeople(2)
                    .reserveeLastName("Müller")
                    .reserveePhoneNumber("+41 79 123 45 67")
                    .build();

            Reservation r2 = Reservation.builder()
                    .tables(java.util.Set.of(t2))
                    .start(tomorrow19)
                    .end(tomorrow21)
                    .numberOfPeople(4)
                    .reserveeLastName("Schmidt")
                    .reserveePhoneNumber("+41 78 987 65 43")
                    .build();

            reservationRepository.saveAll(List.of(r1, r2));
            log.info("Initialized 2 sample reservations.");
        } else {
            log.info("Reservations already exist or no tables available. Skipping reservation initialization.");
        }
    }
}
