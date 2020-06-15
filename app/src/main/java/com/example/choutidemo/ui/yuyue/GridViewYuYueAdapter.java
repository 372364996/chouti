package com.example.choutidemo.ui.yuyue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choutidemo.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class GridViewYuYueAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> data;
    public GridViewYuYueAdapter(Context context, List<Map<String, Object>> data) {
        super();
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.gridview_yuyue_item, null);
            holder = new ViewHolder();
            holder.tv = convertView.findViewById(R.id.time);
            //holder.iv = convertView.findViewById(R.id.class_image);
            convertView.setTag(holder);// 如果convertView为空就 把holder赋值进去
        } else {
            holder = (ViewHolder) convertView.getTag();// 如果convertView不为空，那么就在convertView中getTag()拿出来
        }
//        Integer current = data.get(position);
      //  Picasso.get().load((String) data.get(position).get("Img")).into(holder.iv);
        holder.tv.setText((String) data.get(position).get("Name"));
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}