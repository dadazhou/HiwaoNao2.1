package com.dadazhou.hiwaonao21;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dadazhou.hiwaonao21.view.BinarySlidingMenu;
import com.dadazhou.hiwaonao21.view.Fragment1;
import com.dadazhou.hiwaonao21.view.Fragment2;
import com.dadazhou.hiwaonao21.view.dialog_connect;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private BinarySlidingMenu mMenu;
    private LayoutInflater mInflater;
    private View mLeft_menu;
    private View mMain;
    private View mRight_menu;
    private Button mRight_btn;
    private Button mLeft_btn;
    private Button mConn_btn;
    private FragmentTabHost mTabHost;
    String tabs[] = { "Tab1", "Tab2" };
    Class cls[] = { Fragment1.class, Fragment2.class };
    private RadioGroup m_radioGroup;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private String mIP;

    private BroadcastReceiver b=new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("GETNAME")){
                mIP=intent.getStringExtra("IP");
                mConn_btn.setText(mIP);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mInflater = LayoutInflater.from(this);
        mLeft_menu = mInflater.inflate(R.layout.layout_menu, null);
        mMain = mInflater.inflate(R.layout.main, null);
        mRight_menu = mInflater.inflate(R.layout.layout_menu2, null);

        mMenu = (BinarySlidingMenu) findViewById(R.id.mRoot);
        mRight_btn = (Button) mMain.findViewById(R.id.right_btn);
        mLeft_btn = (Button) mMain.findViewById(R.id.left_btn);
        mConn_btn = (Button) mMain.findViewById(R.id.connect_btn);
        mMenu.addView(mLeft_menu);
        mMenu.addView(mMain);
        mMenu.addView(mRight_menu);

        IntentFilter itf=new IntentFilter();
        itf.addAction("GETNAME");
        this.registerReceiver(b, itf);
        dialog_connect ld=new dialog_connect(this);
        ld.show();

        mRight_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mMenu.scrollRightMenu();
            }
        });

        mLeft_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mMenu.scrollLeftMenu();
            }
        });

        mConn_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog_connect ld=new dialog_connect(MainActivity.this);
                ld.show();
            }
        });

        init();

    }

    private void init() {
        //底部菜单栏
        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setVisibility(View.GONE);
        for (int i = 0; i < tabs.length; i++) {
            // View tabView =
            // this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
            mTabHost.addTab(mTabHost.newTabSpec(tabs[i]).setIndicator(tabs[i]),
                    cls[i], null);
        }
        m_radioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        m_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RadioButton0:

                        mTabHost.setCurrentTabByTag(tabs[0]);
                        break;
                    case R.id.RadioButton1:
                        mTabHost.setCurrentTabByTag(tabs[1]);
                        break;
                }
            }
        });

        //toggle()方法进行显示隐藏交互事件，如果显示的就进行隐藏，如果是隐藏的就显示
        ((RadioButton) m_radioGroup.getChildAt(0)).toggle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 页面适配器
     */
    public class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
