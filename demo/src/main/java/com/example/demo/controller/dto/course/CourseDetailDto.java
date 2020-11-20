package com.example.demo.controller.dto.course;

import com.example.demo.validator.annotation.StringValidatorAnn;
import lombok.Data;


@Data
public class CourseDetailDto {

    @StringValidatorAnn(notEmpty=true,maxLength = 40)
    private String courseId;

    @StringValidatorAnn(notEmpty=true,maxLength = 40)
    private String name;

    private String title;

    private String courseObject;

    @StringValidatorAnn(notEmpty=true,maxLength = 1000)
    private String content;

    private String uuid;
}
