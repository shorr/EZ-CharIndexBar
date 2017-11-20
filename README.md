# EZ-CharIndexBar

EZ-CharIndexBar是一个Android上的“字符索引栏”，效果类似WeChat通讯录右侧的索引栏样式。效果如下：

<img src="/screenshot/demo.gif?raw=true" width=360 height=640 alt="screenshot gif">


## Gradle 依赖
在app的`build.gradle` 下添加如下依赖即可：

```groovy
dependencies {
    compile 'cn.shorr:ez-charindexbar:0.1.1'
}
```

## 使用

#### 在布局xml文件中添加：

```xml
<cn.shorr.slidebar.CharIndexBar
        android:id="@+id/char_index_bar"
        android:layout_width="25dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        android:layout_marginBottom="20dp"
        app:barBackground="#3f000000"
        app:barChars="☆ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
        app:barTextColor="@android:color/black"
        app:barTextSize="14sp" />
```
- barBackground：索引栏背景色
- barChars：索引栏显示字符
- barTextColor：索引栏字体颜色
- barTextSize：索引栏字体大小

#### 在Activity中如下操作：

```java
//1.创建字符指示视图
//使用默认参数字符指示视图
charIndicateView = new CharIndicateView(this);
/*使用自定义参数字符指示视图
CharIndicateConfig config = CharIndicateConfig
                .create()
                .setViewSize(100) //视图大小(dp)
                .setTextSize(50) //字体大小(sp)
                .setTextBold(false) //字体是否加粗
                .setTextColor(Color.WHITE) //字体加粗
                .setBackgroundColor(Color.parseColor("#5f000000")) //视图背景色
                .setBackgroundRadius(8); //视图背景圆角半径(dp)
charIndicateView = new CharIndicateView(this, config);
*/
//动态设置索引栏字符
//charIndexBar.setChars("0123456789ABCDEFGHIJKLMNO");

//2.索引栏和指示视图建立联系
charIndexBar.setupWithIndicateView(charIndicateView);
//3.设置选中监听事件
charIndexBar.setOnSelectedListener(new CharIndexBar.OnSelectedListener() {
  @Override
  public void onSelected(int position, String selectedChar) {
    Log.e(TAG, "选中--" + selectedChar);
    //根据选中的字符来定位列表的位置
   ... 
  }
});
```



