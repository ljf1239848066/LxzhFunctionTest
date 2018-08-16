package com.lxzh123.funcdemo.rxjavatest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
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
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class TestRxJavaActivity extends Activity {

    private final static String TAG="TestRxJavaActivity";
    private TextView tvLog;
    private SeekBar seekBar;
    private SeekBar seekBar1;

    private Disposable mIntervalDisposable;
    private static int index=0;

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
        //range 发送从start开始count个事件
        Observable.range(1,3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.range.accept:i="+integer+"\n");
                    }
                });
        //map Observable类型映射
        Observable.just(1,2,3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "map:"+integer;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        tvLog.append("Observable.map.accept:s="+s+"\n");
                    }
                });
        //zip 两个Observable指定索引事件合并为一个事件(二取小)
        Observable.zip(Observable.just("A", "B", "C"), Observable.just(1, 2, 3, 4, 5), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s+integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                tvLog.append("Observable.zip.accept:s="+s+"\n");
            }
        });
        //repeat 重复指定次数
        Observable.just(1,2)
                .repeat(2)
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                tvLog.append("Observable.repeat.accept:i="+integer+"\n");
            }
        });

        //concat 合并两个Observable, B Observable追加到A Observable后面
        Observable.concat(Observable.just(1,2,3), Observable.just(4,5))
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                tvLog.append("Observable.concat.accept:i="+integer+"\n");
            }
        });

        //flatMap
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
        //concatMap
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
        //distinct 去重
        Observable.just(1,1,3,2,3)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.distinct.accept:i="+integer+"\n");
                    }
                });
        //filter 基于条件过滤
        Observable.just(1,1,3,2,3)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer<2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.filter.accept:i="+integer+"\n");
                    }
                });
        //buffer
        Observable.just(1,2,3,4,5)
                .buffer(2,2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> list) throws Exception {
                        tvLog.append("Observable.buffer.accept:i="+list.toString()+"\n");
                    }
                });
        //timer 延时发送Observable
        Observable.timer(2,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvLog.append("Observable.timer.accept:aLong="+aLong+"\n");
                    }
                });
        //interval 定时发送Observable, 主线程退出时需要销毁
        mIntervalDisposable=Observable.interval(2,3,TimeUnit.SECONDS)
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong<10;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvLog.append("Observable.interval.accept:aLong="+aLong+"\n");
                    }
                });
        //skip 跳过几个事件
        Observable.just(1,2,3,4)
                .skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.skip.accept:i="+integer+"\n");
                    }
                });
        //take 执行指定个数的事件
        Observable.just(1,2,3,4)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.take.accept:i="+integer+"\n");
                    }
                });
        //debounce 去除发送频率过快的项
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // send events with simulated time wait
                emitter.onNext(1); // skip
                Thread.sleep(400);
                emitter.onNext(2); // deliver
                Thread.sleep(505);
                emitter.onNext(3); // skip
                Thread.sleep(100);
                emitter.onNext(4); // deliver
                Thread.sleep(605);
                emitter.onNext(5); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500,TimeUnit.SECONDS)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                tvLog.append("Observable.debounce.accept:i="+integer+"\n");
            }
        });
        //defer 基于已有的Observable产生新的Observable 如果没有被订阅，就不会产生新的 Observable。
        Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1,2,3);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                tvLog.append("Observable.defer.accept:i="+integer+"\n");
            }
        });
        //last 取出可观察到的最后一个值 或者如果没有可观察结果时返回last指定的默认值
        Observable.just(1,2,3,4)
                .last(5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.last.accept:i="+integer+"\n");
                    }
                });
        Observable.just(1,2,3)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer>3;
                    }
                })
                .last(5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.last(filter result=none).accept:i="+integer+"\n");
                    }
                });
        //merge 合并多个Observable 不保证顺序
        Observable.merge(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Thread.sleep(300);
                emitter.onNext(2);
                Thread.sleep(300);
                emitter.onNext(3);
                Thread.sleep(300);
            }
        }),Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(4);
                emitter.onNext(5);
                Thread.sleep(400);
                emitter.onNext(6);
                Thread.sleep(400);
            }
        })).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                tvLog.append("Observable.merge(with delay).accept:i="+integer+"\n");
            }
        });
        Observable.merge(Observable.just(1,2),Observable.just(3,4),Observable.just(5,6))
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                tvLog.append("Observable.merge.accept:i="+integer+"\n");
            }
        });
        //reduce 每次用一个方法处理一个值 从前两个开始
        // i1=1 i2=2 i3=5 则:
        // a) apply i12=i1+i2=3     accept i12=3
        // b) apply i23=i12+i3=8    accept i23=8
        Observable.just(1,2,5)
        .reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer i1, Integer i2) throws Exception {
                tvLog.append("Observable.reduce.apply:i1="+i1+",i2="+i2+"\n");
                return i1+i2;
            }
        })
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                tvLog.append("Observable.reduce.accept:i="+integer+"\n");
            }
        });
        //scan 每次用一个方法处理一个值 从第一个开始
        // i1=1 i2=2 i3=5 则:
        // a)                       accept i1
        // b) apply i12=i1+i2       accept i12=3
        // c) apply i23=i12+i3=3+5  accept i23=8
        Observable.just(1,2,5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer i1, Integer i2) throws Exception {
                        tvLog.append("Observable.scan.apply:i1="+i1+",i2="+i2+"\n");
                        return i1+i2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        tvLog.append("Observable.scan.accept:i="+integer+"\n");
                    }
                });
        //window 将一个Observable按照指定窗口划分为一个个小的Observable
        index=0;
        Observable.just(1,2,3,4,5,6,7)
                .window(3)
                .subscribe(new Consumer<Observable<Integer>>() {
                    @Override
                    public void accept(Observable<Integer> integerObservable) throws Exception {
                        index++;
                        tvLog.append("Observable.window.accept:iDx="+ index+"\n");
                        integerObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                tvLog.append("Observable.window.accept:iDx="+ index+",i="+integer+"\n");
                            }
                        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
