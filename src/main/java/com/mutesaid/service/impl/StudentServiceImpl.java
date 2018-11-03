package com.mutesaid.service.impl;

import com.mutesaid.mapper.StudentMapper;
import com.mutesaid.pojo.Student;
import com.mutesaid.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@CacheConfig(cacheNames = "student")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    private Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Override
    @Cacheable
    public List<Student> selectAll() {
        logger.info("select from DB");
        return studentMapper.selectAll();
    }

    @Override
    @Cacheable(key = "#id")
    public Student selectById(Long id) {
        return studentMapper.selectById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Long insert(Student stu) {
        Long currentTime = System.currentTimeMillis();
        stu.setCreateAt(currentTime);
        stu.setUpdateAt(currentTime);
        studentMapper.insert(stu);
        return stu.getId();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Cacheable(key = "#id")
    public Boolean update(Long id, String key, Object value) {
        Long currentTime = System.currentTimeMillis();
        try{
            System.out.println(key);
            System.out.println(value);
            studentMapper.update(id, key, value, currentTime);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    public Boolean delete(Long id) {
        try {
            studentMapper.delete(id);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
