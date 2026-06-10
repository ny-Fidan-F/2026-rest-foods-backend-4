package ch.noseryoung.restaurant.domain.table;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "restaurant_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "table_id")
    @Schema(
            description = "Unique table id",
            example = "550e8400-e29b-41d4-a716-446655440000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @NotNull
    @Positive(message = "Number of seats must be positive")
    @Column(name = "table_seats")
    @Schema(description = "Number of seats at the table", example = "4")
    private Integer numSeats;
}
