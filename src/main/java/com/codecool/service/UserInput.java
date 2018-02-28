package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.model.ThreadInformation;
import com.codecool.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class UserInput extends Thread {
    private View view;
    private Boolean isGettingData = false;

    public UserInput(View view) {
        this.view = view;
    }

    public Boolean getGettingData() {
        return isGettingData;
    }

    @Override
    public void run() {
        view.cleanScreen();
        createCopyProcess();
    }

    public void createCopyProcess() {
        isGettingData = true;
        String inputFile = view.getFromPath();
        String outputFile = view.getToPath();
        try {
            FileInputStream inputStream = new FileInputStream(inputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            ThreadInformation threadInformation = new ThreadInformation(inputFile, outputFile, 0, 1024);
            FileCopier fileCopier = new FileCopier(inputStream, outputStream, threadInformation);
            Controller.copiers.add(fileCopier);
            fileCopier.start();
            System.out.println("started");
        } catch (FileNotFoundException e) {
            view.printNoFileError();
        }
        isGettingData = false;
    }

}
