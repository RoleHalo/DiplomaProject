package com.example.yibanke.api.service;

import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.pojo.TbAmect;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
/**
*
*/
public interface TbAmectService {

    public PageUtils searchAmectByPage(HashMap param);

    public int insert(ArrayList<TbAmect> list);


    public HashMap searchById(int id);

    public int update(HashMap param);

    public int deleteAmectByIds(Integer [] ids);


    public String createNativeAmectPayOrder(HashMap param);


    public int updateStatus(HashMap param);

    public int searchUserIdByUUID(String uuid);


    public void searchNativeAmectPayResult(HashMap param);


    public HashMap searchChart(HashMap param);


}

