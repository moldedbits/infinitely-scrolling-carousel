package com.moldedbits.infinitecarousel.demo;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;

import com.moldedbits.infinitecarousel.InfiniteCarousel;

public class Carousel extends InfiniteCarousel {

    private Camera mCamera = new Camera();

    public Carousel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStaticTransformationsEnabled(true);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        mCamera.save();

        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        final Matrix imageMatrix = t.getMatrix();
        mCamera.rotateY(child.getLeft() / 20);
        mCamera.getMatrix(imageMatrix);
        mCamera.restore();

        child.invalidate();

        return true;
    }
}
