package com.mine.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;

public class TicTacToeBoard extends View {

    private final int boardcolor;
    private final int Xcolor;
    private final int Ocolor;
    private final int WinnerLineColor;

    private Animation topanim, bottomanim;
    private LottieAnimationView winner;

    private boolean winningLine = false;


    private final Paint paint = new Paint();

    private final Game_Logic game;

    private int cellSize = getWidth()/3;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new Game_Logic();


        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);


        try {
            boardcolor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            Xcolor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            Ocolor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            WinnerLineColor = a.getInteger(R.styleable.TicTacToeBoard_WinningLineColor, 0);

        }finally {
            a.recycle();

        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimensions = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimensions/3;

        setMeasuredDimension(dimensions, dimensions);
    }

//    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (winningLine){
            paint.setColor(WinnerLineColor);
            paint.setStrokeWidth(12);
            drawWinningLine(canvas);
        }
    }


//    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.clicksound);
        final MediaPlayer wmp = MediaPlayer.create(getContext(), R.raw.wonsound);

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            if (!winningLine) {
                //updating the players turn
                if (game.updateGameBoard(row, col)) {
                    invalidate();

                    if (game.winnerCheck()){
                        wmp.start();
                        winningLine = true;
                        invalidate();
                    }

                    if (game.getPlayer() % 2 == 0) {
                        mp.start();
                        game.setPlayer(game.getPlayer() - 1);

                    } else {
                        mp.start();
                        game.setPlayer(game.getPlayer() + 1);

                    }
                }
            }

            invalidate();

            return true;
        }

        return false;
    }

    private void drawGameBoard(Canvas canvas){

        paint.setColor(boardcolor);
        paint.setStrokeWidth(20);

        for(int c=1; c<3; c++){
            canvas.drawLine(cellSize*c,0, cellSize*c, canvas.getWidth(), paint);
        }

        for(int r=1; r<3; r++){
            canvas.drawLine(0, cellSize*r, canvas.getWidth(), cellSize*r, paint);
        }
    }

//    @SuppressLint("NewApi")
    private void drawMarkers(Canvas canvas){
        for(int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                if (game.getGameBoard() [r][c] != 0){
                    if (game.getGameBoard() [r][c] == 1){
                        drawX(canvas, r, c);
                    }
                    else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }


    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(Xcolor);

        canvas.drawLine((float) ((col+1)*cellSize - cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) (col*cellSize + cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                        paint);

        canvas.drawLine((float) (col*cellSize + cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) ((col+1)*cellSize - cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                        paint);
    }


    @SuppressLint("NewApi")
    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(Ocolor);

        canvas.drawOval((float) (col*cellSize + cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) ((col*cellSize + cellSize) - cellSize*0.2),
                        (float) ((row*cellSize + cellSize) - cellSize*0.2),
                        paint);
    }


    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col, row*cellSize + (float)cellSize/2, cellSize*3, row*cellSize + (float)cellSize/2, paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col*cellSize + (float)cellSize/2, row, col*cellSize + (float)cellSize/2, cellSize*3, paint);
    }

    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0, cellSize*3, cellSize*3, 0, paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas){
        canvas.drawLine(0, 0, cellSize*3, cellSize*3, paint);
    }


    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1: drawHorizontalLine(canvas, row, col); break;
            case 2: drawVerticalLine(canvas, row, col); break;
            case 3: drawDiagonalLineNeg(canvas); break;
            case 4: drawDiagonalLinePos(canvas); break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] name, TextView player1score, TextView player2score, LottieAnimationView winner){

        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNAme(name);
        game.setPlayer1score(player1score);
        game.setPlayer2score(player2score);
        game.getWinner(winner);

    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }
}
