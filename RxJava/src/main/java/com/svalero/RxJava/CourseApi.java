package com.svalero.RxJava;

import com.svalero.RxJava.controllers.CourseDetailController;
import com.svalero.RxJava.domain.Course;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class CourseApi extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    URL fxmlLocation = getClass().getResource("/get_courses.fxml");

    if (fxmlLocation == null) {
      throw new RuntimeException("No se encontró el archivo FXML en la ruta especificada.");
    }

    FXMLLoader loader = new FXMLLoader(fxmlLocation);
    Parent root = loader.load();

    primaryStage.setTitle("Course Detail");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
