module com.example.gestioninventairesystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.gestioninventairesystem.Model to javafx.base;

    exports com.example.gestioninventairesystem;
    exports com.example.gestioninventairesystem.Controller to javafx.fxml;
    opens com.example.gestioninventairesystem to javafx.fxml;
    opens com.example.gestioninventairesystem.Controller to javafx.fxml;
}
