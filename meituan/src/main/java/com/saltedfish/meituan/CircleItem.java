package com.saltedfish.meituan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by saltedfish on 2017/3/15.
 */

public class CircleItem extends RelativeLayout {

    private Context mContext;

    //attr
    private String title="";
    private String des="";
    private int image=0;
    private int circle_image=0;
    private int state = -1;
    private int radius = 20;

    //object
    private Circle mCircle;
    private Canvas mCanvas;
    private Paint mPaint;
    private TextView tiltleText;
    private TextView desText;
    private ImageView desImage;
    private Path mPath;
    //color
    private int COLOR_TITLE_ING = 0xffFAEE1C;
    private int COLOR_TITLE_NO = 0xff000000;
    private int COLOR_PATH_NO = 0xffe3e3e3;
    private int COLOR_PATH_ING = 0xffFAEE1C;
    //tag
    private boolean startItem = false;//开始项
    private boolean endItem = false;//结束项
    private boolean isContinue = false;



    public void setState(int state){
        setState(state,null);
    }
    public void setState(int state, Circle.ChangeListener listener){
        this.state = state;
        mCircle.ChangeState(state);
        invalidate();
        if (listener!=null){
            mCircle.setChangeListener(listener);
        }
        if (state == Circle.STATE_ING){
            tiltleText.setTextColor(COLOR_TITLE_ING);
        }else {
            tiltleText.setTextColor(COLOR_TITLE_NO);
        }

    }
    public void setStartItem(boolean is){
        startItem = is;
        invalidate();
    }
    public void setEndItem(boolean is){
        endItem = is;
    }
    public void setTitleText(String title){
        this.title = title;
        tiltleText.setText(title);
    }
    public void setDesText(String desText1){
        des = desText1;
        desText.setText(desText1);
    }
    public void setIngColor(int color){
        COLOR_TITLE_ING = color;
        COLOR_PATH_ING = color;
        invalidate();
    }
    public void setNoColor(int color){
        COLOR_PATH_NO = color;
        invalidate();
    }
    public void setCircleImage(int id){
        circle_image = id;
        mCircle.setImage(id);
    }
    public void setDesImage(int id){
        image = id;
        desImage.setImageDrawable(getResources().getDrawable(id));
    }
    public CircleItem(Context context) {
        super(context);
        init(context);

    }
    public CircleItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CircleItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context) {
        init(context,null);
    }
    private void init(Context context,AttributeSet attr){
        //setWillNotDraw(false);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);


        LayoutInflater.from(mContext).inflate(R.layout.circle_item,CircleItem.this,true);
        mCircle = (Circle) findViewById(R.id.item_circle);
        tiltleText = (TextView) findViewById(R.id.item_title);
        desImage = (ImageView) findViewById(R.id.item_image);
        desText = (TextView) findViewById(R.id.item_des);

        if (attr!=null){
            TypedArray ta = mContext.obtainStyledAttributes(attr,R.styleable.CircleItem);
            title = ta.getString(R.styleable.CircleItem_titleText);
            des = ta.getString(R.styleable.CircleItem_desText);
            image = ta.getResourceId(R.styleable.CircleItem_desImage,0);
            circle_image = ta.getResourceId(R.styleable.CircleItem_circleImage,0);
            COLOR_TITLE_ING = ta.getColor(R.styleable.CircleItem_titleIngColor,0xffFAEE1C);
            COLOR_TITLE_NO = ta.getColor(R.styleable.CircleItem_titleNoColor,0xff000000);
            radius = ta.getInteger(R.styleable.CircleItem_circleRadius,20);
        }

        mCircle.setRadius(radius);
        mCircle.ChangeState(state);
        tiltleText.setText(title);
        tiltleText.setTextColor(COLOR_TITLE_NO);

        desText.setText(des);
        if (image!=0){
            desImage.setImageDrawable(getResources().getDrawable(image));
        }else {
            //desImage.setImageDrawable(null);
        }
        if (circle_image!=0){
            mCircle.setImage(circle_image);
        }
        setWillNotDraw(false);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        mPaint.setColor(COLOR_PATH_ING);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        //如果是不是开始项也不是结束项,就这样画
        if (startItem != true && endItem != true) {
            drawMidItem();
        }
        //如果是开始项
        if (startItem == true && endItem == false){
            drawStartItem();
        }
        //如果是结尾项
        if (startItem == false && endItem == true){
            drawEndItem();
        }
        //即使开始项 又是结尾项,相当于只有一个item
        if (startItem == true && endItem == true ){
            return;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        mCanvas = canvas;
//        mPaint.setColor(COLOR_PATH_ING);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(2);
//        //如果是不是开始项也不是结束项,就这样画
//        if (startItem != true && endItem != true) {
//            drawMidItem();
//        }
//        //如果是开始项
//        if (startItem == true && endItem == false){
//            drawStartItem();
//        }
//        //如果是结尾项
//        if (startItem == false && endItem == true){
//            drawEndItem();
//        }
//        //即使开始项 又是结尾项,相当于只有一个item
//        if (startItem == true && endItem == true ){
//            return;
//        }

    }

    private void drawStartItem(){
        switch (state) {
            case Circle.STATE_NOT:
                mPaint.setColor(COLOR_PATH_NO);
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2);
                mPath.lineTo(mCircle.getCenterX(), getHeight());
                drawDottedLine();
                break;
            case Circle.STATE_ING:
                mPaint.setColor(COLOR_PATH_NO);
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2);
                mPath.lineTo(mCircle.getCenterX(), getHeight());
                drawDottedLine();
                break;
            case Circle.STATE_READY:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2);
                mPath.lineTo(mCircle.getCenterX(), getHeight());
                drawDottedLine();
                break;
            case Circle.STATE_SUCCESS:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2 + 2);
                mPath.lineTo(mCircle.getCenterX(), getHeight());
                drawSolidLine();
                break;
            case Circle.STATE_FAIL://失败不继续就画虚线,表示不继续执行了
                if (isContinue) {
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2 + 2);
                    mPath.lineTo(mCircle.getCenterX(), getHeight());
                    drawSolidLine();
                } else {
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2 + 2);
                    mPath.lineTo(mCircle.getCenterX(), getHeight());
                    drawDottedLine();
                }
        }
    }
    private void drawMidItem(){
        switch (state) {
            case Circle.STATE_NOT:
                mPath = new Path();
                mPaint.setColor(COLOR_PATH_NO);
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop());
                drawDottedLine();
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2);
                mPath.lineTo(mCircle.getCenterX(), getHeight());
                drawDottedLine();
                break;
            case Circle.STATE_ING:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                drawSolidLine();
                mPaint.setColor(COLOR_PATH_NO);
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(),mCircle.getCircleTop()+mCircle.getRadius()*2+2);
                mPath.lineTo(mCircle.getCenterX(),getHeight());
                drawDottedLine();
                break;
            case Circle.STATE_READY:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                drawSolidLine();
                mPath = new Path();
                break;
            case Circle.STATE_SUCCESS:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                drawSolidLine();
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2 + 2);
                mPath.lineTo(mCircle.getCenterX(), getHeight());
                drawSolidLine();
                break;
            case Circle.STATE_FAIL://失败不继续就画虚线,表示不继续执行了
                if (isContinue) {
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), 0);
                    mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                    drawSolidLine();
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2 + 2);
                    mPath.lineTo(mCircle.getCenterX(), getHeight());
                    drawSolidLine();
                } else {
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), 0);
                    mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                    drawSolidLine();
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), mCircle.getCircleTop() + mCircle.getRadius() * 2 + 2);
                    mPath.lineTo(mCircle.getCenterX(), getHeight());
                    drawDottedLine();
                }
        }
    }
    private void drawEndItem(){
        switch (state) {
            case Circle.STATE_NOT:
                mPath = new Path();
                mPaint.setColor(COLOR_PATH_NO);
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop());
                drawDottedLine();
                break;
            case Circle.STATE_ING:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                drawSolidLine();
                break;
            case Circle.STATE_READY:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                drawSolidLine();
                mPath = new Path();
                break;
            case Circle.STATE_SUCCESS:
                mPath = new Path();
                mPath.moveTo(mCircle.getCenterX(), 0);
                mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                drawSolidLine();
                break;
            case Circle.STATE_FAIL://失败不继续就画虚线,表示不继续执行了
                if (isContinue) {
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), 0);
                    mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                    drawSolidLine();
                } else {
                    mPath = new Path();
                    mPath.moveTo(mCircle.getCenterX(), 0);
                    mPath.lineTo(mCircle.getCenterX(), mCircle.getCircleTop() - 2);
                    drawSolidLine();
                }
        }
    }

    private void drawSolidLine() {
        mPaint.setPathEffect(null);
        mCanvas.drawPath(mPath,mPaint);
    }

    private void drawDottedLine() {
        mPaint.setPathEffect(new DashPathEffect(new float[]{6,6},0));
        mCanvas.drawPath(mPath,mPaint);
    }
}
