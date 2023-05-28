package com.game.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PlayersNameActivity extends AppCompatActivity {
    private Button startGameBtn;
    private EditText playerOne, playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_name);

        startGameBtn = findViewById(R.id.btnStartgame);
        playerOne = findViewById(R.id.edtPlayer1);
        playerTwo = findViewById(R.id.edtPlayer2);

        playerOne.setSelection(playerOne.getText().toString().length());
        playerTwo.setSelection(playerTwo.getText().toString().length());

        startGameBtn.setOnClickListener(v  -> {
            String player1name = playerOne.getText().toString().trim();
            String player2name = playerTwo.getText().toString().trim();

            if (TextUtils.isEmpty(player1name)) {
                playerOne.setError("Nie podano nazwy pierwszego gracza");
                return;
            }

            if (TextUtils.isEmpty(player2name)) {
                playerTwo.setError("Nie podano nazwy drugiego gracza");
                return;
            }

            Intent intent = new Intent(PlayersNameActivity.this, GameWithFriendActivity.class);
            intent.putExtra("player1name", playerOne.getText().toString());
            intent.putExtra("player2name", playerTwo.getText().toString());
            startActivity(intent);
        });
    }
}
