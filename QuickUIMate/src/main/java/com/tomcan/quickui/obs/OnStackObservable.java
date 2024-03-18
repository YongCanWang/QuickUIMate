package com.tomcan.quickui.obs;

import com.tomcan.quickui.v.QuickBaseFragment;

/**
 * @author TomCan
 * @description:
 * @date:2022/10/10 9:20
 */
public interface OnStackObservable {
    void onChanged();

    void onStacksInserted(QuickBaseFragment fragment);

    void onStacksRemoved();
}
