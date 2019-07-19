package com.flowlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flowlayout.R;

/**
 * date:2019/7/19
 * name:windy
 * function:
 */
public class FlowLayout extends FrameLayout {

    //    private int bgColor;
//    private int textColor;
    private int space;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
    }

    /**
     * 自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);

//        bgColor = typedArray.getColor(R.styleable.FlowLayout_bgColor, Color.BLACK);
//        textColor = typedArray.getColor(R.styleable.FlowLayout_text_Color, Color.BLACK);
        space = (int) typedArray.getDimension(R.styleable.FlowLayout_space, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //获取控件宽度
        int width = this.getWidth();
        //记录每一行的高度
        int height = 0;
        //记录行数
        int rows = 0;
        //定义当前宽度
        int hWidth = 0;
        //循环遍历 控件
        for (int i = 0; i < getChildCount(); i++) {

            final View view = getChildAt(i);

            //单击事件监听
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flowLayoutImpl != null) {
                        flowLayoutImpl.clickListener((TextView) view);
                    }
                }
            });

            //长按点击监听
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (flowLayoutImpl != null) {
                        flowLayoutImpl.longClickListener((TextView) view);
                    }
                    return true;
                }
            });

            //获取子控件的宽度
            int viewWidth = view.getWidth();
            //获取子控件的高度
            int viewHeight = view.getHeight();
            //此行的宽度
            hWidth = hWidth + viewWidth + space;
            //如果此行的宽度 大于屏幕宽度 则换行
            if (hWidth > width) {
                rows++;
                hWidth = viewWidth + space;  //更新当前行的宽度
            }
            view.layout(hWidth - viewWidth, rows * viewHeight + (rows + 1) * space,
                    hWidth, (rows + 1) * viewHeight + (rows + 1) * space);
        }
    }

    /**
     * 添加子控件
     *
     * @param trim
     */
    public void addTextView(String trim) {
        TextView textView = (TextView) View.inflate(getContext(), R.layout.flow_item, null);
        textView.setText(trim);
        //布局宽高自适应
        LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.HORIZONTAL);
        textView.setLayoutParams(params);

        addView(textView);
    }

    /**
     * 接口回调
     */
    public FlowLayoutImpl flowLayoutImpl;

    public void setFlowLayoutImpl(FlowLayoutImpl flowLayoutImpl) {
        this.flowLayoutImpl = flowLayoutImpl;
    }

    /**
     * 删除子条目
     *
     * @param textView
     */
    public void delTextView(TextView textView) {

        removeView(textView);
    }

    public interface FlowLayoutImpl {

        void clickListener(TextView textView);

        void longClickListener(TextView textView);
    }
}
