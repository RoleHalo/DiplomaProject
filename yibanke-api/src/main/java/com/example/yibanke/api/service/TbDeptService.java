package com.example.yibanke.api.service;

import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.pojo.TbDept;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public interface TbDeptService  {
    public ArrayList<HashMap> searchAllDept();

    public HashMap searchById(int id);

    public PageUtils searchDeptByPage(HashMap param);

    public int insert(TbDept dept);

    public int update(TbDept dept);

    public int deleteDeptByIds(Integer[] isd);
}
