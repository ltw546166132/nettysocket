package com.ltw.module.rnsp171.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ltw.common.api.CommonResult;
import com.ltw.module.rnsp171.model.entity.*;
import com.ltw.module.rnsp171.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/rnsp171")
public class Rnsp171DataController {
    @Resource
    private DepartmentService departmentService;
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyProjectService companyProjectService;
    @Resource
    private CompanyUserService companyUserService;
    @Resource
    private WorkerAccountService workerAccountService;
    @Resource
    private CompanyAdminService companyAdminService;
    @Resource
    private UserRoleRelService userRoleRelService;
    @Resource
    private UserDeptRelService userDeptRelService;
    @Resource
    private UserThirdpartyTokenService userThirdpartyTokenService;

    @GetMapping("/derive")
    public CommonResult<Boolean> test(){
        log.info("数据导入开始----"+DateUtil.now());
        Date start = new Date();
        //导入企业部门
        List<Company> companyList = companyService.list();
        if(CollUtil.isNotEmpty(companyList)){
            List<Department> list = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getParentId, 0).eq(Department::getDeptPlatform, 1));

            List<Department> deptList = CollUtil.list(false);
            for (Company company : companyList){
                String corpName = company.getCorpName();
                Long id = company.getId();
                if(CollUtil.isNotEmpty(list)){
                    boolean result = list.stream().allMatch(department -> department.getPlatformId().equals(id));
                    if(!result){
                        Department department = new Department();
                        department.setDeptName(corpName);
                        department.setLevel(1);
                        department.setParentId(0L);
                        department.setPlatformId(id);
                        department.setDeptPlatform(1);
                        deptList.add(department);
                    }
                }else{
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
            List<Department> list = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getParentId, 0).eq(Department::getDeptPlatform, 2));

            List<Department> deptList = CollUtil.list(false);
            for(CompanyProject project : projectList){
                String corpName = project.getName();
                Long id = project.getId();
                if(CollUtil.isNotEmpty(list)){
                    boolean result = list.stream().allMatch(department -> department.getPlatformId().equals(id));
                    if(!result){
                        Department department = new Department();
                        department.setDeptName(corpName);
                        department.setLevel(1);
                        department.setParentId(0L);
                        department.setPlatformId(id);
                        department.setDeptPlatform(2);
                        deptList.add(department);
                    }
                }else {
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

        //导入企业管理员
        List<CompanyUser> companyUsers = companyUserService.list();
        List<WorkerAccount> workerAccounts = workerAccountService.list();
        List<CompanyAdmin> companyAdmins = companyAdminService.list(new LambdaQueryWrapper<CompanyAdmin>().eq(CompanyAdmin::getDelFlag, false).eq(CompanyAdmin::getType, "SUPER_ADMIN"));
        //TODO 需要删除所有CompanyAdmind的普通管理员   需要清空整个UserRoleRel表  需要清空整个UserDeptRel表
        companyAdminService.remove(new LambdaQueryWrapper<CompanyAdmin>().eq(CompanyAdmin::getType, "ORDINARY"));
        List<Long> userRoleRelIds = userRoleRelService.list().stream().map(UserRoleRel::getId).collect(Collectors.toList());
        userRoleRelService.removeByIds(userRoleRelIds);
        List<Long> userDeptRelIds = userDeptRelService.list().stream().map(UserDeptRel::getId).collect(Collectors.toList());
        userDeptRelService.removeByIds(userDeptRelIds);
        List<CompanyAdmin> updateCompanyAdminList = CollUtil.list(false);
        List<UserRoleRel> userRoleRels = CollUtil.list(false);
        List<UserDeptRel> userDeptRels = CollUtil.list(false);
        if(CollUtil.isNotEmpty(companyAdmins)){
            List<Department> departments = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getLevel, 1).eq(Department::getParentId, 0).eq(Department::getDeptPlatform, 1));
            for (CompanyAdmin companyAdmin : companyAdmins){
                Long oldUserId = companyAdmin.getUserId();
                Optional<CompanyUser> optionalCompanyUser = companyUsers.stream().filter(companyUser -> companyUser.getId().equals(oldUserId)).findFirst();
                if(optionalCompanyUser.isPresent()){
                    CompanyUser companyUser = optionalCompanyUser.get();
                    String phone = companyUser.getPhone();
                    Optional<WorkerAccount> optionalWorkerAccount = workerAccounts.stream().filter(workerAccount -> StrUtil.equals(phone, workerAccount.getPhone())).findFirst();
                    if(optionalWorkerAccount.isPresent()){
                        WorkerAccount workerAccount = optionalWorkerAccount.get();
                        companyAdmin.setUserId(workerAccount.getId());
                        companyAdmin.setStatus("CURRENT");
                        updateCompanyAdminList.add(companyAdmin);

                        executeCompanyUserId(workerAccount.getId(), companyAdmin.getCorpId(), userRoleRels, userDeptRels, departments);
                    }else{
                        //如果再workerAccount表里没有对应的管理员手机号， 则新增手机号
                        WorkerAccount workerAccount = new WorkerAccount();
                        workerAccount.setPhone(phone);
                        workerAccount.setAccountType(1);
                        boolean result = workerAccountService.save(workerAccount);
                        if(result){
                            companyAdmin.setUserId(workerAccount.getId());
                            companyAdmin.setStatus("CURRENT");
                            updateCompanyAdminList.add(companyAdmin);
                            executeCompanyUserId(workerAccount.getId(), companyAdmin.getCorpId(), userRoleRels, userDeptRels, departments);
                        }
                    }
                }
            }
        }
        if(CollUtil.isNotEmpty(updateCompanyAdminList)){
            companyAdminService.updateBatchById(updateCompanyAdminList);
        }
        if(CollUtil.isNotEmpty(userRoleRels)){
            userRoleRelService.saveBatch(userRoleRels);
        }
        if(CollUtil.isNotEmpty(userDeptRels)){
            userDeptRelService.saveBatch(userDeptRels);
        }
        log.info("数据导入结束----"+DateUtil.now());
        Date end = new Date();
        long between = DateUtil.between(start, end, DateUnit.SECOND);
        log.info("一共执行"+between+"秒");
        return CommonResult.success(Boolean.TRUE);
    }

    private void executeCompanyUserId(Long userId, Long companyId, List<UserRoleRel> userRoleRels, List<UserDeptRel> userDeptRels, List<Department> departments){
        UserRoleRel userRoleRel = new UserRoleRel();
        userRoleRel.setUserId(userId);
        userRoleRel.setRoleType(1);
        userRoleRel.setRoleId(1L);
        userRoleRel.setRoleRelId(companyId);
        userRoleRel.setRoleCode("ADMIN");
        userRoleRel.setRoleName("企业超级管理员");
        userRoleRels.add(userRoleRel);
        if(CollUtil.isNotEmpty(departments)){
            Optional<Department> departmentOptional = departments.stream().filter(department -> department.getPlatformId().equals(companyId)).findFirst();
            if(departmentOptional.isPresent()){
                UserDeptRel userDeptRel = new UserDeptRel();
                userDeptRel.setUserId(userId);
                userDeptRel.setDeptId(departmentOptional.get().getId());
                userDeptRel.setDeptPlatform(1);
                userDeptRel.setPlatformId(companyId);
                userDeptRels.add(userDeptRel);
            }
        }

    }

    @RequestMapping("/weixindata")
    public CommonResult<Boolean> weixindata(){
        List<CompanyUser> companyUsers = companyUserService.list();
        List<WorkerAccount> workerAccounts = workerAccountService.list();
        List<WorkerAccount> addWorkerAccountList = CollUtil.list(false);
        List<UserThirdpartyToken> addThirdList = CollUtil.list(false);
        if(CollUtil.isNotEmpty(companyUsers)){
            for (CompanyUser companyUser : companyUsers){
                if(StrUtil.isNotBlank(companyUser.getUnionId())){
                    String phone = companyUser.getPhone();
                    Optional<WorkerAccount> optionalWorkerAccount = workerAccounts.stream().filter(workerAccount -> StrUtil.equals(phone, workerAccount.getPhone())).findFirst();
                    if(optionalWorkerAccount.isPresent()){
                        WorkerAccount workerAccount = optionalWorkerAccount.get();
                        Long id = workerAccount.getId();
                        UserThirdpartyToken userThirdpartyToken = new UserThirdpartyToken();
                        userThirdpartyToken.setUserId(id);
                        userThirdpartyToken.setType(1);
                        userThirdpartyToken.setUnionId(companyUser.getUnionId());
                        userThirdpartyToken.setOpenId(companyUser.getOpenId());
                        userThirdpartyToken.setName("微信用户");
                        addThirdList.add(userThirdpartyToken);
                    }else{
                        WorkerAccount workerAccount = new WorkerAccount();
                        workerAccount.setPhone(phone);
                        workerAccountService.save(workerAccount);
                        UserThirdpartyToken userThirdpartyToken = new UserThirdpartyToken();
                        userThirdpartyToken.setUserId(workerAccount.getId());
                        userThirdpartyToken.setType(1);
                        userThirdpartyToken.setUnionId(companyUser.getUnionId());
                        userThirdpartyToken.setOpenId(companyUser.getOpenId());
                        userThirdpartyToken.setName("微信用户");
                        addThirdList.add(userThirdpartyToken);
                    }
                }

            }
        }
        if(CollUtil.isNotEmpty(addThirdList)){
            userThirdpartyTokenService.saveBatch(addThirdList);
        }
        return CommonResult.success(Boolean.TRUE);
    }

}
