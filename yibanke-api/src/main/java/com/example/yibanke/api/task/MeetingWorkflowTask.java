package com.example.yibanke.api.task;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.yibanke.api.db.dao.TbMeetingMapper;
import com.example.yibanke.api.db.dao.TbUserMapper;
import com.example.yibanke.api.exception.YibankeException;
import com.example.yibanke.api.db.dao.TbMeetingMapper;
import com.example.yibanke.api.db.dao.TbUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class MeetingWorkflowTask {
    //注入
    @Autowired
    private TbUserMapper userDao;

    @Autowired
    private TbMeetingMapper meetingDao;

    @Value("${emos.recieveNotify}")
    private String recieveNotify;

    @Value("${emos.code}")
    private String code;

    @Value("${emos.tcode}")
    private String tcode;

    @Value("${workflow.url}")
    private String workflow;

    //线程池开始新线程 实现异步即可
    @Async("AsyncTaskExecutor")
    public void startMeetingWorkflow(String uuid, int creatorId, String title, String date, String start, String meetingType) {
        //查询申请人 名字角色
        HashMap info = userDao.searchUserInfo(creatorId);

        JSONObject json = new JSONObject();
        //值注入
        //接受审批结果的url
        json.set("url", recieveNotify);

        json.set("uuid", uuid);
        json.set("creatorId", creatorId);
        json.set("creatorName", info.get("name").toString());
        json.set("code", code);
        json.set("tcode", tcode);
        json.set("title", title);
        json.set("date", date);
        json.set("start", start);
        json.set("meetingType", meetingType);

        //中文逗号分割！！  后期要注意
        String[] roles = info.get("roles").toString().split("，");
        //判断用户角色是老师还是学办助理
        // 学办助理创建的会议不需要审批，所以不需要查询学办助理userId和老师本人的userId
        if (ArrayUtil.contains(roles, "老师")) {
            //查询老师本人userId
           // Integer managerId = userDao.searchDeptManagerId(creatorId);
            json.set("managerId", creatorId);

            //查询学办助理userId
            Integer gmId = userDao.searchGmId();
            json.set("gmId", gmId);

            //查询参与的人是否为同一个班级
            boolean bool = meetingDao.searchMeetingMembersInSameDept(uuid);
            json.set("sameDept", bool);
        }

        //调用工作流  startMeetingProcess需要返回instanceId和状态码
        String url = workflow + "/workflow/startMeetingProcess";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() == 200) {
            json = JSONUtil.parseObj(resp.body());
            //状态200时 工作流返回instanceId
            String instanceId = json.getStr("instanceId");
            HashMap param = new HashMap();
            param.put("uuid", uuid);
            param.put("instanceId", instanceId);
            //更新数据库里课程记录的instance_id字段
            int row = meetingDao.updateMeetingInstanceId(param);
            if (row != 1) {
                System.out.println("保存课程工作流实例ID失败 异常Json："+json);
                throw new YibankeException("保存课程工作流实例ID失败");
            }
        } else {
            //日志里记录异常信息
            log.error(resp.body());
        }
    }


    @Async("AsyncTaskExecutor")
    public void deleteMeetingApplication(String uuid, String instanceId, String reason) {
        JSONObject json = new JSONObject();
        json.set("uuid", uuid);
        json.set("instanceId", instanceId);
        json.set("code", code);
        json.set("tcode", tcode);
        json.set("type", "会议申请");
        json.set("reason", reason);
       // System.out.println("Json:"+json);
        String url = workflow + "/workflow/deleteProcessById";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() == 200) {
            log.debug("删除了教室申请");
        }
        else{
            log.error(resp.body());
        }
    }

}