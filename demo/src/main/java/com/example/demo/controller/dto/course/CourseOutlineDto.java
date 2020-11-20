package com.example.demo.controller.dto.course;

import com.example.demo.validator.annotation.StringValidatorAnn;
import lombok.Data;


@Data
public class CourseOutlineDto {

    @StringValidatorAnn(notEmpty=true,maxLength = 40)
    private String courseId;

    private String courseIndex;

    private String title;
    private String content;

}
