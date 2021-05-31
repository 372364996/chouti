package com.hanzhi.onlineclassroom.ui.teachers.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.teachers.TeacherBean;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 11:03
 */
public class TeacherAdapter extends BaseRecycleViewAdapter<TeacherBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.listview;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<TeacherBean, TeacherAdapter> {
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.score)
        TextView score;
        @BindView(R.id.iv_collect)
        ImageView ivCollect;
        @BindView(R.id.info)
        TextView info;
        @BindView(R.id.tags)
        TextView tags;
        public ViewHolder(TeacherAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(TeacherBean data, int position) {
            if (data == null) {
                return;
            }
            glideUtils.loadImage(data.getHeadImg(), image, R.drawable.hanzhilogo);
            title.setText(data.getName());
            info.setText(data.getDescription());
            info.setVisibility(TextUtils.isEmpty(data.getDescription()) ? View.GONE : View.VISIBLE);
            tags.setText(data.getTags());
            score.setText(data.getAvgScore());
            ivCollect.setImageResource(data.isFans() ? R.drawable.favorites_fill : R.drawable.favorites);
            addOnClickListener(R.id.iv_collect);
        }
    }
}
