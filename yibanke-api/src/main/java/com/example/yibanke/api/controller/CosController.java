package com.example.yibanke.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.controller.form.DeleteCosFileForm;
import com.example.yibanke.api.exception.YibankeException;
import com.example.yibanke.api.oss.CosUtil;
import com.example.yibanke.api.oss.TypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/cos")
@Slf4j
@Tag(name = "CosController", description = "对象存储Web接口")
public class CosController {
    @Autowired
    private CosUtil cosUtil;



    //在这里的校验会导致前端headers中token传值有问题!!   ---token带不上用户信息？？
    //@SaCheckLogin
    @RequestMapping("/uploadCosFile")
    @Operation(summary = "上传文件")
    public R uploadCosFile(@Param("file") MultipartFile file, @Param("type") String type) {
        TypeEnum typeEnum = TypeEnum.findByKey(type);
        if (typeEnum == null) {
            throw new YibankeException("type类型错误");
        }
        try {
            HashMap map=cosUtil.uploadFile(file, typeEnum);
            return R.ok(map);
        } catch (IOException e) {
            log.error("文件上传到腾讯云错误", e);
            throw new YibankeException("文件上传到腾讯云错误");
        }
    }

    @PostMapping("/deleteCosFile")
    @SaCheckLogin
    @Operation(summary = "删除文件")
    public R deleteCosFile(@Valid @RequestBody DeleteCosFileForm form) {
        cosUtil.deleteFile(form.getPathes());
        return R.ok();
    }
}

