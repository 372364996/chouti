package com.hanzhi.chouti.ui.mine.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.chouti.bean.mine.MyClassTabBean;
import com.hanzhi.chouti.ui.mine.contract.MyClassContract;
import com.hanzhi.chouti.ui.mine.model.MyClassModel;

import java.util.ArrayList;
import java.util.List;

public class MyClassPresenter extends BasePresenterImpl<MyClassContract.View, MyClassModel> implements MyClassContract.Presenter, MyClassContract.OnGetTabListListener, MyClassContract.OnCancelClassListener {
    public MyClassPresenter(MyClassContract.View view) {
        super(view);
    }

    @Override
    public MyClassModel initModel() {
        return new MyClassModel();
    }

    @Override
    public void getTabList() {
        model.getTabList(this);
    }

    @Override
    public void cancelClass(int id) {
        view.showProgressDialog();
        model.cancelClass(id, this);
    }

    @Override
    public void onGetTabListSuccess(List<MyClassTabBean> list) {
        if(list == null){
            return;
        }
        view.setTabList(list, getTitleList(list));
    }
    public List<String> getTitleList(List<MyClassTabBean> list){
        List<String> titleList = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            titleList.add(list.get(i).getName());
        }
        return titleList;
    }

    @Override
    public void onGetTabListFailure(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    public void onCancelClassSuccess(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }

    @Override
    public void onCancelClassFailure(String message) {
        view.hideProgressDialog();
        ToastUtils.showToast(message);
    }
}
