module cbcrs.sda_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires com.gluonhq.maps;

    opens cbcrs.presentation to javafx.fxml;
    exports cbcrs.presentation;
    exports cbcrs.presentation.controllers;
    exports cbcrs.presentation.viewmodels to javafx.graphics;
    opens cbcrs.presentation.controllers to javafx.fxml;
}