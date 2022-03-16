package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbMeetingRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbMeetingRoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbMeetingRoom record);

    int insertSelective(TbMeetingRoom record);

    TbMeetingRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbMeetingRoom record);

    int updateByPrimaryKey(TbMeetingRoom record);
}