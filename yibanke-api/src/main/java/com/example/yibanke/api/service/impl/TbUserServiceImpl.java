package com.example.yibanke.api.service.impl;



import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.dao.TbUserMapper;

import com.example.yibanke.api.db.pojo.TbUser;
import com.example.yibanke.api.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
*
*/
@Service
public class TbUserServiceImpl implements TbUserService {



    @Autowired
    TbUserMapper userDao;

    @Override
    public Integer login(HashMap param) {
        Integer userId = userDao.login(param);
        return userId;
    }



    @Override
    public HashMap searchUserSummary(int userId) {
        HashMap map = userDao.searchUserSummary(userId);
        return map;
    }

    @Override
    public int updatePassword(HashMap param) {

        //返回修改数据的条数
        int rows = userDao.updatePassword(param);
        return rows;
    }

    @Override
    public HashMap searchById(int userId) {
        HashMap map = userDao.searchById(userId);
        return map;
    }

    @Override
    public ArrayList<HashMap> searchAllUser() {
        ArrayList<HashMap> list = userDao.searchAllUser();
        return list;
    }


    @Override
    public PageUtils searchUserByPage(HashMap param) {
        ArrayList<HashMap> list = userDao.searchUserByPage(param);
        long count = userDao.searchUserCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }

    @Override
    public int insert(TbUser user) {
        int rows = userDao.insert(user);
        return rows;
    }

    @Override
    public int update(HashMap param) {
        int rows = userDao.update(param);
        return rows;
    }

    @Override
    public int deleteUserByIds(Integer[] ids) {
        int rows = userDao.deleteUserByIds(ids);
        return rows;

    }


    @Override
    public ArrayList<String> searchUserRoles(int userId) {
        ArrayList<String> list = userDao.searchUserRoles(userId);
        return list;
    }

    @Override
    public ArrayList<TbUser> searchAllStudent() {

        ArrayList<TbUser> ls = userDao.searchAllStudent();
        ArrayList<TbUser> list = new ArrayList<>();
        for (TbUser stu:ls
             ) {
            if(stu.getRole().contains("2")){
                list.add(stu);
            }

        }

        return list;

    }

    @Override
    public PageUtils searchStudentByPage(HashMap param) {
        ArrayList<HashMap> list = userDao.searchStudentByPage(param);
        long count = userDao.searchStudentCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = userDao.searchUserPermissions(userId);
        return permissions;
    }


    @Override
    public ArrayList<TbUser> searchAllTeacher() {
        ArrayList<TbUser> ls = userDao.searchAllTeacher();
        ArrayList<TbUser> list = new ArrayList<>();
        for (TbUser stu:ls
        ) {
            if(stu.getRole().contains("1")){
                list.add(stu);
            }

        }
        return list;
    }

    @Override
    public PageUtils searchTeacherByPage(HashMap param) {
        ArrayList<HashMap> list = userDao.searchTeacherByPage(param);
        long count = userDao.searchTeacherCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }

    @Override
    public HashMap searchNameAndDept(int userId) {
        HashMap map=userDao.searchNameAndDept(userId);
        return map;
    }

}
