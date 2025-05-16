package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.domain.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class StudentDetailController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label nationalityLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label codeLabel;

    public void setStudent(Student student) {
        nameLabel.setText(student.getFullName());
        dobLabel.setText("Date of Birth: " + student.getDateOfBirth());
        nationalityLabel.setText("Nationality: " + student.getNationality());
        numberLabel.setText("Number: " + student.getPermanentNumber());
        codeLabel.setText("Code: " + student.getCode());
    }
}
