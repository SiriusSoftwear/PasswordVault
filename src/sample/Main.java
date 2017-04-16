package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.siriussoftwear.DatabaseConnection;
import org.siriussoftwear.Instance;

import java.io.File;

public class Main extends Application {
    private static Stage primaryStage;
    static Instance instance;// **Declare static Stage**

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        File f= new File("././config.json");
        if(f.exists()){
            Parent root = FXMLLoader.load(getClass().getResource("auth_pw.fxml"));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            primaryStage.setResizable(false);
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
