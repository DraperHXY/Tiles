package com.mutesaid.service.impl;

import com.mutesaid.mapper.ExpertMapper;
import com.mutesaid.pojo.Expert;
import com.mutesaid.service.ExpertService;
import com.mutesaid.utils.CacheUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertMapper expertMapper;

    private final Logger logger = LogManager.getLogger(ExpertServiceImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<Expert> getAllExpert() {
        Long start = System.currentTimeMillis();
        List<Expert> expert = CacheUtil.getList("exprtList", expertMapper::getAllExpert);
        Long end = System.currentTimeMillis();

        logger.info("优秀学员Service用时{}ms",end - start);
        return expert;
    }
}
