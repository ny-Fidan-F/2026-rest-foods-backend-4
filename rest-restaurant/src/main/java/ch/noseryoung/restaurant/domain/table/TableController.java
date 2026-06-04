package ch.noseryoung.restaurant.domain.table;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tables")
@Tag(name = "Table Management", description = "Endpoints for managing restaurant tables and checking availability")
@Log4j2
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    @Operation(summary = "Get all tables", description = "Retrieves a list of all tables in the restaurant")
    public ResponseEntity<List<RestaurantTable>> getAllTables() {
        log.info("Received request to get all tables");
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a table by ID", description = "Retrieves a single table's details by its unique UUID")
    public ResponseEntity<RestaurantTable> getTableById(@PathVariable UUID id) {
        log.info("Received request to get table: {}", id);
        return ResponseEntity.ok(tableService.getTableById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new table", description = "Adds a new table to the restaurant")
    public ResponseEntity<RestaurantTable> createTable(@Valid @RequestBody RestaurantTable table) {
        log.info("Received request to create table: {}", table.getId());
        return new ResponseEntity<>(tableService.createTable(table), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing table", description = "Modifies table number or seats of an existing table")
    public ResponseEntity<RestaurantTable> updateTable(@PathVariable UUID id, @Valid @RequestBody RestaurantTable tableDetails) {
        log.info("Received request to update table: {}", id);
        return ResponseEntity.ok(tableService.updateTable(id, tableDetails));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a table", description = "Removes a table from the system if it has no associated reservations")
    public ResponseEntity<Void> deleteTable(@PathVariable UUID id) {
        log.info("Received request to delete table: {}", id);
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    @Operation(summary = "Get available tables", description = "Retrieves a list of tables that are not reserved during the specified time window")
    public ResponseEntity<List<RestaurantTable>> getAvailableTables(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("Received request to get available tables from {} to {}", start, end);
        return ResponseEntity.ok(tableService.getAvailableTables(start, end));
    }
}
