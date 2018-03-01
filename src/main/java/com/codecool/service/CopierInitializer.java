package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.model.ThreadInformation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopierInitializer {
    ExecutorService copierExecutor;

    public CopierInitializer(ExecutorService copierExecutor) {
        this.copierExecutor = copierExecutor;
    }

    public void initialize(String inputFile, String outputFile) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        ThreadInformation threadInformation = new ThreadInformation(inputFile, outputFile, 0, 1024);
        Controller.informationList.add(threadInformation);

        FileCopier fileCopier = new FileCopier(inputStream, outputStream, threadInformation);
        this.copierExecutor.execute(fileCopier);
    }
}
