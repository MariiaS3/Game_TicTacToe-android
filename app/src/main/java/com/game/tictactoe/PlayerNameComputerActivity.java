package com.game.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerNameComputerActivity extends AppCompatActivity {
    private Button startGameBtn;
    private EditText player;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_name_computer);

        startGameBtn = findViewById(R.id.btnStartgame);
        player = findViewById(R.id.edtPlayer1);

        player.setSelection(player.getText().toString().length());

        startGameBtn.setOnClickListener(v  -> {
            String playername = player.getText().toString().trim();

            if (TextUtils.isEmpty(playername)) {
                player.setError("Nie podano nazwy gracza");
                return;
            }

            Intent intent = new Intent(PlayerNameComputerActivity.this, GameWithComputerActivity.class);
            intent.putExtra("player1name", player.getText().toString());
            intent.putExtra("player2name", "Komputer");
            startActivity(intent);
            finish();
        });
    }
}
