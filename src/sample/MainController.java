package sample;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.h2.store.PageStreamData;
import org.siriussoftwear.DatabaseConnection;
import org.siriussoftwear.PasswordObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Lukas on 03.04.2017.
 */
public class MainController implements Initializable {
    List<String> mylist=new ArrayList<>();
    ListProperty<String> listProperty = new SimpleListProperty<>();
    private static List<PasswordObject>list= new ArrayList<PasswordObject>();
    @FXML
    private Button yes;
    @FXML
    private Button no;
    @FXML
    private javafx.scene.control.Label remove;
    @FXML
    private Label add;
    @FXML
    private Button button;
    @FXML
    private ListView listView;
    @FXML
    private TextArea name;
    @FXML
    private TextArea user;
    @FXML
    private TextArea pw;
    @FXML
    private TextArea notes;
    @FXML
    private Pane add_button;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connection= new DatabaseConnection();
        try {
            List<PasswordObject>list= connection.GetEntries();
            MainController.list=list;
            for(int i = 0;i<list.size();i++){
                mylist.add(list.get(i).GetName(Main.instance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listProperty.set(FXCollections.observableArrayList(mylist));
        listView.itemsProperty().bind(listProperty);
        button.setVisible(false);

    }
    @FXML
    private void MouseClicked() throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, SQLException, ClassNotFoundException {
        int index=listView.getSelectionModel().getSelectedIndex();
        DatabaseConnection conn = new DatabaseConnection();
        List<PasswordObject>list=conn.GetEntries();
        for(int i = 0;i<list.size();i++){
            mylist.add(list.get(i).GetName(Main.instance));
        }
        name.setText(list.get(index).GetName(Main.instance));
        user.setText(list.get(index).GetUser(Main.instance));
        pw.setText(list.get(index).GetPW(Main.instance));
        notes.setText(list.get(index).GetNotes(Main.instance));
        add_button.setVisible(false);
        name.setEditable(false);
        user.setEditable(false);
        pw.setEditable(false);
        notes.setEditable(false);
    }
    @FXML
    private void AddPassword(){
        name.setEditable(true);
        user.setEditable(true);
        pw.setEditable(true);
        notes.setEditable(true);
        name.setText("");
        user.setText("");
        pw.setText("");
        notes.setText("");
        add_button.setVisible(true);
        button.setVisible(true);
    }
    @FXML
    private void InsertPassword() throws BadPaddingException, IOException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, SQLException, ClassNotFoundException {
        if(add_button.isVisible()){
            String na=name.getText();
            String us=user.getText();
            String password=pw.getText();
            String no=notes.getText();
            DatabaseConnection conn= new DatabaseConnection();
            conn.InsertPassword(new PasswordObject(Main.instance,na,us,password,no,"NEW"));
            Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
            Stage primaryStage=Main.getPrimaryStage();
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
        }
    }
    @FXML
    private void RemovePassword() throws SQLException, ClassNotFoundException, IOException {
        final int index=listView.getSelectionModel().getSelectedIndex();
        Parent root = FXMLLoader.load(getClass().getResource("conf.fxml"));
        Stage primaryStage=Main.getPrimaryStage();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("icon.png").toExternalForm()));
        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PasswordObject object=list.get(index);
                    DatabaseConnection connection = new DatabaseConnection();
                    connection.DeletePassword(object);
                    Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
                    Stage primaryStage=Main.getPrimaryStage();
                    primaryStage.setResizable(false);
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                    primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("icon.png").toExternalForm()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
                    Stage primaryStage=Main.getPrimaryStage();
                    primaryStage.setResizable(false);
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                    primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResource("icon.png").toExternalForm()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    private void GenerateNewPassword(){
        SecureRandom random= new SecureRandom();
        random.nextInt();
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 100; ++i) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        pw.setText(sb.toString());
        ScrollBar scrollBarv = (ScrollBar)pw.lookup(".scroll-bar:vertical");
        scrollBarv.setDisable(true);
    }
    @FXML
    private void RemoveFocused(){
        remove.setStyle("-fx-background-color:#34515e;" +
                "-fx-cursor:hand;\n" +
                "-fx-font-size:25px;" +
                "-fx-text-fill:white;");
    }
    @FXML
    private void RemoveNotFocused(){
        remove.setStyle("-fx-background-color:#8eacbb;" +
                "-fx-cursor:hand;\n" +
                "-fx-font-size:25px;" +
                "-fx-text-fill:black;");
    }
    @FXML
    private void AddFocused(){
        add.setStyle("-fx-background-color:#34515e;" +
                "-fx-cursor:hand;\n" +
                "-fx-font-size:25px;" +
                "-fx-text-fill:white;");
    }
    @FXML
    private void AddNotFocused(){
        add.setStyle("-fx-background-color:#8eacbb;" +
                "-fx-cursor:hand;\n" +
                "-fx-font-size:25px;" +
                "-fx-text-fill:black;");
    }
}
