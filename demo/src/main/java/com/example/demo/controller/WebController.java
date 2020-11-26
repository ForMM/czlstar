package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.controller.dto.course.CourseListDto;
import com.example.demo.controller.vo.CourseVo;
import com.example.demo.controller.vo.HomeCourseVo;
import com.example.demo.enums.CourseType;
import com.example.demo.service.CourseService;
import com.example.demo.service.HomeCarouseService;
import com.example.demo.util.BrowserUtils;
import com.example.demo.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebController {
	
	private static Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired
	private CourseService courseService;

	@Autowired
	private HomeCarouseService homeCarouseService;

	@RequestMapping(value = {"/home","/"},method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request){
		logger.info("enter home");
		String userAgent = request.getHeader("user-agent");
		logger.info("user-agent:{}",userAgent);

		Result cresult = homeCarouseService.homeCarouselImgList();
		if(cresult.getCode()==1){
			model.addAttribute("imgpaths",cresult.getData());
		}

		HomeCourseVo homeCourseVo = new HomeCourseVo();

		CourseListDto courseListDto = new CourseListDto();
		courseListDto.setType(CourseType.AA.getValue());
		courseListDto.setPageSize(6);
		Result result = courseService.courseList(courseListDto);

		if (result.getCode()==1){
			JsonObject jsonObject = JsonUtil.objToJsonObject(result.getData());
			List<CourseVo> dataList = new Gson().fromJson(jsonObject.get("dataList").getAsJsonArray(), new TypeToken<List<CourseVo>>() {
			}.getType());
			homeCourseVo.setCoreCourses(dataList);
		}

		courseListDto.setType(CourseType.BB.getValue());
		courseListDto.setPageSize(4);
		result = courseService.courseList(courseListDto);
		if (result.getCode()==1){
			JsonObject jsonObject = JsonUtil.objToJsonObject(result.getData());
			List<CourseVo> dataList = new Gson().fromJson(jsonObject.get("dataList").getAsJsonArray(), new TypeToken<List<CourseVo>>() {
			}.getType());
			homeCourseVo.setPblCourses(dataList);
		}

		courseListDto.setType(CourseType.CC.getValue());
		courseListDto.setPageSize(3);
		result = courseService.courseList(courseListDto);
		if (result.getCode()==1){
			JsonObject jsonObject = JsonUtil.objToJsonObject(result.getData());
			List<CourseVo> dataList = new Gson().fromJson(jsonObject.get("dataList").getAsJsonArray(), new TypeToken<List<CourseVo>>() {
			}.getType());
			homeCourseVo.setExcellentCourses(dataList);
		}

		if (BrowserUtils.isMobile(userAgent)){
			model.addAttribute("isMobile",true);
		}else {
			model.addAttribute("isMobile",false);
		}

		model.addAttribute("homeCourseVo",homeCourseVo);
		return "home";
	}

	@RequestMapping(value = "/self-course",method = RequestMethod.GET)
	public String selfCourse(Model model,String type,HttpServletRequest request){
		logger.info("selfCourse");
		String userAgent = request.getHeader("user-agent");
		logger.info("user-agent:{}",userAgent);
		if (BrowserUtils.isMobile(userAgent)){
			model.addAttribute("isMobile",true);
		}else {
			model.addAttribute("isMobile",false);
		}
		Result cresult = homeCarouseService.courseTypeImg(type);
		if(cresult.getCode()==1){
			model.addAttribute("imgpaths",cresult.getData());
		}


		HomeCourseVo homeCourseVo = new HomeCourseVo();
		CourseListDto courseListDto = new CourseListDto();
		courseListDto.setType(type);
		Result result = courseService.courseList(courseListDto);

		if (result.getCode()==1){
			JsonObject jsonObject = JsonUtil.objToJsonObject(result.getData());
			List<CourseVo> dataList = new Gson().fromJson(jsonObject.get("dataList").getAsJsonArray(), new TypeToken<List<CourseVo>>() {
			}.getType());
			homeCourseVo.setCoreCourses(dataList);
		}

		model.addAttribute("type",type);
		model.addAttribute("homeCourseVo",homeCourseVo);

		return "self-course";
	}

	@RequestMapping(value = "/self-course-detail",method = RequestMethod.GET)
	public String selfCourseDetail(String id,Model model,HttpServletRequest request){
		logger.info("selfCourseDetail:{}",id);

		String userAgent = request.getHeader("user-agent");
		logger.info("user-agent:{}",userAgent);
		if (BrowserUtils.isMobile(userAgent)){
			model.addAttribute("isMobile",true);
		}else {
			model.addAttribute("isMobile",false);
		}
		Result result = homeCarouseService.courseDetailList(id);
		if (result.getCode()==1){
			model.addAttribute("courseDetail",result.getData());
		}

		return "self-course-detail";
	}
		

	@RequestMapping(value = "/free-course",method = RequestMethod.GET)
	public String freeCourse(Model model,HttpServletRequest request){
		logger.info("freeCourse");
		String userAgent = request.getHeader("user-agent");
		logger.info("user-agent:{}",userAgent);
		if (BrowserUtils.isMobile(userAgent)){
			model.addAttribute("isMobile",true);
		}else {
			model.addAttribute("isMobile",false);
		}
		return "free-course";
	}

	@RequestMapping(value = "/free-course-detail",method = RequestMethod.GET)
	public String freeCourseDetail(Model model,HttpServletRequest request){
		logger.info("freeCourseDetail");
		String userAgent = request.getHeader("user-agent");
		logger.info("user-agent:{}",userAgent);
		if (BrowserUtils.isMobile(userAgent)){
			model.addAttribute("isMobile",true);
		}else {
			model.addAttribute("isMobile",false);
		}
		return "free-course-detail";
	}


}
