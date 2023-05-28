package com.game.tictactoe;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameWithComputerActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imgHome, imgPlayers;
    Button btnResetScore;
    private TextView playerScore, computerScore, matchDrawScore;
    private Button resetBtn;
    private String PlayerName, ComputerName;
    public String score;
    TextView Tv[] = new TextView[9];
    char x = 'X', o = 'O';
    char[] arr = new char[9];
    boolean c[] = new boolean[9];
    private int playerPoints = 0, computerPoints = 0, matchDrawPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_with_computer);

        imgHome = findViewById(R.id.imgHome);
        imgPlayers = findViewById(R.id.imgPlayers);

        for (int i = 0; i < 9; i++) {
            c[i] = false;
        }

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
                startActivity(new Intent(getApplicationContext(), PlayerNameComputerActivity.class));
                finish();
            }
        });

        initial();

        PlayerName = getIntent().getStringExtra("player1name");
        ComputerName = getIntent().getStringExtra("player2name");
        playerScore.setText(PlayerName + " (X): 0");
        computerScore.setText(ComputerName + " (O): 0");

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        btnResetScore = findViewById(R.id.btnResetScore);
        btnResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerPoints = 0;
                computerPoints = 0;
                matchDrawPoints = 0;
                reset();
                updatePointText();
            }

            private void updatePointText() {
                playerScore.setText(PlayerName + " (X): " + playerPoints);
                computerScore.setText(ComputerName + " (O): " + computerPoints);
                matchDrawScore.setText("Remis: " + matchDrawPoints);
            }
        });
    }

    private void initial() {
        for (int i = 0, l=0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String id_name = "btn_" + i + j;
                int btnId = this.getResources().getIdentifier(id_name, "id", getPackageName());
                Tv[l] = findViewById(btnId);
                Tv[l].setOnClickListener(this);
                l++;
            }
        }
        playerScore = findViewById(R.id.p1TV);
        computerScore = findViewById(R.id.p2TV);
        matchDrawScore = findViewById(R.id.dTv);
        resetBtn = findViewById(R.id.resetBtn);
    }

    @Override
    public void onClick(View v) {
            setText(v);
            setEnabled(false);
            if (!checkResultForX()) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        condition();

                        int[] arro = cellsOfO();

                        for (int i = 0; i < 9; i++) {
                            c[i] = false;
                        }

                        for (int i = 0; i < arro.length; i++) {
                            c[arro[i]] = true;
                        }

                        checkWhoWon(o);
                        setEnabled(true);
                    }
                }, 500);
            }
    }

    private int countOfEmptyCells() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (!(arr[i] == x || arr[i] == o)) {
                count++;
            }
        }
        return count;
    }

    private int[] cellsOfX() // cells of x that filled
    {
        int count_Of_Array_Length = 0;

        for (int i = 0; i < 9; i++) {
            if (arr[i] == x) {
                // count of x in arr for arrx length
                ++count_Of_Array_Length;
            }
        }

        int[] arrx = new int[count_Of_Array_Length];
        int j = 0;

        for (int i = 0; i < 9; i++) {
            if (arr[i] == x) {
                arrx[j++] = i;
            }
        }
        return arrx;
    }

    private int[] cellsOfO() // cells of o that filled
    {
        int count_Of_Array_Length = 0;

        for (int i = 0; i < 9; i++) {
            if (arr[i] == o) {
                // count of o in arr for arro length
                ++count_Of_Array_Length;
            }
        }

        int[] arro = new int[count_Of_Array_Length];
        int j = 0;

        for (int i = 0; i < 9; i++) {
            if (arr[i] == o) {
                arro[j++] = i;
            }
        }
        return arro;
    }

    private void condition() {
        if (!suggestionOnOSituation()) {
            int[] arrx = cellsOfX();

            for (int i = 0; i < 9; i++) {
                c[i] = false;
            }


            for (int i = 0; i < arrx.length; i++) {
                c[arrx[i]] = true;

            }
            int cellO = returnOCell();

            for (int i = 0; i < 9; i++) {
                c[i] = false;
            }

            if (cellO == -1) {
                setToRandomCell();
            } else if (arr[cellO] == '\u0000') {
                setText(cellO);
            }
        }
    }

    private boolean suggestionOnOSituation() {
        boolean result = false;

        int[] arro = cellsOfO();

        for (int i = 0; i < 9; i++) {
            c[i] = false;
        }

        for (int i = 0; i < arro.length; i++) {
            c[arro[i]] = true;

        }

        int cellO = returnOCell();
        for (int i = 0; i < 9; i++) {
            c[i] = false;
        }

        if (cellO == -1) {
            result = false;
        } else if (arr[cellO] == '\u0000') {
            setText(cellO);
            result = true;
        }

        return result;
    }

    private void setToRandomCell() {

        int[] emptyCells = new int[countOfEmptyCells()];
        int j = 0;
        for (int i = 0; i < 9; i++) {
            if (arr[i] == '\u0000') {
                emptyCells[j++] = i;
            }
        }

        Random random = new Random();
        int r = random.nextInt(emptyCells.length);
        setText(emptyCells[r]);
    }


    private int returnOCell() {
        int cell = -1;

        if ((c[0] && c[2]) || (c[7] && c[4])) {
            if (arr[1] == '\u0000') {
                cell = 1;
            }
        }

        if ((c[0] && c[1]) || (c[5] && c[8]) || (c[4] && c[6])) {
            if (arr[2] == '\u0000') {
                cell = 2;
            }
        }

        if ((c[1] && c[2]) || (c[6] && c[3]) || (c[4] && c[8])) {
            if (arr[0] == '\u0000') {
                cell = 0;
            }
        }

        if ((c[0] && c[3]) || (c[2] && c[4]) || (c[7] && c[8])) {
            if (arr[6] == '\u0000') {
                cell = 6;
            }
        }

        if ((c[0] && c[6]) || (c[5] && c[4])) {
            if (arr[3] == '\u0000') {
                cell = 3;
            }
        }

        if ((c[0] && c[4]) || (c[2] && c[5]) || (c[6] && c[7])) {
            if (arr[8] == '\u0000') {
                cell = 8;
            }
        }

        if ((c[0] && c[8]) || (c[1] && c[7]) || (c[2] && c[6]) || (c[3] && c[5])) {
            if (arr[4] == '\u0000') {
                cell = 4;
            }
        }

        if ((c[1] && c[4]) || (c[6] && c[8])) {
            if (arr[7] == '\u0000') {
                cell = 7;
            }
        }

        if ((c[2] && c[8]) || (c[3] && c[4])) {

            if (arr[5] == '\u0000') {
                cell = 5;
            }
        }
        return cell;
    }


    private void setText(View v) {
        TextView textView = (TextView) v;
        int id = v.getId();

        textView.setEnabled(false);
        textView.setText(String.valueOf(x));
        textView.setTextColor(getResources().getColor(R.color.x_color));

        switch (id) {
            case R.id.btn_00:
                arr[0] = x;
                break;
            case R.id.btn_01:
                arr[1] = x;
                break;
            case R.id.btn_02:
                arr[2] = x;
                break;
            case R.id.btn_10:
                arr[3] = x;
                break;
            case R.id.btn_11:
                arr[4] = x;
                break;
            case R.id.btn_12:
                arr[5] = x;
                break;
            case R.id.btn_20:
                arr[6] = x;
                break;
            case R.id.btn_21:
                arr[7] = x;
                break;
            case R.id.btn_22:
                arr[8] = x;
                break;
        }
    }

    private void setText(int cellIndex) {
        arr[cellIndex] = o;
        Tv[cellIndex].setEnabled(false);
        Tv[cellIndex].setText(String.valueOf(o));
        Tv[cellIndex].setTextColor(getResources().getColor(R.color.o_color));
    }

    private boolean checkResultForX() {
        int[] arrx = cellsOfX();

        for (int i = 0; i < 9; i++) {
            c[i] = false;
        }

        for (int i = 0; i < arrx.length; i++) {
            c[arrx[i]] = true;
        }
        return checkWhoWon(x);
    }

    private boolean checkWhoWon(char ch) {
        boolean result = false;
        boolean draw = true;
        boolean makedialog = false;
        int count_Of_Array_Length = 0;
        for (int i = 0; i < 9; i++) {
            if (arr[i] != '\u0000') {
                ++count_Of_Array_Length;
            }
        }

        if ((c[0] && c[2] && c[1]) || (c[0] && c[3] && c[6]) || (c[0] && c[4] && c[8]) || (c[1] && c[4] && c[7])
                || (c[2] && c[5] && c[8]) || (c[2] && c[4] && c[6]) || (c[3] && c[4] && c[5]) || (c[6] && c[7] && c[8])) {
            draw = false;
            makedialog = true;
        }

        if (ch == x && count_Of_Array_Length == 9 && draw) {
            // draw
            result = true;
            makeDialog('d');
        }

        if (makedialog) {
            result = true;
            makeDialog(ch);
        }

        for (int i = 0; i < 9; i++) {
            c[i] = false;
        }
        return result;
    }

    private void makeDialog(char ch) {
        androidx.appcompat.app.AlertDialog.Builder alertBilder = new androidx.appcompat.app.AlertDialog.Builder(GameWithComputerActivity.this);
        View activityDialog = getLayoutInflater().inflate(R.layout.activity_message_dialog, null);

        TextView tv_message = activityDialog.findViewById(R.id.textTitle);
        ((TextView) activityDialog.findViewById(R.id.textMessage)).setText(getResources().getString(R.string.message_Play));
        ((TextView) activityDialog.findViewById(R.id.buttonNo)).setText(getResources().getString(R.string.no));
        ((TextView) activityDialog.findViewById(R.id.buttonYes)).setText(getResources().getString(R.string.yes));
        alertBilder.setView(activityDialog);
        final AlertDialog dialog = alertBilder.create();
        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } catch (Exception e) {

        }
        dialog.setCancelable(false);

        if (ch == x) {
            playerPoints++;
            updatePointsText();
            score = PlayerName;
            tv_message.setText("Wygrałeś!!!");
        } else if (ch == o) {
            computerPoints++;
            updatePointsText();
            tv_message.setText("Wygrana komputera!!!");
        } else {
            matchDrawPoints++;
            updatePointsText();
            tv_message.setText("Remis!!!");
        }

        activityDialog.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                reset();
            }
        });

        activityDialog.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(GameWithComputerActivity.this, HomeActivity.class));
                finish();
            }
        });
        dialog.show();
    }

    private void updatePointsText() {
        playerScore.setText(PlayerName + " (X): " + playerPoints);
        computerScore.setText(ComputerName + " (O): " + computerPoints);
        matchDrawScore.setText("Remis : " + matchDrawPoints);
    }

    private void reset() {
        for (int i = 0; i < 9; i++) {
            Tv[i].setEnabled(true);
            Tv[i].setText("");
        }

        for (int i = 0; i < 9; i++) {
            c[i] = false;
        }

        for (int i = 0; i < 9; i++) {
            arr[i] = '\u0000';
        }

    }

    private void setEnabled(boolean f) {
        if (f) {
            for (int i = 0; i < 9; i++) {
                if (arr[i] == '\u0000') {
                    Tv[i].setEnabled(f);
                }
            }
        } else {
            for (int i = 0; i < 9; i++) {
                    Tv[i].setEnabled(f);
            }
        }
    }

}