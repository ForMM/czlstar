package com.example.demo.service.impl;

import com.example.demo.common.GlaobalVal;
import com.example.demo.common.Result;
import com.example.demo.controller.dto.course.*;
import com.example.demo.controller.vo.CourseVo;
import com.example.demo.dao.*;
import com.example.demo.dao.entity.*;
import com.example.demo.enums.CourseType;
import com.example.demo.service.CourseService;
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

import javax.annotation.Resource;
import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    private static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Value("${course.path}")
    public String coursePath;

    @Autowired
    public CourseMapper courseMapper;

    @Autowired
    public CoursePurposeMapper coursePurposeMapper;

    @Autowired
    public CourseObjectMapper courseObjectMapper;

    @Autowired
    public CourseHarvestMapper courseHarvestMapper;

    @Autowired
    public CourseOutlineMapper courseOutlineMapper;

    @Autowired
    public CourseCaseMapper courseCaseMapper;

    @Autowired
    public CourseTeacherMapper courseTeacherMapper;

    @Autowired
    public CourseVideoMapper courseVideoMapper;

    @Override
    public Result uploadCourseImg(MultipartFile file) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("md5加密");

        String uuid = UUIDGenerator.getUUID();
        StorageService storageService = new StorageServiceImpl(coursePath);
        FOSClient.setFosService(storageService);

        try {
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
//            byte[] b = new byte[1024];
//            int len = -1;
//            while((len = fis.read(b)) != -1) {
//                bos.write(b, 0, len);
//            }
            storageService.set(file.getBytes(), uuid);
            result.setData(uuid);
        } catch (Exception e) {
            logger.error("上传课程图片异常", e);
            result.setCode(0);
            result.setMsg("上传课程图片失败");
        }
        return result;
    }


    @Override
    public byte[] loadCourseImg(String uuid) {
        StorageService storageService = new StorageServiceImpl(coursePath);
        FOSClient.setFosService(storageService);
        return storageService.get(uuid);
    }

    @Override
    public Result addCourse(CourseDto courseDto) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {
            Course course = new Course();
            course.setTitle(courseDto.getTitle());
            course.setSubTitle(courseDto.getSubTitle());
            course.setPrice(courseDto.getPrice());
            course.setType(courseDto.getType());
            course.setImg(courseDto.getImg());
            course.setBgImg(courseDto.getBgImg());
            course.setStatus(2);
            course.setCreateTime(new Date());
            courseMapper.insert(course);
        } catch (Exception e) {
            logger.info("添加课程失败", e);
            result.setCode(0);
            result.setMsg("添加课程失败");
        }
        return result;
    }


    @Override
    public Result courseList(CourseListDto courseListDto) {

        Result result = new Result();
        Integer page = courseListDto.getPage();
        Integer pageSize = courseListDto.getPageSize();
        String type = courseListDto.getType();
        page = (page == null ? GlaobalVal.DEFAULT_PAGE : page);
        pageSize = pageSize == null ? GlaobalVal.DEFAULT_PAGESIZE : pageSize;

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", type);

        int countByParam = courseMapper.countByParam(param);
        Paginator paginator = new Paginator(0, pageSize);
        paginator.gotoPage(page);
        int pageCount = paginator.calcPageCount(countByParam); // 总页数

        param.put("startRow", paginator.getStartRow());
        param.put("pageSize", paginator.getPageSize());
        List<Course> list = courseMapper.findByParam(param);
        List<CourseVo> courseVos = new ArrayList<CourseVo>();
        if (list != null && list.size() > 0) {
            for (Course course : list) {
                CourseVo courseVo = new CourseVo();
                courseVo.setId(course.getId());
                courseVo.setType(CourseType.getTypeValue(course.getType()));
                courseVo.setTitle(course.getTitle());
                courseVo.setSubTitle(course.getSubTitle());
                courseVo.setImg(course.getImg());
                courseVo.setPrice(course.getPrice());
                courseVos.add(courseVo);
            }

        }


        Map<String, Object> data = new HashMap<String, Object>();
        data.put("dataList", courseVos);
        data.put("page", page);
        data.put("pageSize", pageSize);
        data.put("pageCount", pageCount);
        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    @Override
    public Result updateCourse(String account) {
        return null;
    }

    @Override
    public Result selectCourseById(String courseId) {
        Result result = new Result();
        Course course = courseMapper.selectByPrimaryKey(courseId);
        if (course==null){
            logger.info("course is null");
            result.setCode(0);
            result.setMsg("操作失败");
        }

        CourseVo courseVo = new CourseVo();
        courseVo.setId(course.getId());
        courseVo.setType(CourseType.getTypeValue(course.getType()));
        courseVo.setTitle(course.getTitle());
        courseVo.setSubTitle(course.getSubTitle());
        courseVo.setImg(course.getImg());
        courseVo.setPrice(course.getPrice());






        result.setCode(1);
        result.setMsg("操作成功");
        result.setData(courseVo);
        return result;
    }

    @Override
    public Result selectCourseList() {
        return null;
    }


    @Override
    public Result addCoursePurpose(CourseDetailDto courseDetailDto) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {

            String courseId = courseDetailDto.getCourseId();
            Course course = courseMapper.selectByPrimaryKey(courseId);
            if (course==null){
                result.setCode(0);
                result.setMsg("添加课程目标失败");
            }

            CoursePurpose coursePurpose= new CoursePurpose();
            coursePurpose.setCourseId(courseDetailDto.getCourseId());
            coursePurpose.setName(courseDetailDto.getName());
            coursePurpose.setTitle(courseDetailDto.getTitle());
            coursePurpose.setPhoto(courseDetailDto.getUuid());
            coursePurpose.setContent(courseDetailDto.getContent());
            coursePurpose.setCreateTime(new Date());
            coursePurposeMapper.insert(coursePurpose);
        } catch (Exception e) {
            logger.info("添加课程目标失败", e);
            result.setCode(0);
            result.setMsg("添加课程目标失败");
        }
        return result;
    }

    @Override
    public Result addCourseObject(CourseObjectDto courseObjectDto) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {

            String courseId = courseObjectDto.getCourseId();
            Course course = courseMapper.selectByPrimaryKey(courseId);
            if (course==null){
                result.setCode(0);
                result.setMsg("添加课程目标失败");
            }

            CourseObject courseObject= new CourseObject();
            courseObject.setCourseId(courseObjectDto.getCourseId());
            courseObject.setObject(courseObjectDto.getCourseObject());
            courseObject.setContent(courseObjectDto.getContent());
            courseObject.setCreateTime(new Date());
            courseObjectMapper.insert(courseObject);
        } catch (Exception e) {
            logger.info("添加课程失败", e);
            result.setCode(0);
            result.setMsg("添加课程失败");
        }
        return result;
    }

    @Override
    public Result addCourseHarvest(CourseObjectDto courseObjectDto) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {

            String courseId = courseObjectDto.getCourseId();
            Course course = courseMapper.selectByPrimaryKey(courseId);
            if (course==null){
                result.setCode(0);
                result.setMsg("添加课程目标失败");
            }

            CourseHarvest courseHarvest= new CourseHarvest();
            courseHarvest.setCourseId(courseObjectDto.getCourseId());
            courseHarvest.setObject(courseObjectDto.getCourseObject());
            courseHarvest.setContent(courseObjectDto.getContent());
            courseHarvest.setCreateTime(new Date());
            courseHarvestMapper.insert(courseHarvest);
        } catch (Exception e) {
            logger.info("添加课程失败", e);
            result.setCode(0);
            result.setMsg("添加课程失败");
        }
        return result;
    }

    @Override
    public Result addCourseOutline(CourseOutlineDto courseOutlineDto) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {

            String courseId = courseOutlineDto.getCourseId();
            Course course = courseMapper.selectByPrimaryKey(courseId);
            if (course==null){
                result.setCode(0);
                result.setMsg("添加课程目标失败");
            }

            CourseOutline courseOutline= new CourseOutline();
            courseOutline.setCourseId(courseOutlineDto.getCourseId());
            courseOutline.setCourseIndex(courseOutlineDto.getCourseIndex());
            courseOutline.setTitle(courseOutlineDto.getTitle());
            courseOutline.setContent(courseOutlineDto.getContent());
            courseOutline.setCreateTime(new Date());
            courseOutlineMapper.insert(courseOutline);
        } catch (Exception e) {
            logger.info("添加课程失败", e);
            result.setCode(0);
            result.setMsg("添加课程失败");
        }
        return result;
    }

    @Override
    public Result addCourseCase(CourseCaseDto courseCaseDto) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {

            String courseId = courseCaseDto.getCourseId();
            Course course = courseMapper.selectByPrimaryKey(courseId);
            if (course==null){
                result.setCode(0);
                result.setMsg("添加课程目标失败");
            }

            CourseCase courseCase= new CourseCase();
            courseCase.setCourseId(courseCaseDto.getCourseId());
            courseCase.setTitle(courseCaseDto.getTitle());
            courseCase.setImg(courseCaseDto.getUuid());
            courseCase.setCreateTime(new Date());
            courseCaseMapper.insert(courseCase);
        } catch (Exception e) {
            logger.info("添加课程失败", e);
            result.setCode(0);
            result.setMsg("添加课程失败");
        }
        return result;
    }

    @Override
    public Result addCourseTeacher(CourseTeacherDto courseTeacherDto) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {

            String courseId = courseTeacherDto.getCourseId();
            Course course = courseMapper.selectByPrimaryKey(courseId);
            if (course==null){
                result.setCode(0);
                result.setMsg("添加课程目标失败");
            }

            CourseTeacher courseTeacher= new CourseTeacher();
            courseTeacher.setCourseId(courseTeacherDto.getCourseId());
            courseTeacher.setName(courseTeacherDto.getName());
            courseTeacher.setProfession(courseTeacherDto.getProfession());
            courseTeacher.setContent(courseTeacherDto.getContent());
            courseTeacher.setTitle(courseTeacherDto.getTitle());
            courseTeacher.setPhoto(courseTeacherDto.getPhoto());
            courseTeacher.setCreateTime(new Date());
            courseTeacherMapper.insert(courseTeacher);
        } catch (Exception e) {
            logger.info("添加课程导师失败", e);
            result.setCode(0);
            result.setMsg("添加课程导师失败");
        }
        return result;
    }


    @Override
    public Result addCourseVideo(CourseVideo courseVideo) {
        Result result = new Result<>();
        result.setCode(1);
        result.setMsg("添加课程成功");
        try {

            String courseId = courseVideo.getCourseId();
            Course course = courseMapper.selectByPrimaryKey(courseId);
            if (course==null){
                result.setCode(0);
                result.setMsg("添加课程目标失败");
            }
            courseVideo.setCreateTime(new Date());
            courseVideoMapper.insert(courseVideo);
        } catch (Exception e) {
            logger.info("添加课程导师失败", e);
            result.setCode(0);
            result.setMsg("添加课程导师失败");
        }
        return result;
    }
}
