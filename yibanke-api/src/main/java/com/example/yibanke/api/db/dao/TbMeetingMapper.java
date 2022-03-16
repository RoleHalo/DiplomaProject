package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbMeeting;
import com.example.yibanke.api.db.pojo.TbMeetingWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbMeetingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TbMeetingWithBLOBs record);

    int insertSelective(TbMeetingWithBLOBs record);

    TbMeetingWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbMeetingWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TbMeetingWithBLOBs record);

    int updateByPrimaryKey(TbMeeting record);
}