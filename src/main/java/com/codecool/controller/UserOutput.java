package com.codecool.controller;

import com.codecool.controller.Controller;
import com.codecool.model.ThreadInformation;
import com.codecool.view.View;

public class UserOutput extends Thread {

    View view;
    private Boolean isScreenEmpty = true;

    public UserOutput(View view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            printInfo(isScreenEmpty);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void printInfo(Boolean reprint) {
        Boolean isAnyInfoUpdated = reprint;
        for(ThreadInformation information : Controller.informationList) {
            if(information.getChanged()) {
                isAnyInfoUpdated = true;
                information.setChanged(false);
            }
        }

        if(isAnyInfoUpdated) {
            view.printInfo(Controller.informationList);
            isScreenEmpty = false;
        }
    }
}
