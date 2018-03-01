package com.codecool.service;

import com.codecool.model.ThreadInformation;

import java.io.*;

public class FileCopier extends Thread {

    private String inputFile;
    private String outputFile;
    private int progress;
    private int size;
    private FileInputStream inputStream;
    private FileOutputStream outputStream;
    private int portionSize;
    private ThreadInformation threadInformation;

    public FileCopier(FileInputStream inputStream,
                      FileOutputStream outputStream, ThreadInformation threadInformation) {
        this.size = 0;
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        this.threadInformation = threadInformation;

        this.inputFile = threadInformation.getFrom();
        this.outputFile = threadInformation.getTo();
        this.progress = threadInformation.getProgress();
        this.portionSize = threadInformation.getPortionSize();
    }

    @Override
    public void run() {

        this.size = (int) new File(this.inputFile).length();
        this.threadInformation.setThreadId((int) this.getId());
        this.copy();
    }

    private void copy() {

        try {
            this.copyByPortion();
            this.copyRest();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            this.threadInformation.setCancelled(true);
        }

    }

    private void copyRest() throws IOException {
        byte[] portion = new byte[this.size % this.portionSize];

        if (this.inputStream.read(portion) != -1) {
            this.inputStream.read(portion);
            this.outputStream.write(portion);
        }

        this.progress = 100;
        this.threadInformation.setProgress(progress);
        this.threadInformation.setChanged(true);
    }

    private void copyByPortion() throws IOException, InterruptedException {
        int portionsAmount = this.size / this.portionSize;

        byte[] portion = new byte[this.portionSize];

        for (int i = 0; i<portionsAmount; i++) {
            this.inputStream.read(portion);
            this.outputStream.write(portion);
            Thread.sleep(0, 1);

            int previousProgress = this.progress;
            this.progress = (int) ((float) i / portionsAmount * 100);

            if(progress == previousProgress) {
                this.threadInformation.setProgress(progress);
                this.threadInformation.setChanged(true);
            }

        }

    }

    @Override
    public String toString() {
        return String.format("%d%%", this.progress);
    }
}
