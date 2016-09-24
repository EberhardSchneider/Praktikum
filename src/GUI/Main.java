package GUI;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;


/**
 * Created by eberh_000 on 21.09.2016.
 */
public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));

        primaryStage.setTitle("Hello World");

        Scene scene = new Scene(root, 300, 275);

        primaryStage.setScene(scene);

        primaryStage.show();


    }







    public static void main(String[] args) {
        launch(args);
    }
}
