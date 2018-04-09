package com.edri.ron.easyenglish.Classes;

import android.os.Bundle;

/**
 * Created by Ron on 06/04/2018.
 */

public abstract class FragmentSaveState {

    private static Bundle addWordBundle;
    private static Bundle dicionaryBundle;
    private static boolean switchState;

    public static Bundle getAddWordBundle() {
        return addWordBundle;
    }

    public static Bundle getDicionaryBundle() {
        return dicionaryBundle;
    }

    public static void setAddWordBundle(String name, String trans, String desc, String example, int radioId) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("trans", trans);
        bundle.putString("desc", desc);
        bundle.putString("example", example);
        bundle.putInt("group", radioId);
        addWordBundle = bundle;
    }

    public static void setAddWordBundle(String name, String trans, int radioId) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("trans", trans);
        bundle.putString("desc", "");
        bundle.putString("example", "");
        bundle.putInt("group", radioId);
        addWordBundle = bundle;
    }

    public static void setDicionaryBundle(String name, String trans, boolean enabled) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("trans", trans);
        bundle.putBoolean("enable", enabled);
        dicionaryBundle = bundle;
    }

    public static boolean getSwitchState() {
        return switchState;
    }

    public static void setSwitchState(boolean newState) {
        switchState = newState;
    }
}
