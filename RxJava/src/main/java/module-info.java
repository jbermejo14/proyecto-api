module com.svalero.f1wiki {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires okio;
    requires io.reactivex.rxjava3;
    requires com.google.gson;

    opens com.svalero.RxJava.response to com.google.gson;
    opens com.svalero.RxJava to javafx.fxml, retrofit2.converter.gson, com.google.gson;

    exports com.svalero.RxJava;
    exports com.svalero.RxJava.domain;
    exports com.svalero.RxJava.table;
    exports com.svalero.RxJava.controllers;
    exports com.svalero.RxJava.response;
    opens com.svalero.RxJava.controllers to javafx.fxml;
    opens com.svalero.RxJava.table to com.google.gson, javafx.fxml, retrofit2.fconverter.gson;
    opens com.svalero.RxJava.domain to com.google.gson, javafx.fxml, retrofit2.converter.gson;
}