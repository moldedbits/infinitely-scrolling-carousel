package com.moldedbits.infinitecarousel.demo;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Transformation;

import com.moldedbits.infinitecarousel.InfiniteCarousel;

public class Carousel extends InfiniteCarousel {

    private Camera mCamera = new Camera();

    private int halfScreenWidth;

    public Carousel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStaticTransformationsEnabled(true);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if(Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            halfScreenWidth = size.x / 2;
        }
        else {
            halfScreenWidth = display.getWidth() / 2;  // deprecated
        }
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        mCamera.save();

        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        final float rotation = (float) (-1) * (child.getLeft() + child.getWidth() / 2 - halfScreenWidth) / 10f;

        final Matrix imageMatrix = t.getMatrix();
        mCamera.rotateY(rotation);
        mCamera.translate(rotation * 5f, 0, 0);
        mCamera.getMatrix(imageMatrix);
        mCamera.restore();

        imageMatrix.preTranslate(- child.getWidth() / 2f, - child.getHeight() / 2f);
        imageMatrix.postTranslate(child.getWidth() / 2f, child.getHeight() / 2f);

        child.invalidate();

        return true;
    }
}
