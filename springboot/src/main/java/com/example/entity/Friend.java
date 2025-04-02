/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/2 10:38
 */
package com.example.entity;

import java.time.LocalDateTime;

public class Friend {
    private Integer id;
    private String username;
    private String friend;
    private String state;
    private String avatar;
    private LocalDateTime invite;
    private LocalDateTime time;

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

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getInvite() {
        return invite;
    }

    public void setInvite(LocalDateTime invite) {
        this.invite = invite;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
