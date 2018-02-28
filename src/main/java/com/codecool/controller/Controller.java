package com.codecool.controller;

import com.codecool.model.ThreadInformation;
import com.codecool.service.FileCopier;
import com.codecool.service.UserInput;
import com.codecool.service.UserOutput;
import com.codecool.view.View;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private View view;
    public static List<ThreadInformation> informationList = new ArrayList<>();
    public static List<FileCopier> copiers = new ArrayList<>();

    public Controller(View view) {
        this.view = view;
    }

    public void startController() {
        view.printInfo(Controller.informationList);


        while (true) {
            UserOutput userOutput = new UserOutput(view);
            userOutput.start();

            System.out.println("Press enter to create new copy");
            view.getLine();
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
    }
}
