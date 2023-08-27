package com.example.yibanke.api.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Schema(description = "更新教室表单")
@Data
public class UpdateMeetingRoomForm {
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "")
    @Schema(description = "教室ID")
    private Integer id;

    @NotBlank(message = "name不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,20}$",message = "name内容不正确")
    @Schema(description = "教室名称")
    private String name;

    @NotNull(message = "max不能为空")
    @Range(min = 1, max = 99999, message = "max必须在1~99999之间")
    @Schema(description = "人数上限")
    private String max;

    @Length(max = 20,message = "desc不能超过20个字符")
    @Schema(description = "备注")
    private String desc;

    @NotNull(message = "status不能为空")
    @Range(min = 0,max = 1,message = "status只能是0或者1")
    @Schema(description = "状态")
    private Short status;

}

