package com.moldedbits.infinitecarousel.demo.stages;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class FirstStageCarousel extends AdapterView {

    /** The adapter with all the data */
    private Adapter mAdapter;

    public FirstStageCarousel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setSelection(int position) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public View getSelectedView() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // if we don't have an adapter, we don't need to do anything
        if (mAdapter == null) {
            return;
        }

        if (getChildCount() == 0) {
            int position = 0;
            int rightEdge = 0;
            Log.d("DEBUG", "width is " + getWidth() + ", count is " + mAdapter.getCount());
            while (rightEdge < getWidth() && position < mAdapter.getCount()) {
                View newBottomChild = mAdapter.getView(position, null, this);
                addAndMeasureChild(newBottomChild);
                rightEdge += newBottomChild.getMeasuredWidth();
                position++;
            }
        }

        positionItems();
    }

    /**
     * Adds a view as a child view and takes care of measuring it
     *
     * @param child The view to add
     */
    private void addAndMeasureChild(View child) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        addViewInLayout(child, -1, params, true);

        int itemHeight = getHeight();
        child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY | itemHeight);
    }

    /**
     * Positions the children at the &quot;correct&quot; positions
     */
    private void positionItems() {
        int left = 0;

        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);

            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int top = (getHeight() - height) / 2;

            child.layout(left, top, left + width, top + height);
            left += width;
        }
    }
}
