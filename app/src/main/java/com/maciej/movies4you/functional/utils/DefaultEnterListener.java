package com.maciej.movies4you.functional.utils;


import android.view.KeyEvent;
import android.view.View;

import com.inverce.mod.v2.core.utils.Ui;


public class DefaultEnterListener implements View.OnKeyListener {

    private boolean useEventBus;

    public DefaultEnterListener(boolean useEventBus) {
        this.useEventBus = useEventBus;
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER) {
            onKeyEnter(view, keyEvent);
            if (useEventBus) {

            } else {
                view.clearFocus();
                Ui.hideSoftInput(view);
                return true;
            }
        }
        return false;
    }

    protected void onKeyEnter(View view, KeyEvent keyEvent) {

    }
}
