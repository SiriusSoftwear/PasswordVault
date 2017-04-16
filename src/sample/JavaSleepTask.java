package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.siriussoftwear.QR;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JavaSleepTask implements RunnableFuture {

    @Override
    public void run() {
        while(true){
            Stage primaryStage=Main.getPrimaryStage();
            try {
                Thread.sleep(1000);
                File f= new File("code.jpg");
                if(f.exists()){
                    System.out.println("hello");
                    Parent root2 = FXMLLoader.load(getClass().getResource("DisplayQRCode.fxml"));
                    primaryStage.setTitle("Hello World");
                    primaryStage.setScene(new Scene(root2));
                    primaryStage.show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
