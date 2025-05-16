package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.ErgastApi;

import com.svalero.f1wiki.domain.Student;
import com.svalero.f1wiki.response.StudentsResponse;
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

public class SearchStudentsController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> studentsListView;

    private List<Student> allStudents = new ArrayList<>();
    private ObservableList<String> displayedStudents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        fetchStudents();

        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterStudents(newVal));

        studentsListView.setOnMouseClicked(event -> {
            String selectedName = studentsListView.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                Student selected = allStudents.stream()
                        .filter(d -> d.getFullName().equals(selectedName))
                        .findFirst()
                        .orElse(null);
                if (selected != null) {
                    openStudentDetail(selected);
                }
            }
        });
    }

    private void fetchStudents() {
        ErgastApi api = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ErgastApi.class);

        Observable<StudentsResponse> observable = Observable.create(emitter -> {
            Call<StudentsResponse> call = api.getStudents();
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<StudentsResponse> call, Response<StudentsResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        emitter.onNext(response.body());
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Exception("API response was unsuccessful"));
                    }
                }

                @Override
                public void onFailure(Call<StudentsResponse> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        response -> Platform.runLater(() -> {
                            allStudents = response.getMRData().getStudentTable().getStudents();
                            if (allStudents != null && !allStudents.isEmpty()) {
                                displayedStudents.setAll(allStudents.stream()
                                        .map(Student::getFullName)
                                        .collect(Collectors.toList()));
                                studentsListView.setItems(displayedStudents);
                            } else {
                                System.out.println("No students found in the response.");
                            }
                        }),
                        Throwable::printStackTrace
                );
    }



    // Filter students based on the search query
    private void filterStudents(String query) {
        if (query == null || query.isBlank()) {
            displayedStudents.setAll(allStudents.stream().map(Student::getFullName).collect(Collectors.toList()));
        } else {
            displayedStudents.setAll(
                    allStudents.stream()
                            .filter(student -> student.getFullName().toLowerCase().contains(query.toLowerCase()))
                            .map(Student::getFullName)
                            .collect(Collectors.toList())
            );
        }
    }

    private void openStudentDetail(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/student_detail.fxml"));
            Parent root = loader.load();

            StudentDetailController controller = loader.getController();
            controller.setStudent(student);

            Stage stage = new Stage();
            stage.setTitle("Student Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
