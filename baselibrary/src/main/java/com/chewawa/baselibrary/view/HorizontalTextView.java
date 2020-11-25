package com.chewawa.baselibrary.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;

import com.chewawa.baselibrary.R;
import com.chewawa.baselibrary.R2;
import com.chewawa.baselibrary.base.BaseRecycleViewAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 水平TextView,左边标题,右边内容
 * nanfeifei
 * 2017/6/28 10:09
 *
 * @version 3.7.0
 */
public class HorizontalTextView extends LinearLayout {
    public final static int DATA = 011;
    public final static int DATA_TIME = 012;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    EditText tvContent;
    @BindView(R2.id.rl_lay)
    RelativeLayout rlLay;
    @BindView(R2.id.tv_operate)
    TextView tvOperate;
    private Context context;
    private int year, day, month, hour, minu;
    String birthdayStr;
    int type;
    float paddingTop, paddingBottom, paddingLeft, paddingRight;
    float buttonPaddingTop, buttonPaddingBottom, buttonPaddingLeft, buttonPaddingRight;
    public HorizontalTextView(Context context) {
        super(context);
        init(context);
        initStyle(context, null, 0);
    }

    public HorizontalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initStyle(context, attrs, 0);
    }

    public HorizontalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initStyle(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalTextView(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initStyle(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_horizontal_title_and_content, this);
        ButterKnife.bind(this);
        this.context = context;
        initTimePicker();
    }

    private void initStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.HorizontalTextView, defStyleAttr, 0);

        if (a != null) {
            if (a.hasValue(R.styleable.HorizontalTextView_android_drawableRight)) {
                setDrawableRight(a.getDrawable(R.styleable.HorizontalTextView_android_drawableRight));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_android_text)) {
                setText(a.getString(R.styleable.HorizontalTextView_android_text));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_android_focusable)) {
                setFocusable(a.getBoolean(R.styleable.HorizontalTextView_android_focusable, false));
            } else {
                setFocusable(false);
            }
            if (a.hasValue(R.styleable.HorizontalTextView_android_textColor)) {
                setTextColor(a.getColor(R.styleable.HorizontalTextView_android_textColor, 0));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_android_inputType)) {
                setInputType(a.getInt(R.styleable.HorizontalTextView_android_inputType, -1));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_android_hint)) {
                setTextHint(a.getString(R.styleable.HorizontalTextView_android_hint));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_android_gravity)) {
                setTextGravity(a.getInt(R.styleable.HorizontalTextView_android_gravity, -1));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_android_textSize)) {
                setTextSize(a.getDimensionPixelSize(R.styleable.HorizontalTextView_android_textSize, 14));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_titleText)) {
                setTitleText(a.getString(R.styleable.HorizontalTextView_titleText));
            }

            if (a.hasValue(R.styleable.HorizontalTextView_titleMinEms)) {
                setTitleMinEms(a.getInt(R.styleable.HorizontalTextView_titleMinEms, 0));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_titleTextCustomColor)) {
                setTitleTextColor(a.getColor(R.styleable.HorizontalTextView_titleTextCustomColor, 0));
            }

            if (a.hasValue(R.styleable.HorizontalTextView_titleTextSize)) {
                setTitleTextSize(a.getDimensionPixelSize(R.styleable.HorizontalTextView_titleTextSize, 14));
            }
            if (a.hasValue(R.styleable.HorizontalTextView_titleDrawableLeft)) {
                setTitleDrawableLeft(a.getDrawable(R.styleable.HorizontalTextView_titleDrawableLeft));
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonBackground)){
                setButtonBackgroundDrawable(a.getDrawable(R.styleable.HorizontalTextView_buttonBackground));
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonVisible)){
                setButtonVisible(a.getInt(R.styleable.HorizontalTextView_buttonVisible, GONE));
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonTextColor)){
                setButtonTextColor(a.getColor(R.styleable.HorizontalTextView_buttonTextColor, 0));
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonTextSize)){
                setButtonTextSize(a.getDimensionPixelSize(R.styleable.HorizontalTextView_buttonTextSize, 10));
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonText)){
                setButtonText(a.getString(R.styleable.HorizontalTextView_buttonText));
            }
            if(a.hasValue(R.styleable.HorizontalTextView_contentPaddingTop)){
                paddingTop = a.getDimension(R.styleable.HorizontalTextView_contentPaddingTop, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_contentPaddingBottom)){
                paddingBottom = a.getDimension(R.styleable.HorizontalTextView_contentPaddingBottom, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_contentPaddingLeft)){
                paddingLeft = a.getDimension(R.styleable.HorizontalTextView_contentPaddingLeft, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_contentPaddingRight)){
                paddingRight = a.getDimension(R.styleable.HorizontalTextView_contentPaddingRight, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_contentPadding)){
                paddingTop = a.getDimension(R.styleable.HorizontalTextView_contentPadding, 0);
                paddingBottom = a.getDimension(R.styleable.HorizontalTextView_contentPadding, 0);
                paddingLeft = a.getDimension(R.styleable.HorizontalTextView_contentPadding, 0);
                paddingRight = a.getDimension(R.styleable.HorizontalTextView_contentPadding, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonPaddingTop)){
                buttonPaddingTop = a.getDimension(R.styleable.HorizontalTextView_buttonPaddingTop, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonPaddingBottom)){
                buttonPaddingBottom = a.getDimension(R.styleable.HorizontalTextView_buttonPaddingBottom, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonPaddingLeft)){
                buttonPaddingLeft = a.getDimension(R.styleable.HorizontalTextView_buttonPaddingLeft, 0);
            }
            if(a.hasValue(R.styleable.HorizontalTextView_buttonPaddingRight)){
                buttonPaddingRight = a.getDimension(R.styleable.HorizontalTextView_buttonPaddingRight, 0);
            }
            setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            setButtonPadding(buttonPaddingLeft, buttonPaddingTop, buttonPaddingRight, buttonPaddingBottom);
            a.recycle();
        }
    }


    /**
     * 初始化时间选择控件
     * <p>
     * void
     */
    private void initTimePicker() {
        // 初始化Calendar日历对象
        Calendar myCalendar = Calendar.getInstance(Locale.CHINA);
        Date myDate = new Date(); // 获取当前日期Date对象
        myCalendar.setTime(myDate);// //为Calendar对象设置时间为当前日期

        year = myCalendar.get(Calendar.YEAR); // 获取Calendar对象中的年
        month = myCalendar.get(Calendar.MONTH);// 获取Calendar对象中的月
        day = myCalendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天
        hour = myCalendar.get(Calendar.HOUR);
        minu = myCalendar.get(Calendar.MINUTE);
    }

    public void setTextType(int type) {
        this.type = type;

        if (DATA == type || DATA_TIME == type) {
            birthdayStr = year + "-" + (month + 1) + "-" + day;
            if (DATA_TIME == type) {
                tvContent.setText(birthdayStr + " " + hour + ":" + minu);
            } else {
                tvContent.setText(birthdayStr);
            }
            tvContent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dpd = new DatePickerDialog(getContext(), dateListener, year, month, day);
                    dpd.show();// 显示DatePickerDialog组件

                }
            });
        } else {
        }
    }


//    public void setOnClickListener(OnClickListener listener) {
//        this.setOnClickListener(listener);
//    }
    public void setOnTextClickListener(OnTextClickListener listener) {
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTextClick(HorizontalTextView.this);
            }
        });
    }
    public void setOnButtonClickListener(OnButtonClickListener listener){
        tvOperate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick(HorizontalTextView.this);
            }
        });
    }

    public interface OnTextClickListener {
        void onTextClick(View view);
    }
    public interface OnButtonClickListener{
        void onButtonClick(View view);
    }

    public void setDrawableRight(Drawable drawable) {
        tvContent.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public void setTextHint(String text) {
        tvContent.setHint(text);
    }

    public void setInputType(int type) {
        tvContent.setInputType(type);
    }

    public String getText() {
        String contextStr = tvContent.getText().toString().trim();
        return contextStr;
    }
    public EditText getEditText(){
        return tvContent;
    }

    public String getHint() {
        String hintStr = tvContent.getHint().toString().trim();
        return hintStr;
    }

    public void setFocusable(boolean aBoolean) {
        tvContent.setFocusable(aBoolean);
        tvContent.setLongClickable(aBoolean);
        tvContent.setTextIsSelectable(false);
        if(!aBoolean){
            tvContent.setInputType(InputType.TYPE_NULL);
            tvContent.setMovementMethod(null);
            tvContent.setKeyListener(null);
        }
    }
    public void setEnabled(boolean aBoolean) {
        tvContent.setEnabled(aBoolean);
    }

    public void setTitleTextSize(float aFloat) {
        tvTitle.setTextSize(COMPLEX_UNIT_PX, aFloat);
    }

    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setTitleMinEms(int anInt) {
        tvTitle.setMinEms(anInt);
    }

    public void setTitleText(CharSequence text) {
        tvTitle.setText(text);
    }

    public String getTitleText() {
        String titleStr = tvTitle.getText().toString().trim();
        return titleStr;
    }

    public void setTitleDrawableLeft(Drawable drawable) {
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setTextGravity(int gravity) {
        tvContent.setGravity(gravity);
    }

    public void setTextSize(float aFloat) {
        tvContent.setTextSize(COMPLEX_UNIT_PX, aFloat);
    }

    public void setTextColor(int color) {
        tvContent.setTextColor(color);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(@DrawableRes int left,
                                                        @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        tvContent.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
    public void setPadding(float left, float top, float right, float bottom){
//        tvContent.setPadding(px2dip(left), px2dip(top), px2dip(right), px2dip(bottom));
        tvContent.setPadding((int)left, (int)top, (int)right, (int)bottom);
    }
    public void setButtonPadding(float left, float top, float right, float bottom){
//        tvContent.setPadding(px2dip(left), px2dip(top), px2dip(right), px2dip(bottom));
        tvOperate.setPadding((int)left, (int)top, (int)right, (int)bottom);
    }
    public void setText(CharSequence text) {
        tvContent.setText(text);
    }
    public void append(CharSequence text){
        tvContent.append(text);
    }
    public void setButtonBackgroundResource(@DrawableRes int resId){
        tvOperate.setBackgroundResource(resId);
    }
    public void setButtonBackgroundDrawable(Drawable background){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tvOperate.setBackground(background);
        }else {
            tvOperate.setBackgroundDrawable(background);
        }
    }
    public void setButtonVisible(int visibility){
        tvOperate.setVisibility(visibility);
    }
    public void setButtonTextColor(@ColorInt int color){
        tvOperate.setTextColor(color);
    }
    public void setButtonTextSize(float size){
        tvOperate.setTextSize(COMPLEX_UNIT_PX, size);
    }
    public void setButtonText(String text){
        tvOperate.setText(text);
    }
    public void setButtonText(@StringRes int res){
        tvOperate.setText(res);
    }
    public TextView getButton(){
        return tvOperate;
    }

    private DatePickerDialog.OnDateSetListener dateListener =
            new DatePickerDialog.OnDateSetListener() {
                /**
                 * params：view：该事件关联的组件 params：myYear：当前选择的年 params：monthOfYear：当前选择的月
                 * params：dayOfMonth：当前选择的日
                 */
                @Override
                public void onDateSet(DatePicker view, int myYear, int monthOfYear,
                                      int dayOfMonth) {

                    // 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
                    year = myYear;
                    month = monthOfYear;
                    day = dayOfMonth;
                    // 更新日期
                    updateDate();
                }

                /**
                 * 当DatePickerDialog关闭时，更新日期显示
                 */
                private void updateDate() {
                    // 在TextView上显示日期
                    birthdayStr = year + "-" + (month + 1) + "-" + day;
                    tvContent.setText(birthdayStr);
                    if (type == DATA_TIME) {
                        TimePickerDialog tpd = new TimePickerDialog(getContext(), timeListener, hour, minu, true);
                        tpd.show();
                    }
                }
            };
    private TimePickerDialog.OnTimeSetListener timeListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour = hourOfDay;
                    minu = minute;
                    tvContent.setText(birthdayStr + " " + hour + ":" + minu);
                }
            };
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        if(context == null){
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        int dpValue = (int) (pxValue / scale + 0.5f);
        return dpValue;
    }
}
