package com.example.demo.dao;

import com.example.demo.dao.entity.CourseHarvest;
import com.example.demo.dao.entity.CourseOutline;
import java.util.List;

public interface CourseOutlineMapper {
    int deleteByPrimaryKey(String id);

    int insert(CourseOutline record);

    CourseOutline selectByPrimaryKey(String id);

    List<CourseOutline> selectAll();

    int updateByPrimaryKey(CourseOutline record);


    List<CourseOutline> selectByCourseId(String courseId);
}