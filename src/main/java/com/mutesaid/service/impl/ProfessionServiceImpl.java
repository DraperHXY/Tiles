package com.mutesaid.service.impl;

import com.mutesaid.mapper.ProfessionMapper;
import com.mutesaid.pojo.Profession;
import com.mutesaid.service.ProfessionService;
import com.mutesaid.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfessionServiceImpl implements ProfessionService {
    @Autowired
    private ProfessionMapper professionMapper;

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, List> getProfesList() {
        List<String> directionList = CacheUtil.get("directionList", professionMapper::getDirection);
        Map<String, List> job = new HashMap(directionList.size());
        directionList.forEach(item->job.put(item, CacheUtil.get(item, professionMapper::getProfessList)));

        return job;
    }
}
