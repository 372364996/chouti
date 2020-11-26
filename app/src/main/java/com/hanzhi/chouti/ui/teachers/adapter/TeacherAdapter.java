package com.hanzhi.chouti.ui.teachers.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.teachers.TeacherBean;

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
        @BindView(R.id.xingxing)
        ImageView xingxing;
        @BindView(R.id.score)
        TextView score;
        @BindView(R.id.zan)
        ImageView zan;
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
            glideUtils.loadImage(data.getHeadImg(), image, 0);
            title.setText(data.getName());
            info.setText(data.getDescription());
            tags.setText(data.getTags());
            score.setText(data.getAvgScore());
        }
    }
}
