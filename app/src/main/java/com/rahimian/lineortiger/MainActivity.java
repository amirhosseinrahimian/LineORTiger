package com.rahimian.lineortiger;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //my views
    private TextView pone, ptwo, VS, appname, tigerscr, lionscr;
    private Button btnagain;
    private ImageView dark, zero;
    private GridLayout gridLayout;

    //enum
    enum currantpl {ONE, TWO, NULL}

    private MediaPlayer mediaPlayer;
    currantpl currantplayer = currantpl.ONE;
    currantpl[] status = new currantpl[9];
    int[][] winstatus = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.back));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getWindow().setNavigationBarDividerColor(getResources().getColor(R.color.orange));
            }
        }
        //findViewById
        zero = findViewById(R.id.zero);
        lionscr = findViewById(R.id.lionscore);
        tigerscr = findViewById(R.id.tigerscore);
        gridLayout = findViewById(R.id.gridLayout);
        appname = findViewById(R.id.appname);
        VS = findViewById(R.id.VS);
        dark = findViewById(R.id.dark);
        pone = findViewById(R.id.potxt);
        ptwo = findViewById(R.id.pttxt);
        btnagain = findViewById(R.id.btnagain);
        btnagain.setY(2000);
        pone.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
        ptwo.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
        for (int i = 0; i <= 8; i++) {
            status[i] = currantpl.NULL;
        }
        //BTN AGAIN !
        btnagain.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                VS.setText("VS");
                appname.setText(R.string.app_name);
                for (int i = 0; i <= 8; i++) {
                    status[i] = currantpl.NULL;
                }
                if (currantplayer == currantpl.ONE) {
                    ptwo.animate().translationY(0);ptwo.animate().translationX(0);
                } else if (currantplayer == currantpl.TWO) {
                    currantplayer = currantpl.ONE;
                    pone.animate().translationY(0);pone.animate().translationX(0);
                }
                VS.animate().translationYBy(50);
                pone.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
                ptwo.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    ImageView imageView = (ImageView) gridLayout.getChildAt(i);
                    imageView.setImageResource(0);
                    imageView.setBackgroundResource(R.color.orange);
                    imageView.setAlpha(.5f);
                    imageView.setClickable(true);
                }
                dark.setVisibility(View.INVISIBLE);
                zero.setVisibility(View.VISIBLE);
                lionscr.setVisibility(View.VISIBLE);
                tigerscr.setVisibility(View.VISIBLE);
                btnagain.animate().translationY(2000).setDuration(1000);
                VS.animate().alpha(1);
                currantplayer = currantpl.ONE;
            }
        });
    }
        @SuppressLint("SetTextI18n")
        public void imgIsClick (View imageview){
            zero.setRotation(0);
            ImageView tappedImageView = (ImageView) imageview;
            int tappedimageviewtag = Integer.parseInt(tappedImageView.getTag().toString());
            tappedImageView.setRotation(0);
            tappedImageView.setAlpha(.2f);
            tappedImageView.setScaleX(-1);
            tappedImageView.setScaleY(-1);
            //log tag
            status[tappedimageviewtag] = currantplayer;
            tappedImageView.animate().alpha(1).scaleY(1).scaleX(1).setDuration(100);
            if (currantplayer == currantpl.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currantplayer = currantpl.TWO;
                ptwo.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
                pone.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
            } else {
                tappedImageView.setImageResource(R.drawable.tiger);
                currantplayer = currantpl.ONE;
                pone.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).setDuration(100);
                ptwo.animate().alpha(.3f).scaleX(1).scaleY(1).setDuration(100);
            }
            tappedImageView.animate().rotation(360).setDuration(500);
            tappedImageView.setClickable(false);
            //WHO IS WINNER
            boolean isAWinner = false;
            for (int[] winner : winstatus) {
                if (status[winner[0]] == status[winner[1]] && status[winner[1]] == status[winner[2]] && status[winner[0]] != currantpl.NULL) {
                    Toast.makeText(MainActivity.this, "win!", Toast.LENGTH_SHORT).show();
                    isAWinner = true;
                    //invsibility
                    zero.setVisibility(View.INVISIBLE);
                    lionscr.setVisibility(View.INVISIBLE);
                    tigerscr.setVisibility(View.INVISIBLE);
                    mediaPlayer = MediaPlayer.create(this, R.raw.win);
                    mediaPlayer.start();
                    //TO uncheckable
                    tappedImageView.animate().rotation(3600).setDuration(500);
                    for (int i = 0; i < gridLayout.getChildCount(); i++) {
                        ImageView imageView = (ImageView) gridLayout.getChildAt(i);
                        imageView.setClickable(false);
                    }
                    for (int i = 0; i <= 2; i++) {
                        int index = winner[i];
                        ImageView imageView = (ImageView) gridLayout.getChildAt(index);
                        imageView.setBackgroundResource(R.color.colorAccent);
                    }
                    btnagain.animate().translationY(0).setDuration(1000);
                    dark.setVisibility(View.VISIBLE);
                    dark.animate().alpha(1).setDuration(100);

                    if (currantplayer == currantpl.ONE) {
                        int scr = Integer.parseInt(tigerscr.getText().toString());
                        scr++;
                        tigerscr.setText(scr + "");
                        pone.animate().alpha(0);
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            // landscape
                            ptwo.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).translationY(100).setDuration(100);
                        } else {
                            // portrait
                            ptwo.animate().alpha(1).scaleX(1.2f).scaleY(1.2f).translationX(+100).setDuration(100);
                        }

                        VS.animate().translationYBy(-50);

                    } else {
                        int scr = Integer.parseInt(lionscr.getText().toString());
                        scr++;
                        lionscr.setText(scr + "");
                        ptwo.animate().alpha(0);
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            // landscape
                            pone.animate().alpha(1).scaleX(1.2f).scaleY(1).translationY(100).setDuration(100);
                        } else {
                            // portrait
                            pone.animate().alpha(1).scaleX(1.2f).scaleY(1).translationX(-100).setDuration(100);
                        }

                        VS.animate().translationYBy(-50);
                    }
                    VS.setText(" THE WINNER IS ");
                    appname.setText("");
                    break;
                }
            }
            //NO WINNER
            if (!isAWinner) {
                int ok = 0;
                for (int i = 0; i <= 8; i++) {
                    if (status[i] != currantpl.NULL) {
                        ok++;
                    }
                }
                if (ok == 9) {
                    Toast.makeText(MainActivity.this, "NO WINNER! \n TRY AGAIN", Toast.LENGTH_SHORT).show();
                    //invsibility
                    zero.setVisibility(View.INVISIBLE);
                    lionscr.setVisibility(View.INVISIBLE);
                    tigerscr.setVisibility(View.INVISIBLE);
                    btnagain.animate().translationY(0).setDuration(1000);
                    dark.setVisibility(View.VISIBLE);
                    dark.animate().alpha(1).setDuration(100);
                    pone.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100);
                    ptwo.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100);
                    appname.setText("");
                    VS.animate().translationYBy(-30);
                    VS.setText("NO WINNER! \n TRY AGAIN");
                    currantplayer = currantpl.NULL;
                    //TO uncheckable
                    for (int i = 0; i < gridLayout.getChildCount(); i++) {
                        ImageView imageView = (ImageView) gridLayout.getChildAt(i);
                        imageView.setClickable(false);
                        imageView.animate().rotation(36000);
                    }

                }
            }
        }
        // 0 --------- 0
        public void zeroing (View zero){
            zero.animate().rotationBy(360).setDuration(2000);
            tigerscr.setText("0");
            lionscr.setText("0");
        }

}


