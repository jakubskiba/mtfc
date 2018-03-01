package com.codecool.model;

import com.codecool.controller.Controller;
import com.codecool.service.FileCopier;
import com.codecool.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadInformation {
    private String from;
    private String to;
    private Integer progress;
    private Integer portionSize;
    private Boolean isChanged;
    private Boolean isCancelled;
    private Integer threadId;

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public ThreadInformation(String from, String to, Integer progress, Integer portionSize) {
        this.from = from;
        this.to = to;
        this.progress = progress;
        this.portionSize = portionSize;
        this.isChanged = true;
        this.isCancelled = false;
        Controller.informationList.add(this);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(Integer portionSize) {
        this.portionSize = portionSize;
    }

    public Boolean getChanged() {
        return isChanged;
    }

    public void setChanged(Boolean changed) {
        isChanged = changed;
    }

    public static void main(String[] args) {
        List<ThreadInformation> list = new ArrayList<>();

        list.add(new ThreadInformation("bla", "bua", 10, 1024));
        list.add(new ThreadInformation("bl1a", "bua2", 20, 1024));

        new View(new Scanner(System.in)).printInfo(list);
    }
}
