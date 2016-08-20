package com.example.yuhui.dms.dmscatalogue.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 商品浏览列表中，展示图右上角以表示 “无货” “满赠” 等状态信息的TagView
 * Created by Administrator on 2016-5-31.
 */
public class CatalogueTagView extends ImageView {
    public static final int TYPE_WITH_GIFT = 1001;
    public static final int TYPE_OUT_OF_STOCK = 1002;
    private static final int ROTATE_DEGREES = 45;
    private String text = "text";
    private Path mPath;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private float mLength1;
    private float mLength2;

    public CatalogueTagView(Context context) {
        this(context, null);
    }

    public CatalogueTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        mLength1 = (float) (metric.widthPixels / 3 * 0.1);
        mLength2 = (float) (metric.widthPixels / 3 * 0.3);


        mPaint = new Paint();
        mPaint.setColor(0xffaaadb3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        //多边形
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mLength2, mLength2);
        mPath.lineTo(mLength2, 0);
        mPath.lineTo(0, 0);


        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        float spPxSzie = 10 * fontScale + 0.5f;
        int TEXT_SIZE = Math.round(spPxSzie);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(TEXT_SIZE);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.rotate(ROTATE_DEGREES);
        canvas.drawText(text, (mLength2 - mLength1) / 1.5f, -mLength1 / 2.2f, mTextPaint);
    }

    public void setTagType(@CatalogueTagType int type) {
        switch (type) {
            case TYPE_WITH_GIFT:
                mPaint.setColor(0xffff7f24);
                this.text = "满赠";
                break;
            case TYPE_OUT_OF_STOCK:
                mPaint.setColor(0xffaaadb3);
                this.text = "无货";
                break;
            default:
                break;
        }
        invalidate();
    }

    public void setText(String str) {
        this.text = str;
        invalidate();
    }

    @IntDef({TYPE_WITH_GIFT, TYPE_OUT_OF_STOCK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CatalogueTagType {
    }
}
