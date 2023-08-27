package com.example.yibanke.api.service;

import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.pojo.TbAmectType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public interface TbAmectTypeService  {
    public ArrayList<TbAmectType> searchAllAmectType();


    public PageUtils searchAmectTypeByPage(HashMap param);


    public int insert(TbAmectType amectType);


    public  HashMap searchById(int id);

    public int update(HashMap param);


    public int deleteAmectTypeByIds(Integer[] ids);

}
