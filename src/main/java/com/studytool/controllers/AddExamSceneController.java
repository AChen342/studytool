package com.studytool.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AddExamSceneController extends MainSceneController {
    @FXML
    private TextField examTitleField;
    @FXML
    private TextField examIdField;
    @FXML
    private Label warnUserLabel;

    private String removeNonIntegers(String str, int caretPosition) {
        int charIdx = caretPosition - 1;
        String editedStr = "";

        if (str.length() > 1 && str != null) {
            if (charIdx == 0) {
                editedStr = str.substring(charIdx + 1);

            } else if (charIdx < str.length() - 1) {
                editedStr = str.substring(0, charIdx) + str.substring(charIdx + 1);

            } else if (charIdx == str.length() - 1) {
                editedStr = str.substring(0, charIdx);
            }
        }

        return editedStr;
    }

    @FXML
    void addNewExam(ActionEvent event) {
        String examTitle;
        int examId;

        warnUserLabel.setVisible(false);

        if (examIdField.getText().isEmpty() || examTitleField.getText().isEmpty()) {
            warnUserLabel.setText("Cannot leave any text field empty!");
            warnUserLabel.setVisible(true);
        } else {
            examTitle = examTitleField.getText();
            examId = Integer.parseInt(examIdField.getText());

            sysMan.addExam(examId, examTitle);
        }
    }

    @FXML
    void checkIfCharacter(KeyEvent event) {
        /*
         * userInput holds whatever the user types into the textfield
         * editedUserInput is the same user input but without characters
         */
        String userInput, editedUserInput;
        int currCaretPos;
        char keyEntered;

        keyEntered = event.getCharacter().charAt(0);

        if (Character.isLetterOrDigit(keyEntered) && !(Character.isDigit(keyEntered))) {
            warnUserLabel.setText("Only numbers allowed!");
            warnUserLabel.setVisible(true);
            currCaretPos = examIdField.getCaretPosition();
            userInput = examIdField.getText();

            editedUserInput = removeNonIntegers(userInput, currCaretPos);
            examIdField.setText(editedUserInput);
            examIdField.positionCaret(currCaretPos - 1);
        } else {
            warnUserLabel.setVisible(false);
        }
    }
}
