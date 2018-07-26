package com.lxzh123.findalgo;

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

public class FindActivity extends Activity {

    private Button btnGen;
    private Button btnFind;
    private EditText etArrCnt;
    private EditText etFindVal;
    private TextView tvOrigArray;
    private TextView tvRstFind;
    private int[] array;
    private int arrcnt=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_findalgo);

        btnGen=(Button)findViewById(R.id.btnGenRandData);
        btnFind=(Button)findViewById(R.id.btnFind);
        etArrCnt=(EditText)findViewById(R.id.etArrCnt);
        etFindVal=(EditText)findViewById(R.id.etFindVal);
        tvOrigArray=(TextView)findViewById(R.id.tvOrigArray);
        tvRstFind=(TextView)findViewById(R.id.tvRstFind);

        btnGen.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str=etArrCnt.getText().toString();
                arrcnt= OptionConverter.toInt(str,20);
                array=genRandData(arrcnt);
                tvOrigArray.setText(Arrays.toString(array));
            }
        });
        btnFind.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                int[] tmpArray=Arrays.copyOf(array,arrcnt);
                StringBuffer buf=new StringBuffer();
                long tStart,tEnd;
                int[] tmp;
                int val,idx=0;
                String str=etFindVal.getText().toString();
                val= OptionConverter.toInt(str,0);

                tmp=tmpArray.clone();
                tStart=SystemClock.elapsedRealtime();
                for(int i=0;i<100;i++) idx=Finder.SequentialFind(tmp,val);
                tEnd=SystemClock.elapsedRealtime();
                buf.append("顺序查找用时:"+(tEnd-tStart)+",结果:"+idx+"\n");

                tvRstFind.setText(buf.toString());
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
