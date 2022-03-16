package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


//保存http请求提交的数据
@Data
@Schema(description = "登陆表单类")
public class LoginForm {
    //内容为空时候返回的信息
    @NotBlank(message = "username不能为空")
    //5-20个字符
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "username内容不正确")

    //@Schema替代Swagger
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "password不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "password内容不正确")
    @Schema(description = "密码")
    private String password;
}

