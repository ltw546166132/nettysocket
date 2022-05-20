package com.ltw.module.rnsp171.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ltw.common.api.CommonResult;
import com.ltw.common.api.ResultCode;
import com.ltw.module.rnsp171.model.entity.Company;
import com.ltw.module.rnsp171.model.entity.CompanyProject;
import com.ltw.module.rnsp171.model.entity.Department;
import com.ltw.module.rnsp171.service.CompanyProjectService;
import com.ltw.module.rnsp171.service.CompanyService;
import com.ltw.module.rnsp171.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rnsp171")
public class Rnsp171DataController {
    @Resource
    private DepartmentService departmentService;
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyProjectService companyProjectService;


    @GetMapping("/test")
    public CommonResult<Boolean> test(){
        List<Company> companyList = companyService.list();
        if(CollUtil.isNotEmpty(companyList)){
            List<Department> deptList = CollUtil.list(false);
            for (Company company : companyList){
                String corpName = company.getCorpName();
                Long id = company.getId();
                List<Department> list = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getParentId, 0).eq(Department::getPlatformId, id).eq(Department::getDeptPlatform, 1));
                if(CollUtil.isEmpty(list)){
                    Department department = new Department();
                    department.setDeptName(corpName);
                    department.setLevel(1);
                    department.setParentId(0L);
                    department.setPlatformId(id);
                    department.setDeptPlatform(1);
                    deptList.add(department);
                }
            }
            if(CollUtil.isNotEmpty(deptList)){
                departmentService.saveBatch(deptList);
            }
        }
        //导项目端部门
        List<CompanyProject> projectList = companyProjectService.list();
        if(CollUtil.isNotEmpty(projectList)){
            List<Department> deptList = CollUtil.list(false);
            for(CompanyProject project : projectList){
                String corpName = project.getName();
                Long id = project.getId();
                List<Department> list = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getParentId, 0).eq(Department::getPlatformId, id).eq(Department::getDeptPlatform, 2));
                if(CollUtil.isEmpty(list)){
                    Department department = new Department();
                    department.setDeptName(corpName);
                    department.setLevel(1);
                    department.setParentId(0L);
                    department.setPlatformId(id);
                    department.setDeptPlatform(2);
                    deptList.add(department);
                }
            }
            if(CollUtil.isNotEmpty(deptList)){
                departmentService.saveBatch(deptList);
            }
        }
        return CommonResult.success(Boolean.TRUE);
    }

}
