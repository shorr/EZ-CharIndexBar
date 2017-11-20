package cn.shorr.ez_charindexbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import cn.shorr.charindexbar.CharIndexBar;
import cn.shorr.charindexbar.CharIndicateView;


public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private List<ContactBean> contactList;  //联系人集合

    private ListView contactListView;  //联系人列表
    private CharIndexBar charIndexBar;  //字符索引栏
    private CharIndicateView charIndicateView;  //字符指示视图
    private ContactlistAdapter contactListAdapter;  //联系人列表适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化变量
        initVariables();
        //初始化View
        initView();
    }

    /**
     * 初始化变量
     */
    private void initVariables() {
        ContactModel model = new ContactModel();
        contactList = model.getContactList();
        contactListAdapter = new ContactlistAdapter(this, contactList);
    }

    /**
     * 初始化View
     */
    private void initView() {
        contactListView = (ListView) findViewById(R.id.contact_listview);
        charIndexBar = (CharIndexBar) findViewById(R.id.char_index_bar);

        //联系人设置适配器
        contactListView.setAdapter(contactListAdapter);

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
                //根据选中的字符来定位ListView的位置
                locateListViewPositionByChar(selectedChar);
            }
        });
    }

    /**
     * 根据选中的字符来定位ListView的位置
     *
     * @param selectedChar
     */
    private void locateListViewPositionByChar(String selectedChar) {
        //遍历联系人列表找到对应字符的位置
        for (int i = 0; i < contactList.size(); i++) {
            String nameInitial = contactList.get(i).getNameInitial();
            if (nameInitial.equals(selectedChar)) {
                //定位ListView的位置
                contactListView.setSelection(i);
                break;
            }
        }
    }

}
