package com.codecool;

import com.codecool.controller.Controller;
import com.codecool.service.CopierInitializer;
import com.codecool.view.View;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
        Scanner in = new Scanner(System.in);
        CopierInitializer initializer = new CopierInitializer();
        View view = new View(in);
        new Controller(view, initializer).startController();
    }
}
