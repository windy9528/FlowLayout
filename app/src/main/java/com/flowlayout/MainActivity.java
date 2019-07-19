package com.flowlayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flowlayout.widget.FlowLayout;
import com.flowlayout.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        /**
         * 组合式控件 接口回调
         */
        searchView.setGroupClickListener(new SearchView.GroupClickListener() {
            @Override
            public void clickToBack() {
                finish();//关闭
            }

            @Override
            public void clickToSearch(String content) {
                //点击搜索功能 添加子条目
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(MainActivity.this, "输入不能为空!", Toast.LENGTH_SHORT).show();
                } else {
                    flowLayout.addTextView(content);
                }
            }
        });

        /**
         * 流式布局 接口回调
         */
        flowLayout.setFlowLayoutImpl(new FlowLayout.FlowLayoutImpl() {
            @Override
            public void clickListener(TextView textView) {
                String trim = textView.getText().toString().trim();
                //回显给输入框
                searchView.setTextView(trim);
                //打印点击的条目
                Toast.makeText(MainActivity.this, trim, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void longClickListener(final TextView textView) {
                new AlertDialog
                        .Builder(MainActivity.this)
                        .setTitle("警告")
                        .setMessage("删除" + textView.getText().toString().trim() + "这个条目")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            private ProgressDialog progressDialog;

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //点击确定按钮，调用删除的方法
                                progressDialog = new ProgressDialog(MainActivity.this);
                                new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.show();
                                            }
                                        });
                                        SystemClock.sleep(1000);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                flowLayout.delTextView(textView);
                                                Toast.makeText(MainActivity.this, "删除成功哟，亲", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }.start();
                            }
                        })
                        .setNegativeButton("取消", null).show();

            }
        });

    }

    /**
     * 初始化控件
     */
    private void initView() {
        searchView = findViewById(R.id.searchView);

        flowLayout = findViewById(R.id.flowLayout);
    }
}
