package com.example.demo.controller.dto.course;

import com.example.demo.validator.annotation.StringValidatorAnn;
import lombok.Data;

import java.io.File;

@Data
public class CourseTeacherDto {

    @StringValidatorAnn(notEmpty=true,maxLength = 40)
    private String courseId;

    private String photo;

    private String name;

    private String profession;

    private String title;

    private String content;

}
