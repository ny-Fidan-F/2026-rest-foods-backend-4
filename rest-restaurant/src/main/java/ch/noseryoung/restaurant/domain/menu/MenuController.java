package ch.noseryoung.restaurant.domain.menu;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable UUID id) {
        return menuService.getMenuById(id);
    }

    @PostMapping
    public Menu createMenu(@Valid @RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable UUID id, @Valid @RequestBody Menu menu) {
        return menuService.updateMenu(id, menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable UUID id) {
        menuService.deleteMenu(id);
    }

}
