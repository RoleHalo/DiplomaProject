package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "删除班级表单")
public class DeleteDeptByIdsForm {

    @NotEmpty(message = "ids不能为空")
    @Schema(description = "班级ID")
    private Integer[] ids;
}


