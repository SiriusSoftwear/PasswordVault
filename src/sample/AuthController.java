package sample;

import com.sun.javaws.progress.Progress;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.siriussoftwear.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lukas on 03.04.2017.
 */
public class AuthController implements Initializable{
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextArea textArea;
    @FXML
    private PasswordField passwordField;
    @FXML
    private void HandlePasswordFieldKeyPressed(KeyEvent event) throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, SQLException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, ClassNotFoundException {
        if(event.getCode().getName()=="Enter"){
            Stage primaryStage=Main.getPrimaryStage();
            primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
            String masterpw=passwordField.getText();
            Auth.userpw=masterpw;
            InstanceLoader instanceLoader= new InstanceLoader();
            Instance instance=instanceLoader.LoadInstance();
            if(instance!=null){
                Parent root = FXMLLoader.load(getClass().getResource("auth_totp.fxml"));
                primaryStage.setResizable(false);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
                Main.instance=instance;
            }
        }
    }
    @FXML
    private void HandleTextAreaKeyPressed(KeyEvent event) throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, SQLException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, ClassNotFoundException {
        if(event.getCode().getName()=="Enter"){
            Stage primaryStage=Main.getPrimaryStage();
            primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
            GoogleAuthenticator gAuth= new GoogleAuthenticator();
            Instance instance=Main.instance;
            int totp=gAuth.getTotpPassword(instance.getTotp());
            int password=Integer.parseInt(textArea.getText().toString());
            if(password==totp){
                Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
                primaryStage.setResizable(false);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long sec=System.currentTimeMillis();
                double per= (double)sec%30000;
                double s=(per)/30000;
                progressIndicator.setProgress(s);

            }
        }, 1000,50);
    }
}
