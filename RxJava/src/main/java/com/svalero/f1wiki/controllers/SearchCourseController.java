package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.ErgastApi;
import com.svalero.f1wiki.domain.Course;

import com.svalero.f1wiki.response.CoursesResponse;

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
    private ListView<String> coursesListView;

    private List<Course> allCourses = new ArrayList<>();
    private ObservableList<String> displayedCourses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        fetchCourses();

        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterCourses(newVal));

        coursesListView.setOnMouseClicked(event -> {
            String selectedName = coursesListView.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                Course selected = allCourses.stream()
                        .filter(d -> d.getName().equals(selectedName))
                        .findFirst()
                        .orElse(null);
                if (selected != null) {
                    openCourseDetail(selected);
                }
            }
        });
    }

    // Fetch drivers from the API
    private void fetchCourses() {
        ErgastApi api = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ErgastApi.class);

        Observable<CoursesResponse> observable = Observable.create(emitter -> {
            Call<CoursesResponse> call = api.getCourses();
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        emitter.onNext(response.body());
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Exception("API response was unsuccessful"));
                    }
                }

                @Override
                public void onFailure(Call<CoursesResponse> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        response -> Platform.runLater(() -> {
                            allCourses = response.getMRData().getCourseTable().getCourses();
                            if (allCourses != null && !allCourses.isEmpty()) {
                                displayedCourses.setAll(allCourses.stream()
                                        .map(Course::getName)
                                        .collect(Collectors.toList()));
                                coursesListView.setItems(displayedCourses);
                            } else {
                                System.out.println("No courses found in the response.");
                            }
                        }),
                        Throwable::printStackTrace
                );
    }



    // Filter drivers based on the search query
    private void filterCourses(String query) {
        if (query == null || query.isBlank()) {
            displayedCourses.setAll(allCourses.stream().map(Course::getName).collect(Collectors.toList()));
        } else {
            displayedCourses.setAll(
                    allCourses.stream()
                            .filter(courses -> courses.getName().toLowerCase().contains(query.toLowerCase()))
                            .map(Course::getName)
                            .collect(Collectors.toList())
            );
        }
    }

    private void openCourseDetail(Course course) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/course_detail.fxml"));
            Parent root = loader.load();

            CourseDetailController controller = loader.getController();
            controller.setCourse
                (course);

            Stage stage = new Stage();
            stage.setTitle("Course Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


