package com.example.yibanke.api.service.impl;


import com.example.yibanke.api.db.dao.TbPermissionMapper;
import com.example.yibanke.api.service.TbPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
*
*/
@Service
public class TbPermissionServiceImpl
implements TbPermissionService {

    @Autowired
    private TbPermissionMapper permissionDao;

    @Override
    public ArrayList<HashMap> searchAllPermission() {
        ArrayList<HashMap> list = permissionDao.searchAllPermission();
        return list;
    }
}
