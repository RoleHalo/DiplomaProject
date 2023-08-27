package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "删除教室表单")
@Data
public class DeleteMeetingRoomByIdsForm {
    @NotEmpty(message = "ids不能为空")
    @Schema(description = "教室ID")
    private Integer[] ids;
}

