package cn.shorr.ez_charindexbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人的Model类
 * Created by Shorr on 2016/12/11.
 */

public class ContactModel {

    private String[] initials = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};  //首字母数组

    /**
     * 获取联系人列表
     *
     * @return
     */
    public List<ContactBean> getContactList() {
        //用于演示的假数据
        List<ContactBean> contactBeanList = new ArrayList<>();

        for (int i = 0; i < initials.length; i++) {
            //每个首字母生成两条记录
            for (int j = 0; j < 2; j++) {
                ContactBean bean = new ContactBean();
                bean.setNameInitial(initials[i]);
                bean.setName(initials[i] + "charindexbar" + j);
                contactBeanList.add(bean);
            }
        }

        return contactBeanList;
    }
}
