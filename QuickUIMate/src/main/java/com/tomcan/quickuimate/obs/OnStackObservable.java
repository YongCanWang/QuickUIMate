package com.tomcan.quickuimate.obs;

import com.tomcan.quickuimate.ui.NaviBaseFragment;

/**
 * @author TomCan
 * @description:
 * @date:2022/10/10 9:20
 */
public interface OnStackObservable {
    void onChanged();

    void onStacksInserted(NaviBaseFragment fragment);

    void onStacksRemoved();
}
