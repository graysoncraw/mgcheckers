module com.mgcheckers {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mgcheckers to javafx.fxml;
    exports com.mgcheckers;
}
