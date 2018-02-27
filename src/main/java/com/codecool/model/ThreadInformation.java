package com.codecool.model;

import com.codecool.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadInformation {
    private String from;
    private String to;
    private Integer progress;
    private Integer portionSize;

    public ThreadInformation(String from, String to, Integer progress, Integer portionSize) {
        this.from = from;
        this.to = to;
        this.progress = progress;
        this.portionSize = portionSize;
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

    public static void main(String[] args) {
        List<ThreadInformation> list = new ArrayList<>();

        list.add(new ThreadInformation("bla", "bua", 10, 1024));
        list.add(new ThreadInformation("bl1a", "bua2", 20, 1024));

        new View(new Scanner(System.in)).printInfo(list);
    }
}
