package ch.noseryoung.restaurant.domain.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, UUID> {
    /* Query made by AI */
    @Query("SELECT t FROM RestaurantTable t WHERE t.id NOT IN " +
       "(SELECT DISTINCT rt.id FROM Reservation r JOIN r.tables rt WHERE r.start < :end AND r.end > :start)")
    List<RestaurantTable> findAvailableTables(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
