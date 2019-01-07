package com.example.nene.InLasVegas;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PRINT";
    /**
     * total scores
     */
    public int p1TotalScore;
    public int p2TotalScore;
    /**
     * current turn accumulative
     */
    public int turnScore;
    /**
     * 1=p1
     * 2=p2
     */
    public int turn;

    private int currentSong;
    private int currentAvatar1;
    private int currentAvatar2;


    final Handler handler= new Handler();

    MediaPlayer bgMusic;
    Button row;
    Button stop;
    Button DJ;
    Switch mute;
    TextView currentPoint;
    ProgressBar p1Total;
    ProgressBar p2Total;
    GifImageView bg;
    ImageView dice;
    GifImageView p1;
    GifImageView p2;
    TextView p1NameTag;
    TextView p2NameTag;
    private boolean isMute;
    private boolean started;
    private boolean pvp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        mute.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setMute();
            }
        });

        DJ.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSong();
            }
        });
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pause();
                play();
                bringBackDice();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewTurn();
            }
        });

    }


    public void setMute(){

        if (!isMute) {
            this.bgMusic.pause();
            isMute=true;
        }else{
            bgMusic.start();
            isMute=false;
        }
    }

    public void changeBackground(){

        Random rand= new Random();
        int newBG=rand.nextInt(11);
        switch (newBG){
            case 1:
                bg.setImageResource(R.drawable.g1);
                break;

            case 2:
                bg.setImageResource(R.drawable.g2);
                break;

            case 3:
                bg.setImageResource(R.drawable.g3);
                break;

            case 4:
                bg.setImageResource(R.drawable.g4);
                break;

            case 5:
                bg.setImageResource(R.drawable.g5);
                break;

            case 6:
                bg.setImageResource(R.drawable.g6);
                break;

            case 7:
                bg.setImageResource(R.drawable.g7);
                break;

            case 8:
                bg.setImageResource(R.drawable.g8);
                break;

            case 9:
                bg.setImageResource(R.drawable.g9);
                break;

            case 10:
                bg.setImageResource(R.drawable.g10);
                break;

        }
    }

    public void changeSong(){

        changeBackground();
        bgMusic.reset();

        if (currentSong==8) currentSong=0;
        currentSong++;
        switch (currentSong){
            case 1:
                bgMusic=MediaPlayer.create(this, R.raw.bg1);
                break;

            case 2:
                bgMusic=MediaPlayer.create(this, R.raw.bg2);
                break;

            case 3:
                bgMusic=MediaPlayer.create(this, R.raw.bg3);
                break;

            case 4:
                bgMusic=MediaPlayer.create(this, R.raw.bg4);
                break;

            case 5:
                bgMusic=MediaPlayer.create(this, R.raw.bg5);
                break;

            case 6:
                bgMusic=MediaPlayer.create(this, R.raw.bg6);
                break;

            case 7:
                bgMusic=MediaPlayer.create(this, R.raw.bg7);
                break;

            case 8:
                bgMusic=MediaPlayer.create(this, R.raw.bg8);
                break;

        }
        bgMusic.setLooping(true);
        bgMusic.start();

    }


    public void checkWin(){

        if (p1TotalScore>=100){
            row.setClickable(false);
            stop.setClickable(false);
            TextView pointTag=findViewById(R.id.currentpointTag);
            pointTag.setText("Ewon wins!!");
            currentPoint.setText("Total score: "+p1TotalScore);
        }
        else if(p2TotalScore>=100){
            row.setClickable(false);
            stop.setClickable(false);
            TextView pointTag=findViewById(R.id.currentpointTag);
            pointTag.setText("Iris wins!!");

            currentPoint.setText("Total score: "+p2TotalScore);

        }

    }

    public void initialize(){
        p1TotalScore=0;
        p2TotalScore=0;
        turnScore=0;
        turn=1;
        currentSong=1;
        isMute=false;
        pvp=true;

        bgMusic=MediaPlayer.create(this, R.raw.bg1);
        bgMusic.setLooping(true);
        bgMusic.start();
        row=(Button) findViewById(R.id.button);
        stop=(Button) findViewById(R.id.stop);
        DJ=(Button) findViewById(R.id.changeMusic);
        mute=(Switch) findViewById(R.id.mute);
        currentPoint = (TextView) findViewById(R.id.currentpoint);
        p1Total=(ProgressBar) findViewById(R.id.p1point);
        p2Total=(ProgressBar) findViewById(R.id.p2point);
        bg=(GifImageView) findViewById(R.id.background);
        p1=(GifImageView) findViewById(R.id.p1);
        p2=(GifImageView) findViewById(R.id.p2);
        dice=(ImageView) findViewById(R.id.dice);
        p1NameTag=(TextView) findViewById(R.id.p1NameTag);
        p2NameTag=(TextView) findViewById(R.id.p2nameTag);

        bg.setImageResource(R.drawable.g1);
        p1.setImageResource(R.drawable.a1);
        p2.setImageResource(R.drawable.a2);

        currentPoint.setText(Integer.toString(turnScore));
        p1Total.setProgress(p1TotalScore);
        p2Total.setProgress(p2TotalScore);

        indicatePlayer();
    }

    public void nextTurn(){

        this.turnScore = 0;
        this.currentPoint.setText("0");

        if (this.turn==1){
            this.turn=2;
        }else{
            this.turn=1;
        }

        indicatePlayer();
        Random rand=new Random();
        if(rand.nextInt(5)==0) {
            changeBackground();
        }
        setDiceInvisible();

    }

    public void pause(){

        this.row.setClickable(false);
        this.stop.setClickable(false);

    }

    public void restart(){
        this.row.setClickable(true);
        this.stop.setClickable(true);
    }

    public void indicatePlayer(){
        if (this.turn==1){
            this.p1NameTag.setTypeface(null,Typeface.BOLD);
            this.p1NameTag.setTextSize(20);
            this.p2NameTag.setTypeface(null,Typeface.NORMAL);
            this.p2NameTag.setTextSize(14);
        }else{
            this.p2NameTag.setTypeface(null,Typeface.BOLD);
            this.p2NameTag.setTextSize(20);
            this.p1NameTag.setTypeface(null,Typeface.NORMAL);
            this.p1NameTag.setTextSize(14);

        }
    }

    /**
     * Add score to total,
     * clear turn score,
     * shift turn
     */
    public void setNewTurn() {


        if (this.turn == 1) {
            this.p1TotalScore += turnScore;
        } else {
            this.p2TotalScore += turnScore;
        }

        p1Total.setProgress(p1TotalScore);
        p2Total.setProgress(p2TotalScore);
        checkWin();

        pause();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextTurn();
            }
        }, 1000);
        restart();

    }


    public void setDiceInvisible(){
        this.dice.setVisibility(View.INVISIBLE);
    }

    public void bringBackDice(){
        this.dice.setVisibility(View.VISIBLE);
    }

    public void play(){
        if (pvp){
            row();
        }

        else{
            switch (turn){

                case 1:
                    row();
                    break;

                case 2:
                    computerPlay();
            }
        }
    }

    public void row(){

        this.started=true;

        Random rand= new Random();
        int result = rand.nextInt(6)+1;
        pause();
        switch (result){

            case 1:
                dice.setImageResource(R.drawable.dice1);
                result=0;
                turnScore=0;
                setNewTurn();
                break;

            case 2:

                dice.setImageResource(R.drawable.dice2);
                break;

            case 3:
                dice.setImageResource(R.drawable.dice3);
                break;

            case 4:
                dice.setImageResource(R.drawable.dice4);
                break;

            case 5:
                dice.setImageResource(R.drawable.dice5);
                break;

            case 6:
                dice.setImageResource(R.drawable.dice6);
                break;


        }

        this.turnScore += result;
        this.currentPoint.setText(Integer.toString(turnScore));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setDiceInvisible();
                restart();
            }
        }, 1000);
    }

    public void computerPlay(){

        //pause the raw and stop functions
        pause();

        // while still pc's turn
        while(true){

            //row the dice
            row();
        }
    }

}

