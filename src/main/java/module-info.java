module com.example.fx_bankaccount {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.media;


    opens com.example.fx_bankaccount to javafx.fxml;
    exports com.example.fx_bankaccount;
}