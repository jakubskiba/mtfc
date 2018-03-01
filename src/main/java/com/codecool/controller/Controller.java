package com.codecool.controller;

import com.codecool.model.ThreadInformation;
import com.codecool.service.CopierInitializer;
import com.codecool.service.FileCopier;
import com.codecool.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {
    private View view;
    private CopierInitializer initializer;

    public static List<ThreadInformation> informationList = new ArrayList<>();
    public static List<FileCopier> copiers = new ArrayList<>();


    public Controller(View view, CopierInitializer initializer) {
        this.view = view;
        this.initializer = initializer;
    }

    public void startController() {
        ExecutorService ioExecutor = Executors.newFixedThreadPool(1);
        while (true) {
            UserOutput userOutput = new UserOutput(view);
            Future outputFuture = ioExecutor.submit(userOutput);
            view.getLine();
            outputFuture.cancel(true);

            UserInput userInput = new UserInput(view, initializer);
            Future inputFuture = ioExecutor.submit(userInput);
            while (!inputFuture.isDone()) {
                try {
                    Thread.sleep(0, 1);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
