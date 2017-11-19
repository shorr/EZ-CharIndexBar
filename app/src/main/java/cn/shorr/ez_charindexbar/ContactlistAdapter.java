package cn.shorr.ez_charindexbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 联系人列表适配器
 * Created by Shorr on 2016/12/11.
 */

public class ContactlistAdapter extends BaseAdapter {

    private Context context;
    private List<ContactBean> contactList;  //联系人列表

    public ContactlistAdapter(Context context, List<ContactBean> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
            holder.nameInitialTv = (TextView) convertView.findViewById(R.id.item_initial);
            holder.nameTv = (TextView) convertView.findViewById(R.id.item_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactBean contactBean = contactList.get(position);
        String nameInitial = contactBean.getNameInitial();
        String name = contactBean.getName();

        //是否隐藏首字母文本
        if (position - 1 >= 0) {
            //如果此条联系人记录首字母和上条一样，则本条记录隐藏首字母
            if (contactList.get(position - 1).getNameInitial().equals(nameInitial)) {
                holder.nameInitialTv.setVisibility(View.GONE);
            } else {
                holder.nameInitialTv.setVisibility(View.VISIBLE);
            }
        } else {
            holder.nameInitialTv.setVisibility(View.VISIBLE);
        }

        holder.nameInitialTv.setText(nameInitial);
        holder.nameTv.setText(name);

        return convertView;
    }

    private class ViewHolder {
        public TextView nameInitialTv;  //首字母文本
        public TextView nameTv;  //名字文本
    }
}
