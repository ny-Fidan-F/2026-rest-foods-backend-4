package ch.noseryoung.restaurant.domain.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menus")
@Schema(description = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_id")
    @Schema(description = "Unique menu id", example = "550e8400-e29b-41d4-a716-446655440000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID menuId;

    @NotBlank
    @Column(name = "name")
    @Schema(description = "Menu name", example = "Pizza Margherita", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank
    @Column(name = "description")
    @Schema(description = "Menu description", example = "Classic pizza with tomato sauce and mozzarella", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull
    @Positive
    @Schema(description = "Menu price", example = "14.50", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double price;

    @NotBlank
    @Column(name = "category")
    @Schema(description = "Menu category", example = "Pizza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @NotBlank
    @Column(name = "imgUrl")
    @Schema(description = "Menu image URL", example = "https://example.com/menu.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imgUrl;

    @Column(name = "chefsChoice")
    @Schema(description = "Whether the menu is a chef's choice", example = "true")
    private boolean chefsChoice;
}
