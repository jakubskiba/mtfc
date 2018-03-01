package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.model.ThreadInformation;
import com.codecool.view.View;

import java.io.File;
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

        switch (view.getOption()) {

            case "1":
                this.createCopyProcess();
                break;

            case "2":
                this.cancelAllTasks();
                break;

            case "3":
                view.printInfo(Controller.informationList);
                int threadIdToCancel = view.getThreadId();
                this.cancelTask(threadIdToCancel);
                break;

        }

    }

    private void cancelAllTasks() {

        for (FileCopier copier : Controller.copiers) {
            copier.interrupt();
        }

    }

    private void cancelTask(int id) {

        for (FileCopier copier : Controller.copiers) {
            if (copier.getId() == id) {
                copier.interrupt();
            }
        }

    }

    private void initiateProcess(String inputFile, String outputFile) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        ThreadInformation threadInformation = new ThreadInformation(inputFile, outputFile, 0, 1024);
        FileCopier fileCopier = new FileCopier(inputStream, outputStream, threadInformation);
        Controller.copiers.add(fileCopier);
        fileCopier.start();
    }

    private void createCopyProcess() {
        isGettingData = true;
        String inputFile = view.getFromPath();
        String outputFile = view.getToPath();
        boolean isOverwrite = view.getOverwrite();

        try {

            if (!isOverwrite && new File(outputFile).isFile()) {
                System.out.println("Can't overwrite existing file!");
            } else {
                this.initiateProcess(inputFile, outputFile);
                System.out.println("started");
            }

        } catch (FileNotFoundException e) {
            view.printNoFileError();
        }
        isGettingData = false;
    }

}
