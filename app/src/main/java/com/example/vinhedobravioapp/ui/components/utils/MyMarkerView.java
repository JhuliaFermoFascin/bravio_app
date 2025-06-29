package com.example.vinhedobravioapp.ui.components.utils;

import android.content.Context;
import android.widget.TextView;

import com.example.vinhedobravioapp.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class MyMarkerView extends MarkerView {

    private final TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.linePoint);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(String.valueOf((int) e.getY()));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
