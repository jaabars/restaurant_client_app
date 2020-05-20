package kg.megacom.restaurantclientapp.models;


import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String address;
    private int houseNo;
    private int flatNo;
    private String phone;
    private String password;

    public Customer() {
    }

}
