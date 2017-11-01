package com.dx.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.dx.ui.utils.Memory;
import com.dx.ui.utils.StorageUtil;
import com.dx.ui.view.CleanCompletionView;
import com.dx.ui.view.HomeDashView;
import com.dx.ui.view.InterfaceU;

public class MainActivity extends AppCompatActivity  {
    private CleanCompletionView av;
    HomeDashView homeDashView;
    private long sizeStorageUsed=0L;
    private long sizeStorageTotal=0L;
    private int percentStorage=0;
    private long sizeMemUsed=0L;
    private long sizeMemTotal=0L;
    private int percentMem=0;
    private TextView tvPctStorage;
    private TextView tvSizeStorage;
    private TextView tvPctMEM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVars();
        initView();

    }
    private void initView(){
        tvPctStorage = (TextView)findViewById(R.id.tvPctstorage);
        tvPctStorage.setText(""+percentStorage);
        double u = sizeStorageUsed/(1024.0*1024*1024L);
        double t = sizeStorageTotal/(1024.0*1024*1024L);
        tvSizeStorage = (TextView)findViewById(R.id.tvSizeStorage);
        tvSizeStorage.setText(String.format(getResources().getString(R.string.storagesizess), u, t));
        tvPctMEM = (TextView)findViewById(R.id.tvPctMEM);
        tvPctMEM.setText(""+percentMem+"%");

        homeDashView = (HomeDashView)findViewById(R.id.hdv);
        homeDashView.setValueCallback(new InterfaceU(){
            @Override
            public void onAnimUpdate(int dashProgress, int bottomProgress) {
                //JLog.e(dashProgress+","+bottomProgress);
            }
        });
        homeDashView.setPercentData(percentStorage, percentMem, 3000L);//存储 内存 动画时长

        final View inflate = ((ViewStub)this.findViewById(R.id.eq)).inflate();
        inflate.setVisibility(View.VISIBLE);
        (av = (CleanCompletionView)findViewById(R.id.gw)).setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {

            }
        });
        av.startAnim(null, 2000);
        av.startAnim(new AnimatorListenerAdapter() {
            public void onAnimationEnd(final Animator animator) {
                super.onAnimationEnd(animator);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        homeDashView.resume();
    }

    @Override
    public void onStop() {
        super.onStop();
        homeDashView.clean();
    }


    public void initVars() {
         Long memfree = Memory.getAvailMemory();
        sizeMemTotal = Memory.getTotalMemory();
        sizeMemUsed = sizeMemTotal - memfree;
        percentMem = (int)(sizeMemUsed*100.0/sizeMemTotal);

        sizeStorageTotal = StorageUtil
                .getTotalExternalMemorySize();
        sizeStorageUsed= sizeStorageTotal
                - StorageUtil.getAvailableExternalMemorySize();
        percentStorage = (int)(sizeStorageUsed*100.0/sizeStorageTotal);
    }


}
