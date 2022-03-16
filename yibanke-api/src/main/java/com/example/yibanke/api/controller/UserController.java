package com.example.yibanke.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.controller.form.*;
import com.example.yibanke.api.db.pojo.TbUser;
import com.example.yibanke.api.service.TbUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

//JSON格式数据
@RestController

@RequestMapping("/user")

//SpringDoc调试使用
@Tag(name = "UserController",description = "用户Web接口")
public class UserController {

    @Autowired
    private TbUserService userService;

    //post方法
    @PostMapping("/login")
    //SpringDoc调试使用，即swagger测试需要的注解
    @Operation(summary = "登陆系统")
    //Valid后端验证
    public R login(@Valid @RequestBody LoginForm form){
        //JSON只有在web层有效  service和dao需要转
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        Integer userId = userService.login(param);
        R r = R.ok().put("result",userId != null ? true : false);
        if(userId != null){
            StpUtil.setLoginId(userId);
            Set<String> permissions = userService.searchUserPermissions(userId);
            String token = StpUtil.getTokenInfo().getTokenValue();
            r.put("permissions",permissions).put("token",token);
        }

        return r;
    }

    /**
     * 登陆成功后加载用户的基本信息
     */
    @GetMapping("/loadUserInfo")
    @Operation(summary = "登陆成功后加载用户的基本信息")
    @SaCheckLogin
    public R loadUserInfo() {
        int userId = StpUtil.getLoginIdAsInt();
        HashMap summary = userService.searchUserSummary(userId);

        return R.ok(summary);
    }

    //get方法
    @GetMapping("/logout")
    @Operation(summary = "退出系统")//swagger测试需要的注解
    public R logout() {
        /**开源工具类
         * logout()：
         *  1、把redis缓存的令牌信息抹除
         *  2、浏览器保存的cookie过期
         *  3、默认状态码200
         */
        StpUtil.logout();
        return R.ok();
    }



    @PostMapping("/updatePassword")
    //SaCheckLogin确保必须是用户登录之后！！！
    // 不判断权限，所有用户都可以改自己密码
    @SaCheckLogin
    @Operation(summary = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordForm form) {
        //getLoginIdAsInt():浏览器的cookie的token令牌反向转回userId
        int userId = StpUtil.getLoginIdAsInt();
        HashMap param = new HashMap() {{
            put("userId", userId);
            put("password", form.getPassword());
        }};
        int rows = userService.updatePassword(param);
        return R.ok().put("rows", rows);
    }


    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找用户")
    @SaCheckPermission(value = {"ROOT", "USER:SELECT"}, mode = SaMode.OR)
    public R searchById(@Valid @RequestBody SearchUserByIdForm form) {
        HashMap map = userService.searchById(form.getUserId());
        return R.ok(map);
    }

    @GetMapping("/searchAllUser")
    @Operation(summary = "查询所有用户")
    @SaCheckLogin
    public R searchAllUser() {
        ArrayList<HashMap> list = userService.searchAllUser();
        return R.ok().put("list", list);
    }


    @PostMapping("/searchUserByPage")
    @Operation(summary = "查询用户分页记录")
    @SaCheckPermission(value = {"ROOT","USER_SELECT"},mode = SaMode.OR)
    public R searchBYPage(@Valid @RequestBody SearchUserByPageForm form){

        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1)*length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start",start);
        PageUtils pageUtils = userService.searchUserByPage(param);
        return R.ok().put("page",pageUtils);
    }


    @PostMapping("/insert")
    @SaCheckPermission(value = {"ROOT", "USER:INSERT"}, mode = SaMode.OR)
    @Operation(summary = "添加用户")
    public R insert(@Valid @RequestBody InsertUserForm form) {
        TbUser user = JSONUtil.parse(form).toBean(TbUser.class);
        user.setStatus((byte) 1);
        //Role转换为JSON字符串
        user.setRole(JSONUtil.parseArray(form.getRole()).toString());
        user.setCreateTime(new Date());
        int rows = userService.insert(user);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/update")
    @SaCheckPermission(value = {"ROOT","USER:UPDATE"},mode = SaMode.OR)
    @Operation(summary = "修改用户")
    public R update(@Valid @RequestBody UpdateUserForm form){
        //转为hashmap形式数据  已经包含了role的value  需要replace更新
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.replace("role",JSONUtil.parse(form.getRole()).toString());

        int rows = userService.update(param);
        if(rows == 1){
            StpUtil.logoutByLoginId(form.getUserId());
        }
        return R.ok().put("rows",rows);
    }


    @PostMapping("/deleteUserByIds")
    @SaCheckPermission(value = {"ROOT","USER:DELETE"},mode = SaMode.OR)
    @Operation(summary = "删除用户")
    public R deleteUserByIds(@Valid @RequestBody DeleteUserByIdsForm form){
        Integer userId = StpUtil.getLoginIdAsInt();
        if(ArrayUtil.contains(form.getIds(),userId)){

            return R.error("您不能删除自己的用户");
        }
        int rows = userService.deleteUserByIds(form.getIds());
        if( rows > 0 ){
            for(Integer id : form.getIds()){
                StpUtil.logoutByLoginId(id);
            }
        }
        return R.ok().put("rows",rows);
    }



}
