package com.chewawa.baselibrary.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.R2;
import com.chewawa.baselibrary.utils.DensityUtil;


/**
 * Created by liqin on 2015/12/24.
 */
public class InsureBar implements View.OnClickListener {
  ImageView ivBack;
  TextView tvTitle;
  Activity activity;
  ImageView ivRight;
  TextView tvBack;
  TextView tvRight;
  TextView tvFinish;
  RelativeLayout rlBarLay;
  TextView tvSearchHint;

  public ImageView getBack() {
    return ivBack;
  }

  public InsureBar(Activity activity) {
    this.activity = activity;
    this.ivBack = (ImageView) activity.findViewById(R.id.iv_back);
    this.tvTitle = (TextView) activity.findViewById(R.id.tv_title);
    this.ivRight = (ImageView) activity.findViewById(R.id.iv_right);
    this.tvFinish = (TextView) activity.findViewById(R.id.tv_finish);
    this.tvBack = activity.findViewById(R.id.tv_back);
    this.tvRight = activity.findViewById(R.id.tv_right);
    this.rlBarLay = (RelativeLayout) activity.findViewById(R.id.rl_bar_lay);
    this.tvSearchHint = activity.findViewById(R.id.tv_search_hint);
    this.ivBack.setOnClickListener(this);
    this.tvBack.setOnClickListener(this);
    this.tvFinish.setOnClickListener(this);
    this.tvFinish.setVisibility(View.GONE);
    this.ivRight.setVisibility(View.GONE);
    this.tvRight.setVisibility(View.GONE);
    this.tvSearchHint.setVisibility(View.GONE);
  }
  public InsureBar(Activity activity, View view) {
    this.activity = activity;
    this.ivBack = (ImageView) view.findViewById(R.id.iv_back);
    this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
    this.ivRight = (ImageView) view.findViewById(R.id.iv_right);
    this.tvFinish = (TextView) view.findViewById(R.id.tv_finish);
    this.tvBack = view.findViewById(R.id.tv_back);
    this.tvRight = view.findViewById(R.id.tv_right);
    this.tvSearchHint = view.findViewById(R.id.tv_search_hint);
    this.rlBarLay = (RelativeLayout) view.findViewById(R.id.rl_bar_lay);
    this.ivBack.setOnClickListener(this);
    this.tvBack.setOnClickListener(this);
    this.tvFinish.setOnClickListener(this);
    this.tvFinish.setVisibility(View.GONE);
    this.ivRight.setVisibility(View.GONE);
    this.tvRight.setVisibility(View.GONE);
    this.tvSearchHint.setVisibility(View.GONE);
  }
  public ImageView getRight() {
    return ivRight;
  }
  public TextView getTvRight(){
    return tvRight;
  }
  public ImageView getIvRight(){
    return ivRight;
  }
  public TextView getTvSearchView(){ return tvSearchHint;}
  public void setTvRightHeight(float dpValue, int rightMargin){
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
            , RelativeLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.height = DensityUtil.dip2px(activity, dpValue);
    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
    layoutParams.rightMargin = DensityUtil.dip2px(activity, rightMargin);
    tvRight.setLayoutParams(layoutParams);
    tvRight.setPadding(DensityUtil.dip2px(activity, 8), 0, DensityUtil.dip2px(activity, 8), 0);
  }
  public void setTvBackHeight(float dpValue){
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
            , RelativeLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.height = DensityUtil.dip2px(activity, dpValue);
    layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
    tvBack.setLayoutParams(layoutParams);
    tvBack.setPadding(DensityUtil.dip2px(activity, 4), 0, DensityUtil.dip2px(activity, 12), 0);
  }
  public TextView getTvBack(){
    return tvBack;
  }
  public RelativeLayout getParentLay(){
    return rlBarLay;
  }

  /**
   * 设置标题栏右边文字（不带背景框）
   * @param rightRes
   */
  public void setRight(int rightRes){
    if(ivRight!=null){
      ivRight.setImageResource(rightRes);
    }
  }
  /**
   * 设置标题栏右边文字是否显示
   * @param visibility
   */
  public void setVisibleRight(int visibility){
    if(ivRight != null){
      ivRight.setVisibility(visibility);
    }
  }
  /**
   * 重写返回键监听
   * @param onClickListener
   */
  public void setOnBackClickListener(View.OnClickListener onClickListener){
    this.ivBack.setOnClickListener(onClickListener);
  }


  public void setTitle(String str) {
    this.tvTitle.setText(str);
  }
  public void setTitleColor(@ColorRes int color){
    this.tvTitle.setTextColor(ContextCompat.getColor(activity, color));
  }

  public TextView getTvFinish() {
    return tvFinish;
  }

  /**
   * 是否显示关闭按钮
   * */
  public void showFinish(int visibility){
    this.tvFinish.setVisibility(visibility);
  }

  public void hideBack() {
    ivBack.setVisibility(View.GONE);
  }

  /**
   * 设置背景色
   * @param color
   */
  public void setBackgroundColor(@ColorRes int color){
    rlBarLay.setBackgroundColor(ContextCompat.getColor(activity, color));
  }

  @Override public void onClick(View v) {
    int i = v.getId();
    if (i == R.id.iv_back) {
      activity.finish();
    } else if (i == R.id.tv_back) {
      activity.finish();
    } else if (i == R.id.tv_finish) {
      activity.finish();
    }

  }
}
