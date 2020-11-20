package com.example.demo.controller.dto.course;

import com.example.demo.validator.annotation.StringValidatorAnn;
import lombok.Data;


@Data
public class CourseObjectDto {

    @StringValidatorAnn(notEmpty=true,maxLength = 40)
    private String courseId;

    private String courseObject;

    @StringValidatorAnn(notEmpty=true,maxLength = 1000)
    private String content;

}
