/**
 *
 */
package com.wizarpos.androidtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.wizarpos.androidtest.R;


/**
 * @author harlen
 * @Description:键盘按键，支持数字输入和金额输入
 * @date 2016年3月7日 下午3:44:18
 */
public class KeyboardView extends LinearLayout implements OnClickListener {

    private Context mContext;
    private String mValue = "";
    private OnKeyBoardInput mOnKeyBoardInput;
    private int maxLength = 100;//最大长度
    private boolean withDot;

    /**
     * @param context
     */
    public KeyboardView(Context context) {
        super(context);
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.KeyboardView);
        withDot = t.getBoolean(R.styleable.KeyboardView_with_dot, true);
        maxLength = t.getInt(R.styleable.KeyboardView_maxLength, maxLength);
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.keyboard_layout, null);
        View dot = root.findViewById(R.id.key_dot);
        View del = root.findViewById(R.id.key_del);
        if (!withDot) {
            dot.setVisibility(View.GONE);
            LayoutParams delParam = (LayoutParams) del.getLayoutParams();
            delParam.weight = 2.0f;
            del.setLayoutParams(delParam);

        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        root.findViewById(R.id.key_0).setOnClickListener(this);
        root.findViewById(R.id.key_1).setOnClickListener(this);
        root.findViewById(R.id.key_2).setOnClickListener(this);
        root.findViewById(R.id.key_3).setOnClickListener(this);
        root.findViewById(R.id.key_4).setOnClickListener(this);
        root.findViewById(R.id.key_5).setOnClickListener(this);
        root.findViewById(R.id.key_6).setOnClickListener(this);
        root.findViewById(R.id.key_7).setOnClickListener(this);
        root.findViewById(R.id.key_8).setOnClickListener(this);
        root.findViewById(R.id.key_9).setOnClickListener(this);
        root.findViewById(R.id.key_dot).setOnClickListener(this);
        root.findViewById(R.id.key_del).setOnClickListener(this);

        addView(root, params);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        String tag = v.getTag().toString();
        if (withDot) {
            //金额处理
            handleMoney(viewId, tag);
        } else {
            handleDigit(viewId, tag);
            //数字处理
        }
        if (mOnKeyBoardInput != null) {
            mOnKeyBoardInput.onKeyClick(mValue);
        }
    }

    private void handleMoney(int viewId, String tag) {

        if (viewId == R.id.key_del) {
            //删除按钮
            if (mValue.length() > 0) {
                mValue = mValue.substring(0, mValue.length() - 1);
            }
        } else {
            if (mValue.length() >= maxLength) {
                //如果大于最大长度，就返回不处理
                return;
            }
            if (viewId == R.id.key_dot) {
                //小数点的处理
                if (mValue.contains(".")) {
                    //如果已经有小数点，就不做任何处理
                } else {
                    mValue += tag;
                }
            } else {
                int index = mValue.lastIndexOf(".");
                if (index != -1) {
                    //有小数点
                    if (mValue.length() - index == 3) {
                        //小数点后保留两位
                        return;
                    } else {
                        mValue += tag;
                    }
                } else {
                    //无小数点
                    if (mValue.startsWith("0")) {//0
                        return;
                    } else {
                        mValue += tag;
                    }
                }

            }
        }
    }

    private void handleDigit(int viewId, String tag) {
        if (viewId == R.id.key_del) {
            //删除按钮
            if (mValue.length() > 0) {
                mValue = mValue.substring(0, mValue.length() - 1);
            }
        } else {
            if (mValue.length() >= maxLength) {
                //如果大于最大长度，就返回不处理
                return;
            }
            mValue += tag;
        }
    }

    public String getValue() {
        return mValue;
    }

    public String cleverValue() {
        return mValue = "";
    }

    public OnKeyBoardInput getOnKeyBoardInput() {
        return mOnKeyBoardInput;
    }

    public void setOnKeyBoardInput(OnKeyBoardInput mOnKeyBoardInput) {
        this.mOnKeyBoardInput = mOnKeyBoardInput;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public interface OnKeyBoardInput {
        void onKeyClick(String value);
    }

}
