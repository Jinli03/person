/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/9 15:20
 */
package com.example.entity;

import java.time.LocalDateTime;

public class Task {
    private Integer id;
    private String username;
    private String kind;
    private String priority;
    private String content;
    private String note;
    private String tag;
    private LocalDateTime start;
    private LocalDateTime finish;
    private LocalDateTime warn;
    private String state;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", kind='" + kind + '\'' +
                ", priority='" + priority + '\'' +
                ", content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", tag='" + tag + '\'' +
                ", start='" + start + '\'' +
                ", finish='" + finish + '\'' +
                ", warn='" + warn + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public LocalDateTime getWarn() {
        return warn;
    }

    public void setWarn(LocalDateTime warn) {
        this.warn = warn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
