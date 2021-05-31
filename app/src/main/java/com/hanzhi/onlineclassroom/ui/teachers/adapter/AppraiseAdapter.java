package com.hanzhi.onlineclassroom.ui.teachers.adapter;

import android.view.View;
import android.widget.TextView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.teachers.AppraiseBean;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 11:03
 */
public class AppraiseAdapter extends BaseRecycleViewAdapter<AppraiseBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.item_recycle_appraise;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<AppraiseBean, AppraiseAdapter> {
        @BindView(R.id.iv_head_img)
        CircleImageView ivHeadImg;
        @BindView(R.id.tv_appraise_time)
        TextView tvAppraiseTime;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_ranking)
        TextView tvRanking;
        @BindView(R.id.tv_appraise_context)
        TextView tvAppraiseContext;
        public ViewHolder(AppraiseAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(AppraiseBean data, int position) {
            if (data == null) {
                return;
            }
            glideUtils.loadCircleImage(data.getHeadImg(), ivHeadImg, R.drawable.hanzhilogo);
            tvAppraiseTime.setText(data.getCreateTime());
            tvUserName.setText(data.getUserName());
            tvRanking.setText(String.valueOf(data.getScores()));
            tvAppraiseContext.setText(data.getContent());
        }
    }
}
