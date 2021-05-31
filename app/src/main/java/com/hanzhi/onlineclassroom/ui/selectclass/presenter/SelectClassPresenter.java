package com.hanzhi.onlineclassroom.ui.selectclass.presenter;

import com.chewawa.baselibrary.base.presenter.BasePresenterImpl;
import com.chewawa.baselibrary.utils.ToastUtils;
import com.hanzhi.onlineclassroom.bean.selectclass.ClassTabBean;
import com.hanzhi.onlineclassroom.ui.selectclass.contract.SelectClassContract;
import com.hanzhi.onlineclassroom.ui.selectclass.model.SelectClassModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 18:39
 */
public class SelectClassPresenter extends BasePresenterImpl<SelectClassContract.View, SelectClassModel> implements SelectClassContract.Presenter, SelectClassContract.OnGetTabListListener {
    public SelectClassPresenter(SelectClassContract.View view) {
        super(view);
    }

    @Override
    public SelectClassModel initModel() {
        return new SelectClassModel();
    }

    @Override
    public void getTabList() {
        model.getTabList(this);

    }

    @Override
    public void onGetTabListSuccess(List<ClassTabBean> list) {
        if(list == null){
            return;
        }
        view.setTabList(list, getTitleList(list));
    }
    public List<String> getTitleList(List<ClassTabBean> list){
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
}
