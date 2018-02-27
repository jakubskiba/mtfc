package com.codecool.controller;

import com.codecool.model.ThreadInformation;
import com.codecool.service.UserInput;
import com.codecool.service.UserOutput;
import com.codecool.view.View;

public class Controller {
    private View view;
    public static Boolean isAppRunning = true;

    public Controller(View view) {
        this.view = view;
    }

    public void startController() {
        view.printInfo(ThreadInformation.threadInformations);


        String command = "";
        while (!command.equals("quit")) {
            UserOutput userOutput = new UserOutput(view);
            userOutput.start();

            System.out.println("Press enter to create new copy");
            command = view.getLine();
            userOutput.interrupt();

            UserInput userInput = new UserInput(view);
            userInput.start();

            synchronized (userInput) {
                try {
                    userInput.join();
                } catch (InterruptedException e) {
                }
            }

        }

        isAppRunning = false;
    }
}
