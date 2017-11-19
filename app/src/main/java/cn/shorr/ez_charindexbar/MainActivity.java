package cn.shorr.ez_charindexbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import cn.shorr.slidebar.CharIndicateConfig;
import cn.shorr.slidebar.CharIndicateView;
import cn.shorr.slidebar.CharIndexBar;

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
        charIndexBar = (CharIndexBar) findViewById(R.id.char_slider_bar);

        //联系人设置适配器
        contactListView.setAdapter(contactListAdapter);

        CharIndicateConfig config = CharIndicateConfig
                .create()
                .setViewSize(100)
                .setTextSize(50)
                .setTextBold(false)
                .setTextColor(Color.WHITE)
                .setBackgroundColor(Color.parseColor("#5f000000"))
                .setBackgroundRadius(8);
        charIndicateView = new CharIndicateView(this, config);
        //索引栏和指示视图建立联系
        charIndexBar.setupWithIndicateView(charIndicateView);
        //设置选中监听事件
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
