package com.studytool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Startup extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainSceneLoader = new FXMLLoader(getClass().getResource("/com/studytool/mainScene.fxml"));
        Parent root = mainSceneLoader.load();

        MainSceneController controller = mainSceneLoader.getController();
        controller.setMainStage(primaryStage);

        primaryStage.setTitle("Study Tool");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}