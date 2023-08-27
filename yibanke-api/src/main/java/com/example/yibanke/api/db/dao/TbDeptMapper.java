package com.example.yibanke.api.db.dao;

import com.example.yibanke.api.db.pojo.TbDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbDeptMapper {

    public ArrayList<HashMap> searchAllDept();

    public HashMap searchById(int id);

    public ArrayList<HashMap> searchDeptByPage(HashMap param);

    public long searchDeptCount(HashMap param);

    public int insert(TbDept dept);

    public int update(TbDept dept);

    public boolean searchCanDelete(Integer[] ids);

    public int deleteDeptByIds(Integer[] isd);
}