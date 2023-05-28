package com.game.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btnFriend,btnComputer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFriend = findViewById(R.id.btnPlayWithFriend);
        btnComputer = findViewById(R.id.btnPlayWithComputer);

        btnComputer.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PlayerNameComputerActivity.class)));
        btnFriend.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PlayersNameActivity.class)));
    }
}
