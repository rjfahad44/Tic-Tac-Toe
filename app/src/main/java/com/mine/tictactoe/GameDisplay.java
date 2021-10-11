package com.mine.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game_display);

        Button playAgainBTN = findViewById(R.id.playAgain_Button);
        Button homeBTN = findViewById(R.id.home_Button);
        TextView playerTurnBTN = findViewById(R.id.player_display);
        TextView Player1score = findViewById(R.id.player1score);
        TextView Player2score = findViewById(R.id.player2score);

        // winning Lottie Animation
        LottieAnimationView winner = findViewById(R.id.winningAnimation);
        winner.setVisibility(View.GONE);


        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);


        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAME");

        Player1score.setText(playerNames[0] + " : ");
        Player2score.setText(playerNames[1] + " : ");

        Player1score.setVisibility(View.VISIBLE);
        Player2score.setVisibility(View.VISIBLE);

        if (playerNames != null){
            playerTurnBTN.setText((playerNames[0] + "'s Turn"));
        }

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);

        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurnBTN, playerNames, Player1score, Player2score, winner);
    }

    public void playAgainButtonClick(View view) {
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }

    public void homeButtonClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}