package com.example.demo.service.impl;

import com.example.demo.common.GlaobalVal;
import com.example.demo.common.Result;
import com.example.demo.controller.dto.course.CourseDto;
import com.example.demo.controller.dto.course.CourseListDto;
import com.example.demo.controller.vo.CourseDetailVo;
import com.example.demo.controller.vo.CourseVo;
import com.example.demo.dao.*;
import com.example.demo.dao.entity.*;
import com.example.demo.enums.CourseType;
import com.example.demo.service.CourseService;
import com.example.demo.service.HomeCarouseService;
import com.example.demo.util.Paginator;
import com.example.demo.util.UUIDGenerator;
import com.example.demo.util.storage.FOSClient;
import com.example.demo.util.storage.meituan.StorageService;
import com.example.demo.util.storage.meituan.StorageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class HomeCarouseServiceImpl implements HomeCarouseService {
    private static Logger logger = LoggerFactory.getLogger(HomeCarouseServiceImpl.class);

    @Value("${course.path}carouse-img/")
    public String carousePath;

    @Autowired
    public HomeCarouselImgMapper homeCarouselImgMapper;

    @Autowired
    public CourseMapper courseMapper;

    @Autowired
    public CoursePurposeMapper coursePurposeMapper;

    @Autowired
    public CourseObjectMapper courseObjectMapper;

    @Autowired
    public CourseHarvestMapper courseHarvestMapper;

    @Autowired
    public CourseTeacherMapper courseTeacherMapper;

    @Autowired
    public CourseCaseMapper courseCaseMapper;

    @Autowired
    public CourseOutlineMapper courseOutlineMapper;

    @Override
    public Result uploadCarouseImg(MultipartFile file) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("md5加密");

        String uuid = UUIDGenerator.getUUID();
        StorageService storageService = new StorageServiceImpl(carousePath);
        FOSClient.setFosService(storageService);

        try{
            storageService.set(file.getBytes(),uuid);
            result.setData(uuid);
        }catch (Exception e){
            logger.error("上传首页轮播图片异常",e);
            result.setCode(0);
            result.setMsg("上传首页轮播图片失败");
        }

        HomeCarouselImg homeCarouselImg = new HomeCarouselImg();
        homeCarouselImg.setPath(uuid);
        homeCarouselImg.setCreateTime(new Date());
        homeCarouselImg.setType("");
        homeCarouselImgMapper.insert(homeCarouselImg);
        return result;
    }


    @Override
    public byte[] loadCarouseImg(String uuid) {
        StorageService storageService = new StorageServiceImpl(carousePath);
        FOSClient.setFosService(storageService);
        return storageService.get(uuid);
    }

    @Override
    public Result homeCarouselImgList() {
        Result result = new Result();
        List<HomeCarouselImg> list = homeCarouselImgMapper.selectAll();
        List<String> homeCarouselImgs = new ArrayList<String>();
        if(list!=null&&list.size()>0){
            for(HomeCarouselImg homeCarouselImg:list){
                homeCarouselImgs.add(homeCarouselImg.getPath());
            }
        }
        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(homeCarouselImgs);
        return result;
    }

    @Override
    public Result courseTypeImg(String type) {
        Result result = new Result();
        HomeCarouselImg homeCarouselImg= homeCarouselImgMapper.selectByType(type);
        if(homeCarouselImg ==null){
            result.setCode(0);
            result.setMsg("请添加");
            return result;
        }
        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(homeCarouselImg.getPath());
        return result;
    }

    @Override
    public Result courseDetailList(String courseId) {
        Result result = new Result();
        Course course = courseMapper.selectByPrimaryKey(courseId);
        if(course ==null){
            result.setCode(0);
            result.setMsg("课程为空");
            return result;
        }

        CourseDetailVo courseDetailVo = new CourseDetailVo();
        courseDetailVo.setCourse(course);

        CoursePurpose coursePurpose = coursePurposeMapper.selectByCourseId(course.getId());
        courseDetailVo.setCoursePurpose(coursePurpose);

        List<CourseObject> courseObjects = courseObjectMapper.selectByCourseId(course.getId());
        courseDetailVo.setCourseObjectList(courseObjects);

        List<CourseHarvest> courseHarvests = courseHarvestMapper.selectByCourseId(course.getId());
        courseDetailVo.setCourseHarvestList(courseHarvests);

        List<CourseTeacher> courseTeachers = courseTeacherMapper.selectByCourseId(course.getId());
        courseDetailVo.setCourseTeacherList(courseTeachers);

        List<CourseCase> courseCases = courseCaseMapper.selectByCourseId(course.getId());
        courseDetailVo.setCourseCaseList(courseCases);

        List<CourseOutline> courseOutlines = courseOutlineMapper.selectByCourseId(course.getId());
        courseDetailVo.setCourseOutlineList(courseOutlines);
        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(courseDetailVo);
        return  result;

    }
}
