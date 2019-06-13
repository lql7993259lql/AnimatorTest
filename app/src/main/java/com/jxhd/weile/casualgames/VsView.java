package com.jxhd.weile.casualgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by liuqinglin on 2018/6/6.
 */
public class VsView extends View {


    private float x;
    private float y;
    private int mwidth;

    public int getMwidth() {
        return mwidth;
    }

    public void setMwidth(int mwidth) {
        this.mwidth = mwidth;
    }

    public int getMheight() {
        return mheight;
    }

    public void setMheight(int mheight) {
        this.mheight = mheight;
    }

    private int mheight;

    public VsView(Context context) {
        this(context, null);
    }

    public VsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = setImgSize(BitmapFactory.decodeResource(getResources(), R.mipmap.vs_2), mwidth / 2, mheight / 2);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, paint);
    }


    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }
}
