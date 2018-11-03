package com.mutesaid.service.impl;

import com.mutesaid.mapper.ProfessionMapper;
import com.mutesaid.service.ProfessionService;
import com.mutesaid.utils.CacheUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfessionServiceImpl implements ProfessionService {
    @Autowired
    private ProfessionMapper professionMapper;

    private final Logger logger = LogManager.getLogger(ProfessionServiceImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, List> getProfesList() {
        Long start = System.currentTimeMillis();
        Long directionStart = System.currentTimeMillis();
        List<String> directionList = CacheUtil.getList("directionList", professionMapper::getDirection);
        Long directionEnd = System.currentTimeMillis();
        logger.info("查询职业分类用时{}ms",directionEnd - directionStart);
        Map<String, List> job = new HashMap(directionList.size());
        Long professionStart = System.currentTimeMillis();
        directionList.forEach(item->job.put(item, CacheUtil.getList(item, professionMapper::getProfessList)));
        Long professionEnd = System.currentTimeMillis();
        logger.info("按职业分类查找职业用时{}ms",professionEnd - professionStart);
        Long end = System.currentTimeMillis();
        logger.info("查询职业Service用时{}ms",end - start);

        return job;
    }
}
