package com.saltedfish.meituan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by saltedfish on 2017/3/14.
 */

public class Circle extends View {
    private Context mContext;
    private String mTag = "";
    private int mState = -1;
    private ChangeListener mListener;
    private Canvas mCanvas;
    private Paint mPaint;
    private int angle = 1;
    //color
    private int COLOR_NOT = 0xffe3e3e3;//还没有进行时候的颜色
    private int COLOR_READY1 = 0xffF6F5F5;
    private int COLOR_READY2 = 0xffE3E3E3;//准备时的颜色
    private int COLOR_ING1 = 0xffFAEE1C;//正在进行中的颜色
    private int COLOR_ING2 = 0xffF95959;//小圆圈转的颜色
    //size
    private int RADIUS = 20;
    //shape
    private Rect mRect;
    //state
    public final static int STATE_NOT = -1;//还没有进行
    public final static int STATE_READY =  0;//准备中
    public final static int STATE_ING =  1;//进行中
    public final static int STATE_SUCCESS =  2;//成功
    public final static int STATE_FAIL =  3;//失败

    //resource
    private int mImage = 0;
    private int fail = R.drawable.fail;
    public Circle(Context context) {
        super(context);
        init(context);

    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Circle);
        RADIUS = ta.getInteger(R.styleable.Circle_radiusSize,50);
        mImage = ta.getResourceId(R.styleable.Circle_image,0);
        init(context);

    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Circle);
        RADIUS = ta.getInteger(R.styleable.Circle_radiusSize,50);
        mImage = ta.getResourceId(R.styleable.Circle_image,0);
        init(context);
    }

    //初始化
    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRect = new Rect(50,50,50+RADIUS*2,50+RADIUS*2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defWidth = 80;
        int defHeigh = 100;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY){
            defHeigh = heightSize;
        }else if (heightMode == MeasureSpec.AT_MOST){
            defHeigh = Math.min(defHeigh,heightSize);
        }
        if (widthMode == MeasureSpec.EXACTLY){
            defWidth = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST){
            defWidth = Math.min(defWidth,widthSize);
        }

        setMeasuredDimension(defWidth,defHeigh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        canvas.save();
        switch (mState){
            case STATE_NOT:
                drawNotCircle();
                break;
            case STATE_READY :
                drawReadyCircle();
                break;
            case STATE_ING :
                drawrHaveingCircle();
                canvas.restore();
                displayBitmap(mImage);
                break;
            case STATE_SUCCESS :
                drawSuccessCircle();
                break;
            case STATE_FAIL :
                drawReadyCircle();
                displayBitmap(fail);
                break;
        }
    }

    private void drawNotCircle(){
        mPaint.setColor(COLOR_NOT);
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(getCenterX(),getCenterY(),(RADIUS / 10 )>=5? (RADIUS / 10) : 5,mPaint);
    }
    private void drawReadyCircle(){
        mPaint.setColor(COLOR_READY1);
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(mRect.centerX(),mRect.centerY(),RADIUS,mPaint);
        mPaint.setColor(COLOR_READY2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mCanvas.drawCircle(mRect.centerX(),mRect.centerY(),RADIUS,mPaint);
    }

    private void drawrHaveingCircle(){
        mPaint.setColor(COLOR_ING1);
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(mRect.centerX(),mRect.centerY(),RADIUS,mPaint);
        mPaint.setColor(COLOR_ING1);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mCanvas.drawCircle(mRect.centerX(),mRect.centerY(),RADIUS+8,mPaint);//画外圈
        mCanvas.rotate(angle,getCenterX(),getCenterY());
        mPaint.setColor(COLOR_ING2);
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(getCenterX(),getCenterY()+RADIUS+8,(RADIUS/10)>=2?(RADIUS/10):2,mPaint);//画外圈圆
        if (angle > 360){
            angle = 0;
        }
        angle += 10;
        postInvalidateDelayed(20);
    }
    private void drawSuccessCircle(){
        mPaint.setColor(COLOR_ING1);
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(getCenterX(),getCenterY(),(RADIUS / 10 )>=5? (RADIUS / 10) : 5,mPaint);
    }


    private void displayBitmap(int image){
        if (image!=0){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);
            mCanvas.drawBitmap(bitmap,null,new Rect(mRect.left+5,mRect.top+5,mRect.right-5,mRect.bottom-5),null);
        }
    }


    public void ChangeState(int state){
        mState = state;
        invalidate();
        if (mListener!=null){
            mListener.ChangeState(state,mTag);
        }
    }
    public void setChangeListener(ChangeListener listener){
        mListener = listener;
    }
    public void setTag(String tag){
        mTag = tag;
    }
    public int getCenterX(){
        return mRect.centerX();
    }
    public int getCenterY(){
        return mRect.centerY();
    }
    public void setImage(int id){
        mImage = id;
        invalidate();
    }
    //按圆圈的大小来获得高度
    public int getCircleTop(){
        int top = mRect.top;
        switch (mState){
            case STATE_NOT:
                top += RADIUS-((RADIUS / 10 )>=5? (RADIUS / 10) : 5);//圆圈变小
                break;
            case STATE_READY :
                //不改变
                break;
            case STATE_ING :
                top -= 8;//圆圈变大
                break;
            case STATE_SUCCESS :
                top += RADIUS-((RADIUS / 10 )>=5? (RADIUS / 10) : 5);//圆圈变小
                break;
            case STATE_FAIL :
                //不改变
                break;
        }
        return top;
    }
    //按圆圈的大小来获得离y轴的距离
    public int getCircleLeft(){
        int left = mRect.left;
        switch (mState){
            case STATE_NOT:
                left += RADIUS-((RADIUS / 10 )>=5? (RADIUS / 10) : 5);//圆圈变小
                break;
            case STATE_READY :
                //不改变
                break;
            case STATE_ING :
                left -= 8;//圆圈变大
                break;
            case STATE_SUCCESS :
                left += RADIUS-((RADIUS / 10 )>=5? (RADIUS / 10) : 5);//圆圈变小
                break;
            case STATE_FAIL :
                //不改变
                break;
        }
        return left;
    }
    public void setRadius(int radius){
        RADIUS = radius;
        mRect = new Rect(50,50,50+RADIUS*2,50+RADIUS*2);
        invalidate();
    }
    public int getRadius(){
        int radius = RADIUS;
        switch (mState){
            case STATE_NOT:
                radius = ((RADIUS / 10 )>=5? (RADIUS / 10) : 5);//圆圈变小
                break;
            case STATE_READY :
                //不改变
                break;
            case STATE_ING :
                radius += 8;//圆圈变大
                break;
            case STATE_SUCCESS :
                radius = ((RADIUS / 10 )>=5? (RADIUS / 10) : 5);//圆圈变小
                break;
            case STATE_FAIL :
                //不改变
                break;
        }
        return radius;
    }
    public interface ChangeListener{
//        public void Not(String tag);
//        public void Ready(String tag);
//        public void Success(String tag);
//        public void Fail(String tag);
//        public void Haveing(String tag);
        public void ChangeState(int state,String tag);
    }
}
