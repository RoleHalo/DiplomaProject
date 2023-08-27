package com.example.yibanke.api.service.impl;



import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.dao.TbMeetingMapper;
import com.example.yibanke.api.db.pojo.TbMeeting;
import com.example.yibanke.api.exception.YibankeException;
import com.example.yibanke.api.service.TbMeetingService;
import com.example.yibanke.api.task.MeetingWorkflowTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
*
*/
@Service
@Slf4j
public class TbMeetingServiceImpl implements TbMeetingService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbMeetingMapper meetingDao;

    @Autowired
    private MeetingWorkflowTask meetingWorkflowTask;

    @Override
    public PageUtils searchOfflineMeetingByPage(HashMap param) {
        System.out.println("param:" + param);
        ArrayList<HashMap> list = meetingDao.searchOfflineMeetingByPage(param);
        //System.out.println("课程列表："+list);
        long count = meetingDao.searchOfflineMeetingCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        //把meeting字段转换成JSON数组对象
        for (HashMap map : list) {
            String meeting = (String) map.get("meeting");
            //如果Meeting是有效的字符串，就转换成JSON数组对象
            if (meeting != null && meeting.length() > 0) {
                map.replace("meeting", JSONUtil.parseArray(meeting));
            }
        }
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public int insert(TbMeeting meeting) {
        int rows = meetingDao.insert(meeting);
        if (rows != 1) {
            throw new YibankeException("课程添加失败");
        }
        //workTask中处理逻辑
        meetingWorkflowTask.startMeetingWorkflow(meeting.getUuid(),
                Integer.parseInt(String.valueOf(meeting.getCreatorId())),
                meeting.getTitle(),
                String.valueOf(meeting.getDate()), meeting.getStart() + ":00",
                meeting.getType() == 1 ? "线上会议":"线下会议");
        return rows;
    }


    @Override
    public ArrayList<HashMap> searchOfflineMeetingInWeek(HashMap param) {
        ArrayList<HashMap> list = meetingDao.searchOfflineMeetingInWeek(param);
        return list;
    }

    @Override
    public HashMap searchMeetingInfo(short status, long id) {
        //判断正在进行中的会议
        HashMap map;
        //此处代码升级过，正在进行和已经结束的会议都可以查询present和unpresent字段
        if (status == 4||status==5) {
            map = meetingDao.searchCurrentMeetingInfo(id);
        } else {
            map = meetingDao.searchMeetingInfo(id);
        }
        return map;
    }

    @Override
    public int deleteMeetingApplication(HashMap param) {
        Long id = MapUtil.getLong(param,"id");
        String uuid = MapUtil.getStr(param,"uuid");
        String instanceId = MapUtil.getStr(param,"instanceId");

        //查询课程详情 判断会议开始时间是否不足20分钟
        HashMap meeting = meetingDao.searchMeetingById(param);
        String date = MapUtil.getStr(meeting,"date");
        String start = MapUtil.getStr(meeting,"start");
        int status = MapUtil.getInt(meeting,"status");

        boolean isCreator = Boolean.parseBoolean(MapUtil.getStr(meeting,"isCreator"));
        DateTime dateTime = DateUtil.parse(date+" "+start);

        DateTime now = DateUtil.date();
        if(now.isAfterOrEquals(dateTime.offset(DateField.MINUTE,20))){

            throw new YibankeException("距离课程开始不足20分钟，不能删除课程");
        }

        if(!isCreator){

            throw new YibankeException("只有申请人才能删除该课程");
        }

        if(status == 1 || status == 3){

            int rows = meetingDao.deleteMeetingApplication(param);
            if(rows == 1){
                String reason = MapUtil.getStr(param,"reason");
                //instanceid关闭未完成工作流线程
                meetingWorkflowTask.deleteMeetingApplication(uuid,instanceId,reason);
            }

            return rows;
        }else {
            System.out.println("此时的satus为："+status);
            throw new YibankeException("只能删除待审批或者未开始的课程");
        }

    }

    @Override
    public PageUtils searchOnlineMeetingByPage(HashMap param) {
        ArrayList<HashMap> list = meetingDao.searchOnlineMeetingByPage(param);
        long count = meetingDao.searchOnlineMeetingCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public Long searchRoomIdByUUID(String uuid) {
        if (redisTemplate.hasKey(uuid)) {
            Object temp = redisTemplate.opsForValue().get(uuid);
            long roomId = Long.parseLong(temp.toString());
            return roomId;
        }
        return null;
    }

    @Override
    public ArrayList<HashMap> searchOnlineMeetingMembers(HashMap param) {
        ArrayList<HashMap> list = meetingDao.searchOnlineMeetingMembers(param);
        return list;
    }

    @Override
    public boolean searchCanCheckinMeeting(HashMap param) {
        long count = meetingDao.searchCanCheckinMeeting(param);
        return count == 1 ? true : false;
    }

    @Override
    public int updateMeetingPresent(HashMap param) {
        int rows = meetingDao.updateMeetingPresent(param);
        return rows;
    }



}