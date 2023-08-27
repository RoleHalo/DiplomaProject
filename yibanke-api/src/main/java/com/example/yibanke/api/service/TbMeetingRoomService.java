package com.example.yibanke.api.service;


import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.pojo.TbMeetingRoom;

import java.util.ArrayList;
import java.util.HashMap;

public interface TbMeetingRoomService {
    public ArrayList<HashMap> searchAllMeetingRoom();

    public HashMap searchById(int id);

    public ArrayList<String> searchFreeMeetingRoom(HashMap param);

    public PageUtils searchMeetingRoomByPage(HashMap param);

    public int insert(TbMeetingRoom meetingRoom);

    public int update(TbMeetingRoom meetingRoom);

    public int deleteMeetingRoomByIds(Integer[] ids);

}
