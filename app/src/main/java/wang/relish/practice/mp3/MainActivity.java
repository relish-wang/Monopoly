package wang.relish.practice.mp3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    MediaPlayer mp;
    private boolean isPaused;
    private int pausedPage = -1;
    List<Integer> mMP3s = new ArrayList<Integer>() {
        {
            add(R.raw.mp3_0);
            add(R.raw.mp3_1);
            add(R.raw.mp3_2);
            add(R.raw.mp3_3);
            add(R.raw.mp3_4);
            //...
            //AND SO ON FOR 50 PAGES//
        }
    };

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv_status);

        mViewPager = (ViewPager) findViewById(R.id.vp_image);
        ImageAdapter adapter = new ImageAdapter(mMP3s);
        mViewPager.setAdapter(adapter);

        final GestureDetector tapGestureDetector = new GestureDetector(this, new TapGestureListener());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (!isPaused) {
                    mp.release();
                    mp = MediaPlayer.create(MainActivity.this, mMP3s.get(position));
                    mp.start();
                    tv.append("START page" + position + "\n");
                } else {
                    //do nothing.
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
        });
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int position = mViewPager.getCurrentItem();
            if (mp != null) {
                if (isPaused) {
                    if (pausedPage == position) {
                        mp.start();
                        tv.append("page" + position + " CONTINUE\n");
                    } else {
                        mp = MediaPlayer.create(MainActivity.this, mMP3s.get(position));
                        mp.start();
                        tv.append("START page" + position + "\n");
                    }
                    isPaused = false;
                } else {
                    mp.pause();
                    isPaused = true;
                    pausedPage = position;
                    tv.append("PAUSE page" + position + "\n");
                }
            } else {
                mp = MediaPlayer.create(MainActivity.this, mMP3s.get(position));
                mp.start();
                tv.append("START page" + position + "\n");

            }
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer m) {
                    Toast.makeText(MainActivity.this,
                            "COMPLETED", Toast.LENGTH_LONG).show();
                    // Set the MainActivity member to null
                    MainActivity.this.mp = null;
                }
            });
            return super.onSingleTapConfirmed(e);
        }
    }
}


