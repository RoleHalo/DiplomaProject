package com.example.yibanke.api.service;


import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.pojo.TbLeave;


import java.util.HashMap;

public interface TbLeaveService  {

    public PageUtils searchLeaveByPage(HashMap param);


    public boolean searchContradiction(HashMap param);

    public int insert(TbLeave leave);


    public int deleteLeaveById(HashMap param);


    public HashMap searchLeaveById(HashMap param);

}
