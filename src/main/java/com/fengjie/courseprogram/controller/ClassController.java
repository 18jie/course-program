package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.model.entity.Class;
import com.fengjie.courseprogram.model.param.ClassParam;
import com.fengjie.courseprogram.server.ClassService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author fengjie
 * @date 2019/5/6 15:38
 */
@Controller
@RequestMapping("/teacher/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("/getClassInfo")
    public @ResponseBody
    RestResponse<Class> getClassInfo(ClassParam classParam){
        Class classById = classService.getClassById(classParam.getClassId());
        if(null != classById){
            return RestResponse.success(classById);
        }
        return RestResponse.fail();
    }

    /**
     * 修改或者新增班级
     * @param classParam
     * @return
     */
    @PostMapping("/saveClass")
    public @ResponseBody RestResponse saveClass(ClassParam classParam){
        Class c = new Class();
        c.setName(classParam.getClassName());
        c.setId(classParam.getClassId());
        int i = classService.saveClass(c);
        if(i == 1){
            return RestResponse.success();
        }
        return RestResponse.fail();
    }


    @PostMapping("/delete")
    public @ResponseBody RestResponse deleteClass(ClassParam classParam){
        int i = classService.deleteClass(classParam.getClassId());
        if(i == 1){
            return RestResponse.success();
        }
        return RestResponse.fail();
    }
}
