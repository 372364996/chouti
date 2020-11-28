package com.hanzhi.chouti.ui.selectclass.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chewawa.baselibrary.utils.glide.GlideUtils;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.selectclass.ClassBean;

import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 15:10
 */
public class ClassDetailAdapter extends PagerAdapter {
    private List<ClassBean.ClassMaterialsBean> mData;
    private Context mContext;
    GlideUtils glideUtils;
    public ClassDetailAdapter(Context context , List<ClassBean.ClassMaterialsBean> list) {
        mContext = context;
        mData = list;
        glideUtils = new GlideUtils(mContext);
    }
    @Override
    public int getCount() {
        if(mData == null){
            return 0;
        }
        return mData.size();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_viewpager_image,null);
        ImageView imageView = view.findViewById(R.id.iv_class_image);
        glideUtils.loadImage(mData.get(position).getUrl(), imageView, R.drawable.book_image);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
