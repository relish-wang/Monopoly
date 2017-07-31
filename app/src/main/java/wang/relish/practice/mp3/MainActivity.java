package wang.relish.practice.mp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    private MediaPlayer mPlayer;
    private boolean isPaused;
    private int mPausedPage = -1;
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

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (!isPaused) {
                    mPlayer.release();
                    mPlayer = MediaPlayer.create(MainActivity.this, mMP3s.get(position));
                    mPlayer.start();
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

    private class ImageAdapter extends PagerAdapter {

        List<Integer> mData;

        ImageAdapter(List<Integer> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = container.getContext();// use this context is enough
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageBitmap(rawResIdToBitmap(context, mData.get(position)));//use the image album of the song
            container.addView(imageView, 0);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = mViewPager.getCurrentItem();
                    if (mPlayer != null) {
                        if (isPaused) {
                            if (mPausedPage == position) {
                                mPlayer.start();
                                tv.append("page" + position + " CONTINUE\n");
                            } else {
                                mPlayer = MediaPlayer.create(MainActivity.this, mMP3s.get(position));
                                mPlayer.start();
                                tv.append("START page" + position + "\n");
                            }
                            isPaused = false;
                        } else {
                            mPlayer.pause();
                            isPaused = true;
                            mPausedPage = position;
                            tv.append("PAUSE page" + position + "\n");
                        }
                    } else {
                        mPlayer = MediaPlayer.create(MainActivity.this, mMP3s.get(position));
                        mPlayer.start();
                        tv.append("START page" + position + "\n");
                        isPaused = false;
                    }
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer m) {
                            Toast.makeText(MainActivity.this,
                                    "COMPLETED", Toast.LENGTH_LONG).show();
                            // Set the MainActivity member to null
                            MainActivity.this.mPlayer = null;
                        }
                    });
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }

    /**
     * fetch the bitmap(the image album of the song) from the raw resource id of the song
     */
    private static Bitmap rawResIdToBitmap(Context context, int rawResId) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + rawResId));
        byte[] buffer = retriever.getEmbeddedPicture();
        return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
    }
}


