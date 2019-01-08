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
    private int intelligentLevel;
    private int winScore;


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
    TextView pointTag;

    private boolean isMute;
    private boolean started;
    private boolean pvp;

    Random rand;


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
                pointTag.setText("Your Point");
                currentPoint.setVisibility(View.VISIBLE);
                pause();
                row();
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

        if (p1TotalScore>=winScore){
            row.setClickable(false);
            stop.setClickable(false);
            pointTag.setText("Ewon wins!!");
            currentPoint.setText("Total score: "+p1TotalScore);
        }
        else if(p2TotalScore>=winScore){
            row.setClickable(false);
            stop.setClickable(false);
            TextView pointTag=findViewById(R.id.currentpointTag);
            if (pvp){
                pointTag.setText("Iris wins!!");
            }else{
                pointTag.setText("Super Intelligence outcomes humans! Doomed!");
            }

            currentPoint.setText("Total score: "+p2TotalScore);

        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 2000);

    }

    public void initialize(){
        p1TotalScore=0;
        p2TotalScore=0;
        turnScore=0;
        rand=new Random();
        turn=rand.nextInt(1)+1;
        currentSong=1;
        winScore=20;
        started=false;
        isMute=false;
        pvp=false;

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
        pointTag=(TextView) findViewById(R.id.currentpointTag);
        bg.setImageResource(R.drawable.g1);
        p1.setImageResource(R.drawable.a1);
        p2.setImageResource(R.drawable.a2);

        currentPoint.setText(Integer.toString(turnScore));
        p1Total.setProgress(p1TotalScore);
        p2Total.setProgress(p2TotalScore);

        if (!pvp){
            p2NameTag.setText("Super Intelligence");
        }

        pointTag.setText("Start from "+ getCurrentPlayerName() );
        currentPoint.setVisibility(View.INVISIBLE);

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
        if(rand.nextInt(5)==0) {
            changeBackground();
        }
        setDiceInvisible();

        if (pvp||turn==1){
            restart();
        }else if ((!pvp)&&turn==2){
            //pause for a sec
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    row();
                }
            }, 500);
        }

    }

    public void pause(){

        this.row.setEnabled(false);
        this.stop.setEnabled(false);


    }

    public void restart(){
        this.row.setEnabled(true);
        this.stop.setEnabled(true);

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

    }


    public void setDiceInvisible(){
        this.dice.setVisibility(View.INVISIBLE);
    }

    public void bringBackDice(){
        this.dice.setVisibility(View.VISIBLE);
    }

    public void changeDice(int result){

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
    }

    public void computerThinksForAWhile(int result){
        if (intelligentLevel==1){

        }
        if (intelligentLevel==2&&turnScore>=20) {
            setNewTurn();
        }
        if (intelligentLevel==3){
            if (rand.nextInt(3)==0){
                setNewTurn();
            }
        }

    }
    public void row(){

        this.started=true;

        int result = rand.nextInt(6)+1;
        pause();
        Log.i(TAG,Integer.toString(result));

        changeDice(result);

        this.turnScore += result;
        this.currentPoint.setText(Integer.toString(turnScore));

        if (turn==2&&!pvp){

            computerThinksForAWhile(result);

        }else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    setDiceInvisible();
                    if (turn == 1 || pvp) {
                        restart();
                        bringBackDice();

                    }

                    if (turn == 2 && !pvp) {
                        row();
                        bringBackDice();
                    }
                }
            }, 1000);
        }
    }


    public String getCurrentPlayerName(){

        if (turn==1){
            return p1NameTag.getText().toString();
        }else{
            return p2NameTag.getText().toString();
        }
    }

    public void reset(){

        p1TotalScore=0;
        p2TotalScore=0;
        turnScore=0;
        currentPoint.setText("0");
        p1Total.setProgress(0);
        p2Total.setProgress(0);
        started=false;
        restart();

        turn=rand.nextInt(1)+1;
        pointTag.setText("Start from P"+turn );
        currentPoint.setVisibility(View.INVISIBLE);


    }

}

