package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbPermissionMapper {

    public ArrayList<HashMap> searchAllPermission();
}