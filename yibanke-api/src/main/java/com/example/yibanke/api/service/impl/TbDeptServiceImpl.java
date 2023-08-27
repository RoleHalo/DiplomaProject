package com.example.yibanke.api.service.impl;


import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.dao.TbDeptMapper;
import com.example.yibanke.api.db.pojo.TbDept;
import com.example.yibanke.api.exception.YibankeException;
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

    @Override
    public PageUtils searchDeptByPage(HashMap param) {
        ArrayList<HashMap> list = deptDao.searchDeptByPage(param);
        long count = deptDao.searchDeptCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);

        return pageUtils;
    }

    @Override
    public int insert(TbDept dept) {
        int rows = deptDao.insert(dept);
        return rows;
    }

    @Override
    public int update(TbDept dept) {
        int rows = deptDao.update(dept);
        return rows;
    }

    @Override
    public int deleteDeptByIds(Integer[] ids) {
        if(!deptDao.searchCanDelete(ids)){
            throw new YibankeException("无法删除关联部门");
        }
        int rows = deptDao.deleteDeptByIds(ids);
        return rows;
    }

}
