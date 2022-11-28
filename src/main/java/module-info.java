module com.mgcheckers {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.mgcheckers to javafx.fxml;
    exports com.mgcheckers;
}
