package com.example.remotejoystickcontrolapp.model;

import com.example.remotejoystickcontrolapp.R;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class FGPlayer {

    // Fields
    private Socket fg;
    private PrintWriter out;
    private boolean isConnected = false;
    BlockingDeque<Runnable> dispatchQueue = new LinkedBlockingDeque<>();

    // Establishing connection and running a thread for the "tasks"
    public void connect(String ip, int port) {
        try {
            System.out.println("Connecting...");
            this.fg = new Socket(ip, port);
            System.out.println("Connected");
            this.out = new PrintWriter(fg.getOutputStream(), true);
            this.isConnected = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (isConnected) {
                try {
                    dispatchQueue.take().run();
                } catch (InterruptedException e) {
                    //TODO
                }
            }
        }).start();
    }

    // Disconnecting and setting isConnected to false
    public void disconnect() {
        try {
            this.out.close();
            this.fg.close();
            this.isConnected = false;
        } catch (Exception e) {
            //TODO
        }
    }

    // Aileron task
    public void sendAileron(double v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(() -> {
                out.print("set /controls/flight/aileron " + v + "\r\n");
                out.flush();
            });
        }
    }

    // Elevator task
    public void sendElevator(double v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(() -> {
                out.print("set /controls/flight/elevator " + v + "\r\n");
                out.flush();
            });
        }
    }

    // Rudder task
    public void sendRudder(double v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(() -> {
                out.print("set /controls/flight/rudder " + v + "\r\n");
                out.flush();
            });
        }
    }

    // Throttle task
    public void sendThrottle(double v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(() -> {
                out.print("set /controls/engines/current-engine/throttle  " + v + "\r\n");
                out.flush();
            });
        }
    }
}
