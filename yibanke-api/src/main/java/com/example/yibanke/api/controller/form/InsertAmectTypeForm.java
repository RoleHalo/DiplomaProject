package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "添加缴费类型表单")
public class InsertAmectTypeForm {
    @NotBlank(message = "type不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,10}$", message = "type内容不正确")
    @Schema(description = "缴费类型")
    private String type;

    @NotBlank(message = "money不能为空")
    @Pattern(regexp = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)", message = "money内容不正确")
    @Schema(description = "缴费金额")
    private String money;

}

