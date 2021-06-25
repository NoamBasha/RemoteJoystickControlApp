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
    BlockingDeque<Runnable> dispatchQueue = new LinkedBlockingDeque<Runnable>();

    // Establishing connection and running a thread for the "tasks"
    public void connect(String ip, int port) throws Exception {
        try {
            this.fg = new Socket(ip, port);
            this.out = new PrintWriter(fg.getOutputStream(), true);
            this.isConnected = true;
        } catch (Exception e) {
            //TODO
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isConnected) {
                    try {
                        dispatchQueue.take().run();
                    } catch (InterruptedException e) {
                        //TODO
                    }
                }
            }
        }).start();
    }

    // Disconnecting and setting isConnected to false
    public void disconnect() throws Exception {
        try {
            this.out.close();
            this.fg.close();
            this.isConnected = false;
        } catch (Exception e) {
            //TODO
        }
    }

    // Aileron task
    public void sendAileron(float v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(new Runnable() {
                @Override
                public void run() {
                    out.print("set /controls/flight/aileron " + v + "\r\n");
                    out.flush();
                }
            });
        }
    }

    // Elevator task
    public void sendElevator(float v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(new Runnable() {
                @Override
                public void run() {
                    out.print("set /controls/flight/elevator " + v + "\r\n");
                    out.flush();
                }
            });
        }
    }

    // Rudder task
    public void sendRudder(float v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(new Runnable() {
                @Override
                public void run() {
                    out.print("set /controls/flight/rudder " + v + "\r\n");
                    out.flush();
                }
            });
        }
    }

    // Throttle task
    public void sendThrottle(float v) throws InterruptedException {
        if (this.out != null) {
            dispatchQueue.put(new Runnable() {
                @Override
                public void run() {
                    out.print("set /controls/engines/current-engine/throttle  " + v + "\r\n");
                    out.flush();
                }
            });
        }
    }

}
