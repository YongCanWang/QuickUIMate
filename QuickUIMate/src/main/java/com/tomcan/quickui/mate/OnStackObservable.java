package com.tomcan.quickui.mate;

import androidx.fragment.app.Fragment;

/**
 * @author TomCan
 * @description:
 * @date:2022/10/10 9:20
 */
@Deprecated
public interface OnStackObservable {
    void onChanged();

    void onStacksInserted(Fragment fragment);

    void onStacksRemoved();
}
