package com.saltedfish.meituan;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by saltedfish on 2017/3/16.
 */

public class MeiTuan extends LinearLayout {

    private Context mContext;

    private int COLOR_ACTIVATE = 0xffFAEE1C;
    private int COLOR_NO_ACTIVATE = 0xffe3e3e3;

    private ArrayList<CircleItem> circleItems;

    public MeiTuan(Context context) {
        super(context);
        init(context);

    }

    public MeiTuan(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MeiTuan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context){
        init(context,null);
    }
    private void init(Context context,AttributeSet attrs) {
        mContext = context;
        circleItems = new ArrayList<>();
        if (attrs!=null){
            TypedArray ta = mContext.obtainStyledAttributes(attrs,R.styleable.MeiTuan);
            COLOR_ACTIVATE = ta.getColor(R.styleable.MeiTuan_activateColor,0xffFAEE1C);
            COLOR_NO_ACTIVATE = ta.getColor(R.styleable.MeiTuan_noActivateColor,0xffe3e3e3);
        }

        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i =0;i <getChildCount();i++){
            CircleItem view = (CircleItem) getChildAt(i);
            view.setIngColor(COLOR_ACTIVATE);
            view.setNoColor(COLOR_NO_ACTIVATE);
            circleItems.add(view);
        }
        circleItems.get(0).setStartItem(true);
        circleItems.get(circleItems.size()-1).setEndItem(true);
    }

    //-1表示一个都没开始
    //0表示第一个已经开始进行中了
    public void setStateIndex(int index){
        if (index>=0 && index < circleItems.size()){
            circleItems.get(index).setState(Circle.STATE_ING);
        }
        CircleItem circleItem;
        for (int i = 0; i < circleItems.size() ; i++){
            circleItem = circleItems.get(i);
            if (i>index){
                circleItem.setState(Circle.STATE_NOT);
            }
            if (i<index){
                circleItem.setState(Circle.STATE_SUCCESS);
            }

        }
        if (index>circleItems.size())
            circleItems.get(circleItems.size()-1).setState(Circle.STATE_SUCCESS);
    }
}
