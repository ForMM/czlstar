package com.example.demo.dao;

import com.example.demo.dao.entity.CourseHarvest;
import java.util.List;

public interface CourseHarvestMapper {
    int deleteByPrimaryKey(String id);

    int insert(CourseHarvest record);

    CourseHarvest selectByPrimaryKey(String id);

    List<CourseHarvest> selectAll();

    int updateByPrimaryKey(CourseHarvest record);

    List<CourseHarvest> selectByCourseId(String courseId);
}