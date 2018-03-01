package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.exception.FileIsLockedException;
import com.codecool.exception.SameFileException;
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

    public void initialize(String inputFile, String outputFile) throws FileNotFoundException, FileIsLockedException, SameFileException {
        cleanTasks();

        checkIsTheSameFile(inputFile, outputFile);
        checkOutputFile(outputFile);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);


        ThreadInformation threadInformation = new ThreadInformation(inputFile, outputFile, 0, 1024);
        Controller.informationList.add(threadInformation);

        FileCopier fileCopier = new FileCopier(inputStream, outputStream, threadInformation);
        fileCopier.setPriority(Thread.NORM_PRIORITY);
        Future newTask = this.copierExecutor.submit(fileCopier);

        tasks.put(fileCopier.getId(), newTask);
    }

    private void checkIsTheSameFile(String inputFile, String outputFile) throws SameFileException {
        try {
            if(isTheSameFile(inputFile, outputFile)) {
                throw new SameFileException();
            }
        } catch (IOException e) {
            throw new SameFileException();
        }
    }

    private boolean isTheSameFile(String path1, String path2) throws IOException {
        File file1 = new File(path1);
        File file2 = new File(path2);

        return file1.getCanonicalPath().equals(file2.getCanonicalPath());
    }

    private void checkOutputFile(String outputFile) throws FileIsLockedException {
        List<String> blockedFilePaths = getBlockedFilePaths();

        for(String blockedPath : blockedFilePaths) {
            try {
                if(isTheSameFile(outputFile, blockedPath)) {
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
