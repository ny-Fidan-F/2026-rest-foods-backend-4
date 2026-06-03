package ch.noseryoung.restaurant.domain.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu getMenuById(UUID id) {
        return menuRepository.findById(id)
                .orElseThrow();
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu updateMenu(UUID id, Menu updatedMenu) {
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
        Menu existingMenu = getMenuById(id);
        menuRepository.delete(existingMenu);
    }


}
