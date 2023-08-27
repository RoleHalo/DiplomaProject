package com.example.yibanke.api.service.impl;


import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.dao.TbRoleMapper;
import com.example.yibanke.api.db.pojo.TbRole;
import com.example.yibanke.api.exception.YibankeException;
import com.example.yibanke.api.service.TbRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
*
*/
@Service
public class TbRoleServiceImpl implements TbRoleService {

    @Autowired
    private TbRoleMapper roleDao;

    @Override
    public ArrayList<HashMap> searchAllRole() {
        ArrayList<HashMap> list = roleDao.searchAllRole();
        return list;
    }

    @Override
    public HashMap searchById(int id) {
        HashMap map = roleDao.searchById(id);
        return map;
    }

    @Override
    public PageUtils searchRoleByPage(HashMap param) {
        ArrayList<HashMap> list = roleDao.searchRoleByPage(param);
        long count = roleDao.searchRoleCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public int insert(TbRole role) {
        int rows = roleDao.insert(role);
        return rows;
    }

    @Override
    public ArrayList<Integer> searchUserIdByRoleId(int roleId) {
        ArrayList<Integer> list = roleDao.searchUserIdByRoleId(roleId);
        return list;
    }

    @Override
    public int update(TbRole role) {
        int rows = roleDao.update(role);
        return rows;
    }

    @Override
    public int deleteRoleByIds(Integer[] ids) {
        if (!roleDao.searchCanDelete(ids)) {
            throw new YibankeException("无法删除关联用户的角色");
        }
        int rows = roleDao.deleteRoleByIds(ids);
        return rows;
    }

}
