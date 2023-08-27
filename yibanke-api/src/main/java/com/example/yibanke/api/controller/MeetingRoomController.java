package com.example.yibanke.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.json.JSONUtil;
import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.controller.form.*;
import com.example.yibanke.api.db.pojo.TbMeetingRoom;
import com.example.yibanke.api.service.TbMeetingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;


//JSON格式数据
@RestController

@RequestMapping("/meeting_room")

//SpringDoc调试使用
@Tag(name = "MeetingRoomController",description = "在线课堂管理Web接口")
public class MeetingRoomController {

    @Autowired
    private TbMeetingRoomService meetingRoomService;


    @GetMapping("/searchAllMeetingRoom")
    @Operation(summary = "查询所有在线教室")
    @SaCheckLogin
    public R searchAllMeetingRoom() {
        ArrayList<HashMap> list = meetingRoomService.searchAllMeetingRoom();
        return R.ok().put("list", list);
    }

    @PostMapping("/searchById")
    @Operation(summary = "根据ID查找在线教室")
    @SaCheckPermission(value = {"ROOT", "MEETING_ROOM:SELECT"}, mode = SaMode.OR)
    public R searchById(@Valid @RequestBody SearchMeetingRoomByIdForm form) {
        HashMap map = meetingRoomService.searchById(form.getId());
        return R.ok(map);
    }

    @PostMapping("/searchFreeMeetingRoom")
    @Operation(summary = "查询空闲教室")
    @SaCheckLogin
    public R searchFreeMeetingRoom(@Valid @RequestBody SearchFreeMeetingRoomForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        ArrayList<String> list = meetingRoomService.searchFreeMeetingRoom(param);
        return R.ok().put("list", list);
    }

    @PostMapping("/searchMeetingRoomByPage")
    @Operation(summary = "查询在线教室分页数据")
    @SaCheckLogin
    public R searchMeetingRoomByPage(@Valid @RequestBody SearchMeetingRoomByPageForm form) {
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start", start);
        PageUtils pageUtils = meetingRoomService.searchMeetingRoomByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加在线教室")
    @SaCheckPermission(value = {"ROOT", "MEETING_ROOM:INSERT"}, mode = SaMode.OR)
    public R insert(@Valid @RequestBody InsertMeetingRoomForm form){
        TbMeetingRoom meetingRoom = JSONUtil.parse(form).toBean(TbMeetingRoom.class);
        int rows = meetingRoomService.insert(meetingRoom);
        return R.ok().put("rows",rows);
    }

    @PostMapping("/update")
    @Operation(summary = "修改在线教室")
    @SaCheckPermission(value = {"ROOT", "MEETING_ROOM:UPDATE"}, mode = SaMode.OR)
    public R update(@Valid @RequestBody UpdateMeetingRoomForm form){
        TbMeetingRoom meetingRoom = JSONUtil.parse(form).toBean(TbMeetingRoom.class);
        int rows = meetingRoomService.update(meetingRoom);
        return R.ok().put("rows",rows);
    }

    @PostMapping("/deleteMeetingRoomByIds")
    @Operation(summary = "删除在线教室记录")
    @SaCheckPermission(value = {"ROOT", "MEETING_ROOM:DELETE"}, mode = SaMode.OR)
    public R deleteMeetingRoomByIds(@Valid @RequestBody DeleteUserByIdsForm form){

        int rows = meetingRoomService.deleteMeetingRoomByIds(form.getIds());
        return R.ok().put("rows",rows);
    }

}
