package com.flowlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flowlayout.R;

/**
 * date:2019/7/19
 * name:windy
 * function:
 */
public class SearchView extends LinearLayout implements View.OnClickListener {

    private TextView tvSearch;
    private ImageView imgBack;
    private EditText edContent;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);//加载布局

        initAttrs(context, attrs);
    }

    /**
     * 初始化布局
     *
     * @param context
     */
    private void initView(Context context) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.search_layout, this, true);

        imgBack = view.findViewById(R.id.img_back);
        edContent = view.findViewById(R.id.ed_content);
        tvSearch = view.findViewById(R.id.tv_search);

        tvSearch.setOnClickListener(this);
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {

        // 获取属性集合 TypedArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView);

        int color = typedArray.getColor(R.styleable.SearchView_contentColor, Color.BLUE);
        float size = typedArray.getDimension(R.styleable.SearchView_contentSize, 18);

        edContent.setTextColor(color);
        edContent.setTextSize(size);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (groupClickListener != null) {
                    groupClickListener.clickToBack();
                }
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }

    /**
     * 点击搜索功能
     */
    private void search() {
        if (groupClickListener != null) {
            String trim = edContent.getText().toString().trim();
            groupClickListener.clickToSearch(trim);
        }
    }

    /**
     * 接口回调
     */
    public GroupClickListener groupClickListener;

    public void setGroupClickListener(GroupClickListener groupClickListener) {
        this.groupClickListener = groupClickListener;
    }

    /**
     * 回显 设置内容
     *
     * @param trim
     */
    public void setTextView(String trim) {
        edContent.setText(trim);
    }

    public interface GroupClickListener {

        void clickToBack();

        void clickToSearch(String content);
    }
}
