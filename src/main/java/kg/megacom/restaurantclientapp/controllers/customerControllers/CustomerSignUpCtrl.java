package kg.megacom.restaurantclientapp.controllers.customerControllers;

import com.squareup.okhttp.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import kg.megacom.restaurantclientapp.httphelper.customerOkHttpHelper.CustomerOkHttpHelper;
import kg.megacom.restaurantclientapp.models.Customer;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerSignUpCtrl  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtHouseNum;

    @FXML
    private TextField txtFlatNum;

    @FXML
    private TextField txtPhone;
    @FXML
    private PasswordField passField;



    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;



    @FXML
    void onButtonClicked(ActionEvent event) throws IOException {
        if(event.getSource().equals(btnSave)){
             Response response = onSaveButtonClicked();
             if (response.isSuccessful()){
                 clearTextFields();
                 Stage stage = new Stage();
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/customerSignInForm.fxml"));
                 loader.load();
                 stage.setScene(new Scene(loader.getRoot()));
                 Window window = btnSave.getScene().getWindow();
                 window.hide();
                 stage.show();
             }
        }
             else if (event.getSource().equals(btnCancel)){
                 clearTextFields();

             }

    }

    private void clearTextFields() {
        txtName.clear();
        txtAddress.clear();
        txtFlatNum.clear();
        txtHouseNum.clear();
        txtPhone.clear();
        passField.clear();
    }

    private Response onSaveButtonClicked() throws IOException {
        Customer customer = new Customer();
        customer.setName(txtName.getText());
        customer.setAddress(txtAddress.getText());
        customer.setFlatNo(Integer.parseInt(txtFlatNum.getText()));
        customer.setHouseNo(Integer.parseInt(txtHouseNum.getText()));
        customer.setPhone(txtPhone.getText().trim());
        customer.setPassword(passField.getText().trim());

        Response response = CustomerOkHttpHelper.getInstance().saveCustomer(customer);
        return response;



    }

    @FXML
    void initialize() {


    }



}
