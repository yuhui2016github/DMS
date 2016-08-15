package com.example.yuhui.dms.dmscatalogue.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yuhui.dms.R;

/**
 * Created by yuhui on 2016-8-15.
 */
public class ImagePagerAdapter extends PagerAdapter {
    private ImageView[] tips;
    private int[] imgIdArray;
    private ImageView[] imageViews;
    private Context context;
    private ViewGroup group;
    private boolean isLoop = false;

    public static class Builder {
        private Context context;
        private int[] imgIdArray;
        private ViewGroup group;
        private ImagePagerAdapter imagePagerAdapter;
        private boolean isLoop = false;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setImgIdArray(int[] imgIdArray) {
            this.imgIdArray = imgIdArray;
            return this;
        }

        public Builder setGroup(ViewGroup group) {
            this.group = group;
            return this;
        }

        public Builder setLoop(boolean isLoop) {
            this.isLoop = isLoop;
            return this;
        }

        public ImagePagerAdapter create() {
            imagePagerAdapter = new ImagePagerAdapter(context);
            imagePagerAdapter.setLoop(isLoop);
            imagePagerAdapter.setImgIdArray(imgIdArray);
            imagePagerAdapter.initImageViews();
            if (group != null) {
                imagePagerAdapter.setGroup(group);
                imagePagerAdapter.initTips();
            }
            return imagePagerAdapter;
        }
    }

    protected ImagePagerAdapter(Context context) {
        this.context = context;
    }

    public void setImgIdArray(int[] imgIdArray) {
        this.imgIdArray = imgIdArray;
    }

    private void initImageViews() {
        //将图片装载到数组中
        imageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < imageViews.length; i++) {
            ImageView imageView = new ImageView(context);
            imageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }
    }

    public void setGroup(ViewGroup group) {
        this.group = group;
    }

    private void initTips() {
        tips = new ImageView[imgIdArray.length];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                new ViewGroup.LayoutParams(10, 10));
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(layoutParams);
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            group.addView(imageView);
        }
    }

    protected void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public boolean getLoop() {
        return this.isLoop;
    }

    @Override
    public int getCount() {
        if (isLoop) {
            return Integer.MAX_VALUE;
        } else {
            return imgIdArray.length;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews[position % imageViews.length]);
    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews[position % imageViews.length], 0);
        return imageViews[position % imageViews.length];
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    public void setImageBackground(int selectItems) {
        if (tips != null) {
            selectItems = selectItems % imgIdArray.length;
            for (int i = 0; i < tips.length; i++) {
                if (i == selectItems) {
                    tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
                } else {
                    tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }
        }
    }
}
