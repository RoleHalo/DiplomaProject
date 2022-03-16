package com.example.yibanke.api.service.impl;


import com.example.yibanke.api.db.dao.TbDeptMapper;
import com.example.yibanke.api.service.TbDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
*
*/
@Service
public class TbDeptServiceImpl implements TbDeptService {


    @Autowired
    private TbDeptMapper deptDao;

    @Override
    public ArrayList<HashMap> searchAllDept() {
        ArrayList<HashMap> list = deptDao.searchAllDept();
        return list;
    }

    @Override
    public HashMap searchById(int id) {
        HashMap map = deptDao.searchById(id);
        return map;
    }
}
