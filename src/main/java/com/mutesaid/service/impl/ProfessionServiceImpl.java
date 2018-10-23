package com.mutesaid.service.impl;

import com.mutesaid.mapper.ProfessionMapper;
import com.mutesaid.pojo.Profession;
import com.mutesaid.service.ProfessionService;
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
    public Map<String, List> getProfesList() {
        List<String> directionList = professionMapper.getDirection();
        @SuppressWarnings("unchecked")
        Map<String, List> job = new HashMap(directionList.size());
        directionList.forEach(item->job.put(item, professionMapper.getProfessList(item)));

        return job;
    }
}
