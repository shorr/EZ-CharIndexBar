package cn.shorr.slidebar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * 字符指示器
 * Created by Shorr on 2016/12/7.
 */
@SuppressLint("AppCompatCustomView")
public class CharIndicateView extends TextView {

    private static final int DEFAULT_VIEW_DP_SIZE = 100;
    private static final int DEFAULT_TEXT_SP_SIZE = 50;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_BACKGROUND = Color.parseColor("#5f000000");
    private static final int DEFAULT_TEXT_BACKGROUND_DP_RADIUS = 6;

    private Context context;
    private CharIndicateConfig config;

    public CharIndicateView(Context context) {
        super(context);
        this.config = CharIndicateConfig.create();
        init(context);
    }

    public CharIndicateView(Context context, CharIndicateConfig config) {
        super(context);
        this.config = config;
        init(context);
    }

    /**
     * 初始化操作
     */
    private void init(Context context) {
        this.context = context;

        FrameLayout rootLayout = (FrameLayout) ((Activity) context).findViewById(android.R.id.content);
        int viewSize = config.viewSize == -1 ? DEFAULT_VIEW_DP_SIZE : config.viewSize;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                UnitConvert.dp2px(context, viewSize), UnitConvert.dp2px(context, viewSize));
        layoutParams.gravity = Gravity.CENTER;
        this.setLayoutParams(layoutParams);
        this.setGravity(Gravity.CENTER);
        this.setIncludeFontPadding(false);
        if (config.textBold) this.setTypeface(null, Typeface.BOLD);
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.textSize == -1 ?
                DEFAULT_TEXT_SP_SIZE : config.textSize);
        this.setTextColor(config.textColor == -1 ? DEFAULT_TEXT_COLOR : config.textColor);
        this.setBackground(config.backgroundColor == -1 ? DEFAULT_TEXT_BACKGROUND : config.backgroundColor,
                config.backgroundRadius == -1 ? DEFAULT_TEXT_BACKGROUND_DP_RADIUS : config.backgroundRadius);
        this.setVisibility(View.GONE);

        rootLayout.addView(this);
    }

    /**
     * 设置背景
     *
     * @param color
     * @param radius dp
     */
    public void setBackground(int color, int radius) {
        radius = UnitConvert.dp2px(context, radius);
        //设置圆角矩形背景
        float[] outerRadii = new float[8];
        for (int i = 0; i < outerRadii.length; i++) {
            outerRadii[i] = radius;
        }
        RoundRectShape shape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(color);
        //将圆角矩形背景设置到当前View
        this.setBackgroundDrawable(shapeDrawable);
    }

    /**
     * 展示选中字符
     *
     * @param selectedChar 要显示的字符
     */
    public void showSelectedChar(String selectedChar) {
        this.setText(selectedChar);
    }

}
