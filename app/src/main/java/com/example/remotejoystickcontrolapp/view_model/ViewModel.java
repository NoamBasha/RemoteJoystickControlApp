package com.example.remotejoystickcontrolapp.view_model;

import com.example.remotejoystickcontrolapp.model.FGPlayer;

import java.io.PrintWriter;
import java.net.Socket;

public class ViewModel {

    private FGPlayer fgPlayer;
    public String ip;
    public int port;

    public ViewModel(FGPlayer fgPlayer) {
        this.fgPlayer = fgPlayer;
    }

    // Establishing connection and running a thread for the "tasks"
    public void connect() throws Exception {
        try {
            this.fgPlayer.connect(ip, port);
        } catch (Exception e) {

        }
    }

    // Disconnecting and setting isConnected to false
    public void disconnect() throws Exception {
        this.fgPlayer.disconnect();
    }

    // Aileron task
    public void sendAileron(float v) throws InterruptedException {
        //TODO Should normalize v?
        this.fgPlayer.sendAileron(v);
    }

    // Elevator task
    public void sendElevator(float v) throws InterruptedException {
        this.fgPlayer.sendElevator(v);
    }

    // Rudder task
    public void sendRudder(float v) throws InterruptedException {
        this.fgPlayer.sendRudder(v);
    }

    // Throttle task
    public void sendThrottle(float v) throws InterruptedException {
        this.fgPlayer.sendThrottle(v);
    }
}
