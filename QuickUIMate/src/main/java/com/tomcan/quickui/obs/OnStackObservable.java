package com.tomcan.quickui.obs;

import com.tomcan.quickui.v.QuickBaseFragment_V1_0;

/**
 * @author TomCan
 * @description:
 * @date:2022/10/10 9:20
 */
public interface OnStackObservable {
    void onChanged();

    void onStacksInserted(QuickBaseFragment_V1_0 fragment);

    void onStacksRemoved();
}
