module com.Controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    requires java.sql; 

    opens com.Controller to javafx.fxml;
    exports com.Controller;
}
