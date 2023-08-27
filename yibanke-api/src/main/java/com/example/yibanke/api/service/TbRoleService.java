package com.example.yibanke.api.service;


import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.db.pojo.TbRole;

import java.util.ArrayList;
import java.util.HashMap;

public interface TbRoleService {
    public ArrayList<HashMap> searchAllRole();

    public HashMap searchById(int id);

    public PageUtils searchRoleByPage(HashMap param);

    public int insert(TbRole role);

    public ArrayList<Integer> searchUserIdByRoleId(int roleId);

    public int update(TbRole role);

    public int deleteRoleByIds(Integer[] ids);

}
