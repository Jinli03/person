/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/9 13:59
 */
package com.example.entity;

import java.time.LocalDate;

public class Health {
    private Integer id;
    private String username;
    private LocalDate date;
    private String kind;
    private float data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
