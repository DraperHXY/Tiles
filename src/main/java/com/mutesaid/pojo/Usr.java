package com.mutesaid.pojo;


import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

/**
 * @author 
 */
public class Usr{
    private Long id;

    @Email(message = "Email.usr.name")
    private String name;

    @Length(min = 6, max = 21, message = "Length.usr.pwd")
    private String pwd;

    private Long createAt;

    private Long updateAt;

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
}