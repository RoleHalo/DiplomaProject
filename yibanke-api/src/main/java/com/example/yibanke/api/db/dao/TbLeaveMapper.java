package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbLeave;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbLeaveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbLeave record);

    int insertSelective(TbLeave record);

    TbLeave selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbLeave record);

    int updateByPrimaryKey(TbLeave record);
}