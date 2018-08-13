package com.lxzh123.funcdemo.rxjavatest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestRxJavaActivity extends Activity {

    private final static String TAG="TestRxJavaActivity";
    private TextView tvLog;
    private SeekBar seekBar;
    private SeekBar seekBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_rx_java);
        tvLog=(TextView)findViewById(R.id.tvLog);
        seekBar=(SeekBar)findViewById(R.id.pbProgress);
        seekBar1=(SeekBar)findViewById(R.id.pbProgress1);
        seekBar.setMax(100);
        seekBar1.setMax(100);

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

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(int i=0;i<10;i++){
                    emitter.onNext(i);
                }
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                this.disposable=d;
            }

            @Override
            public void onNext(Integer integer) {
                tvLog.append("Observable.create.subscribe.onNext:i="+integer+"\n");
                if(integer==5){
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                tvLog.append("Observable.create.subscribe.onComplete:\n");
            }
        });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer interger) throws Exception {
                List<String> list=new ArrayList<>();
                for(int i=0;i<3;i++){
                    list.add("flatMap:i="+i+",int="+interger);
                }
                return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tvLog.append("Observable.create.accept:s="+s+"\n");
            }
        });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer interger) throws Exception {
                List<String> list=new ArrayList<>();
                for(int i=0;i<3;i++){
                    list.add("concatMap:i="+i+",int="+interger);
                }
                return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tvLog.append("Observable.create.accept:s="+s+"\n");
            }
        });

        Flowable.just("Hello Flowable just\n")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tvLog.append("Flowable.just:accept:" + s);
                    }
                });
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello Flowable create-onNext1\n");
                emitter.onNext("Hello Flowable create-onNext2\n");
                emitter.onComplete();
            }
        },BackpressureStrategy.BUFFER).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tvLog.append("Flowable.create:accept:"+s);
            }
        });
        Single.just("Hello Single just\n")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tvLog.append("Single.just:accept:+"+s);
                    }
                });
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                emitter.onSuccess("Hello Single create-onSuccess1\n");
                emitter.onSuccess("Hello Single create-onSuccess2\n");//不执行
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tvLog.append("Single.create:accept:"+s);
            }
        });
        //Completable 无just
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                emitter.onComplete();
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                tvLog.append("Completable.create:onSubscribe\n");
            }

            @Override
            public void onComplete() {
                tvLog.append("Completable.create:onComplete\n");
            }

            @Override
            public void onError(Throwable e) {
                tvLog.append("Completable.create:onError\n");
            }
        });
        Maybe.just("Hello Maybe just\n")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tvLog.append("Maybe.just:accept:"+s);
                    }
                });
        Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {
                emitter.onSuccess("Maybe.create:onSuccess\n");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tvLog.append("Maybe.create:accept:" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                tvLog.append("Maybe.create:Throwable:accept:" + throwable.getMessage());
            }
        });

    }
}
