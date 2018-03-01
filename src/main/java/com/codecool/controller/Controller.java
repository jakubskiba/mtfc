package com.codecool.controller;

import com.codecool.model.ThreadInformation;
import com.codecool.service.CopierInitializer;
import com.codecool.service.FileCopier;
import com.codecool.view.View;

import java.util.ArrayList;
import java.util.List;

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
        while (true) {
            UserOutput userOutput = new UserOutput(view);
            userOutput.start();

            view.getLine();
            userOutput.interrupt();

            UserInput userInput = new UserInput(view, initializer);
            userInput.start();

            synchronized (userInput) {
                try {
                    userInput.join();
                } catch (InterruptedException e) {
                }
            }

        }
    }
}
