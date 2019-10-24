/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author usharifzoda
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Label headerLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    
    @FXML private Button loginButton;
    
    @FXML
    private void loginButtonPressed(ActionEvent event) throws IOException {
        System.out.println("Success! Username: " + usernameTextField.getText() + " || Password:" + passwordTextField.getText());
//        Locale.setDefault(new Locale ("fr"));
        ResourceBundle bundle = ResourceBundle.getBundle("language_files/language");
        
        if(!((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test")))){
//            if(Locale.getDefault().getLanguage().equals("fr")){
//            System.out.println("Login ERROR");
            Alert failedAlert = new Alert(Alert.AlertType.ERROR);
            failedAlert.setTitle(bundle.getString("errorTitle"));
            failedAlert.setContentText(bundle.getString("errorText"));
            failedAlert.showAndWait();   
        }
        else {
            Parent mainViewParent = FXMLLoader.load(getClass().getResource("/FXMLViews/MainView.fxml"));
            Scene mainViewScene = new Scene(mainViewParent);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainViewScene);
            window.show();

        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
