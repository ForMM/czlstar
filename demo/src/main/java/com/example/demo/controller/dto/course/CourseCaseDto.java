package com.example.demo.controller.dto.course;

import com.example.demo.validator.annotation.StringValidatorAnn;
import lombok.Data;


@Data
public class CourseCaseDto {

    @StringValidatorAnn(notEmpty=true,maxLength = 40)
    private String courseId;
    private String title;
    private String uuid;

}
