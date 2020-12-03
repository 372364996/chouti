package com.hanzhi.chouti.ui.mine.adapter;

import android.view.View;
import android.widget.TextView;

import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;
import com.chewawa.baselibrary.base.BaseRecycleViewHolder;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.bean.mine.ClassCardBean;

import butterknife.BindView;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2020/11/26 16:58
 */
public class WalletCardAdapter extends BaseRecycleViewAdapter<ClassCardBean> {

    @Override
    public int getConvertViewId(int viewType) {
        return R.layout.item_recycle_wallet_card;
    }

    @Override
    public BaseRecycleViewHolder getNewHolder(View view, int viewType) {
        return new ViewHolder(this, view);
    }

    public static class ViewHolder extends BaseRecycleViewHolder<ClassCardBean, WalletCardAdapter> {
        @BindView(R.id.tv_use_num)
        TextView tvUseNum;
        @BindView(R.id.tv_residue_num)
        TextView tvResidueNum;
        @BindView(R.id.tv_use_info)
        TextView tvUseInfo;
        public ViewHolder(WalletCardAdapter adapter, View itemView) {
            super(adapter, itemView);
        }

        @Override
        public void loadData(ClassCardBean data, int position) {
            if (data == null) {
                return;
            }
            tvUseNum.setText(data.getName());
            int residue = data.getCounts() - data.getUseCounts();
            tvResidueNum.setText(context.getString(R.string.class_apply_residue_num, residue));
        }
    }
}
