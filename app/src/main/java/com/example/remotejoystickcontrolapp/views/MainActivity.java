package com.example.remotejoystickcontrolapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.remotejoystickcontrolapp.R;
import com.example.remotejoystickcontrolapp.databinding.ActivityMainBinding;
import com.example.remotejoystickcontrolapp.model.FGPlayer;
import com.example.remotejoystickcontrolapp.view_model.ViewModel;

public class MainActivity extends AppCompatActivity {


    // Fields
    private FGPlayer fgPlayer;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        this.fgPlayer = new FGPlayer();
        this.viewModel = new ViewModel(fgPlayer);
        activityMainBinding.setViewModel(viewModel);

    }
}