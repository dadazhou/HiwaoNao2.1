package com.dadazhou.hiwaonao21.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dadazhou.hiwaonao21.R;

/**
 * Created by hw_zld on 2016/2/23.
 */
public class dialog_connect extends Dialog {
    EditText mIp,mPort;
    Button connect,quit;
    String connect_infor;

    public dialog_connect(Context context) {
        super(context);

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_connect);
        mIp=(EditText)findViewById(R.id.ip_edit);
        mPort=(EditText)findViewById(R.id.port_edit);
        connect=(Button)findViewById(R.id.connect_btn);
        quit=(Button)findViewById(R.id.quit_btn);
        connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                connect_infor = mIp.getText().toString()+":"+mPort.getText().toString();
                Intent ip=new Intent();
                ip.setAction("GETNAME");
                ip.putExtra("IP", connect_infor);
                dialog_connect.this.getContext().sendBroadcast(ip);
                dismiss();
            }
        });
    }
}
