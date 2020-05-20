package kg.megacom.restaurantclientapp.controllers.orderCtrl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import kg.megacom.restaurantclientapp.httphelper.dishOkHttpHelper.DishOkHttpHelper;
import kg.megacom.restaurantclientapp.httphelper.menuOkHttpHelper.MenuOkHttpHelper;
import kg.megacom.restaurantclientapp.httphelper.priceOkHttpHelper.PriceOkHttpHelper;
import kg.megacom.restaurantclientapp.models.Dish;
import kg.megacom.restaurantclientapp.models.Menu;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class OrderCreateController {
    List<Dish> dishes = new ArrayList<>();
    List<Double> doubleList = new ArrayList<>();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Menu> comboBoxRestaurant;

    @FXML
    private ComboBox<Dish> comboBoxDishes;

    @FXML
    private Label lblDishName;

    @FXML
    private Label lblDishSize;

    @FXML
    private Label lblDishPrice;

    @FXML
    private ImageView imgViewDish;


    @FXML
    private Spinner<Double> spinnerCount;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<Dish> tblViewDishes;

    @FXML
    private TableColumn<Dish, String> colmnDishName;

    @FXML
    private TableColumn<Dish, Double> colmDishSize;

    @FXML
    private TableColumn<Dish, Double> colmDishCount;

    @FXML
    private TableColumn<Dish, Double> colmDishCost;

    @FXML
    private Label lblSumCost;

    @FXML
    private Button btnSendOrder;

    @FXML
    void onButtonClicked(ActionEvent event) throws IOException {
        if (event.getSource().equals(btnAdd)){
            Dish dish = new Dish();
            dish.setName(lblDishName.getText());
            dish.setSize(Double.parseDouble(lblDishSize.getText()));
           double d = spinnerCount.getValue()*Double.parseDouble(lblDishPrice.getText());
            dish.setCost(d);
            dish.setCount(spinnerCount.getValue());
            dishes.add(dish);
            doubleList.add(dish.getCost());
            double sumCost = doubleList.stream().reduce((a,b)->a+b).get();
            lblSumCost.setText(String.valueOf(sumCost));
            tblViewDishes.setItems(FXCollections.observableArrayList(dishes));
        }

    }


    @FXML
    void onRowClicked(MouseEvent event) {
       tblViewDishes.getSelectionModel().clearSelection();
    }

    @FXML
    void initialize() throws IOException {
        initRestaurants();
        selectRestaurant();
        initSelectedDishFromComboBox();
        initColumns();

    }

    private void initColumns() {
        colmnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colmDishCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        colmDishSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colmDishCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void initRestaurants() throws IOException {
        comboBoxRestaurant.setCellFactory(param -> new ListCell<Menu>(){
            @Override
            protected void updateItem(Menu item, boolean empty){
                super.updateItem(item,empty);
                if(item!=null&&!empty){
                    setText(item.getRestaurant().getName());
                }else {
                    setText(null);
                }
            }

        });
        List<Menu> menuList = MenuOkHttpHelper.getInstance().getMenus();
        comboBoxRestaurant.setItems(FXCollections.observableArrayList(menuList));
    }
    private void selectRestaurant(){
        comboBoxRestaurant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Menu>() {
            @Override
            public void changed(ObservableValue<? extends Menu> observable, Menu oldValue, Menu newValue) {
                try {
                    List<Dish> dishList = DishOkHttpHelper.getInstance().getDishesByMenuId(newValue.getId());
                    initDishComboBox(dishList);
                    comboBoxRestaurant.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initDishComboBox(List<Dish> dishes) {
        comboBoxDishes.setCellFactory(param -> new ListCell<Dish>(){
            @Override
            protected void updateItem(Dish item, boolean empty){
                super.updateItem(item,empty);
                if(item!=null&&!empty){
                    setText(item.getName());
                }else {
                    setText(null);
                }
            }
        });
        comboBoxDishes.setItems(FXCollections.observableArrayList(dishes));

    }
    private void initSelectedDishFromComboBox(){
        comboBoxDishes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Dish>() {
            @SneakyThrows
            @Override
            public void changed(ObservableValue<? extends Dish> observable, Dish oldValue, Dish newValue) {
                lblDishName.setText(newValue.getName());
                lblDishPrice.setText(String.valueOf(PriceOkHttpHelper.getInstance().getPrice(newValue.getId())));
                spinnerCount.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0,20.0,1.0));
                lblDishSize.setText(String.valueOf(newValue.getSize()));
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(newValue.getImgPath()));
                imgViewDish.setImage(new Image(fileChooser.getInitialDirectory().toURI().toString()));

            }
        });

    }
}

