package com.codecool.controller;

import com.codecool.model.ThreadInformation;
import com.codecool.service.CopierManager;
import com.codecool.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {
    private View view;
    private CopierManager initializer;

    public static Boolean isRunning = true;
    public static List<ThreadInformation> informationList = new ArrayList<>();
    
    public Controller(View view, CopierManager initializer) {
        this.view = view;
        this.initializer = initializer;
    }

    public void startController() {
        ExecutorService ioExecutor = Executors.newFixedThreadPool(1);
        while (isRunning) {
            handleIO(ioExecutor);
        }

        ioExecutor.shutdown();
        initializer.shutdown();
    }

    private void handleIO(ExecutorService ioExecutor) {
        handleOutput(ioExecutor);
        handleInput(ioExecutor);
    }

    private void handleOutput(ExecutorService ioExecutor) {
        UserOutput userOutput = new UserOutput(view);
        userOutput.setPriority(Thread.MAX_PRIORITY);
        Future outputFuture = ioExecutor.submit(userOutput);
        view.getLine();
        outputFuture.cancel(true);
    }

    private void handleInput(ExecutorService ioExecutor) {
        UserInput userInput = new UserInput(view, initializer);
        userInput.setPriority(Thread.MAX_PRIORITY);
        Future inputFuture = ioExecutor.submit(userInput);
        while (!inputFuture.isDone()) {
            try {
                Thread.sleep(0, 1);
            } catch (InterruptedException e) {
            }
        }
    }
}
