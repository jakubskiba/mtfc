package com.codecool.view;

import com.codecool.model.ThreadInformation;

import java.util.List;
import java.util.Scanner;

public class View {
    private final int PATH_CELL_WIDTH = 30;
    private Scanner in;

    public View() {
        this.in = new Scanner(System.in);
    }

    public String getFromPath() {
        System.out.print("from path: ");
        return in.nextLine();
    }

    public int getThreadId() {
        System.out.print("Choose task id to cancel: ");
        return Integer.valueOf(in.nextLine());
    }

    public String getToPath() {
        System.out.print("to path: ");
        return in.nextLine();
    }

    public boolean getOverwrite() {
        System.out.print("overwrite? (y/n): ");
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
        System.out.printf("|%-3s|%-30s|%-30s|%-8s|%-40s|%n","id", "from", "to", "progress", "status bar");

        for(int i = 0; i<117;i++) {
            System.out.print("-");
        }

        System.out.println();

        for(ThreadInformation info : informationList) {
            System.out.println(getInfoString(info));
        }

        System.out.println("Press enter to see menu");

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
        String status = getStatus(info);
        String progressBar = getProgressBar(info);
        String threadId = info.getThreadId() == null ? "" : info.getThreadId().toString();
        sb.append(String.format("|%-3s|%-30s|%-30s|%-8s|%-40s|", threadId, from, to, status, progressBar));

        return sb.toString();
    }

    private String getProgressBar(ThreadInformation info) {
        StringBuilder sb = new StringBuilder();
        int progress = info.getProgress() / 5 * 2;
        int rest = 40 - progress;

        for(int i = 0; i < progress; i++) {
            sb.append("=");
        }

        for(int i = 0; i < rest; i++) {
            sb.append(" ");
        }

        return sb.toString();
    }


    private String getStatus(ThreadInformation info) {
        if (info.getCancelled()) {
            return "Cancel";
        } else if(info.getProgress() == 100) {
            return "Done";
        } else if(!info.getStarted()) {
            return "Wait";
        } else {
            return info.getProgress() + "%";
        }
    }

    public String getLine() {
        System.out.println("Press enter");
        return in.nextLine();
    }

    public String getOption() {
        String menu = "Choose an option [1,2,3,4]:\n"
                    + "1. Copy next file\n"
                    + "2. Cancel all copies\n"
                    + "3. Cancel specific copy\n"
                    + "4. Exit program (stops all copying process)"
                    + "or press enter to go back to the preview";
        System.out.println(menu);

        return in.nextLine();
    }

    public void printOverrideError() {
        System.out.println("Can't overwrite existing file!");
        getLine();
    }

    public void printFileIsLockedException() {
        System.err.println("File is locked!");
        getLine();
    }

    public void printSameFileException() {
        System.err.println("This is the same file!");
        getLine();
    }
}
