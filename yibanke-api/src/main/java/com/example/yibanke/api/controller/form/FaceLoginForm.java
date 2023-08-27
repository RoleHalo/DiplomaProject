package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

//保存http请求提交的数据
@Data
@Schema(description = "人脸识别登录表单类")
public class FaceLoginForm {
    //内容为空时候返回的信息
    @NotBlank(message = "faceData不能为空")
    //@Schema替代Swagger
    @Schema(description = "人脸图像的base64数据")
    private String faceData;

}

