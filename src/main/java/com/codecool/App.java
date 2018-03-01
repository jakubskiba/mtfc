package com.codecool;

import com.codecool.controller.Controller;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        Controller controller = (Controller) ctx.getBean("mainController");
        controller.startController();
    }
}
