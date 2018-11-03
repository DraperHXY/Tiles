package com.mutesaid.service;

import com.mutesaid.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Student> selectAll();

    Student selectById(Long id);

    Long insert(Student stu);

    <T> Boolean update(Long id, String key, T value);

    Boolean delete(Long id);
}
