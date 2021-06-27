package com.example.remotejoystickcontrolapp.view_model;

import com.example.remotejoystickcontrolapp.model.FGPlayer;

public class ViewModel {

    private final FGPlayer fgPlayer;

    public ViewModel(FGPlayer fgPlayer) {
        this.fgPlayer = fgPlayer;
    }

    public void connect(String ip, int port) {
        try {
            this.fgPlayer.connect(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Aileron task
    public void setAileron(double v) {
        this.fgPlayer.setAileron(v);
    }

    // Elevator task
    public void setElevator(double v) {
        this.fgPlayer.setElevator(v);
    }

    // Rudder task
    public void setRudder(double v) {
        this.fgPlayer.setRudder(v);
    }

    // Throttle task
    public void setThrottle(double v) {
        this.fgPlayer.setThrottle(v);
    }
}
