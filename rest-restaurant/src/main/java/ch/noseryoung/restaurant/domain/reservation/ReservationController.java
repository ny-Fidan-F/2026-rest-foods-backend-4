package ch.noseryoung.restaurant.domain.reservation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservation Management", description = "Endpoints for managing customer reservations")
@Log4j2
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @Operation(summary = "Get all reservations", description = "Retrieves a list of all table reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        log.info("Received request to get all reservations");
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a reservation by ID", description = "Retrieves a single reservation's details by its unique UUID")
    public ResponseEntity<Reservation> getReservationById(@PathVariable UUID id) {
        log.info("Received request to get reservation: {}", id);
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new reservation", description = "Books one or more tables if they are available and have enough total capacity")
    public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation) {
        log.info("Received request to create reservation for: {} with tables: {}", reservation.getReserveeLastName(), reservation.getTables());
        return new ResponseEntity<>(reservationService.createReservation(reservation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing reservation", description = "Modifies reservation details, verifying availability and capacity for multiple tables")
    public ResponseEntity<Reservation> updateReservation(@PathVariable UUID id, @Valid @RequestBody Reservation reservationDetails) {
        log.info("Received request to update reservation: {} with tables: {}", id, reservationDetails.getTables());
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationDetails));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel a reservation", description = "Deletes an existing table reservation")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        log.info("Received request to delete reservation: {}", id);
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
