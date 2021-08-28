package com.node.objects;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class canvasImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String creator;

    private String nameOfImage;

    private int day_of_the_week;

    public String getNameOfImage() {
        return nameOfImage;
    }

    public void setNameOfImage(String nameOfImage) {
        this.nameOfImage = nameOfImage;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String uuid) {
        this.creator = uuid;
    }

    public int getDay_of_the_week() {
        return day_of_the_week;
    }

    public void setDay_of_the_week(int day_of_the_week) {
        this.day_of_the_week = day_of_the_week;
    }
}
