package com.jxhd.weile.casualgames;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import android.graphics.PointF;
import android.animation.TypeEvaluator;

/**
 * Created by liuqinglin on 2018/6/6.
 */
public class CollideAnimation extends RelativeLayout {
    private Context context;
    //水波纹
    private WaveView mWaveView;

    //    public ArrayList<CircleView> data;
    //圆心
    public static int center = 0;


    //设置圆心球跟小球的的间距比例
    public static final int SPACEBETWEEN = 5;
    //宽度8等份
    private static final int DIVIDE = 8;
    //圆心的控件
    private ImageView imageView;
    //中心图片
    private Bitmap bitmap;

    private static final double RIGHTDEGREES = 90;
    private static final int UTURN = 180;

    //控制动画是否可以执行
    private boolean flag = true;


    View mview = null;


    private final static int[] userArr = {R.mipmap.person_01, R.mipmap.person_02, R.mipmap.person_03, R.mipmap.person_04, R.mipmap.person_05,
            R.mipmap.person_06, R.mipmap.person_07, R.mipmap.person_08, R.mipmap.person_09, R.mipmap.person_10,
            R.mipmap.person_11, R.mipmap.person_12, R.mipmap.person_13, R.mipmap.person_14, R.mipmap.person_15,
            R.mipmap.person_19, R.mipmap.person_16, R.mipmap.person_17, R.mipmap.person_18, R.mipmap.person_20};
    private int[] ranks = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};


    public CollideAnimation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CollideAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }


    /**
     * 初始化控件
     *
     * @param context
     */
    private void init(final Context context) {

        ranks = rank(ranks);

        mWaveView = new WaveView(context);
        addView(mWaveView);
        mWaveView.setDuration(12000);
        mWaveView.setStyle(Paint.Style.FILL);
        mWaveView.setColor(getResources().getColor(R.color.matching_waveview_bg));
        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
        mWaveView.start();

        imageView = new ImageView(context);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_default_avatar);
        imageView.setImageBitmap(createCircleImage(bitmap));
        addView(imageView);

        mview = new View(context);
        addView(mview);
//        data = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            CircleView paintView = new CircleView(context);
//            data.add(paintView);
//            addView(paintView);
//        }

        startAnimator();

    }

    private void startAnimator() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                while (flag) {
//                    flag = false;
                //控制小球的位置
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(i);
                }
//                }
            }
        }).start();
    }

    int rankNum = 0;
    /**
     * 刷新动画
     */
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            //属性动画
//            if(flag){
//                // 1、将图片按不规则打乱排序，循环一次后重新排序
//                if (rankNum % 10 == 0) {
//                    ranks = rank(ranks);
//                }
//                rankNum += 1;
//                final int what = msg.what;
//
//                // 2、初始化两个小球，并按控件的大小来设置小球大小，
////            final CircleView paintView = data.get(what*2);
//                final CircleView paintView = new CircleView(context);
//                addView(paintView);
//                paintView.setCenter(center, userArr[ranks[(rankNum * 2) % userArr.length]]);
//                int a = center * 2 / DIVIDE;
//                int radius1 = (2 * center / 16);
//                int radius = 2 * (2 * center / 16);
//                int num = (what) % 3;
//                //随机取出0-360的某一个角度
//                Random r = new Random();
//                double angle = r.nextInt(2*UTURN);
////            paintView.setColor(Color.RED);
////            paintView.setAngle(angle, center - radius * num + radius - 15, center - radius * num + radius - 15);
//                paintView.setAngle(angle, a + radius1 + (num * 2 * radius1), a + radius1 + (num * 2 * radius1));
//
////            final CircleView paintView1 = data.get(what*2+1);
////            addView(paintView1);
//                final CircleView paintView1 = new CircleView(context);
//                addView(paintView1);
//                paintView1.setCenter(center, userArr[ranks[(rankNum * 2 + 1) % userArr.length]]);
////            paintView1.setColor(Color.YELLOW);
////            paintView1.setAngle(180 + angle, center - radius * num + radius - 15, center - radius * num + radius - 15);
//                paintView1.setAngle(UTURN + angle, a + radius1 + (num * 2 * radius1), a + radius1 + (num * 2 * radius1));
//
//                final VsView vsView = new VsView(context);
//                vsView.setVisibility(View.GONE);
//                addView(vsView);
//                final float x = center
//                        + (float) ((a + radius1 + (num * 2 * radius1)) * Math.cos((angle + RIGHTDEGREES) * Math.PI / UTURN));
//                final float y = center
//                        + (float) ((a + radius1 + (num * 2 * radius1)) * Math.sin((angle + RIGHTDEGREES) * Math.PI / UTURN));
//                vsView.setX(x);
//                vsView.setY(y);
//                vsView.setMwidth(radius);
//                vsView.setMheight(radius);
//
//
//                // 3、换算两个小球碰撞跟圆心的夹角
//
////                float ty = (float) Math.sqrt((a + radius1 + (num * 2 * radius1)) * (a + radius1 + (num * 2 * radius1)) + radius1 * radius1);
////                double degrees = Math.floor(Math.toDegrees(Math.asin(radius1 / ty)));
//                double ty =  Math.sqrt(Math.pow((a + radius1 + (num * 2 * radius1)),2) + Math.pow(radius1 , 2));
//                double degrees =Math.toDegrees(Math.asin(radius1 / ty));
////                double ty =  Math.sqrt(Math.pow((a + radius1 + (num * 2 * radius1)),2) + Math.pow(radius1 , 2));
////                double degrees = Math.floor(Math.toDegrees(Math.asin(radius1 / ty)));
//
//                // 4、小球绕圆心旋转动画以及透明度消失
//                final ObjectAnimator animator1 = ObjectAnimator.ofFloat(paintView, "rotation", 0, (float) (RIGHTDEGREES - degrees));
//                paintView.setPivotX(center);
//                paintView.setPivotY(center);
//
//
//                final ObjectAnimator animator2 = ObjectAnimator.ofFloat(paintView1, "rotation", 0, - (float) ((RIGHTDEGREES - degrees)));
//                paintView1.setPivotX(center);
//                paintView1.setPivotY(center);
//
//                final ObjectAnimator animatorAlpha1 = ObjectAnimator.ofFloat(paintView, "alpha", 1f, 0.0f);
//                final ObjectAnimator animatorAlpha2 = ObjectAnimator.ofFloat(paintView1, "alpha", 1f, 0.0f);
//                final ObjectAnimator animatorAlpha3 = ObjectAnimator.ofFloat(vsView, "alpha", 1f, 0.0f);
//
//                final AnimatorSet animatorAlphaSet = new AnimatorSet();
//                animatorAlphaSet.setDuration(1500);
//
//                final AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.setDuration(3000);
//                //animatorSet.play(anim);//执行当个动画
//                animatorSet.playTogether(animator1,animator2);//同时执行,animator3,animator4
////          animatorSet.playSequentially(animator1,animator2);//依次执行动画
//                animatorSet.setInterpolator(new DecelerateInterpolator());
//
//                animatorSet.addListener( new AnimatorListenerAdapter() {
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//
//                        vsView.setVisibility(View.VISIBLE);
//                        animatorAlphaSet.playTogether(animatorAlpha1, animatorAlpha2, animatorAlpha3);
//                        animatorAlphaSet.addListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                removeView(paintView);
//                                removeView(paintView1);
//                                removeView(vsView);
//
//                                handler.sendEmptyMessage(what);
//                            }
//                        });
//                        animatorAlphaSet.start();
//
//                    }
//                });
//                animatorSet.start();
//            }


            //按角度换算坐标
//            if(flag){
//                // 1、将图片按不规则打乱排序，循环一次后重新排序
//                if (rankNum % 10 == 0) {
//                    ranks = rank(ranks);
//                }
//                rankNum += 1;
//                final int what = msg.what;
//
//                // 2、初始化两个小球，并按控件的大小来设置小球大小，
////            final CircleView paintView = data.get(what*2);
//                final CircleView paintView = new CircleView(context);
//                addView(paintView);
//                paintView.setCenter(center, userArr[ranks[(rankNum * 2) % userArr.length]]);
//                final int a = center * 2 / DIVIDE;
//                final int radius1 = (2 * center / 16);
//                int radius = 2 * (2 * center / 16);
//                final int num = (what) % 3;
//                //随机取出0-360的某一个角度
//                Random r = new Random();
//                final int angle = r.nextInt(2*UTURN);
////            paintView.setColor(Color.RED);
////            paintView.setAngle(angle, center - radius * num + radius - 15, center - radius * num + radius - 15);
//                paintView.setAngle(angle, a + radius1 + (num * 2 * radius1), a + radius1 + (num * 2 * radius1));
//
////            final CircleView paintView1 = data.get(what*2+1);
////            addView(paintView1);
//                final CircleView paintView1 = new CircleView(context);
//                addView(paintView1);
//                paintView1.setCenter(center, userArr[ranks[(rankNum * 2 + 1) % userArr.length]]);
////            paintView1.setColor(Color.YELLOW);
////            paintView1.setAngle(180 + angle, center - radius * num + radius - 15, center - radius * num + radius - 15);
//                paintView1.setAngle(UTURN + angle, a + radius1 + (num * 2 * radius1), a + radius1 + (num * 2 * radius1));
//
//                final VsView vsView = new VsView(context);
//                vsView.setVisibility(View.GONE);
//                addView(vsView);
//                final float x = center
//                        + (float) ((a + radius1 + (num * 2 * radius1)) * Math.cos((angle + RIGHTDEGREES) * Math.PI / UTURN));
//                final float y = center
//                        + (float) ((a + radius1 + (num * 2 * radius1)) * Math.sin((angle + RIGHTDEGREES) * Math.PI / UTURN));
//                vsView.setX(x);
//                vsView.setY(y);
//                vsView.setMwidth(radius);
//                vsView.setMheight(radius);
//
//
//                // 3、换算两个小球碰撞跟圆心的夹角
//            double ty =  Math.sqrt(Math.pow((a + radius1 + (num * 2 * radius1)),2) + Math.pow(radius1 , 2));
//            double degrees = Math.floor(Math.toDegrees(Math.asin(radius1 / ty)));
//
//                // 4、小球绕圆心旋转动画以及透明度消失
//                final ObjectAnimator animator2 = ObjectAnimator.ofFloat(mview, "rotation", 0, (float) (RIGHTDEGREES - degrees));
//
//                animator2.setDuration(2000);
////                animator2.setInterpolator(new AccelerateDecelerateInterpolator());
//                // 回调监听
//                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float value = (Float) animation.getAnimatedValue();
////                        Log.d("zhangphil", value + "");
//                        paintView.setAngle((int)(angle+(float)value), a + radius1 + (num * 2 * radius1), a + radius1 + (num * 2 * radius1));
//                        paintView1.setAngle((int)(180+angle-((float)value)), a + radius1 + (num * 2 * radius1), a + radius1 + (num * 2 * radius1));
//
//                    }
//
//                });
//
//                final ObjectAnimator animatorAlpha1 = ObjectAnimator.ofFloat(paintView, "alpha", 1f, 0.0f);
//                final ObjectAnimator animatorAlpha2 = ObjectAnimator.ofFloat(paintView1, "alpha", 1f, 0.0f);
//                final ObjectAnimator animatorAlpha3 = ObjectAnimator.ofFloat(vsView, "alpha", 1f, 0.0f);
//
//                final AnimatorSet animatorAlphaSet = new AnimatorSet();
//                animatorAlphaSet.setDuration(1000);
//                animatorAlphaSet.playTogether(animatorAlpha1, animatorAlpha2, animatorAlpha3);
//                animatorAlphaSet.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        removeView(paintView);
//                        removeView(paintView1);
//                        removeView(vsView);
//
//                        handler.sendEmptyMessage(what);
//                    }
//                });
//
//
//                animator2.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//
//
//
//                        animatorAlphaSet.start();
//                        vsView.setVisibility(View.VISIBLE);
//
//                    }
//                });
//                animator2.start();
//
//            }


            //第三种 属性动画估值器
            if (flag) {
                // 1、将图片按不规则打乱排序，循环一次后重新排序
                if (rankNum % 10 == 0) {
                    ranks = rank(ranks);
                }
                rankNum += 1;
                final int what = msg.what;

                // 2、初始化两个小球，并按控件的大小来设置小球大小，
//            final CircleView paintView = data.get(what*2);
                final CircleView paintView = new CircleView(context);
                addView(paintView);
                final int a = center * 2 / DIVIDE;
                paintView.setCenter(center, userArr[ranks[(rankNum * 2) % userArr.length]],a+a/SPACEBETWEEN);

                final int radius1 = (center-(a+a/SPACEBETWEEN))/3/2;
                final int radius = (center-(a+a/SPACEBETWEEN))/3;
                final int num = (what) % 3;
                //随机取出0-360的某一个角度
                Random r = new Random();
                final double angle = r.nextInt(2 * UTURN);
                paintView.setAngle(angle, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN);

                final CircleView paintView1 = new CircleView(context);
                addView(paintView1);
                paintView1.setCenter(center, userArr[ranks[(rankNum * 2 + 1) % userArr.length]],a+a/SPACEBETWEEN);

                paintView1.setAngle(UTURN + angle, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN);

                final VsView vsView = new VsView(context);
                vsView.setVisibility(View.GONE);
                addView(vsView);
                final float x = center
                        + (float) ((a + radius1+a/SPACEBETWEEN + (num * 2 * radius1)) * Math.cos((angle + RIGHTDEGREES) * Math.PI / UTURN));
                final float y = center
                        + (float) ((a + radius1+a/SPACEBETWEEN + (num * 2 * radius1)) * Math.sin((angle + RIGHTDEGREES) * Math.PI / UTURN));
                vsView.setX(x);
                vsView.setY(y);
                vsView.setMwidth(radius);
                vsView.setMheight(radius);


                // 3、换算两个小球碰撞跟圆心的夹角
                double ty = Math.sqrt(Math.pow((a + radius1+a/SPACEBETWEEN + (num * 2 * radius1)), 2) + Math.pow(radius1, 2));
                double degrees = Math.toDegrees(Math.asin(radius1 / ty));

                // 4、小球绕圆心旋转动画以及透明度消失
                ValueAnimator mValueAnimator = ValueAnimator.ofObject(new BezierEvaluator()
                        , angle, angle + RIGHTDEGREES - degrees);//第一个pointF：开始点，第二个PointF：终点
                mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //根据计算好的点不断更新View的位置

                        double fangle = (Double) animation.getAnimatedValue();
                        paintView.setAngle(fangle, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN);

                    }
                });

                ValueAnimator mValueAnimator1 = ValueAnimator.ofObject(new BezierEvaluator()
                        , UTURN + angle, UTURN + angle - RIGHTDEGREES + degrees);//第一个pointF：开始点，第二个PointF：终点
                mValueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //根据计算好的点不断更新View的位置

                        double fangle = (Double) animation.getAnimatedValue();
                        paintView1.setAngle(fangle, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN, a + radius1 + (num * 2 * radius1)+a/SPACEBETWEEN);

                    }
                });


                //scale动画和贝塞尔曲线动画一起

                final ObjectAnimator animatorAlpha1 = ObjectAnimator.ofFloat(paintView, "alpha", 1f, 0.0f);
                final ObjectAnimator animatorAlpha2 = ObjectAnimator.ofFloat(paintView1, "alpha", 1f, 0.0f);
                final ObjectAnimator animatorAlpha3 = ObjectAnimator.ofFloat(vsView, "alpha", 1f, 0.0f);
                final AnimatorSet animSet = new AnimatorSet();
                final AnimatorSet animatorAlphaSet = new AnimatorSet();
                animatorAlphaSet.setDuration(1500);
                animatorAlphaSet.playTogether(animatorAlpha1, animatorAlpha2, animatorAlpha3);
                animatorAlphaSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        removeView(paintView);
                        removeView(paintView1);
                        removeView(vsView);


                        animatorAlphaSet.removeAllListeners();
                        animSet.removeAllListeners();
                        handler.sendEmptyMessage(what);
                    }
                });



                animSet.play(mValueAnimator).with(mValueAnimator1);
                animSet.setDuration(3500);
                animSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        animatorAlphaSet.start();
                        vsView.setVisibility(View.VISIBLE);

                    }
                });
                animSet.start();


            }
        }
    };

    /**
     * 设置圆心的图片
     *
     * @param bitmap
     */
    public void setUserBitmap(Bitmap bitmap) {
        if (imageView != null) {
            imageView.setImageBitmap(createCircleImage(bitmap));
        }
    }

    /**
     * 控制是否执行动画
     *
     * @param flag
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
        if (flag) {
            startAnimator();
            mWaveView.start();
        } else {
            mWaveView.stop();
        }
    }


    /**
     * 测量大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        int v = Math.min(measureWidth, measureHeight);
        center = v / 2;

        setMeasuredDimension(v, v);
    }

    /**
     * 给前面两个子控件布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int childCount = getChildCount();
        if (childCount >= 2) {
            View child0 = getChildAt(0);
            int childw = child0.getMeasuredWidth();
            int childh = child0.getMeasuredHeight();
            child0.layout(0, 0, childw, childh);
            View child1 = getChildAt(1);
            child1.layout(childw / 2 - childw / DIVIDE, childw / 2 - childw / DIVIDE, childw / 2 + childw / DIVIDE, childw / 2 + childw / DIVIDE);
        }
    }

    /**
     * 用于记录方法运算次数
     */
    public static int runCount = 0;

    /**
     * 将一个数组打乱按不规则排序
     *
     * @param arr
     * @return
     */
    public int[] rank(int[] arr) {
        int[] arr2 = new int[arr.length];
        int count = arr.length;
        int cbRandCount = 0;// 索引
        int cbPosition = 0;// 位置
        int k = 0;
        do {
            runCount++;
            Random rand = new Random();
            int r = count - cbRandCount;
            cbPosition = rand.nextInt(r);
            arr2[k++] = arr[cbPosition];
            cbRandCount++;
            arr[cbPosition] = arr[r - 1];// 将最后一位数值赋值给已经被使用的cbPosition
        } while (cbRandCount < count);
//        System.out.println("m3运算次数  = "+runCount);
        return arr2;
    }


    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    class BezierEvaluator implements TypeEvaluator<Double> {
        @Override
        public Double evaluate(float fraction, Double startValue, Double endValue) {


            double angle = endValue - startValue;
            double val = startValue + fraction * angle;

//            float mDifference = mCircleRadius * C;        // 圆形的控制点与数据点的差值
//            PointF controlPoint1 = new PointF();    //贝塞尔曲线控制点
//            controlPoint1.set(startValue.x, startValue.y + mDifference);
//            PointF controlPoint2 = new PointF();    //贝塞尔曲线控制点
//            controlPoint2.set(endValue.x + mDifference, endValue.y);
//            B0(t) = (1 - t) 2 P0 + 2 (1 - t) tC1 + t2P1(0 ≤ t ≤ 1) 二次贝塞尔曲线方程
//            B(t) = P0 * (1 - t) ^ 3 + 3 * P1 * t * (1 - t) ^ 2 + 3 * P2 * t ^ 2 * (1 - t) + P3 * t ^ 3, t ∈ [
//            0, 1]三次贝塞尔曲线方程
//            PointF point = new PointF();
//            float oneMinusT = 1.0f - t;
//            point.x = oneMinusT * oneMinusT * startValue.x + 2 * fraction * oneMinusT * controlPoint.x + fraction * fraction * endValue.x;
//            point.y = oneMinusT * oneMinusT * startValue.y + 2 * fraction * oneMinusT * controlPoint.y + fraction * fraction * endValue.y;
//            PointF point = new PointF();
//            float temp = 1 - t;
//            point.x = startValue.x * temp * temp * temp + 3 * controlPoint1.x * t * temp * temp + 3 * controlPoint2.x * t * t * temp + endValue.x * t * t * t;
//            point.y = startValue.y * temp * temp * temp + 3 * controlPoint1.y * t * temp * temp + 3 * controlPoint2.y * t * t * temp + endValue.y * t * t * t;
            return val;
        }
    }


//    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
//        // 获得图片的宽高.
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        // 计算缩放比例.
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // 取得想要缩放的matrix参数.
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
//        // 得到新的图片.
//        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
//        return newbm;
//    }

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
