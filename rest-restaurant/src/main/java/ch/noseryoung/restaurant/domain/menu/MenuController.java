package ch.noseryoung.restaurant.domain.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
@Tag(name = "Menus", description = "Endpoints for managing menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "Get all menus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menus retrieved")
    })
    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @Operation(summary = "Get menu by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu found"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })
    @GetMapping("/{id}")
    public Menu getMenuById(@Parameter(description = "Menu id", required = true, example = "550e8400-e29b-41d4-a716-446655440000") @PathVariable UUID id) {
        return menuService.getMenuById(id);
    }

    @Operation(summary = "Create menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menu created"),
            @ApiResponse(responseCode = "400", description = "Invalid menu data")
    })
    @PostMapping
    public Menu createMenu(@Valid @RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @Operation(summary = "Update menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu updated"),
            @ApiResponse(responseCode = "400", description = "Invalid menu data"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })
    @PutMapping("/{id}")
    public Menu updateMenu(@Parameter(description = "Menu id", required = true, example = "550e8400-e29b-41d4-a716-446655440000") @PathVariable UUID id, @Valid @RequestBody Menu menu) {
        return menuService.updateMenu(id, menu);
    }

    @Operation(summary = "Delete menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menu deleted"),
            @ApiResponse(responseCode = "404", description = "Menu not found")
    })
    @DeleteMapping("/{id}")
    public void deleteMenu(@Parameter(description = "Menu id", required = true, example = "550e8400-e29b-41d4-a716-446655440000") @PathVariable UUID id) {
        menuService.deleteMenu(id);
    }

}
