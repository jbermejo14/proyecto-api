package com.svalero.RxJava.domain;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Course {
    @SerializedName("title")

    private String title;
    @SerializedName("description")

    private String description;
    @SerializedName("startDate")

    private Date startDate;
    @SerializedName("active")

    private Boolean active;

    // Getters y setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
