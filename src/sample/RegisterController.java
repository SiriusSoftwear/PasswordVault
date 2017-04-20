package sample;


import com.warrenstrange.googleauth.GoogleAuthenticator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.siriussoftwear.Auth;
import org.siriussoftwear.Instance;
import org.siriussoftwear.InstanceLoader;

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

public class RegisterController implements Initializable {
    @FXML
    private Label asdf;
    @FXML
    private TextArea totp;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label label;
    @FXML
    private Label label2;
    @FXML
    private ImageView imageView;
    @FXML
    private javafx.scene.control.TextArea textArea;
    @FXML
    private Button button;

    @FXML
    private void HandleTextAreaKeyPressed(KeyEvent event) throws IOException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, SQLException, NoSuchPaddingException, InvalidKeyException, ClassNotFoundException, InterruptedException {
        if (event.getCode().getName() == "Enter") {
            Stage primaryStage = Main.getPrimaryStage();
            primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
            String masterpw = textArea.getText().toString();
            Auth.userpw = masterpw;
            progressBar.setProgress(-1);
            progressBar.progressProperty().unbind();
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            textArea.setText("");
                            textArea.setEditable(false);
                        }
                    });
                    System.out.println("check1");
                    InstanceLoader instanceLoader = new InstanceLoader();
                    System.out.println("check2");
                    Instance instance = instanceLoader.LoadInstance();
                    System.out.println("check3");
                    Main.instance = instance;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            label.setText("Your code is:");
                            label2.setText(Main.instance.getTotp());
                            button.setVisible(true);
                            textArea.setVisible(false);
                            totp.setVisible(true);
                            asdf.setVisible(true);
                            progressIndicator.setVisible(true);
                        }
                    });
                    updateProgress(1,1);
                    return null;
                }
            };
            progressBar.progressProperty().bind(task.progressProperty());
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long sec=System.currentTimeMillis();
                double per= (double)sec%30000;
                double s=(per)/30000;
                progressIndicator.setProgress(s);

            }
        }, 50,50);
        button.setVisible(false);
    }

    @FXML
    private void MouseClicked() throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("auth_pw.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
    }
    @FXML
    private void CheckTOTP(KeyEvent event) throws IOException {
        if (event.getCode().getName() == "Enter") {
            int code=Integer.parseInt(totp.getText());
            GoogleAuthenticator gAuth= new GoogleAuthenticator();
            Instance instance=Main.instance;
            int totp1=gAuth.getTotpPassword(instance.getTotp());
            if(code==totp1){
                asdf.setText("Correct!");
                asdf.setContentDisplay(ContentDisplay.CENTER);
                totp.setVisible(false);
                progressIndicator.setVisible(false);
                totp.setText("");

            }else{
                System.out.println("False Code!");
                totp.setText("");

            }
        }
    }



}
