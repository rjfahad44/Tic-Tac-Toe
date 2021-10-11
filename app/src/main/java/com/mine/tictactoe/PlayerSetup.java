package com.mine.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerSetup extends AppCompatActivity {

    private EditText player1;
    private EditText player2;

    TextView title;
    Button submitbutton;
    Animation topanim, bottomanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.player_setup);

        player1 = findViewById(R.id.Player1Name);
        player2 = findViewById(R.id.Player2Name);

        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        title = findViewById(R.id.playerSetupTitle);
        submitbutton = findViewById(R.id.SubmitBtn);

        title.setAnimation(topanim);
        submitbutton.setAnimation(bottomanim);

    }

    public void submitButtonClick(View view){
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();

        Intent intent = new Intent(this, GameDisplay.class);
        intent.putExtra("PLAYER_NAME", new String[] {player1Name, player2Name});
        startActivity(intent);
    }
}