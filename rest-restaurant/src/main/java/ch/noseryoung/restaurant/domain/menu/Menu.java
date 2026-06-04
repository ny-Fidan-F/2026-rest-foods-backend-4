package ch.noseryoung.restaurant.domain.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
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

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    @Size(max = 50, message = "Name must be at most 50 characters")
    @Schema(description = "Menu name", example = "Pizza Margherita", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false)
    @Size(max = 500, message = "Description must be at most 50 characters")
    @Schema(description = "Menu description", example = "Classic pizza with tomato sauce and mozzarella", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(name = "price")
    @DecimalMin("0.01")
    @Schema(description = "Menu price", example = "14.50", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    @Column(name = "category", nullable = false)
    @Size(max = 50, message = "Category must be at most 50 characters")
    @Schema(description = "Menu category", example = "Pizza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @Column(name = "imgUrl")
    @Size(max = 300, message = "Image-Url must be at most 300 characters")
    @URL(message = "Image-Url must be a valid URL")
    @Schema(description = "Menu image URL", example = "https://example.com/menu.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imgUrl;

    @Column(name = "chefsChoice", nullable = false)
    @Schema(description = "Whether the menu is a chef's choice", example = "true")
    private boolean chefsChoice;
}
