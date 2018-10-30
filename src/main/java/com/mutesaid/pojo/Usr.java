package com.mutesaid.pojo;


import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author 
 */
public class Usr implements Serializable {

    private Long id;

    @Email(message = "Email.usr.name")
    private String name;

    @Length(min = 6, max = 21, message = "Length.usr.pwd")
    private String pwd;

    private Long createAt;

    private Long updateAt;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

//    @Override
//    public String toString() {
//        return "Usr{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", pwd='" + pwd + '\'' +
//                ", createAt=" + createAt +
//                ", updateAt=" + updateAt +
//                '}';
//    }
}