package com.hanzhi.chouti.ui.selectclass;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chewawa.baselibrary.base.NBaseActivity;
import com.hanzhi.chouti.MainActivity;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.ClassApplyBean;
import com.hanzhi.chouti.bean.ClassCardBean;
import com.hanzhi.chouti.ui.selectclass.adapter.ClassCardAdapter;
import com.hanzhi.chouti.ui.selectclass.contract.ClassApplyContract;
import com.hanzhi.chouti.ui.selectclass.presenter.ClassApplyPresenter;
import com.hanzhi.chouti.view.WrapContentHeightViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/28 16:47
 */
public class ClassApplyActivity extends NBaseActivity<ClassApplyPresenter> implements ClassApplyContract.View {

    @BindView(R.id.ll_title_lay)
    LinearLayout llTitleLay;
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.tv_session_name)
    TextView tvSessionName;
    @BindView(R.id.tv_class_time)
    TextView tvClassTime;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.vp_class_card)
    WrapContentHeightViewPager vpClassCard;
    ClassApplyBean classApplyBean;
    ClassCardAdapter classCardAdapter;
    List<ClassCardBean> cardList;
    public static void start(Context context, ClassApplyBean classApplyBean) {
        Intent starter = new Intent(context, ClassApplyActivity.class);
        starter.putExtra("classApplyBean", classApplyBean);
        context.startActivity(starter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_class_apply;
    }

    @Override
    protected void initView() {
        classApplyBean = getIntent().getParcelableExtra("classApplyBean");
        initToolBar();
        toolbarLay.setTitle(R.string.title_class_apply);
        initClassInfo(classApplyBean);
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        presenter.getClassApplyData(classApplyBean);
        presenter.getClassCardList();
    }

    @Override
    public ClassApplyPresenter initPresenter() {
        return new ClassApplyPresenter(this);
    }

    public void initClassInfo(ClassApplyBean classApplyBean) {
        if (classApplyBean == null) {
            return;
        }
        tvTeacherName.setText(classApplyBean.getTeacherName());
        tvSessionName.setText(classApplyBean.getClassName());
        tvClassTime.setText(classApplyBean.getDateTimeStr());
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: {
                if(cardList == null||cardList.size() == 0||vpClassCard == null){
                    return;
                }
                presenter.submitClassApply(classApplyBean, cardList.get(vpClassCard.getCurrentItem()).getId());
                break;
            }
        }
    }

    @Override
    public void setClassApplyData(String message) {
        tvTips.setText(message);
    }

    @Override
    public void setClassCardList(List<ClassCardBean> list) {
        this.cardList = list;
        classCardAdapter = new ClassCardAdapter(this, list);
        vpClassCard.setAdapter(classCardAdapter);
    }

    @Override
    public void submitClassApplySuccess() {
        MainActivity.startMainActivity(ClassApplyActivity.this, 3);
        finish();
    }
}
