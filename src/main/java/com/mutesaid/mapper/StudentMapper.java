package com.mutesaid.mapper;

import com.mutesaid.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    List<Student> selectAll();

    Student selectById(Long id);

    void insert(Student stu);

    void update(@Param("id") Long id,
                       @Param("key") String key,
                       @Param("value") Object value,
                       @Param("updateTime") Long updateTime);

    void delete(Long id);
}
