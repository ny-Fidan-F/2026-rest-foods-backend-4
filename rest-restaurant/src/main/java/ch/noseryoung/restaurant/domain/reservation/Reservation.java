package ch.noseryoung.restaurant.domain.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull(message = "Start date cannot be blank")
    LocalDateTime start;

    @NotNull(message = "End date cannot be blank")
    LocalDateTime end;

    @Positive(message = "Number of people cannot be zero")
    int numberOfPeople;

    @NotBlank(message = "Reservee last name cannot be blank")
    String reserveeLastName;

    @NotBlank(message = "Reservee last name cannot be blank")
    String reserveePhoneNumber;

    @ManyToOne
    /* We have to use JoinColumn because it is an entity relationship */
    @JoinColumn(name = "reservation_table")
    Table table;
}
