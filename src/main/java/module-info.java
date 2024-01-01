module com.studytool {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.studytool to javafx.fxml;

    exports com.studytool;
}
