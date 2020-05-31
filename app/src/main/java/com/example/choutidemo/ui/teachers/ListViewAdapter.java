package com.example.choutidemo.ui.teachers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choutidemo.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
            viewHolder.tv_item_button = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.tv_item_info = (TextView) convertView.findViewById(R.id.info);
            viewHolder.tv_item_tags = (TextView) convertView.findViewById(R.id.tags);
            viewHolder.tv_item_avgScore = (TextView) convertView.findViewById(R.id.score);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        Picasso.get().load("http://hanzhiapp.hdlebaobao.cn"+(String) data.get(position).get("headImg")).into(viewHolder.tv_item_button);
//        Bitmap bimage=  getBitmapFromURL("https://dss2.bdstatic.com/6Ot1bjeh1BF3odCf/it/u=3531241846,3989253207&fm=85&app=92&f=JPEG?w=121&h=75&s=5A41B9424346314D704D0D0803008083");
//        .setImageBitmap(bimage);
        viewHolder.tv_item_title.setText((String) data.get(position).get("title"));
        viewHolder.tv_item_info.setText((String) data.get(position).get("info"));
        viewHolder.tv_item_tags.setText((String) data.get(position).get("tags"));
        viewHolder.tv_item_avgScore.setText((String) data.get(position).get("avgScore"));
        viewHolder.tv_item_button.setOnClickListener(this);

        viewHolder.tv_item_button.setTag(position);
        return convertView;
    }

    private final class ViewHolder {
        public TextView tv_item_title, tv_item_info,tv_item_tags,tv_item_avgScore;
        public ImageView tv_item_button;
    }


}
