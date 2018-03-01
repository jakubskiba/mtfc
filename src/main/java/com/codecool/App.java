package com.codecool;

import com.codecool.controller.Controller;
import com.codecool.service.CopierInitializer;
import com.codecool.view.View;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        Controller controller = (Controller) ctx.getBean("mainController");
        controller.startController();
    }
}
