package com.mine.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView title;
    Button playbtn;
    Animation topanim, bottomanim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);




        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        title = findViewById(R.id.textView);
        playbtn = findViewById(R.id.button);

        title.setAnimation(topanim);
        playbtn.setAnimation(bottomanim);
    }

    public void PlayButtonClick(View view){

        Intent intent = new Intent(this, PlayerSetup.class);
        startActivity(intent);

//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.clicksound);
//        mp.start();

//        Toast.makeText(this, "main activity", Toast.LENGTH_LONG).show();
    }
}