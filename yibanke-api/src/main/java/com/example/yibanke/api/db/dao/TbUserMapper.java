package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Mapper
public interface TbUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKeyWithBLOBs(TbUser record);

    int updateByPrimaryKey(TbUser record);


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


}