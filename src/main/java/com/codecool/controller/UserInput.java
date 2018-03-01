package com.codecool.controller;

import com.codecool.controller.Controller;
import com.codecool.exception.FileIsLockedException;
import com.codecool.exception.SameFileException;
import com.codecool.model.ThreadInformation;
import com.codecool.service.CopierInitializer;
import com.codecool.service.FileCopier;
import com.codecool.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class UserInput extends Thread {
    private View view;
    private CopierInitializer initializer;

    public UserInput(View view, CopierInitializer initializer) {
        this.view = view;
        this.initializer = initializer;
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

            case "4":
                this.stopProgram();
                break;
        }
    }

    private void stopProgram() {
        initializer.cancelAll();
        Controller.isRunning = false;
    }

    private void cancelAllTasks() {

        initializer.cancelAll();


    }

    private void cancelTask(int id) {
        initializer.cancelOne(id);

    }

    private void createCopyProcess() {
        String inputFile = view.getFromPath();
        String outputFile = view.getToPath();
        boolean isOverwrite = view.getOverwrite();

        try {

            if (!isOverwrite && new File(outputFile).isFile()) {
                view.printOverrideError();
            } else {
                this.initializer.initialize(inputFile, outputFile);
            }

        } catch (FileNotFoundException e) {
            view.printNoFileError();
        } catch (FileIsLockedException e) {
            view.printFileIsLockedException();
        } catch (SameFileException e) {
            view.printSameFileException();
        }
    }

}
