package productlist.exam.productlistexam.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import productlist.exam.productlistexam.ProductListApplication;
import productlist.exam.productlistexam.utils.ImageUtils;

public class ImagePagerAdapter extends PagerAdapter {
    @LayoutRes
    private int mLayoutRes;
    @NonNull
    private List<String> mData;

    private ImageView[] mViews;

    public ImagePagerAdapter(@NonNull List<String> urls) {
        mData = urls;
        mViews = new ImageView[mData.size()];
    }

    @Override
    public int getCount() {
        return mViews.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = mViews[position];
        if (view == null) {
            view = new ImageView(container.getContext());
            mViews[position] = view;
        }
        bindView(position, view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews[position]);
    }

    private void bindView(int position, ImageView view) {
        ImageUtils.loadUrlIntoImageView(mData.get(position), view, false);
    }
}
