package ch.noseryoung.restaurant.domain.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "table_id")
    UUID id;
}
