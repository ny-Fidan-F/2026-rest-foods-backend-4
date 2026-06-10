package ch.noseryoung.restaurant.domain.reservation;

import ch.noseryoung.restaurant.domain.table.RestaurantTable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(
            description = "Unique reservation id",
            example = "550e8400-e29b-41d4-a716-446655440000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @NotNull(message = "Start date cannot be null")
    @Column(name = "start_time")
    @Schema(description = "Reservation start time", example = "2026-06-10T18:00:00")
    private LocalDateTime start;

    @NotNull(message = "End date cannot be empty")
    @Column(name = "end_time")
    @Schema(description = "Reservation end time", example = "2026-06-10T20:00:00")
    private LocalDateTime end;

    @Positive(message = "Number of people cannot be zero")
    @Schema(description = "Number of guests", example = "4")
    private int numberOfPeople;

    @NotBlank(message = "Reservee last name cannot be blank")
    @Schema(description = "Last name of the person making the reservation", example = "Muster")
    private String reserveeLastName;

    @NotBlank(message = "Reservee phone number cannot be blank")
    @Schema(description = "Contact phone number", example = "+41 79 123 45 67")
    private String reserveePhoneNumber;

    @ManyToMany
    @JoinTable(
        name = "reservation_restaurant_table",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "table_id")
    )
    @Builder.Default
    @Schema(description = "Tables assigned to the reservation")
    private Set<RestaurantTable> tables = new HashSet<>();
}
