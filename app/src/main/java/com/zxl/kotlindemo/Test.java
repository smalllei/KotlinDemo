package com.zxl.kotlindemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
        mmm(new Listener() {
            @Override
            public void on(int aa) {

            }
        });
    }

    public void mmm(Listener listener){


    }


  interface Listener{
        void on(int aa);
  }

}
