module com.Controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    requires java.sql;
    requires com.google.gson;

    opens com.Controller to javafx.fxml;
    
    exports com.GameLogic;

    exports com.Controller;
}
