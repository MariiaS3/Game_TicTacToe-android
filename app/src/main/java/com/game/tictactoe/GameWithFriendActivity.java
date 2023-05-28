package com.game.tictactoe;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameWithFriendActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imgHome, imgPlayers;
    Button btnResetScore;
    private TextView player1Score, player2Score, matchDrawScore;
    private Button resetBtn;
    private Button[][] btns = new Button[3][3];
    private int player1Points = 0, player2Points = 0, matchDrawPoints = 0;
    private int roundCount = 0;
    private Boolean player1Tern = true;
    private String Player1Name, Player2Name;
    public String score;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_with_friend);

        imgHome = findViewById(R.id.imgHome);
        imgPlayers = findViewById(R.id.imgPlayers);

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        imgPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlayersNameActivity.class));
                finish();
            }
        });

        initial();

        Player1Name = getIntent().getStringExtra("player1name");
        Player2Name = getIntent().getStringExtra("player2name");
        player1Score.setText(Player1Name + " (X): 0");
        player2Score.setText(Player2Name + " (O): 0");

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
            }
        });
        btnResetScore = findViewById(R.id.btnResetScore);
        btnResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    private void initial(){
        player1Score = findViewById(R.id.p1TV);
        player2Score = findViewById(R.id.p2TV);
        matchDrawScore = findViewById(R.id.dTv);
        resetBtn = findViewById(R.id.resetBtn);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String id_name = "btn_" + i + j;
                int btnId = this.getResources().getIdentifier(id_name, "id", getPackageName());
                btns[i][j] = findViewById(btnId);
                btns[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v){
        if(player1Tern){
            ((Button) v).setText("x");
            ((Button) v).setTextColor(this.getResources().getColor(R.color.x_color));
            ((Button) v).setEnabled(false);
        } else{
            ((Button) v).setText("o");
            ((Button) v).setTextColor(this.getResources().getColor(R.color.o_color));
            ((Button) v).setEnabled(false);
        }
        roundCount++;

        if(checkWin()){
            if(player1Tern){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount == 9){
            draw();
        } else {
            player1Tern = !player1Tern;
        }
    }

    private boolean checkWin(){
        String field[][] = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = btns[i][j].getText().toString();
            }
        }

        //columns
        for (int i = 0; i < 3; i++) {
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }

        //rows
        for (int i = 0; i < 3; i++) {
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }

        //diagonal
        if((field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
                ||field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    private void draw(){
        matchDrawPoints++;
        updatePointText();
        askForAnotherGame("Remis!");
        score = "Remis";
    }

    private void player1Wins(){
        player1Points++;
        updatePointText();
        score = Player1Name;
        askForAnotherGame(score + " wygrał!!!");
    }

    private void player2Wins(){
        player2Points++;
        updatePointText();
        score = Player2Name;
        askForAnotherGame(score + " wygrał!!!");
    }

    private void resetBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                btns[i][j].setText("");
                btns[i][j].setEnabled(true);
            }
        }
        roundCount = 0;
        player1Tern = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        matchDrawPoints = 0;
        resetBoard();
        updatePointText();
    }

    private void updatePointText() {
        player1Score.setText(Player1Name + " (X): " + player1Points);
        player2Score.setText(Player2Name + " (O): " + player2Points);
        matchDrawScore.setText("Remis : " + matchDrawPoints);

    }

    private void askForAnotherGame(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameWithFriendActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(GameWithFriendActivity.this).inflate(
                R.layout.activity_message_dialog,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(msg);
        ((TextView) view.findViewById(R.id.textMessage)).setText(getResources().getString(R.string.message_Play));
        ((TextView) view.findViewById(R.id.buttonNo)).setText(getResources().getString(R.string.no));
        ((TextView) view.findViewById(R.id.buttonYes)).setText(getResources().getString(R.string.yes));
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                resetBoard();
            }
        });
        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(GameWithFriendActivity.this, HomeActivity.class));
                finish();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
