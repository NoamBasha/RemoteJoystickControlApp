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

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Fields
        FGPlayer fgPlayer = new FGPlayer();
        this.viewModel = new ViewModel(fgPlayer);
        activityMainBinding.setViewModel(viewModel);

        Joystick joystick = findViewById(R.id.joystick);
        joystick.joystickListener = (a, e) -> {
            viewModel.sendAileron(a / 248.0);
            viewModel.sendElevator(e  / 248.0);
        };

        Button connectButton = findViewById(R.id.connect_button);
        EditText ipText = findViewById(R.id.ip_text);
        EditText portText = findViewById(R.id.port_text);
        SeekBar throttleSeekbar = findViewById(R.id.throttle);
        SeekBar rudderSeekbar = findViewById(R.id.rudder);

        connectButton.setOnClickListener(v -> {
            String ip = ipText.getText().toString();
            int port = Integer.parseInt(portText.getText().toString());
            new Thread(() -> {
                viewModel.connect(ip, port);
            }).start();
        });

        throttleSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    viewModel.sendThrottle(progress / 100.0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        rudderSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    viewModel.sendRudder((progress - 50) / 50.0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}