package com.codecool;

import com.codecool.controller.Controller;
import com.codecool.service.CopierInitializer;
import com.codecool.view.View;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main( String[] args ) {
        Scanner in = new Scanner(System.in);
        ExecutorService copierExecutor = Executors.newFixedThreadPool(2);
        CopierInitializer initializer = new CopierInitializer(copierExecutor);
        View view = new View(in);
        new Controller(view, initializer).startController();
    }
}
