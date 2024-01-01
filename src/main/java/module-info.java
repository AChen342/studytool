module com.studytool {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.studytool.app to javafx.fxml;
    opens com.studytool.controllers to javafx.fxml;
    opens com.studytool.model to javafx.fxml;

    exports com.studytool.app;
    exports com.studytool.controllers;
    exports com.studytool.model;
}
