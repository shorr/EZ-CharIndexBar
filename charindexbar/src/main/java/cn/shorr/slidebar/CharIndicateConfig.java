package cn.shorr.slidebar;

/**
 * Configuration of CharIndicateView
 * Created by Shorr on 2017/8/31.
 */

public class CharIndicateConfig {

    public int viewSize = -1;
    public int textSize = -1;
    public int textColor = -1;
    public int backgroundColor = -1;
    public int backgroundRadius = -1;
    public boolean textBold = false;

    private static CharIndicateConfig config;

    public static CharIndicateConfig create() {
        config = new CharIndicateConfig();
        return config;
    }

    private CharIndicateConfig() {

    }

    /**
     * set the view size
     *
     * @param dpSize dp
     * @return
     */
    public CharIndicateConfig setViewSize(int dpSize) {
        viewSize = dpSize;
        return config;
    }

    /**
     * set the text size
     *
     * @param spSize sp
     * @return
     */
    public CharIndicateConfig setTextSize(int spSize) {
        textSize = spSize;
        return config;
    }

    /**
     * set the text color
     *
     * @param color
     * @return
     */
    public CharIndicateConfig setTextColor(int color) {
        textColor = color;
        return config;
    }

    /**
     * set the text bold
     *
     * @param bold
     * @return
     */
    public CharIndicateConfig setTextBold(boolean bold) {
        textBold = bold;
        return config;
    }

    /**
     * set the text background color
     *
     * @param color
     * @return
     */
    public CharIndicateConfig setBackgroundColor(int color) {
        backgroundColor = color;
        return config;
    }

    /**
     * set the text background radius
     *
     * @param dpRadius dp
     * @return
     */
    public CharIndicateConfig setBackgroundRadius(int dpRadius) {
        backgroundRadius = dpRadius;
        return config;
    }


}
