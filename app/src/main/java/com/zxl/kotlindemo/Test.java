package com.zxl.kotlindemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/24
 * @time: 15:49
 * @description:
 */

public class Test extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] points = new int[2];
        for (int i =0;i<10;i++){}
        GridView  view=new GridView(this);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

    }




}
