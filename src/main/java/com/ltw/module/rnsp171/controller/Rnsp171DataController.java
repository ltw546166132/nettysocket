package com.ltw.module.rnsp171.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ltw.common.api.CommonResult;
import com.ltw.common.utils.PasswordUtil;
import com.ltw.module.rnsp171.model.dto.CompanyAdminSwitchDTO;
import com.ltw.module.rnsp171.model.dto.ProjectAdminSwitchDTO;
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

    //项目管理员数据源
    static List<ProjectAdminSwitchDTO> switchDTO1 = CollUtil.list(false);
    //企业管理员数据源
    static List<CompanyAdminSwitchDTO> switchDTO2 = CollUtil.list(false);

    static {
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("18858049113").projectId(18L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("18955368527").projectId(22L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13567866800").projectId(29L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("17816382231").projectId(34L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13586823645").projectId(35L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13732150031").projectId(69L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("15800370755").projectId(74L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("15888088180").projectId(40L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13968354226").projectId(46L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("18069248732").projectId(57L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("18069248732").projectId(79L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13586885595").projectId(64L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13586885595").projectId(63L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13586885595").projectId(80L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("18657009333").projectId(54L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13606803765").projectId(52L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13372570222").projectId(66L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("18073847505").projectId(67L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("18800306880").projectId(77L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13906605283").projectId(78L).build());
        switchDTO1.add(ProjectAdminSwitchDTO.builder().phone("13586823645").projectId(83L).build());




        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("18858049113").password("q1111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("13681665047").password("bs123456").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("13567866800").password("hyzx123456").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("17816382231").password("q1111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("18857985200").password("q1111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("15888088180").password("zhang088180").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("18757423821").password("asd123123").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("13858272266").password("q1111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("13586885595").password("yzjs111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("18657009333").password("q1111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("13606803765").password("q1111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("13067851277").password("q1111111").build());
        switchDTO2.add(CompanyAdminSwitchDTO.builder().phone("13067851277").password("q1111111").build());


    }


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
    @Resource
    private ProjectAdminService projectAdminService;

    /**
     * 导入部门
     * @return
     */
    @GetMapping("/switchdepartment")
    public CommonResult<Boolean> switchdepartment(){
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
        log.info("数据导入结束----"+DateUtil.now());
        Date end = new Date();
        long between = DateUtil.between(start, end, DateUnit.SECOND);
        log.info("一共执行"+between+"秒");
        return CommonResult.success(Boolean.TRUE);
    }

    /**
     * 导入企业管理员
     * @return
     */
    @GetMapping("/companyadminswitch")
    public CommonResult<Boolean> test(){
        Date start = new Date();
        //导入企业管理员
        List<CompanyUser> companyUsers = companyUserService.list();
        List<WorkerAccount> workerAccounts = workerAccountService.list();
        List<CompanyAdmin> companyAdmins = companyAdminService.list(new LambdaQueryWrapper<CompanyAdmin>().eq(CompanyAdmin::getDelFlag, false).eq(CompanyAdmin::getType, "SUPER_ADMIN"));
        //TODO 需要删除所有CompanyAdmind的普通管理员   需要清空整个UserRoleRel表  需要清空整个UserDeptRel表
        companyAdminService.remove(new LambdaQueryWrapper<CompanyAdmin>().eq(CompanyAdmin::getType, "ORDINARY"));
        List<Long> userRoleRelIds = userRoleRelService.list().stream().map(UserRoleRel::getId).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(userRoleRelIds)){
            userRoleRelService.removeByIds(userRoleRelIds);
        }
        List<Long> userDeptRelIds = userDeptRelService.list().stream().map(UserDeptRel::getId).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(userDeptRelIds)){
            userDeptRelService.removeByIds(userDeptRelIds);
        }
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

    private void executeProjectUserId(Long userId, Long projectId, List<UserRoleRel> userRoleRels, List<UserDeptRel> userDeptRels, List<Department> departments){
        UserRoleRel userRoleRel = new UserRoleRel();
        userRoleRel.setUserId(userId);
        userRoleRel.setRoleType(2);
        userRoleRel.setRoleId(1L);
        userRoleRel.setRoleRelId(projectId);
        userRoleRel.setRoleCode("ADMIN");
        userRoleRel.setRoleName("项目超级管理员");
        userRoleRels.add(userRoleRel);
        if(CollUtil.isNotEmpty(departments)){
            Optional<Department> departmentOptional = departments.stream().filter(department -> department.getPlatformId().equals(projectId)).findFirst();
            if(departmentOptional.isPresent()){
                UserDeptRel userDeptRel = new UserDeptRel();
                userDeptRel.setUserId(userId);
                userDeptRel.setDeptId(departmentOptional.get().getId());
                userDeptRel.setDeptPlatform(2);
                userDeptRel.setPlatformId(projectId);
                userDeptRels.add(userDeptRel);
            }
        }

    }

    /**
     * 导入微信公众号绑定关系
     * @return
     */
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

    /**
     * 导入项目管理员数据
     * @return
     */
    @GetMapping("/projectadminswitch")
    public CommonResult<Boolean> projectAdminSwitch(){
        Date start = new Date();
        List<UserRoleRel> userRoleRels = CollUtil.list(false);
        List<UserDeptRel> userDeptRels = CollUtil.list(false);
        List<ProjectAdmin> projectAdminAddList = CollUtil.list(false);
        List<ProjectAdmin> projectAdminList = projectAdminService.list();
        List<CompanyProject> projectList = companyProjectService.list();
        List<WorkerAccount> workerAccounts = workerAccountService.list();
        List<Department> departments = departmentService.list(new LambdaQueryWrapper<Department>().eq(Department::getLevel, 1).eq(Department::getParentId, 0).eq(Department::getDeptPlatform, 2));
        if(CollUtil.isNotEmpty(switchDTO1)){
            projectflag : for (ProjectAdminSwitchDTO projectAdminSwitchDTO : switchDTO1){
                Optional<WorkerAccount> optionalWorkerAccount = workerAccounts.stream().filter(workerAccount -> StrUtil.equals(projectAdminSwitchDTO.getPhone(), workerAccount.getPhone())).findFirst();
                if(optionalWorkerAccount.isPresent()){
                    WorkerAccount workerAccount = optionalWorkerAccount.get();
                    for (ProjectAdmin projectAdmin : projectAdminList) {
                        if(projectAdmin.getProjectId().equals(projectAdminSwitchDTO.getProjectId()) && projectAdmin.getUserId().equals(workerAccount.getId())){
                            continue projectflag;
                        }
                    }
                    ProjectAdmin projectAdmin = new ProjectAdmin();
                    projectAdmin.setProjectId(projectAdminSwitchDTO.getProjectId());
                    projectAdmin.setUserId(workerAccount.getId());
                    projectAdmin.setStatus("CURRENT");
                    projectAdmin.setType("SUPER_ADMIN");
                    projectAdmin.setNickName(workerAccount.getNickName());
                    projectAdminAddList.add(projectAdmin);
                    executeProjectUserId(workerAccount.getId(), projectAdminSwitchDTO.getProjectId(), userRoleRels, userDeptRels, departments);
                }else{
                    WorkerAccount workerAccount = new WorkerAccount();
                    workerAccount.setPhone(projectAdminSwitchDTO.getPhone());
                    if(CollUtil.isNotEmpty(projectList)){
                        Optional<CompanyProject> projectOptional = projectList.stream().filter(companyProject -> companyProject.getId().equals(projectAdminSwitchDTO.getProjectId())).findFirst();
                        if(projectOptional.isPresent()){
                            CompanyProject project = projectOptional.get();
                            if(StrUtil.isNotBlank(project.getLoginPassword())){
                                String salt = PasswordUtil.getSalt();
                                workerAccount.setSalt(salt);
                                workerAccount.setPassword(SecureUtil.md5(project.getLoginPassword() + salt));
                            }
                        }
                    }

                    workerAccountService.save(workerAccount);
                    ProjectAdmin projectAdmin = new ProjectAdmin();
                    projectAdmin.setProjectId(projectAdminSwitchDTO.getProjectId());
                    projectAdmin.setUserId(workerAccount.getId());
                    projectAdmin.setStatus("CURRENT");
                    projectAdmin.setType("SUPER_ADMIN");
                    projectAdmin.setNickName(workerAccount.getNickName());
                    projectAdminAddList.add(projectAdmin);
                    executeProjectUserId(workerAccount.getId(), projectAdminSwitchDTO.getProjectId(), userRoleRels, userDeptRels, departments);
                }

            }
            if(CollUtil.isNotEmpty(projectAdminAddList)){
                projectAdminService.saveBatch(projectAdminAddList);
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
        }
        return CommonResult.success(Boolean.TRUE);
    }

    /**
     * 修改企业管理员账号密码
     */
    @GetMapping("/updatecompanypassword")
    public CommonResult<Boolean> updateCompanyPassword(){
        if(CollUtil.isNotEmpty(switchDTO2)){
            List<WorkerAccount> updateAccount = CollUtil.list(false);
            List<WorkerAccount> workerAccounts = workerAccountService.list();
            for (CompanyAdminSwitchDTO companyAdminSwitchDTO : switchDTO2){
                Optional<WorkerAccount> optionalWorkerAccount = workerAccounts.stream().filter(workerAccount -> StrUtil.equals(companyAdminSwitchDTO.getPhone(), workerAccount.getPhone())).findFirst();
                if(optionalWorkerAccount.isPresent()){
                    WorkerAccount workerAccount = optionalWorkerAccount.get();
                    String salt = workerAccount.getSalt();
                    if(StrUtil.isBlank(salt)){
                        salt = PasswordUtil.getSalt();
                        workerAccount.setSalt(salt);
                    }
                    workerAccount.setPassword(SecureUtil.md5(companyAdminSwitchDTO.getPassword() + salt));
                    updateAccount.add(workerAccount);
                }
            }
            if(CollUtil.isNotEmpty(updateAccount)){
                boolean result = workerAccountService.updateBatchById(updateAccount);
                CommonResult.success(result);
            }
        }
        return CommonResult.success(Boolean.FALSE);
    }
}
