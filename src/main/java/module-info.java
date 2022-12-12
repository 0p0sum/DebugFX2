module com.example.debugfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.debugfx to javafx.fxml;
    exports com.example.debugfx;
}