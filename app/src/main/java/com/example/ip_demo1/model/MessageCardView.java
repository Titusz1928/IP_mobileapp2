package com.example.ip_demo1.model;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.cardview.widget.CardView;

public class MessageCardView extends CardView {

    private static final int MAX_WIDTH_DP = 500; // Maximum width in dp
    private int maxWidthPx; // Maximum width in pixels

    public MessageCardView(Context context) {
        super(context);
        init();
    }

    public MessageCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Calculate maximum width in pixels
        maxWidthPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, MAX_WIDTH_DP,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Get the width mode and size from the measure spec
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // Ensure the width does not exceed the maximum width
        int newWidthMeasureSpec = widthMeasureSpec;
        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize > maxWidthPx) {
            newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidthPx, MeasureSpec.AT_MOST);
        }

        super.onMeasure(newWidthMeasureSpec, heightMeasureSpec);
    }
}
