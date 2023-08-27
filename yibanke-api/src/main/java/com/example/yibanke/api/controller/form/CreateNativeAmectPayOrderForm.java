package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "创建Native支付缴费订单表单")
public class CreateNativeAmectPayOrderForm {
    @NotNull(message = "amectId不能为空")
    @Min(value = 1, message = "amectId不能小于1")
    @Schema(description = "缴费单ID")
    private Integer amectId;
}

