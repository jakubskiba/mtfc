package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.model.ThreadInformation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CopierInitializer {
    private ExecutorService copierExecutor;
    private Map<Long, Future> tasks;

    public CopierInitializer(ExecutorService copierExecutor) {
        this.copierExecutor = copierExecutor;
        this.tasks = new Hashtable<>();
    }

    public void initialize(String inputFile, String outputFile) throws FileNotFoundException {
        cleanTasks();

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        ThreadInformation threadInformation = new ThreadInformation(inputFile, outputFile, 0, 1024);
        Controller.informationList.add(threadInformation);

        FileCopier fileCopier = new FileCopier(inputStream, outputStream, threadInformation);
        Future newTask = this.copierExecutor.submit(fileCopier);

        tasks.put(fileCopier.getId(), newTask);
    }

    public void cancelAll() {
        for (Future copier : tasks.values()) {
            copier.cancel(true);
        }
        cleanTasks();
    }

    public void cancelOne(long id) {
        if(tasks.containsKey(id)) {
            tasks.get(id).cancel(true);
        }
        cleanTasks();
    }

    private void cleanTasks() {
        List<Future> toRemove = new ArrayList<>();
        for(Future copier : tasks.values()) {
            if(copier.isCancelled() || copier.isDone()) {
                toRemove.add(copier);
            }
        }

        for(Future copier : toRemove) {
            this.tasks.remove(copier);
        }
    }
}
