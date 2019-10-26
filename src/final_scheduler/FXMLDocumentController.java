/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_scheduler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
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
    String logFile;
    FileWriter writer;
    BufferedWriter buffWriter;
    
    ResourceBundle bundle;
    
    @FXML
    private void loginButtonPressed(ActionEvent event) throws IOException {
        System.out.println("Success! Username: " + usernameTextField.getText() + " || Password:" + passwordTextField.getText());
        Locale.setDefault(new Locale ("fr"));
        Locale myLocale = Locale.getDefault();
        writer=new FileWriter("log.txt",true);
        buffWriter=new BufferedWriter(writer);

        logFile="log.txt";
        
        if((Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) && !((usernameTextField.getText().equals("test")) && (passwordTextField.getText().equals("test")))){
            recordActivity(true);
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
         recordActivity(true);
         Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
        recordActivity(true);
        
                   
    }
        
 
    }
    
    public void recordActivity( boolean status)
    {
        try 
        {    
        // FileReader reads text files in the default encoding.
            
            if(status)
                buffWriter.write(ZonedDateTime.now(ZoneId.systemDefault())+" test test successful"+'\n');
            else 
                buffWriter.write(ZonedDateTime.now(ZoneId.systemDefault())+" test test failed"+'\n');
        // Always close files.
            buffWriter.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                logFile + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + logFile + "'");      
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Locale.setDefault(new Locale ("fr"));
        bundle = ResourceBundle.getBundle("language_files/language");
//        
        if((Locale.getDefault().getLanguage().equals("fr"))){
            passwordLabel.setText(bundle.getString("password"));
            usernameLabel.setText(bundle.getString("username"));
            loginButton.setText(bundle.getString("login"));
        }
//
//        
    }    
    
}
