package com.example.demo.dao;

import com.example.demo.dao.entity.CourseCase;

import java.util.List;

public interface CourseCaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(CourseCase record);

    CourseCase selectByPrimaryKey(String id);

    List<CourseCase> selectAll();

    int updateByPrimaryKey(CourseCase record);

    List<CourseCase> selectByCourseId(String courseId);
}