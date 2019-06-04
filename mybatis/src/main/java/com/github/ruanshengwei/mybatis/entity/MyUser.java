package com.github.ruanshengwei.mybatis.entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="myuser")
public class MyUser {
    @Id
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}