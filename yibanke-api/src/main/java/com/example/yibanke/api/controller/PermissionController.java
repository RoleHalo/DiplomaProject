package com.example.yibanke.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.service.TbPermissionService;
import com.example.yibanke.api.service.TbPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/permission")
@Tag(name="PermissionController",description = "权限Web接口")
public class PermissionController {
    @Autowired
    private TbPermissionService permissionService;

    @GetMapping("/searchAllPermission")
    @Operation(summary = "查询所有权限")
    @SaCheckPermission(value = {"ROOT","ROLE:INSERT","ROLE:UPDATE"},mode = SaMode.OR)
    public R searchAllPermission(){
        ArrayList<HashMap> list=permissionService.searchAllPermission();
        return R.ok().put("list",list);
    }
}
