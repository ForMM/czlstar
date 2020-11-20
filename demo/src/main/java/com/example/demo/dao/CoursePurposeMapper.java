package com.example.demo.dao;

import com.example.demo.dao.entity.CoursePurpose;
import java.util.List;

public interface CoursePurposeMapper {
    int deleteByPrimaryKey(String id);

    int insert(CoursePurpose record);

    CoursePurpose selectByPrimaryKey(String id);

    List<CoursePurpose> selectAll();

    int updateByPrimaryKey(CoursePurpose record);

    CoursePurpose selectByCourseId(String courseId);
}