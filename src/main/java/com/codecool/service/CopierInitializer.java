package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.exception.FileIsLockedException;
import com.codecool.model.ThreadInformation;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class CopierInitializer {
    private ExecutorService copierExecutor;
    private Map<Long, Future> tasks;

    public CopierInitializer(ExecutorService copierExecutor) {
        this.copierExecutor = copierExecutor;
        this.tasks = new Hashtable<>();
    }

    public void initialize(String inputFile, String outputFile) throws FileNotFoundException, FileIsLockedException {
        cleanTasks();

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        checkOutputFile(outputFile);

        ThreadInformation threadInformation = new ThreadInformation(inputFile, outputFile, 0, 1024);
        Controller.informationList.add(threadInformation);

        FileCopier fileCopier = new FileCopier(inputStream, outputStream, threadInformation);
        Future newTask = this.copierExecutor.submit(fileCopier);

        tasks.put(fileCopier.getId(), newTask);
    }

    private void checkOutputFile(String outputFile) throws FileIsLockedException {
        List<String> blockedFilePaths = getBlockedFilePaths();

        for(String blockedPath : blockedFilePaths) {
            File newFile = new File(outputFile);
            File existingFile = new File(blockedPath);

            try {
                if(existingFile.getCanonicalPath().equals(newFile.getCanonicalPath())) {
                    throw new FileIsLockedException();
                }
            } catch (IOException e) {
                throw new FileIsLockedException();
            }
        }
    }

    private List<String> getBlockedFilePaths() {
        List<ThreadInformation> activeThreads = Controller.informationList
                .stream()
                .filter(info -> !info.isDone() && !info.getCancelled())
                .collect(Collectors.toList());

        List<String> blockedFilePaths = new ArrayList<>();
        activeThreads.stream().map(ThreadInformation::getTo).forEach(blockedFilePaths::add);
        activeThreads.stream().map(ThreadInformation::getFrom).forEach(blockedFilePaths::add);

        return blockedFilePaths;
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
