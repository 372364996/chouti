package com.hanzhi.onlineclassroom.ui.selectclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.hanzhi.onlineclassroom.R;
import com.hanzhi.onlineclassroom.bean.mine.ClassCardBean;

import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 15:10
 */
public class ClassCardAdapter extends PagerAdapter {
    private List<ClassCardBean> mData;
    private Context mContext;

    public ClassCardAdapter(Context context, List<ClassCardBean> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_viewpager_class_card, null);
        TextView tvUseNum = view.findViewById(R.id.tv_use_num);
        TextView tvResidueNum = view.findViewById(R.id.tv_residue_num);
        TextView tvUseInfo = view.findViewById(R.id.tv_use_info);
        ClassCardBean classCardBean = mData.get(position);
        if(classCardBean != null){
            tvUseNum.setText(classCardBean.getName());
            int residue = classCardBean.getCounts() - classCardBean.getUseCounts();
            tvResidueNum.setText(mContext.getString(R.string.class_apply_residue_num, residue));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
