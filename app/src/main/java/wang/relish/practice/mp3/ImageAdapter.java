package wang.relish.practice.mp3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends PagerAdapter {

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
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
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