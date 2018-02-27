package com.codecool.service;

import com.codecool.controller.Controller;
import com.codecool.model.ThreadInformation;
import com.codecool.view.View;

public class UserOutput extends Thread {

    View view;

    public UserOutput(View view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            printInfo();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void printInfo() {
        Boolean isAnyInfoUpdated = false;
        for(ThreadInformation information : Controller.informationList) {
            if(information.getChanged()) {
                isAnyInfoUpdated = true;
                information.setChanged(false);
            }
        }

        if(isAnyInfoUpdated) {
            view.printInfo(Controller.informationList);
            isAnyInfoUpdated = false;
        }
    }
}
