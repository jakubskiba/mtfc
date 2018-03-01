package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.model.ThreadInformation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CopierInitializer {
    public void initialize(String inputFile, String outputFile) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        ThreadInformation threadInformation = new ThreadInformation(inputFile, outputFile, 0, 1024);
        Controller.informationList.add(threadInformation);

        FileCopier fileCopier = new FileCopier(inputStream, outputStream, threadInformation);
        Controller.copiers.add(fileCopier);

        fileCopier.start();
    }
}
