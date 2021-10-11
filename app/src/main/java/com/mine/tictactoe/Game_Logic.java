package com.mine.tictactoe;

import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import static com.mine.tictactoe.R.raw.clicksound;


public class Game_Logic extends AppCompatActivity{

    private int[][] gameBoard;

    private int[] winType = {-1, -1, -1};

    private String[] playerName = {"Player 1", "Player 2"};
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    private TextView Player1score;
    private TextView Player2score;
    private LottieAnimationView winner;


    private int p1Score = 0;
    private int p2Score = 0;

    private int player = 1;

    private boolean isPlayerTurn = true;



    Game_Logic(){
        gameBoard = new int[3][3];
        for(int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row, int col){

        if (gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player;


            if (player == 1){
                playerTurn.setText((playerName[1] + "'s Turn"));
            }
            else {
                playerTurn.setText((playerName[0] + "'s Turn"));

            }

            return true;
        }
        else {
            return false;
        }
    }


    public boolean winnerCheck(){

        boolean isWinner = false;
        // Horizontal Check (winType == 1)
        for (int r=0; r<3; r++){
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0){
                winType = new int[] {r, 0, 1};
                isWinner = true;
            }
        }

        // Vertical Check (winType == 2)
        for (int c=0; c<3; c++){
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[2][c] == gameBoard[0][c] && gameBoard[0][c] != 0){
                winType = new int[] {0, c, 2};
                isWinner = true;
            }
        }

        // Negative Diagonal Check (winType == 3)
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0){
            winType = new int[] {0, 2, 3};
            isWinner = true;
        }

        // Positive Diagonal Check (winType == 4)
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0){
            winType = new int[] {2, 2, 4};
            isWinner = true;
        }

        int boardFilled = 0;

        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                if (gameBoard[r][c] != 0){
                    boardFilled += 1;
                }
            }
        }

        if (isWinner){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);

//            winner = findViewById(R.id.winningAnimation);
//            winner.setVisibility(View.VISIBLE);
//            winner.playAnimation();

            playerTurn.setText((playerName[player-1] + " Won!!!"));


            if (playerName[player-1] == playerName[0]){
                p1Score += 1;
                isPlayerTurn = true;
                Player1score.setText(playerName[player-1] + " : " + p1Score);
            }
             else {
                 p2Score += 1;
                 isPlayerTurn = false;
                 Player2score.setText(playerName[player-1] + " : " + p2Score);

             }

            return true;
        }
        else if (boardFilled == 9){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!!");
            return true;
        }
        else {
            return false;
        }
    }


    public void resetGame(){
        for(int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }

        player = 1;
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        if(isPlayerTurn) {
            playerTurn.setText((playerName[0] + "'s Turn"));

//            isPlayerTurn = false;
        }
        else {
            playerTurn.setText((playerName[1] + "'s Turn"));

//            isPlayerTurn = true;
        }

    }

    public void setPlayAgainBTN(Button playAgainBTN){
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN){
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setPlayerNAme(String[] playerNAme) {
        this.playerName = playerNAme;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player){
        this.player = player;
    }

    public int getPlayer(){
        return player;
    }

    public void setPlayer1score(TextView Player1score){
        this.Player1score = Player1score;
    }

    public void setPlayer2score(TextView Player2score){
        this.Player2score = Player2score;
    }

    public int[] getWinType() { return winType; }

    public void getWinner(LottieAnimationView winner){
        this.winner = winner;
    }
}
