package com.example.demo.controller.vo;

import com.example.demo.dao.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class CourseDetailVo {
    private Course course;
    private CoursePurpose coursePurpose;
    private List<CourseObject> courseObjectList;
    private List<CourseHarvest> courseHarvestList;
    private List<CourseOutline> courseOutlineList;
    private List<CourseTeacher> courseTeacherList;
    private List<CourseCase> courseCaseList;
    private List<CourseVideo> courseVideoList;
}
