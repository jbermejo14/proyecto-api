package com.svalero.RxJava.controllers;

import com.svalero.RxJava.ErgastApi;
import com.svalero.RxJava.domain.Course;
import com.svalero.RxJava.response.CoursesResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchCourseController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> constructorsListView;

    private List<Course> allCourses = new ArrayList<>();
    private ObservableList<String> displayedCourses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        fetchConstructors();
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterCourse(newVal));

        constructorsListView.setOnMouseClicked(event -> {
            String selectedName = constructorsListView.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                Course selected = allCourses.stream()
                    .filter(d -> d.getTitle().equals(selectedName))
                    .findFirst()
                    .orElse(null);
                if (selected != null) {
                    openConstructorDetail(selected);
                }
            }
        });
    }

    // Fetch drivers from the API
    private void fetchConstructors() {
        ErgastApi api = new Retrofit.Builder()
            .baseUrl("http://3.95.223.106:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ErgastApi.class);

        Observable<List<Course>> observable = Observable.create(emitter -> {
            Call<List<Course>> call = api.getCourses();
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        emitter.onNext(response.body());
                        emitter.onComplete();
                        System.out.println("Cursos recibidos: " + response.body().size());
                    } else {
                        emitter.onError(new Exception("API response was unsuccessful"));
                    }
                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });

        observable.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                response -> Platform.runLater(() -> {
                    allCourses = response;
                    if (allCourses != null && !allCourses.isEmpty()) {
                        displayedCourses.setAll(allCourses.stream()
                            .map(Course::getTitle)
                            .collect(Collectors.toList()));
                        constructorsListView.setItems(displayedCourses);
                    } else {
                        System.out.println("No courses found in the response.");
                    }
                }),
                Throwable::printStackTrace
            );
    }

    private void openConstructorDetail(Course constructor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/course_detail.fxml"));
            Parent root = loader.load();

            CourseDetailController controller = loader.getController();
            controller.setCourse(constructor);

            Stage stage = new Stage();
            stage.setTitle("Course Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Filter drivers based on the search query
    private void filterCourse(String query) {
        if (query == null || query.isBlank()) {
            displayedCourses.setAll(allCourses.stream().map(Course::getTitle).collect(Collectors.toList()));
        } else {
            displayedCourses.setAll(
                allCourses.stream()
                    .filter(constructors -> constructors.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .map(Course::getTitle)
                    .collect(Collectors.toList())
            );
        }
    }
}


