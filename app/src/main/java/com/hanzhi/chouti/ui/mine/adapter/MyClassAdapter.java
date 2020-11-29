package com.hanzhi.chouti.ui.mine.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.mine.MyClassBean;
import com.hanzhi.chouti.utils.DateUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hanzhi.chouti.utils.DateUtils.YMDS;
import static com.hanzhi.chouti.utils.DateUtils.YMD_DOT;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 16:58
 */
public class MyClassAdapter extends BaseRecycleViewAdapter<MyClassBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.item_recycle_my_class;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<MyClassBean, MyClassAdapter> {
        @BindView(R.id.tv_teacher_status)
        TextView tvTeacherStatus;
        @BindView(R.id.iv_head_img)
        CircleImageView ivHeadImg;
        @BindView(R.id.tv_class_date)
        TextView tvClassDate;
        @BindView(R.id.tv_class_name)
        TextView tvClassName;
        @BindView(R.id.tv_class_time)
        TextView tvClassTime;
        @BindView(R.id.tv_teacher_level)
        TextView tvTeacherLevel;
        @BindView(R.id.btn_reapply)
        Button btnReapply;
        @BindView(R.id.btn_cancel)
        Button btnCancel;
        public ViewHolder(MyClassAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(MyClassBean data, int position) {
            if (data == null) {
                return;
            }
            tvTeacherStatus.setText(data.getOrderStatusDescription());
            glideUtils.loadCircleImage(data.getHeadImg(), ivHeadImg, 0);
            tvClassName.setText(data.getClassName());
            tvTeacherLevel.setText(data.getClassTypeName());
            tvClassDate.setText(YMD_DOT.format(DateUtils.getDate(YMDS, data.getClassTime())));
            tvClassTime.setText(data.getStartAndEndTime());
            addOnClickListener(R.id.btn_reapply, R.id.btn_cancel);
        }
    }
}
