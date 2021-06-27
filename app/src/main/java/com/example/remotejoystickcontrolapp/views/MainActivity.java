package com.example.remotejoystickcontrolapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
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

        FGPlayer fgPlayer = new FGPlayer();
        this.viewModel = new ViewModel(fgPlayer);
        activityMainBinding.setViewModel(viewModel);

        activityMainBinding.joystick.joystickListener = (a, e) -> {
            viewModel.setAileron(a / activityMainBinding.joystick.outerCircleR);
            viewModel.setElevator(e  / activityMainBinding.joystick.outerCircleR);
        };

        activityMainBinding.connectButton.setOnClickListener(v -> {
            String ip = activityMainBinding.ipText.getText().toString();
            String port = activityMainBinding.portText.getText().toString();
            int port_num;
            try {
                port_num = Integer.parseInt(port);
                new Thread(() -> viewModel.connect(ip, port_num)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        activityMainBinding.throttle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setThrottle(progress / 100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        activityMainBinding.rudder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setRudder((progress - 50) / 50.0);
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