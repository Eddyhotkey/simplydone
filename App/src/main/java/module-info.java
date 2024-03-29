module com.example.simplydoneapp {
    requires java.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;

    requires okhttp3;

    opens com.example.simplydoneapp to javafx.fxml;
    exports com.example.simplydoneapp;
}