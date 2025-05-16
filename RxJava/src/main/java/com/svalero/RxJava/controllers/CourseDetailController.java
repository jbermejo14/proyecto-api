package com.svalero.RxJava.controllers;

import com.svalero.RxJava.domain.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;

public class CourseDetailController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label activeLabel;

    public void setCourse(Course course) {
        titleLabel.setText(course.getTitle());
        descriptionLabel.setText(course.getDescription());

        if (course.getStartDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            startDateLabel.setText("Start Date: " + dateFormat.format(course.getStartDate()));
        } else {
            startDateLabel.setText("Start Date: N/A");
        }

        activeLabel.setText("Active: " + (course.getActive() != null && course.getActive() ? "Yes" : "No"));
    }
}
