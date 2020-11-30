package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.controller.dto.course.*;
import com.example.demo.dao.entity.CourseVideo;
import com.example.demo.log.annotation.LogAnnotation;
import com.example.demo.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/course")
public class CourseController {

    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

	@LogAnnotation
	@RequestMapping(value = "/coursePage", method = RequestMethod.GET)
	public String coursePage(Model model) {
		logger.info("coursePage");
		CourseListDto courseListDto = new CourseListDto();
		courseListDto.setPage(0);
		courseListDto.setPageSize(10);
		Result result = courseService.courseList(courseListDto);
		model.addAttribute("result", result);
		return "manage/course";
	}

    @LogAnnotation
    @RequestMapping(value = "/addCoursePage", method = RequestMethod.GET)
    public String addCoursePage(String name) {
        logger.info("addCoursePage");
        return "manage/add-course";
    }

    @LogAnnotation
    @RequestMapping(value = "/addCourseDetailPage", method = RequestMethod.GET)
    public String addCourseDetailPage(String courseId,Model model) {
        logger.info("addCourseDetailPage");
        model.addAttribute("courseId",courseId);
        return "manage/add-course-detail";
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/uploadCourseImg", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadCourseImg(@RequestParam("file") MultipartFile file) {
        logger.info("filename:{},size:{}", file.getName(), file.getSize());
        return courseService.uploadCourseImg(file);
    }
    
    @LogAnnotation
    @RequestMapping(value = "/uploadCourseBgImg", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadCourseBgImg(@RequestParam("file") MultipartFile file) {
        logger.info("filename:{},size:{}", file.getName(), file.getSize());
        return courseService.uploadCourseImg(file);
    }
    

    /**
     * 获取文件
     * @param uuid
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/loadCourseImg", method = RequestMethod.GET)
    @ResponseBody
    public byte[] loadCourseImg(String uuid) {
        logger.info("uuid:{}", uuid);
        return courseService.loadCourseImg(uuid);
    }

    /**
     * 添加课程
     * @param courseDto
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourse(CourseDto courseDto) {
        logger.info("courseDto:{}", courseDto.toString());
        return courseService.addCourse(courseDto);
    }

    /**
     * 获取课程列表
     * @param courseListDto
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/courseList", method = RequestMethod.POST)
    @ResponseBody
    public Result courseList(CourseListDto courseListDto) {
        logger.info("courseList:{}", courseListDto.toString());
        return courseService.courseList(courseListDto);
    }

    /**
     * 添加课程目标
     * @param courseDetailDto
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/addCoursePurpose", method = RequestMethod.POST)
    @ResponseBody
    public Result addCoursePurpose(CourseDetailDto courseDetailDto) {
        logger.info("addCoursePurpose:{}", courseDetailDto.toString());
        return courseService.addCoursePurpose(courseDetailDto);
    }

    /**
     * 添加课程对象
     * @param courseObjectDto
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/addCourseObject", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseObject(CourseObjectDto courseObjectDto) {
        logger.info("addCourseObject:{}", courseObjectDto.toString());
        return courseService.addCourseObject(courseObjectDto);
    }

    /**
     * 添加课程对象
     * @param courseObjectDto
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/addCourseHarvest", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseHarvest(CourseObjectDto courseObjectDto) {
        logger.info("addCourseHarvest:{}", courseObjectDto.toString());
        return courseService.addCourseHarvest(courseObjectDto);
    }

    /**
     * 添加课程大纲
     * @param courseOutlineDto
     * @return
     */
    @LogAnnotation
    @RequestMapping(value = "/addCourseOutline", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseOutline(CourseOutlineDto courseOutlineDto) {
        logger.info("addCourseOutline:{}", courseOutlineDto.toString());
        return courseService.addCourseOutline(courseOutlineDto);
    }


    @LogAnnotation
    @RequestMapping(value = "/addCourseCase", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseCase(CourseCaseDto courseCaseDto) {
        logger.info("addCourseCase:{}", courseCaseDto.toString());
        return courseService.addCourseCase(courseCaseDto);
    }

    @LogAnnotation
    @RequestMapping(value = "/addCourseTeacher", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseTeacher(CourseTeacherDto courseTeacherDto) {
        logger.info("addCourseTeacher:{}", courseTeacherDto.toString());
        return courseService.addCourseTeacher(courseTeacherDto);
    }

    @LogAnnotation
    @RequestMapping(value = "/addCourseVideo", method = RequestMethod.POST)
    @ResponseBody
    public Result addCourseVideo(CourseVideo video) {
        logger.info("addCourseVideo:{}", video.toString());
        return courseService.addCourseVideo(video);
    }

    @RequestMapping(value = "/getCourseVideo", method = RequestMethod.GET)
    public String getCourseVideo(HttpServletResponse response, String filepath) {
        logger.info("filePath:{}", filepath);
        OutputStream outputStream=null;
        try{
            byte[] bytes = courseService.loadCourseImg(filepath);
            response.setContentType("video/mp4");
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            logger.error("getCourseVideo",e);
        }
        return "";
    }

}
