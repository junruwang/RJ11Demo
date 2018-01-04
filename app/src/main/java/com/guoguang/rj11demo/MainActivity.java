package com.guoguang.rj11demo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button openDev,send;
    private TextView result;
    private SerialHelper comprint;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String data=(String)msg.obj;
            result.setText(data);
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDev = (Button) findViewById(R.id.openDev);
        send=(Button)findViewById(R.id.send);
        result=(TextView)findViewById(R.id.receive);

        comprint=new SerialHelper("/dev/ttyS4",115200);

        openDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    comprint.openPort();

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String str="abcd123456789";
                //comprint.send(str.getBytes());
                //result.setText(comprint.receive());
                ReadThread readThread=new ReadThread();
                readThread.start();
            }
        });
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()) {
                try
                {
                    String str="abcd12345678910";
                    comprint.send(str.getBytes());
                    try
                    {
                        Thread.sleep(50);//延时50ms
                        Message message=new Message();
                        String data=comprint.receive();
                        message.obj=data;
                        handler.sendMessage(message);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                } catch (Throwable e)
                {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


}
