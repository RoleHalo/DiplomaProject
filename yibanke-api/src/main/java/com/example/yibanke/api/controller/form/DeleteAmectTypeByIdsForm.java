package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "删除缴费类型记录")
public class DeleteAmectTypeByIdsForm {
    @NotEmpty(message = "ids不能为空")
    @Schema(description = "缴费类型编号")
    private Integer[] ids;
}

