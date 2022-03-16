package com.example.yibanke.api.db.dao;
import com.example.yibanke.api.db.pojo.TbReim;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbReimMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbReim record);

    int insertSelective(TbReim record);

    TbReim selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbReim record);

    int updateByPrimaryKeyWithBLOBs(TbReim record);

    int updateByPrimaryKey(TbReim record);
}