package com.lxzh123.sortalgo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

import org.apache.log4j.helpers.OptionConverter;

import java.util.Arrays;
import java.util.Random;

public class SortActivity extends Activity {

    private Button btnGen;
    private Button btnSort;
    private EditText etArrCnt;
    private TextView tvOrigArray;
    private TextView tvRstSort;
    private int[] array;
    private int arrcnt=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sortalgo);
        btnGen=(Button)findViewById(R.id.btnGenRandData);
        btnSort=(Button)findViewById(R.id.btnSort);
        etArrCnt=(EditText)findViewById(R.id.etArrCnt);
        tvOrigArray=(TextView)findViewById(R.id.tvOrigArray);
        tvRstSort=(TextView)findViewById(R.id.tvRst_Sort);


        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=etArrCnt.getText().toString();
                arrcnt=OptionConverter.toInt(str,20);
                array=genRandData(arrcnt);
                tvOrigArray.setText(Arrays.toString(array));
            }
        });
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] tmpArray=Arrays.copyOf(array,arrcnt);
                StringBuffer buf=new StringBuffer();
                long tStart,tEnd;
                int[] tmp;

                tmp=tmpArray.clone();
                tStart=SystemClock.elapsedRealtime();
                Sorter.BubbleSort(tmp,true);
                tEnd=SystemClock.elapsedRealtime();
                buf.append("冒泡排序用时:"+(tEnd-tStart)+",结果:\n"+Arrays.toString(tmp));

                tmp=tmpArray.clone();
                tStart=SystemClock.elapsedRealtime();
                Sorter.BubbleSort(tmp,false);
                tEnd=SystemClock.elapsedRealtime();
                buf.append("\n冒泡排序用时:"+(tEnd-tStart)+",结果:\n"+Arrays.toString(tmp));

                tmp=tmpArray.clone();
                tStart=SystemClock.elapsedRealtime();
                Sorter.InsertSort(tmp,true);
                tEnd=SystemClock.elapsedRealtime();
                buf.append("\n插入排序用时:"+(tEnd-tStart)+",结果:\n"+Arrays.toString(tmp));

                tmp=tmpArray.clone();
                tStart=SystemClock.elapsedRealtime();
                Sorter.InsertSort(tmp,false);
                tEnd=SystemClock.elapsedRealtime();
                buf.append("\n插入排序用时:"+(tEnd-tStart)+",结果:\n"+Arrays.toString(tmp));

                tmp=tmpArray.clone();
                tStart=SystemClock.elapsedRealtime();
                Sorter.SelectSort(tmp,true);
                tEnd=SystemClock.elapsedRealtime();
                buf.append("\n选择排序用时:"+(tEnd-tStart)+",结果:\n"+Arrays.toString(tmp));

                tmp=tmpArray.clone();
                tStart=SystemClock.elapsedRealtime();
                Sorter.SelectSort(tmp,false);
                tEnd=SystemClock.elapsedRealtime();
                buf.append("\n选择排序用时:"+(tEnd-tStart)+",结果:\n"+Arrays.toString(tmp));

                tvRstSort.setText(buf.toString());
            }
        });
    }

    private int[] genRandData(int len){
        int[] arr=new int[len];
        Random rand=new Random();
        int max=len*5;
        for(int i=0;i<len;i++){
            arr[i]=rand.nextInt(max);
        }
        return arr;
    }
}
