module com.mgcheckers {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;

    opens com.mgcheckers to javafx.fxml;
    exports com.mgcheckers;
}
