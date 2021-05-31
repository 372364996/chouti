package com.hanzhi.onlineclassroom.ui.selectclass.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.selectclass.ClassBean;

import butterknife.BindView;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 16:58
 */
public class SelectClassAdapter extends BaseRecycleViewAdapter<ClassBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.gridview_item;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<ClassBean, SelectClassAdapter> {
        @BindView(R.id.iv_class_image)
        ImageView ivClassImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        public ViewHolder(SelectClassAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(ClassBean data, int position) {
            if (data == null) {
                return;
            }
            tvName.setText(data.getName());
            glideUtils.loadImage(data.getImg(), ivClassImage, R.drawable.book_image);
        }
    }
}
