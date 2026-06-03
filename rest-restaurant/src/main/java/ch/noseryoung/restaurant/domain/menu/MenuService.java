package ch.noseryoung.restaurant.domain.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(Menu menu) {
        logger.info("Creating menu");
        return menuRepository.save(menu);
    }

    public Menu getMenuById(UUID id) {
        logger.info("Getting menu with id: {}", id);
        return menuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Menu with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found");
                });
    }

    public List<Menu> getAllMenus() {
        logger.info("Getting all menus");
        return menuRepository.findAll();
    }

    public Menu updateMenu(UUID id, Menu updatedMenu) {
        logger.info("Updating menu with id: {}", id);
        Menu existingMenu = getMenuById(id);

        existingMenu.setName(updatedMenu.getName());
        existingMenu.setDescription(updatedMenu.getDescription());
        existingMenu.setPrice(updatedMenu.getPrice());
        existingMenu.setCategory(updatedMenu.getCategory());
        existingMenu.setImgUrl(updatedMenu.getImgUrl());
        existingMenu.setChefsChoice(updatedMenu.isChefsChoice());

        return menuRepository.save(existingMenu);
    }

    public void deleteMenu(UUID id) {
        logger.info("Deleting menu with id: {}", id);
        Menu existingMenu = getMenuById(id);
        menuRepository.delete(existingMenu);
    }


}
