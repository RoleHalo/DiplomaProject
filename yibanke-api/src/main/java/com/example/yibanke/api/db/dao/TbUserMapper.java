package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Mapper
public interface TbUserMapper {

    public int insert(TbUser record);

    public Set<String> searchUserPermissions(int userId);

    public Integer login(HashMap param);

    public HashMap searchUserSummary(int userId);

    public int updatePassword(HashMap param);

    public HashMap searchById(int userId);

    public ArrayList<HashMap> searchAllUser();

    public ArrayList<HashMap> searchUserByPage(HashMap param);

    public long searchUserCount(HashMap param);

    public int update(HashMap param);

    public int deleteUserByIds(Integer[] ids);


    public HashMap searchUserInfo(int userId);

    public Integer searchDeptManagerId(int id);

    public Integer searchGmId();


    public ArrayList<TbUser> searchAllStudent();

    public ArrayList<HashMap> searchStudentByPage(HashMap param);

    public long searchStudentCount(HashMap param);


    public ArrayList<TbUser> searchAllTeacher();

    public ArrayList<HashMap> searchTeacherByPage(HashMap param);

    public long searchTeacherCount(HashMap param);



    public ArrayList<String> searchUserRoles(int userId);


   public HashMap searchNameAndDept(int userId);
}