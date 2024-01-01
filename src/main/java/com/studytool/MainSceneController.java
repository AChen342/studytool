package com.studytool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneController {
    private Stage stage;
    private Parent root;
    protected SystemManager sysMan;

    public MainSceneController() {
        sysMan = new SystemManager();
    }

    public void setMainStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void goToMainScene(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/studytool/mainScene.fxml"));
        stage = (Stage) ((Parent) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void goToAddExamScene(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/studytool/addExamScene.fxml"));
        stage = (Stage) ((Parent) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void goToExamList(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/studytool/examListScene.fxml"));
        stage = (Stage) ((Parent) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
