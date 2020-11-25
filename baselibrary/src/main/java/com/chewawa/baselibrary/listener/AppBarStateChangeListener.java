package com.chewawa.baselibrary.listener;


import com.chewawa.baselibrary.BaseApplication;
import com.chewawa.baselibrary.utils.DensityUtil;
import com.google.android.material.appbar.AppBarLayout;

/**
 * CollapsingToolbarLayout折叠监听
 * nanfeifei
 * 2017/2/21 17:36
 *
 * @version 3.7.0
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

  public int height = 0;
  public AppBarStateChangeListener(){

  }
  public AppBarStateChangeListener(int height){
    this.height = height;
  }
  public enum State {
    EXPANDED,
    COLLAPSED,
    IDLE
  }
    public enum ScrollEvent {
        TOP,
        OTHER
    }
  private State mCurrentState = State.IDLE;

  @Override
  public final void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
      if (verticalOffset >= 0) {
          onInterceptScrollEvent(appBarLayout, ScrollEvent.TOP);
      } else {
        onInterceptScrollEvent(appBarLayout, ScrollEvent.OTHER);
      }
    if (verticalOffset == 0) {
      if (mCurrentState != State.EXPANDED) {
        onStateChanged(appBarLayout, State.EXPANDED);
      }
      mCurrentState = State.EXPANDED;
    } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
      if (mCurrentState != State.COLLAPSED) {
        onStateChanged(appBarLayout, State.COLLAPSED);
      }
      mCurrentState = State.COLLAPSED;
    }else if (Math.abs(verticalOffset) < appBarLayout.getTotalScrollRange()
        - DensityUtil.dip2px(BaseApplication.getInstance(),height)){
      if (mCurrentState != State.IDLE) {
        onStateChanged(appBarLayout, State.IDLE);
      }
      mCurrentState = State.IDLE;
    }
  }

  public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
  public abstract void onInterceptScrollEvent(AppBarLayout appBarLayout, ScrollEvent scrollEvent);
}