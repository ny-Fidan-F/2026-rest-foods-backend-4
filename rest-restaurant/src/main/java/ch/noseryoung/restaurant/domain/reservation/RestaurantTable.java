package ch.noseryoung.restaurant.domain.reservation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Entity
@Table(name = "restaurant_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "table_id")
    private UUID id;

    @Column(name = "table_number", unique = true, nullable = false)
    private int tableNumber;

    @Column(name = "table_seats", nullable = false)
    private int numSeats;
}
