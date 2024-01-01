module com.studytool {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.studytool.gui to javafx.fxml;
    opens com.studytool.controllers to javafx.fxml;
    opens com.studytool.model to javafx.fxml;

    exports com.studytool.gui;
    exports com.studytool.controllers;
    exports com.studytool.model;
}
