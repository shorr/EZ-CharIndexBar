package cn.shorr.slidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 字符索引栏
 * Created by Shorr on 2016/12/7.
 */
public class CharIndexBar extends View {

    private static final String DEFAULT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private static final int DEFAULT_TEXT_SP_SIZE = 14;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#3f000000");

    /*可自定义相关属性*/
    private String chars;  //要显示的所有字符
    private int backgroundColor;  //背景色
    private int charTextSize;  //字体大小
    private int charTextColor;  //字体颜色

    private int canvasColor = Color.TRANSPARENT;  //画布颜色
    private int lastSelectedPosition = -1;  //记录上次选中的位置

    private Paint paint;  //画笔
    private Paint.FontMetricsInt fontMetricsInt;  //字体度量值
    private CharIndicateView charIndicateView;  //字符指示视图
    private OnSelectedListener onSelectedListener;  //字符选中的监听

    public CharIndexBar(Context context) {
        this(context, null);
    }

    public CharIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化View相关自定义属性
        initFromAttributes(context, attrs);
        //初始化相关操作
        init();
    }


    /**
     * 初始化View相关自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initFromAttributes(Context context, AttributeSet attrs) {
        //获取相关View属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CharIndexBar, 0, 0);
        try {
            String chars = a.getString(R.styleable.CharIndexBar_barChars);
            this.chars = (chars == null ? DEFAULT_CHARS : chars);
            charTextSize = a.getDimensionPixelSize(R.styleable.CharIndexBar_barTextSize,
                    UnitConvert.sp2px(context, DEFAULT_TEXT_SP_SIZE));
            charTextColor = a.getColor(R.styleable.CharIndexBar_barTextColor, DEFAULT_TEXT_COLOR);
            backgroundColor = a.getColor(R.styleable.CharIndexBar_barBackground, DEFAULT_BACKGROUND_COLOR);
        } finally {
            //回收TypedArray
            a.recycle();
        }
    }

    /**
     * 初始化操作
     */
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //文字水平居中显示
        paint.setTextAlign(Paint.Align.CENTER);
        //设置字体大小
        paint.setTextSize(charTextSize);
        //设置字体颜色
        paint.setColor(charTextColor);
        //获取FontMetricsInt
        fontMetricsInt = paint.getFontMetricsInt();
    }

    /**
     * 和字符指示View建立联系
     *
     * @param charIndicateView
     */
    public void setupWithIndicateView(CharIndicateView charIndicateView) {
        this.charIndicateView = charIndicateView;
    }

    /**
     * 设置监听事件
     *
     * @param onSelectedListener
     */
    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置索引栏背景色
        canvas.drawColor(canvasColor);
        //单个字符所占的高度
        float singleCharHeight = ((float) getHeight()) / chars.length();
        //字符要显示的x值
        float charX = ((float) getWidth()) / 2;
        //计算出字体高度
        int fontHeight = fontMetricsInt.descent - fontMetricsInt.ascent;
        //计算出竖直方向居中时的偏移量
        float centerYOffset = singleCharHeight / 2 - (-fontMetricsInt.ascent - fontHeight / 2);

        //根据x、y值画出所有字符
        for (int i = 0; i < chars.length(); i++) {
            canvas.drawText(chars.substring(i, i + 1),
                    charX, singleCharHeight * (i + 1) - centerYOffset, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:  //手指按下
                //设置画布颜色
                canvasColor = backgroundColor;
                //重新绘制
                invalidate();
                //显示字符指示View
                if (charIndicateView != null) {
                    charIndicateView.setVisibility(View.VISIBLE);
                }
                //根据Y值得到选中的位置
                selectedPositionByY(event.getY());

                return true;
            case MotionEvent.ACTION_MOVE:  //手指滑动
                //根据Y值得到选中的位置
                selectedPositionByY(event.getY());

                return true;
            case MotionEvent.ACTION_UP:  //手指抬起
                //画布颜色设为透明
                canvasColor = Color.TRANSPARENT;
                //重新绘制
                invalidate();
                //隐藏字符指示View
                if (charIndicateView != null) {
                    charIndicateView.setVisibility(View.GONE);
                }
                //复位记录上次选中位置的值
                lastSelectedPosition = -1;

                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据View的Y值得到选中的字符位置
     *
     * @param y
     */
    private void selectedPositionByY(float y) {
        //若滑动范围超出索引栏的高度范围，不再计算位置
        if (y < 0 || y > getHeight()) {
            return;
        }
        //单个字符所占的高度
        float singleCharHeight = ((float) getHeight()) / chars.length();
        //计算出当前选中的位置
        int position = (int) (y / singleCharHeight);
        //防止重复显示
        if (position != lastSelectedPosition) {
            //根据选中位置，获取相应位置的字符
            String selectedChar = chars.substring(position, position + 1);
            //展示选中的字符
            if (charIndicateView != null) {
                charIndicateView.showSelectedChar(selectedChar);
            }
            //设置监听的回调方法
            if (onSelectedListener != null) {
                onSelectedListener.onSelected(position, selectedChar);
            }
            //记录下当前位置
            lastSelectedPosition = position;
        }

    }

    /**
     * 字符选中的监听事件
     */
    public interface OnSelectedListener {
        /**
         * 选中的回调方法
         *
         * @param position     选中的位置
         * @param selectedChar 选中的字符
         */
        void onSelected(int position, String selectedChar);
    }
}
