package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbCheckin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbCheckinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCheckin record);

    int insertSelective(TbCheckin record);

    TbCheckin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCheckin record);

    int updateByPrimaryKey(TbCheckin record);
}