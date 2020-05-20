package kg.megacom.restaurantclientapp.controllers.customerControllers;

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

import java.io.IOException;

public class CustomerSignInCtrl {

    @FXML
    private TextField txtPhone;

    @FXML
    private PasswordField passField;

    @FXML
    private Button btnEnter;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSignUp;

    @FXML
    void onButtonClicked(ActionEvent event) throws IOException {
        if (event.getSource().equals(btnEnter)){
           int result  = onEnterButtonClicked();
            if (result==1){
                Stage stage = new Stage();
                stage.setTitle("Order create");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/orderCreateForm.fxml"));
                loader.load();
                stage.setScene(new Scene(loader.getRoot()));
                Window window = btnEnter.getScene().getWindow();
                window.hide();
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Такой учетной записи нет");
                alert.show();
            }
        }else if (event.getSource().equals(btnSignUp)){
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/customerSignUpForm.fxml"));
            loader.load();
            stage.setScene(new Scene(loader.getRoot()));
            Window window = btnSignUp.getScene().getWindow();
            window.hide();
            stage.show();
        }else if(event.getSource().equals(btnCancel)){
            txtPhone.clear();
            passField.clear();
        }
    }

    private int onEnterButtonClicked() throws IOException {
        String phone = txtPhone.getText();
        String password = passField.getText();
        System.out.println(phone);
        System.out.println(password);
        int n = CustomerOkHttpHelper.getInstance().getCustomerByPhoneAndPassword(phone,password);
       return n;
    }

}
