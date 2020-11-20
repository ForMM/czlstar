package com.example.demo.dao;

import com.example.demo.dao.entity.Kaitouimg;

import java.util.List;

public interface CourseKaitouimgMapper {
    int deleteByPrimaryKey(String id);

    int insert(Kaitouimg record);

    Kaitouimg selectByPrimaryKey(String id);

    List<Kaitouimg> selectAll();

    int updateByPrimaryKey(Kaitouimg record);

    List<Kaitouimg> selectByCourseId(String courseId);
}