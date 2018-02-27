package com.codecool.service;

import java.io.*;

public class FileCopier extends Thread {

    private String inputFile;
    private String outputFile;
    private int progress;
    private int size;
    private FileInputStream inputStream;
    private FileOutputStream outputStream;
    private int portionSize;

    public FileCopier(String inputFile, String outputFile, FileInputStream inputStream,
                      FileOutputStream outputStream, int portionSize) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.progress = 0;
        this.size = 0;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.portionSize = portionSize;
    }

    @Override
    public void run() {

        this.size = (int) new File(this.inputFile).length();
        this.copy();
    }

    private void copy() {
        try {
            copyByPortion();
            copyRest();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyRest() throws IOException {
        byte[] portion = new byte[this.size % this.portionSize];
        if (this.inputStream.read(portion) != -1) {
            this.inputStream.read(portion);
            this.outputStream.write(portion);
        }
        this.progress = 100;
    }

    private void copyByPortion() throws IOException {
        int portionsAmount = this.size / this.portionSize;

        byte[] portion = new byte[this.portionSize];
        for (int i = 0; i<portionsAmount; i++) {
            this.inputStream.read(portion);
            this.outputStream.write(portion);

            this.progress = (int) ((float) i / portionsAmount * 100);
        }
    }

    @Override
    public String toString() {
        return String.format("%d%%", this.progress);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String inputFile = "/home/kyubu/Downloads/The.Big.Bang.Theory.S11E11.HDTV.x264-SVA[eztv].mkv";
        String outputFile = "/tmp/f.mkv";
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        FileCopier fc = new FileCopier(inputFile, outputFile, inputStream, outputStream, 1024);
        fc.start();
        while (fc.isAlive()) {
            System.out.println(fc);
        }
        System.out.println(fc);
    }
}
