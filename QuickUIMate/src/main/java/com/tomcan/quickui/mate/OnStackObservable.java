package com.tomcan.quickui.mate;

import com.tomcan.frame.v.QuickBaseFragment_V1_0;

/**
 * @author TomCan
 * @description:
 * @date:2022/10/10 9:20
 */
@Deprecated
public interface OnStackObservable {
    void onChanged();

    void onStacksInserted(QuickBaseFragment_V1_0 fragment);

    void onStacksRemoved();
}
