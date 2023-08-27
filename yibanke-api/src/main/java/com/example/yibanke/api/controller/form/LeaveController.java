package com.example.yibanke.api.controller.form;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.db.pojo.TbLeave;
import com.example.yibanke.api.service.TbLeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

@RestController
@RequestMapping("/leave")
@Tag(name="LeaveController",description = "用户请假Web接口")
public class LeaveController {


    @Autowired
    private TbLeaveService leaveService;

    @PostMapping("/searchLeaveByPage")
    @Operation(summary = "查询请假分页数据")
    @SaCheckLogin
    public R searchLeaveByPage(@Valid @RequestBody SearchLeaveByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start", start);
        // 传入myId便于 请假记录只有本人可以删除
        param.put("myId", StpUtil.getLoginIdAsInt());//getLoginIdAsInt()获取userId
        // 学生只可以查询到自己的请假记录
        if (!(StpUtil.hasPermission("LEAVE:SELECT")||StpUtil.hasPermission("ROOT"))) {
            param.put("userId", StpUtil.getLoginIdAsInt());
        }
        PageUtils pageUtils = leaveService.searchLeaveByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加请假记录")
    @SaCheckLogin
    public R insert(@Valid @RequestBody InsertLeaveForm form) {
        //验证结束时间是不是早于开始时间
        DateTime date_1 = DateUtil.parse(form.getStart());
        DateTime date_2 = DateUtil.parse(form.getEnd());
        if (date_1.isAfterOrEquals(date_2)) {
            return R.error("请假开始时间不能晚于或者等于截止时间");
        }
        //判断当前请假是否跟其他请假有交集
        HashMap param = new HashMap() {{
            put("userId", StpUtil.getLoginIdAsInt());
            put("start", form.getStart());
            put("end", form.getEnd());
        }};
        if (leaveService.searchContradiction(param)) {
            return R.error("当前请假申请与已有请假申请日期上有交集覆盖");
        }
        //计算请假天数
        long hours = date_1.between(date_2, DateUnit.HOUR);
        //天数取整   精确到小数点后1位数
        String days = new BigDecimal(hours).divide(new BigDecimal(24), 1, RoundingMode.CEILING).toString();
        //包含 .0 ，则删去
        if (days.contains(".0")) {
            days = days.replace(".0", "");
        }
        //为0时  强制等于 0.1  便于取整
        if (days.equals("0")) {
            days = "0.1";
        }
        TbLeave leave = JSONUtil.parse(form).toBean(TbLeave.class);
        leave.setUserId(StpUtil.getLoginIdAsInt());
        leave.setDays(days);

        int rows = leaveService.insert(leave);
        return R.ok().put("rows", rows);
    }
}

