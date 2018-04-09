package com.edri.ron.easyenglish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * Created by Ron on 06/04/2018.
 */

public class FragmentWeb extends Fragment {

    private LinearLayout linearLayoutRoot;

    public FragmentWeb() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        linearLayoutRoot = (LinearLayout)inflater.inflate(R.layout.fragment_web_dictionary, container, false);
        WebView webView = (WebView) linearLayoutRoot.findViewById(R.id.webView);
        webView.loadUrl("http://www.morfix.co.il/");
        return linearLayoutRoot;
    }


}
