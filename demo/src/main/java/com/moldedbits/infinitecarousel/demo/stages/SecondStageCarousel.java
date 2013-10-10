package com.moldedbits.infinitecarousel.demo.stages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import java.util.LinkedList;

public class SecondStageCarousel extends AdapterView {

    /** The adapter with all the data */
    private Adapter mAdapter;

    private int mTouchStartX;
    private int mListLeftStart;
    private int mListLeft;

    /** A list of cached (re-usable) item views */
    private final LinkedList<View> mCachedItemViews = new LinkedList<View>();

    /** The adaptor position of the first visible item */
    private int mFirstItemPosition;

    /** The adaptor position of the last visible item */
    private int mLastItemPosition;

    private int mListLeftOffset;

    /** Children added with this layout mode will be added below the last child */
    private static final int LAYOUT_MODE_BELOW = 0;

    /** Children added with this layout mode will be added above the first child */
    private static final int LAYOUT_MODE_ABOVE = 1;

    public SecondStageCarousel(Context context, AttributeSet attrs) {
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
            mLastItemPosition = -1;
            fillListDown(mListLeft, 0);
        } else {
            int offset = mListLeft + mListLeftOffset - getChildAt(0).getLeft();
            removeNonVisibleViews(offset);
            fillList(offset);
        }

        positionItems();
        invalidate();
    }

    /**
     * Adds a view as a child view and takes care of measuring it
     *
     * @param child The view to add
     * @param layoutMode Either LAYOUT_MODE_ABOVE or LAYOUT_MODE_BELOW
     */
    private void addAndMeasureChild(final View child, final int layoutMode) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        final int index = layoutMode == LAYOUT_MODE_ABOVE ? 0 : -1;
        addViewInLayout(child, index, params, true);

        final int itemHeight = getHeight();
        child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY | itemHeight);
    }

    /**
     * Positions the children at the &quot;correct&quot; positions
     */
    private void positionItems() {
        int left = mListLeft + mListLeftOffset;

        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);

            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            int top = (getHeight() - height) / 2;

            child.layout(left, top, left + width, top + height);
            left += width;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getChildCount() == 0) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = (int)event.getX();
                mListLeftStart = getChildAt(0).getLeft();
                break;

            case MotionEvent.ACTION_MOVE:
                int scrolledDistance = (int)event.getX() - mTouchStartX;
                mListLeft = mListLeftStart + scrolledDistance;
                requestLayout();
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * Removes view that are outside of the visible part of the list. Will not
     * remove all views.
     *
     * @param offset Offset of the visible area
     */
    private void removeNonVisibleViews(final int offset) {
        // We need to keep close track of the child count in this function. We
        // should never remove all the views, because if we do, we loose track
        // of were we are.
        int childCount = getChildCount();

        // if we are not at the right end of the list and have more than one child
        if (mLastItemPosition != mAdapter.getCount() - 1 && childCount > 1) {
            // check if we should remove any views in the top
            View firstChild = getChildAt(0);
            while (firstChild != null && firstChild.getRight() + offset < 0) {
                // remove the leftmost view
                removeViewInLayout(firstChild);
                childCount--;
                mCachedItemViews.addLast(firstChild);
                mFirstItemPosition++;

                // update the list offset (since we've removed the top child)
                mListLeftOffset += firstChild.getMeasuredWidth();

                // Continue to check the next child only if we have more than
                // one child left
                if (childCount > 1) {
                    firstChild = getChildAt(0);
                } else {
                    firstChild = null;
                }
            }
        }

        // if we are not at the left end of the list and have more than one child
        if (mFirstItemPosition != 0 && childCount > 1) {
            // check if we should remove any views in the bottom
            View lastChild = getChildAt(childCount - 1);
            while (lastChild != null && lastChild.getLeft() + offset > getWidth()) {
                // remove the bottom view
                removeViewInLayout(lastChild);
                childCount--;
                mCachedItemViews.addLast(lastChild);
                mLastItemPosition--;

                // Continue to check the next child only if we have more than
                // one child left
                if (childCount > 1) {
                    lastChild = getChildAt(childCount - 1);
                } else {
                    lastChild = null;
                }
            }
        }
    }

    /**
     * Fills the list with child-views
     *
     * @param offset Offset of the visible area
     */
    private void fillList(final int offset) {
        final int rightEdge = getChildAt(getChildCount() - 1).getRight();
        fillListDown(rightEdge, offset);

        final int leftEdge = getChildAt(0).getLeft();
        fillListUp(leftEdge, offset);
    }

    /**
     * Starts at the bottom and adds children until we've passed the list bottom
     *
     * @param rightEdge The bottom edge of the currently last child
     * @param offset Offset of the visible area
     */
    private void fillListDown(int rightEdge, final int offset) {
        while (rightEdge + offset < getWidth()) { // && mLastItemPosition < mAdapter.getCount() - 1) {
            mLastItemPosition++;
            while(mLastItemPosition < 0)
                mLastItemPosition += mAdapter.getCount();
            mLastItemPosition = mLastItemPosition % mAdapter.getCount();

            final View newBottomchild = mAdapter.getView(mLastItemPosition, getCachedView(), this);
            addAndMeasureChild(newBottomchild, LAYOUT_MODE_BELOW);
            rightEdge += newBottomchild.getMeasuredWidth();
        }
    }

    /**
     * Starts at the top and adds children until we've passed the list top
     *
     * @param leftEdge The top edge of the currently first child
     * @param offset Offset of the visible area
     */
    private void fillListUp(int leftEdge, final int offset) {
        while (leftEdge + offset > 0) {// && mFirstItemPosition > 0) {
            mFirstItemPosition--;
            while(mFirstItemPosition < 0)
                mFirstItemPosition += mAdapter.getCount();
            mFirstItemPosition = mFirstItemPosition % mAdapter.getCount();

            final View newTopCild = mAdapter.getView(mFirstItemPosition, getCachedView(), this);
            addAndMeasureChild(newTopCild, LAYOUT_MODE_ABOVE);
            final int childWidth = newTopCild.getMeasuredWidth();
            leftEdge -= childWidth;

            // update the list offset (since we added a view at the top)
            mListLeftOffset -= childWidth;
        }
    }

    /**
     * Checks if there is a cached view that can be used
     *
     * @return A cached view or, if none was found, null
     */
    private View getCachedView() {
        if (mCachedItemViews.size() != 0) {
            return mCachedItemViews.removeFirst();
        }
        return null;
    }
}
