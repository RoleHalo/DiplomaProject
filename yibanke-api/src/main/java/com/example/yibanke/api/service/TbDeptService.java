package com.example.yibanke.api.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public interface TbDeptService  {
    public ArrayList<HashMap> searchAllDept();
    public HashMap searchById(int id);
}
