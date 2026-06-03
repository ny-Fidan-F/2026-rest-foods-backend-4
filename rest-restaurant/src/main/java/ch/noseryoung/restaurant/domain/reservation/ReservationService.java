package ch.noseryoung.restaurant.domain.reservation;

import ch.noseryoung.restaurant.domain.exceptions.InvalidReservationException;
import ch.noseryoung.restaurant.domain.exceptions.ReservationConflictException;
import ch.noseryoung.restaurant.domain.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    private void validateReservation(Reservation reservation){
        validateReservationTimes(reservation.getStart(), reservation.getEnd());

        if (reservation.getTable() == null) {
            throw new InvalidReservationException("Reservation must be assigned to a table");
        }
    }

    public Reservation createReservation(Reservation reservation) {
        log.info("Creating new reservation for reservee: {}", reservation.getReserveeLastName());
        
        validateReservation(reservation);

        RestaurantTable table = tableRepository.findById(reservation.getTable().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Table with ID " + reservation.getTable().getId() + " not found"));

        validateTableCapacity(table, reservation.getNumberOfPeople());
        checkTableAvailabilityForNewReservation(table.getId(), reservation.getStart(), reservation.getEnd());

        reservation.setTable(table);
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(UUID id, Reservation reservationDetails) {
        log.info("Updating reservation with ID: {}", id);
        Reservation existingReservation = getReservationById(id);

        validateReservation(reservationDetails);

        RestaurantTable table = tableRepository.findById(reservationDetails.getTable().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Table with ID " + reservationDetails.getTable().getId() + " not found"));

        validateTableCapacity(table, reservationDetails.getNumberOfPeople());
        checkTableAvailabilityForExistingReservation(table.getId(), id, reservationDetails.getStart(), reservationDetails.getEnd());

        existingReservation.setStart(reservationDetails.getStart());
        existingReservation.setEnd(reservationDetails.getEnd());
        existingReservation.setNumberOfPeople(reservationDetails.getNumberOfPeople());
        existingReservation.setReserveeLastName(reservationDetails.getReserveeLastName());
        existingReservation.setReserveePhoneNumber(reservationDetails.getReserveePhoneNumber());
        existingReservation.setTable(table);

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
        if (start.isBefore(LocalDateTime.now().minusMinutes(5))) {
            throw new InvalidReservationException("Reservation start time cannot be in the past");
        }
    }

    private void validateTableCapacity(RestaurantTable table, int people) {
        if (people <= 0) {
            throw new InvalidReservationException("Number of people must be positive");
        }
        if (table.getNumSeats() < people) {
            throw new InvalidReservationException("Table capacity (" + table.getNumSeats() + ") is insufficient for " + people + " people");
        }
    }

    private void checkTableAvailabilityForNewReservation(UUID tableId, LocalDateTime start, LocalDateTime end) {
        List<Reservation> conflicts = reservationRepository.findOverlappingReservationsForTable(tableId, start, end);
        if (!conflicts.isEmpty()) {
            throw new ReservationConflictException("Table is already reserved during this time");
        }
    }

    private void checkTableAvailabilityForExistingReservation(UUID tableId, UUID reservationId, LocalDateTime start, LocalDateTime end) {
        List<Reservation> conflicts = reservationRepository.findOverlappingReservationsForTableExcluding(tableId, reservationId, start, end);
        if (!conflicts.isEmpty()) {
            throw new ReservationConflictException("Table is already reserved during this time");
        }
    }
}
