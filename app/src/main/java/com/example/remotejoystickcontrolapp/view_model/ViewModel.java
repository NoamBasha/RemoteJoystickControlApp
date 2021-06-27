package com.example.remotejoystickcontrolapp.view_model;

import com.example.remotejoystickcontrolapp.model.FGPlayer;

import java.io.PrintWriter;
import java.net.Socket;

public class ViewModel {

    private FGPlayer fgPlayer;

    public ViewModel(FGPlayer fgPlayer) {
        this.fgPlayer = fgPlayer;
    }

    // Establishing connection and running a thread for the "tasks"
    public void connect(String ip, int port) {
        try {
            this.fgPlayer.connect(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Disconnecting and setting isConnected to false
    public void disconnect() throws Exception {
        this.fgPlayer.disconnect();
    }

    // Aileron task
    public void sendAileron(double v) throws InterruptedException {
        this.fgPlayer.sendAileron(v);
    }

    // Elevator task
    public void sendElevator(double v) throws InterruptedException {
        this.fgPlayer.sendElevator(v);
    }

    // Rudder task
    public void sendRudder(double v) throws InterruptedException {
        this.fgPlayer.sendRudder(v);
    }

    // Throttle task
    public void sendThrottle(double v) throws InterruptedException {
        this.fgPlayer.sendThrottle(v);
    }
}
