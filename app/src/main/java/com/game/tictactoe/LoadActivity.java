package com.game.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LoadActivity extends AppCompatActivity {
    private static int LOAD_SCREEN = 2000;

    @Override
    protected void onCreate(Bundle saveInstanceState){
            super.onCreate(saveInstanceState);
            setContentView(R.layout.activity_load);

            new Handler() .postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent Load = new Intent(LoadActivity.this, HomeActivity.class);
                    startActivity(Load);
                    finish();
                }
            }, LOAD_SCREEN);
    }
}
