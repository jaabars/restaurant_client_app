package kg.megacom.restaurantclientapp.models;

import lombok.Data;

@Data
public class Dish {
    private Long id;
    private String name;
    private double size;
    private String imgPath;
    private boolean active;
    private Menu menu;
    private double count;
    private double cost;

}
