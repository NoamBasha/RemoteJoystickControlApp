package com.example.remotejoystickcontrolapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.remotejoystickcontrolapp.R;
import com.example.remotejoystickcontrolapp.databinding.ActivityMainBinding;
import com.example.remotejoystickcontrolapp.model.FGPlayer;
import com.example.remotejoystickcontrolapp.view_model.ViewModel;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Fields
        FGPlayer fgPlayer = new FGPlayer();
        this.viewModel = new ViewModel(fgPlayer);
        activityMainBinding.setViewModel(viewModel);

        Joystick joystick = findViewById(R.id.joystick);
        joystick.joystickListener = (e, a) -> {
                viewModel.sendAileron(a);
                viewModel.sendElevator(e);
        };
        connectButtonListener();
        throttleSeekbarListener();
        rudderSeekbarListener();
    }

    private void connectButtonListener() {
        Button connectButton = findViewById(R.id.connect_button);
        EditText ipText = findViewById(R.id.ip_text);
        EditText portText = findViewById(R.id.port_text);
        connectButton.setOnClickListener(v -> {
            String ip = ipText.getText().toString();
            int port = Integer.parseInt(portText.getText().toString());
            new Thread(() -> {
                viewModel.connect(ip, port);
            }).start();
        });
    }

    private void throttleSeekbarListener() {
        SeekBar throttleSeekbar = findViewById(R.id.throttle);
        throttleSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    double nProgress = progress / 100.0;
                    System.out.println(nProgress);
                    viewModel.sendThrottle(nProgress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void rudderSeekbarListener() {
        SeekBar rudderSeekbar = findViewById(R.id.rudder);
        rudderSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    double nProgress = (progress - 50) / 50.0;
                    System.out.println(nProgress);
                    viewModel.sendRudder(nProgress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
}