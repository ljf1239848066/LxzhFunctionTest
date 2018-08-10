package com.lxzh123.funcdemo.rxjavatest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestRxJavaActivity extends Activity {

    private final static String TAG="TestRxJavaActivity";
    private TextView tvLog;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_rx_java);
        tvLog=(TextView)findViewById(R.id.tvLog);
        seekBar=(SeekBar)findViewById(R.id.pbProgress);
        seekBar.setMax(100);

        // 观察者
        Observer<String> observer=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"observer:onSubscribe");
                tvLog.append("observer:onSubscribe\n");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"observer:onNext:"+s);
                tvLog.append("observer:onNext:"+s+"\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"observer:onError");
                tvLog.append("observer:onError\n");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"observer:onComplete");
                tvLog.append("observer:onComplete\n");
            }
        };

        Observable observable= Observable.fromArray("Hello","Rx","Java");
        observable.subscribe(observer);

        Observer<Integer> observer1=new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"observer1:onSubscribe");
                tvLog.append("observer1:onSubscribe\n");
            }

            @Override
            public void onNext(Integer i) {
                Log.d(TAG,"observer1:onNext:"+i);
                tvLog.append("observer1:onNext:"+i+"\n");
                seekBar.setProgress(i*5);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"observer1:onError");
                tvLog.append("observer1:onError\n");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"observer1:onComplete");
                tvLog.append("observer1:onComplete\n");
            }
        };

        Observable<Integer> observable1=Observable.fromArray(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);

        observable1.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer1);

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                int cnt=1;
                int idx=1;
                while(true){
                    emitter.onNext(idx++);
                    try {
                        Thread.sleep(150);
                    }catch (Exception ex){

                    }
                    if(idx==20){
                        idx=1;
                        cnt++;
                    }
                    if(cnt==5){
                        break;
                    }
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer i) {
                Log.d(TAG,"observable2.subscribe:doOnNext:"+i);
                seekBar.setProgress(i*5);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG,"observable2.subscribe:onComplete");
                seekBar.setProgress(100);
            }
        });
    }
}
