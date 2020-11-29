package com.example.demo.dao;

import com.example.demo.dao.entity.CourseVideo;
import java.util.List;

public interface CourseVideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CourseVideo record);

    CourseVideo selectByPrimaryKey(String id);

    List<CourseVideo> selectAll();

    int updateByPrimaryKey(CourseVideo record);

    List<CourseVideo> selectByCourseId(String courseId);

}