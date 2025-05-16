package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.domain.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CourseDetailController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label nationalityLabel;

    public void setCourse(Course course) {
        nameLabel.setText(course.getName());
        nationalityLabel.setText("Nationality: " + course.getNationality());
    }

}
