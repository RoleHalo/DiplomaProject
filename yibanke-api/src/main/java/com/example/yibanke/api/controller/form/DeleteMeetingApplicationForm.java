package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Schema(description = "删除课程申请表单")
public class DeleteMeetingApplicationForm {
    @NotNull(message = "id不能为空")
    @Min(value = 1)
    @Schema(description = "id")
    private Long id;

    @NotBlank(message = "uuid不能为空")
    @Schema(description = "uuid")
    private String uuid;

    @NotBlank(message = "instanceId不能为空")
    @Schema(description = "工作流instanceId")
    private String instanceId;

    @NotBlank(message = "原因不能为空")
    @Schema(description = "原因")
    private String reason;

}
