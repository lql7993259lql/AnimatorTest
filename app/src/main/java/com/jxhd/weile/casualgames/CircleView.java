package com.jxhd.weile.casualgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by liuqinglin on 2018/6/6.
 */
public class CircleView extends View {
    private Paint mpaint;
    private Bitmap bitmap;
    float cx = 0;
    float cy = 0;
    int radius = 160;
    int color = Color.RED;
    float centerX = -500;
    float centerY = -500;
    double angle = 0;
    private float dinscex;
    private float dinscey;

    private static final int UTURN = 180;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setBitmap() {

    }

    private void init() {
        mpaint = new Paint();
        mpaint.setColor(Color.YELLOW);
        mpaint.setStrokeWidth(1);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setAntiAlias(true); // 消除锯齿

    }


    public void setCenter(float center, int resId,int a) {
        this.centerX = center;
        this.centerY = center;
        bitmap = BitmapFactory.decodeResource(getResources(), resId);

//        bitmap = createCircleImage(setImgSize(bitmap, (int)center*2/16 * 2, (int)center*2/16 * 2));
        bitmap = createCircleImage(setImgSize(bitmap, (int)(center-a)/3, (int)(center-a)/3));
        postInvalidate();
    }


    public void setColor(int color) {
        this.color = color;
        if (mpaint != null) {
            mpaint.setColor(this.color);
        }
    }

    public void  setAngle(double angle, float x, float y) {
        this.angle = angle;
        this.dinscex = x;
        this.dinscey = y;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);

        int min = Math.min(widthMeasure, heightMeasure);

        radius = min / 16;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        if(centerX>0){
//            this.cx = this.centerX
//                    + (float) ((this.dinscex - radius) * Math.cos(angle * Math.PI / UTURN));
//            this.cy = this.centerY
//                    + (float) ((this.dinscey - radius) * Math.sin(angle * Math.PI / UTURN));
//            bitmap = createCircleImage(setImgSize(bitmap, radius * 2, radius * 2));
//            canvas.drawBitmap(bitmap, this.cx - bitmap.getWidth() / 2, this.cy - bitmap.getHeight() / 2, mpaint);
//        }
        if (centerX > 0) {
            this.cx = this.centerX
                    + (float) ((this.dinscex) * Math.cos(angle * Math.PI / UTURN));
            this.cy = this.centerY
                    + (float) ((this.dinscey) * Math.sin(angle * Math.PI / UTURN));

            canvas.drawBitmap(bitmap, this.cx - bitmap.getWidth() / 2, this.cy - bitmap.getHeight() / 2, mpaint);
        }
        postInvalidate();


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

    public Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }


}
