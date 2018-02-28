package com.codecool.view;

import com.codecool.model.ThreadInformation;

import java.util.List;
import java.util.Scanner;

public class View {
    private final int PATH_CELL_WIDTH = 30;
    private Scanner in;

    public View(Scanner in) {
        this.in = in;
    }

    public String getFromPath() {
        System.out.print("from path: ");
        return in.nextLine();
    }

    public String getToPath() {
        System.out.print("to path: ");
        return in.nextLine();
    }

    public boolean getOverride() {
        System.out.print("override? (y/n): ");
        String line = in.nextLine();
        return line.equalsIgnoreCase("y");
    }

    public void printNoFileError() {
        System.err.println("No such file");
    }

    public void cleanScreen() {
        for(int i = 0; i<50; i++) {
            System.out.println();
        }
    }

    public void printInfo(List<ThreadInformation> informationList) {
        cleanScreen();
        System.out.printf("|%-30s|%-30s|%-8s|%-10s|%n", "from", "to", "progress", "status bar");
        for(int i = 0; i<44;i++) {
            System.out.print("-");
        }
        System.out.println();
        for(ThreadInformation info : informationList) {
            System.out.println(getInfoString(info));
        }
    }

    private String getLastCharacters(String str, int amount) {
        if(str.length() <= amount) {
            return str;
        } else {
            return str.substring(str.length()-amount, str.length());
        }
    }

    private String getInfoString(ThreadInformation info) {
        StringBuilder sb = new StringBuilder();
        String from = getLastCharacters(info.getFrom(), PATH_CELL_WIDTH);
        String to = getLastCharacters(info.getTo(), PATH_CELL_WIDTH);

        sb.append(String.format("|%-30s|%-30s|%-7d%%|", from, to, info.getProgress()));
        int progress = info.getProgress() / 10;
        int rest = 10 - progress;
        for(int i = 0; i < progress; i++) {
            sb.append("=");
        }

        for(int i = 0; i < rest; i++) {
            sb.append(" ");
        }

        sb.append("|");

        return sb.toString();
    }

    public String getLine() {
        return in.nextLine();
    }
}