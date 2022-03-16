package com.example.yibanke.api.service;


import com.example.yibanke.api.common.util.PageUtils;
import com.example.yibanke.api.common.util.R;
import com.example.yibanke.api.db.pojo.TbUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface TbUserService  {
    public Set<String> searchUserPermissions(int userId);

    public Integer login(HashMap param);

    public HashMap searchUserSummary(int userId);

    public int updatePassword(HashMap param);

    public HashMap searchById(int userId);

    public ArrayList<HashMap> searchAllUser();

    public PageUtils searchUserByPage(HashMap param);

    public int insert(TbUser user);

    public int update(HashMap param);

    public int deleteUserByIds(Integer[] ids);

}
