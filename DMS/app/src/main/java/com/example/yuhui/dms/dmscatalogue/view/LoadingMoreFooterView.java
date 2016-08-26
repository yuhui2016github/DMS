package com.example.yuhui.dms.dmscatalogue.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yuhui.dms.R;

/**
 * Created by Administrator on 2016/5/29.
 * 上拉加载的footerView
 */
public class LoadingMoreFooterView extends LinearLayout implements View.OnClickListener {//, BaseMoreFooter

    private static final int ROTATE_ANIM_DURATION = 1000;

    private LoadingState mState = LoadingState.STATE_COMPLETE;

    private LoadingMoreFooterClickCallback loadingMoreFooterClickCallback;
    private ProgressBar mFooterProgressBar;
    private ImageView mFooterImageView;
    private TextView mFooterTextView;
    private View mFooterView;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;


    public void setLoadingMoreFooterClickCallback(LoadingMoreFooterClickCallback loadingMoreFooterClickCallback) {
        this.loadingMoreFooterClickCallback = loadingMoreFooterClickCallback;
    }

    public LoadingMoreFooterView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        mFooterView = LayoutInflater.from(context).inflate(R.layout.catalogue_refresh_footer, null, false);

        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pull_to_load_progress);
        mFooterImageView = (ImageView) mFooterView.findViewById(R.id.pull_to_load_image);
        mFooterTextView = (TextView) mFooterView.findViewById(R.id.pull_to_load_text);
        addView(mFooterView);
        mFooterView.setVisibility(GONE);
    }

    public void loading() {
        // mSpinView.setVisibility(View.VISIBLE);
        mFooterView.setVisibility(VISIBLE);
        mFooterTextView.setText("正在加载...");
        mFooterImageView.setVisibility(GONE);
        mFooterProgressBar.setVisibility(VISIBLE);
        this.setVisibility(View.VISIBLE);
        setOnClickListener(null);
        mState = LoadingState.STATE_LOADING;
    }

    public void complete() {
        mFooterView.setVisibility(GONE);
        this.setVisibility(View.VISIBLE);

        setOnClickListener(null);
        mState = LoadingState.STATE_COMPLETE;
    }

    public void noMore() {
        mFooterTextView.setText("没有更多了");
        mFooterProgressBar.setVisibility(GONE);
        this.setVisibility(View.VISIBLE);
        setOnClickListener(null);
        mState = LoadingState.STATE_NO_MORE;
        //添加过度动画
        smoothScrollAnimation(mFooterView.getHeight());
    }

    private void smoothScrollAnimation(int height) {
        ValueAnimator animator = ValueAnimator.ofInt(0, height);
        animator.setDuration(ROTATE_ANIM_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mFooterView.setPadding(0, -value, 0, 0);
            }
        });
        animator.start();
    }

    public void setFooterViewPadding() {
        mFooterView.setPadding(0, 0, mFooterView.getHeight(), 0);
    }

    public void clickLoadMore() {
        mFooterTextView.setText("点击加载");
        this.setVisibility(View.VISIBLE);
        setOnClickListener(this);
        mState = LoadingState.STATE_CLICK_LOAD_MORE;
    }

    public boolean isClickLoadMore() {
        return mState == LoadingState.STATE_CLICK_LOAD_MORE;
    }

    public boolean isLoading() {
        return mState == LoadingState.STATE_LOADING;
    }

    public void setViewVisibility(int visibility) {
        setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        if (loadingMoreFooterClickCallback != null) {
            loadingMoreFooterClickCallback.onClick(v);
        }
    }
}
