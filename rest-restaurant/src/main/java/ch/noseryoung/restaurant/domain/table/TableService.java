package ch.noseryoung.restaurant.domain.table;

import ch.noseryoung.restaurant.domain.exceptions.InvalidReservationException;
import ch.noseryoung.restaurant.domain.exceptions.ResourceAlreadyExistsException;
import ch.noseryoung.restaurant.domain.exceptions.ResourceNotFoundException;
import ch.noseryoung.restaurant.domain.reservation.Reservation;
import ch.noseryoung.restaurant.domain.reservation.ReservationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TableService {

    private final TableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public TableService(TableRepository tableRepository, ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RestaurantTable> getAllTables() {
        log.info("Fetching all tables");
        return tableRepository.findAll();
    }

    public RestaurantTable getTableById(UUID id) {
        log.info("Fetching table with ID: {}", id);
        return tableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table with ID " + id + " not found"));
    }

    public RestaurantTable createTable(RestaurantTable table) {
        log.info("Creating a new restaurant table");

        return tableRepository.save(table);
    }


    public RestaurantTable updateTable(UUID id, RestaurantTable tableDetails) {
        log.info("Updating table with ID: {}", id);

        RestaurantTable existingTable = getTableById(id);

        existingTable.setNumSeats(tableDetails.getNumSeats());
        return tableRepository.save(existingTable);
    }


    public void deleteTable(UUID id) {
        log.info("Deleting table with ID: {}", id);
        RestaurantTable table = getTableById(id);
        
        if (reservationRepository.existsByTablesId(id)) {
            throw new InvalidReservationException("Cannot delete table because it has associated reservations");
        }
        
        tableRepository.delete(table);
    }

    public List<RestaurantTable> getAvailableTables(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching available tables from {} to {}", start, end);
        if (start == null || end == null) {
            throw new InvalidReservationException("Start and end times must be provided");
        }
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new InvalidReservationException("Start time must be before end time");
        }

        return tableRepository.findAvailableTables(start, end);
    }
}
