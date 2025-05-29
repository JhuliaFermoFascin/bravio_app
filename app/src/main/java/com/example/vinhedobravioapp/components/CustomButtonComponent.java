package com.example.vinhedobravioapp.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.enums.ButtonStyle;

public class CustomButtonComponent extends LinearLayout {

    private FrameLayout container;
    private TextView textView;
    private ImageView iconView;
    private ProgressBar progressView;

    public CustomButtonComponent(Context context) {
        this(context, null);
    }

    public CustomButtonComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomButtonComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_button, this, true);

        container = findViewById(R.id.container_btn);
        textView = findViewById(R.id.btn_text);
        iconView = findViewById(R.id.btn_icon);
        progressView = findViewById(R.id.btn_progress);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomButtonAttrs);

            int indexStyle = a.getInt(R.styleable.CustomButtonAttrs_btn_style, 0);
            applyBtnStyle(ButtonStyle.values()[indexStyle]);

            String text = a.getString(R.styleable.CustomButtonAttrs_btn_text);
            if (text != null) {
                setBtnText(text);
            }

            int iconResId = a.getResourceId(R.styleable.CustomButtonAttrs_btn_icon, 0);
            if (iconResId != 0) {
                setBtnIcon(ContextCompat.getDrawable(context, iconResId));
            }

            a.recycle();
        }
    }


    public void setBtnText(String text){
        textView.setText(text);
    }

    public void setBtnIcon(Drawable drawable){
        iconView.setImageDrawable(drawable);
        iconView.setVisibility(VISIBLE);
    }

    public void showBtnLoading(boolean loading){
        progressView.setVisibility(loading ? VISIBLE: GONE);
        textView.setVisibility(loading ? GONE : VISIBLE);

        if(iconView.getDrawable() != null){
            iconView.setVisibility(loading ? GONE : VISIBLE);
        }

        container.setEnabled(!loading);
    }

    public void setOnClickListener(OnClickListener listener) {
        container.setOnClickListener(listener);
    }

    public void applyBtnStyle(ButtonStyle style){
        Context context = getContext();

        int textColor = ContextCompat.getColor(context, R.color.background_text_primary);
        Drawable backgroundColor = ContextCompat.getDrawable(context, R.drawable.button_purple);

        switch (style){
            case PRIMARY:
            case SECONDARY:
                textColor = ContextCompat.getColor(context, R.color.background_text_primary);
                backgroundColor = ContextCompat.getDrawable(context, R.drawable.button_purple);
                break;

            case EXIT:
            case SAVE:
                textColor = ContextCompat.getColor(context, R.color.background_text_primary);
                backgroundColor = ContextCompat.getDrawable(context, R.drawable.button_dark_green);
                break;

            case MENU_PRIMARY:
                textColor = ContextCompat.getColor(context, R.color.button_text_green_primary);
                backgroundColor = ContextCompat.getDrawable(context, R.drawable.border_purple_rounded);
                break;

            case MENU_SECONDARY:
                textColor = ContextCompat.getColor(context, R.color.background_text_primary);
                backgroundColor = ContextCompat.getDrawable(context, R.drawable.button_green);
                break;
        }

        container.setBackground(backgroundColor);
        textView.setTextColor(textColor);
    }
}
