package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.controller.dto.course.*;
import com.example.demo.dao.entity.CourseTeacher;
import com.example.demo.dao.entity.CourseVideo;
import org.springframework.web.multipart.MultipartFile;

public interface CourseService {




	public Result uploadCourseImg(MultipartFile file);
	public byte[] loadCourseImg(String uuid);
	public Result addCourse(CourseDto courseDto);
	public Result updateCourse(String account);
	public Result selectCourseById(String courseId);
	public Result selectCourseList();

	public Result courseList(CourseListDto courseListDto);

	/**
	 * 添加学习目标
	 * @param courseDetailDto
	 * @return
	 */
	public Result addCoursePurpose(CourseDetailDto courseDetailDto);

	/**
	 * 添加学习对象
	 * @param courseObjectDto
	 * @return
	 */
	public Result addCourseObject(CourseObjectDto courseObjectDto);

	/**
	 * 添加学习收获
	 * @param courseObjectDto
	 * @return
	 */
	public Result addCourseHarvest(CourseObjectDto courseObjectDto);

	/**
	 * 添加学习大纲
	 * @param courseOutlineDto
	 * @return
	 */
	public Result addCourseOutline(CourseOutlineDto courseOutlineDto);

	/**
	 * 添加学习案例
	 * @param courseCaseDto
	 * @return
	 */
	public Result addCourseCase(CourseCaseDto courseCaseDto);

	public Result addCourseTeacher(CourseTeacherDto courseTeacherDto);
	public Result addCourseVideo(CourseVideo courseVideo);


}
