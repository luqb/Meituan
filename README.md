# Meituan
点外卖时间轴???不知道怎么命名...每天点外卖都会看到的,觉得挺好玩的就模仿着做一个,不喜勿喷~


# gif

  ![gif](https://raw.githubusercontent.com/SaltedFishkk/Meituan/master/gif/meituan2.gif)
  
# usage 
## java
>
>
>  private MeiTuan meituan;
>
>    @Override
>
>    protected void onCreate(Bundle savedInstanceState) {
>
>        super.onCreate(savedInstanceState);
>
>        setContentView(R.layout.activity_main);
>
>        meituan = (MeiTuan) findViewById(R.id.meituan);
>
>        new Handler().postDelayed(()->meituan.setStateIndex(0),2000);
>
>        new Handler().postDelayed(()->meituan.setStateIndex(1),6000);
>
>        new Handler().postDelayed(()->meituan.setStateIndex(2),10000);
>
>        new Handler().postDelayed(()->meituan.setStateIndex(5),14000);
>
>        //大于个数就相当与全部完成
>
>    }
>
## xml
>
    <com.saltedfish.meituan.MeiTuan
        android:id="@+id/meituan"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
        <com.saltedfish.meituan.CircleItem
            android:id="@+id/circle_item"
            android:layout_width="match_parent"
            android:layout_height="150dp"
            circleitem:circleRadius="20"
            circleitem:titleText="黄焖鸡已经配送[30分钟前]"
            circleitem:titleIngColor="#FAEE1C"
            circleitem:desText="商品准备中,由商家配送,配送进度请咨询商家"
            circleitem:circleImage="@drawable/motuo"/>
        <com.saltedfish.meituan.CircleItem
            android:id="@+id/circle_item1"
            android:layout_width="match_parent"
            android:layout_height="150dp"
            circleitem:circleRadius="20"
            circleitem:titleText="黄焖鸡已经到达目的地[3分钟前]"
            circleitem:titleIngColor="#FAEE1C"
            circleitem:desText="商品准备中,由商家配送,配送进度请咨询商家"
            circleitem:circleImage="@drawable/fangzi"/>
        <com.saltedfish.meituan.CircleItem
            android:id="@+id/circle_item2"
            android:layout_width="match_parent"
            android:layout_height="150dp"
            circleitem:circleRadius="20"
            circleitem:titleText="请确认收到黄焖鸡"
            circleitem:titleIngColor="#FAEE1C"
            circleitem:desText="商品准备中,由商家配送,配送进度请咨询商家"
            circleitem:circleImage="@drawable/shoukuan"/>
    </com.saltedfish.meituan.MeiTuan>

>
## 依赖
>
 dependencies {
 
	        compile 'com.github.SaltedFishkk:Meituan:v1.0'
         
	}
 
>
