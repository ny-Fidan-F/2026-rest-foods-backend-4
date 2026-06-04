package ch.noseryoung.restaurant.domain.reservation;

import ch.noseryoung.restaurant.domain.exceptions.InvalidReservationException;
import ch.noseryoung.restaurant.domain.exceptions.ReservationConflictException;
import ch.noseryoung.restaurant.domain.exceptions.ResourceNotFoundException;
import ch.noseryoung.restaurant.domain.table.RestaurantTable;
import ch.noseryoung.restaurant.domain.table.TableRepository;
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
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
    }

    public List<Reservation> getAllReservations() {
        log.info("Fetching all reservations");
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(UUID id) {
        log.info("Fetching reservation with ID: {}", id);
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " + id + " not found"));
    }

    private void validateReservation(Reservation reservation) {
        validateReservationTimes(reservation.getStart(), reservation.getEnd());

        if (reservation.getTables().isEmpty()) {
            throw new InvalidReservationException("Reservation must be assigned to at least one table");
        }
    }

    private Set<RestaurantTable> getReservationTables(Reservation reservation) {
        Set<RestaurantTable> managedTables = new java.util.HashSet<>();
        for (RestaurantTable table : reservation.getTables()) {

            RestaurantTable managedTable = tableRepository.findById(table.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Table with ID " + table.getId() + " not found"));
            managedTables.add(managedTable);
        }

        return managedTables;
    }

    private Set<UUID> getTableNumbers(Set<RestaurantTable> tables){
        return tables.stream().map(RestaurantTable::getId).collect(Collectors.toSet());
    }

    public Reservation createReservation(Reservation reservation) {
        validateReservation(reservation);

        Set<RestaurantTable> tables = getReservationTables(reservation);
        Set<UUID> tableNumbers  = getTableNumbers(tables);

        log.info("Creating new reservation for reservee: {} at tables: {}", reservation.getReserveeLastName(), tableNumbers);

        validateTablesCapacity(tables, reservation.getNumberOfPeople());
        checkTablesAvailability(tables, reservation.getStart(), reservation.getEnd());

        reservation.setTables(tables);
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(UUID id, Reservation reservationDetails) {
        log.info("Updating reservation with ID: {}", id);
        Reservation existingReservation = getReservationById(id);

        validateReservation(reservationDetails);

        Set<RestaurantTable> tables = getReservationTables(reservationDetails);
        Set<UUID> tableNumbers  = getTableNumbers(tables);

        log.info("Reservation details update: reservee: {} at tables: {}", reservationDetails.getReserveeLastName(), tableNumbers);

        validateTablesCapacity(tables, reservationDetails.getNumberOfPeople());
        checkTablesAvailabilityExcluding(tables, id, reservationDetails.getStart(), reservationDetails.getEnd());

        existingReservation.setStart(reservationDetails.getStart());
        existingReservation.setEnd(reservationDetails.getEnd());
        existingReservation.setNumberOfPeople(reservationDetails.getNumberOfPeople());
        existingReservation.setReserveeLastName(reservationDetails.getReserveeLastName());
        existingReservation.setReserveePhoneNumber(reservationDetails.getReserveePhoneNumber());
        existingReservation.setTables(tables);

        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(UUID id) {
        log.info("Deleting reservation with ID: {}", id);
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }

    private void validateReservationTimes(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new InvalidReservationException("Reservation start and end times cannot be null");
        }
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new InvalidReservationException("Reservation start time must be before end time");
        }
        if (start.isBefore(LocalDateTime.now())) {
            throw new InvalidReservationException("Reservation start time cannot be in the past");
        }
    }

    private void validateTablesCapacity(Set<RestaurantTable> tables, int people) {
        if (people <= 0) {
            throw new InvalidReservationException("Number of people must be positive");
        }
        int totalCapacity = tables.stream().mapToInt(RestaurantTable::getNumSeats).sum();
        if (totalCapacity < people) {
            throw new InvalidReservationException("Selected tables do not have enough total capacity");
        }
    }

    private void checkTablesAvailability(Set<RestaurantTable> tables, LocalDateTime start, LocalDateTime end) {
        for (RestaurantTable table : tables) {
            List<Reservation> conflicts = reservationRepository.findOverlappingReservationsForTable(table.getId(), start, end);
            if (!conflicts.isEmpty()) {
                throw new ReservationConflictException("Table " + table.getId() + " is already reserved during this time");
            }
        }
    }

    private void checkTablesAvailabilityExcluding(java.util.Set<RestaurantTable> tables, UUID reservationId, LocalDateTime start, LocalDateTime end) {
        for (RestaurantTable table : tables) {
            List<Reservation> conflicts = reservationRepository.findOverlappingReservationsExcluding(table.getId(), reservationId, start, end);
            if (!conflicts.isEmpty()) {
                throw new ReservationConflictException("Table " + table.getId() + " is already reserved during this time");
            }
        }
    }
}
