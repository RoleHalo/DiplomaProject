package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbLeave;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbLeaveMapper {

    public ArrayList<HashMap> searchLeaveByPage(HashMap param);

    public long searchLeaveCount(HashMap param);

    public int updateLeaveInstanceId(HashMap param);


    public long searchContradiction(HashMap param);

    public int insert(TbLeave leave);


    public String searchInstanceIdById(int id);

    public int deleteLeaveById(HashMap param);


    public HashMap searchLeaveById(HashMap param);


}