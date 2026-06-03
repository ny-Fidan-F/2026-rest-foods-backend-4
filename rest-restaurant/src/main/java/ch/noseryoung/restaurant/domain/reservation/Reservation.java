package ch.noseryoung.restaurant.domain.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@jakarta.persistence.Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Start date cannot be null")
    @Column(name = "start_time")
    private LocalDateTime start;

    @NotNull(message = "End date cannot be empty")
    @Column(name = "end_time")
    private LocalDateTime end;

    @Positive(message = "Number of people cannot be zero")
    private int numberOfPeople;

    @NotBlank(message = "Reservee last name cannot be blank")
    private String reserveeLastName;

    @NotBlank(message = "Reservee phone number cannot be blank")
    private String reserveePhoneNumber;

    @ManyToOne
    /* We have to use JoinColumn because it is an entity relationship */
    @JoinColumn(name = "reservation_table")
    private RestaurantTable table;
}
