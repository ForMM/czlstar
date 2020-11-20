package com.example.demo.dao;

import com.example.demo.dao.entity.CourseObject;
import java.util.List;

public interface CourseObjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(CourseObject record);

    CourseObject selectByPrimaryKey(String id);

    List<CourseObject> selectAll();

    int updateByPrimaryKey(CourseObject record);

    List<CourseObject> selectByCourseId(String courseId);

}