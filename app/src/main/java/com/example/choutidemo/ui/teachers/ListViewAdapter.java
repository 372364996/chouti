package com.example.choutidemo.ui.teachers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choutidemo.R;

import java.util.List;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private InnerItemOnclickListener mListener;

    public ListViewAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public interface InnerItemOnclickListener {
        void itemClick(View v);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Zujian {
        public ImageView image;
        public TextView title;
        public Button view;
        public TextView info;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.listview, null);
            // zujian.image=(ImageView)convertView.findViewById(R.id.image);
            viewHolder.tv_item_title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tv_item_button = (ImageView) convertView.findViewById(R.id.zan);
            viewHolder.tv_item_info = (TextView) convertView.findViewById(R.id.info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        // zujian.image.setBackgroundResource((Integer)data.get(position).get("image"));
        viewHolder.tv_item_title.setText((String) data.get(position).get("title") + position);
        viewHolder.tv_item_info.setText((String) data.get(position).get("info") + position);

        viewHolder.tv_item_button.setOnClickListener(this);

        viewHolder.tv_item_button.setTag(position);
        return convertView;
    }

    private final class ViewHolder {
        public TextView tv_item_title, tv_item_info;
        public ImageView tv_item_button;
    }


}
