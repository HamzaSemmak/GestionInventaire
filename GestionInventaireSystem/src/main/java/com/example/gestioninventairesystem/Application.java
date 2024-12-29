package com.example.gestioninventairesystem;

import com.example.gestioninventairesystem.Controller.UpdateProductController;
import com.example.gestioninventairesystem.Logger.Logger;
import com.example.gestioninventairesystem.Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static User user;

    public static boolean isAuth = false;

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gestion Inventaire");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Logger.info("Start Of Application...");
        launch();
        Logger.info("End Of Application...");
    }

    public static void login(User user) {
        Application.user = user;
        Application.isAuth = true;
        redirectToPage("main-view.fxml");
    }

    public static void redirectToPage(String fxmlFileName) {
        if (mainStage == null) {
            Logger.error("Main stage is not initialized!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlFileName));
            Scene scene = new Scene(fxmlLoader.load());
            mainStage.setScene(scene);
        } catch (IOException e) {
            Logger.error("Error during redirection: " + e.getMessage());
        }
    }

    public static void redirectToPageUpdate(String pageName, int productId) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(pageName));
            Parent root = loader.load();
            UpdateProductController controller = loader.getController();
            controller.setProductId(productId);
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            Logger.error("Error during redirection: " + e.getMessage());
        }
    }



}
