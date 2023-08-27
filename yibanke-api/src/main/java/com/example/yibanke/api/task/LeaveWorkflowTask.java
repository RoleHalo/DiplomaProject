package com.example.yibanke.api.task;

import org.springframework.stereotype.Component;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.yibanke.api.db.dao.TbLeaveMapper;
import com.example.yibanke.api.db.dao.TbUserMapper;
import com.example.yibanke.api.exception.YibankeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
@Component
@Slf4j
public class LeaveWorkflowTask {
    @Value("${emos.code}")
    private String code;

    @Value("${emos.tcode}")
    private String tcode;

    @Value("${workflow.url}")
    private String workflow;

    @Value("${emos.recieveNotify}")
    private String recieveNotify;

    @Autowired
    private TbUserMapper userDao;

    @Autowired
    private TbLeaveMapper leaveDao;

    @Async("AsyncTaskExecutor")
    public void startLeaveWorkflow(int id,int creatorId,String days) {
        HashMap info = userDao.searchUserInfo(creatorId);
        System.out.println("Info:"+info);
        JSONObject json = new JSONObject();
        //归档工作流审批意见 log打印到日志
        json.set("url", recieveNotify);
        json.set("creatorId", creatorId);
        json.set("creatorName", info.get("name").toString());
        json.set("code", code);
        json.set("tcode", tcode);
        json.set("title", info.get("dept").toString() + info.get("name").toString() + "的请假");


        ArrayList<String> userRoles = userDao.searchUserRoles(creatorId);
//        System.out.println("userRoles:"+userRoles);
//        boolean bool = userRoles.contains("讲师")||userRoles.contains("学办助理");
//        System.out.println(" userRoles.contains(\"1\")||userRoles.contains(\"4\"):"+bool);

       // json.set("managerId", creatorId);
        if(userRoles.contains("学生")){
            //查班主任老师
            Integer managerId = userDao.searchDeptManagerId(creatorId);
            System.out.println("!!!!!!!!!!!!managerId"+managerId);
            json.set("managerId", managerId);
        }else if(userRoles.contains("老师")||userRoles.contains("超级管理员")){
            //查学办助理
            Integer managerId = userDao.searchGmId();
            System.out.println("!!!!!!!!!!!!managerId"+managerId);
            json.set("managerId", managerId);
        } else{
            //此时为学办助理（测试角色不算！！）
            //无需其他人审批  可自己经打报告后自己留请假及审批即可
            json.set("managerId", creatorId);
            System.out.println("!!!!!!!!!!!!managerId"+creatorId);
        }
        //查询学办助理id
        Integer gmId = userDao.searchGmId();
        json.set("gmId", gmId);
        System.out.println("!!!!!!!!!!!!gmId"+gmId);
        json.set("days", Double.parseDouble(days));

        String url = workflow + "/workflow/startLeaveProcess";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() == 200) {
            json = JSONUtil.parseObj(resp.body());
            String instanceId = json.getStr("instanceId");
            HashMap param = new HashMap();
            param.put("id", id);
            param.put("instanceId", instanceId);
            int row = leaveDao.updateLeaveInstanceId(param);
            if (row != 1) {
                throw new YibankeException("保存请假工作流实例ID失败");
            }
        } else {
            log.error(resp.body());
        }
    }


    //使用异步线程关闭请假审批工作流
    @Async("AsyncTaskExecutor")
    public void deleteLeaveWorkflow(String instanceId, String type, String reason) {
        JSONObject json = new JSONObject();
        json.set("instanceId", instanceId);
        json.set("type", type);
        json.set("reason", reason);
        json.set("code", code);
        json.set("tcode", tcode);
        String url = workflow + "/workflow/deleteProcessById";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() != 200) {
            log.error(resp.body());
            throw new YibankeException("请假工作流删除失败");
        }
    }






}


