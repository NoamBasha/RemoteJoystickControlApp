package com.example.remotejoystickcontrolapp.model;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class FGPlayer {

    private PrintWriter out;
    BlockingDeque<Runnable> dispatchQueue = new LinkedBlockingDeque<>();

    // Establishing connection and running a thread for the tasks
    public void connect(String ip, int port) {
        try {
            Socket fg = new Socket(ip, port);
            this.out = new PrintWriter(fg.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        new Thread(() -> {
            while (true) {
                try {
                    dispatchQueue.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Aileron task
    public void setAileron(double v) {
        if (this.out != null) {
            try {
                dispatchQueue.put(() -> {
                    out.print("set /controls/flight/aileron " + v + "\r\n");
                    out.flush();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Elevator task
    public void setElevator(double v) {
        if (this.out != null) {
            try {
                dispatchQueue.put(() -> {
                    out.print("set /controls/flight/elevator " + v + "\r\n");
                    out.flush();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Rudder task
    public void setRudder(double v) {
        if (this.out != null) {
            try {
                dispatchQueue.put(() -> {
                    out.print("set /controls/flight/rudder " + v + "\r\n");
                    out.flush();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Throttle task
    public void setThrottle(double v) {
        if (this.out != null) {
            try {
                dispatchQueue.put(() -> {
                    out.print("set /controls/engines/current-engine/throttle  " + v + "\r\n");
                    out.flush();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
