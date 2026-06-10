package ch.noseryoung.restaurant.domain.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
@Tag(name = "Menus", description = "Endpoints for managing menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "Get all menus", description = "Retrieves all menus or filters them by category")
    @ApiResponse(
            responseCode = "200",
            description = "Menus retrieved",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Menu.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus(
            @Parameter(description = "Menu category to filter by", example = "Pizza")
            @RequestParam(required = false) String category,
            @Parameter(description = "Whether to return only chef's-choice menus", example = "true")
            @RequestParam(required = false) Boolean chefsChoice) {

        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(menuService.getMenusByCategory(category));
        }

        if (chefsChoice != null) {
            return ResponseEntity.ok(menuService.getMenusByChefsChoice(chefsChoice));
        }

        return ResponseEntity.ok(menuService.getAllMenus());
    }

    @Operation(summary = "Get menu by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Menu found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Menu.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Menu not found",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(
            @Parameter(description = "Menu id", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable("id") UUID id) {

        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @Operation(summary = "Create menu")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Menu created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Menu.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid menu data",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<Menu> createMenu(@Valid @RequestBody Menu menu) {

        Menu createdMenu = menuService.createMenu(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    @Operation(summary = "Update menu")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Menu updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Menu.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid menu data",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Menu not found",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(
            @Parameter(description = "Menu id", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable("id") UUID id,
            @Valid @RequestBody Menu menu) {

        return ResponseEntity.ok(menuService.updateMenu(id, menu));
    }

    @Operation(summary = "Delete menu")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Menu deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Menu not found",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(
            @Parameter(description = "Menu id", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable("id") UUID id) {

        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

}
