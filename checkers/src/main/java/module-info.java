module com.Controller {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.Controller to javafx.fxml;
    exports com.Controller;
}
