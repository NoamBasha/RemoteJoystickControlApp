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


    // Fields
    private FGPlayer fgPlayer;
    private ViewModel viewModel;
    private SeekBar throttleSeekbar;
    private SeekBar rudderSeekbar;
    private Joystick joystick;
    private String ip;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        this.fgPlayer = new FGPlayer();
        this.viewModel = new ViewModel(fgPlayer);
        activityMainBinding.setViewModel(viewModel);



        this.joystick = findViewById(R.id.joystick);
        joystick.joystickListener = (a, e)-> {
            this.viewModel.sendAileron(a);
            this.viewModel.sendElevator(e);
        };
        connectButtonListener();
        throttleSeekbarListener();
        rudderSeekbarListener();
    }

    private void rudderSeekbarListener() {
        //TODO
    }

    private void throttleSeekbarListener() {
        //TODO
    }

    private void connectButtonListener() {
        Button connectButton = findViewById(R.id.connect_button);
        EditText ipText = findViewById(R.id.ip_text);
        EditText portText = findViewById(R.id.port_text);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ipText.getText().toString();
                int port = Integer.parseInt(portText.getText().toString());
                try {
                    viewModel.connect(ip, port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}