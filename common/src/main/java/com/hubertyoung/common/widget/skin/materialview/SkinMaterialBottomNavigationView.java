package com.hubertyoung.common.widget.skin.materialview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.acty.litemall.R;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatSupportable;

import static skin.support.widget.SkinCompatHelper.INVALID_ID;

/**
 * Created by ximsfei on 17-3-1.
 */

public class SkinMaterialBottomNavigationView extends BottomNavigationView implements SkinCompatSupportable {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-android.R.attr.state_enabled};

    private int mTextColorResId = INVALID_ID;
    private int mIconTintResId = INVALID_ID;
    private int mDefaultTintResId = INVALID_ID;

    public SkinMaterialBottomNavigationView(@NonNull Context context) {
        this(context, null);
    }

    public SkinMaterialBottomNavigationView( @NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinMaterialBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationView, defStyleAttr,
                R.style.Widget_Design_BottomNavigationView);

        if (a.hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
            mIconTintResId = a.getResourceId(R.styleable.BottomNavigationView_itemIconTint, INVALID_ID);
        } else {
            mDefaultTintResId = resolveColorPrimary();
        }
        if (a.hasValue(R.styleable.BottomNavigationView_itemTextColor)) {
            mTextColorResId = a.getResourceId(R.styleable.BottomNavigationView_itemTextColor, INVALID_ID);
        } else {
            mDefaultTintResId = resolveColorPrimary();
        }
        a.recycle();
        applyItemIconTintResource();
        applyItemTextColorResource();
    }

    private void applyItemTextColorResource() {
        mTextColorResId = SkinCompatHelper.checkResourceId(mTextColorResId);
        if (mTextColorResId != INVALID_ID) {
            setItemTextColor(SkinCompatResources.getInstance().getColorStateList(mTextColorResId));
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId);
            if (mDefaultTintResId != INVALID_ID) {
                setItemTextColor(createDefaultColorStateList(android.R.attr.textColorSecondary));
            }
        }
    }

    private void applyItemIconTintResource() {
        mIconTintResId = SkinCompatHelper.checkResourceId(mIconTintResId);
        if (mIconTintResId != INVALID_ID) {
            setItemIconTintList(SkinCompatResources.getInstance().getColorStateList(mIconTintResId));
        } else {
            mDefaultTintResId = SkinCompatHelper.checkResourceId(mDefaultTintResId);
            if (mDefaultTintResId != INVALID_ID) {
                setItemIconTintList(createDefaultColorStateList(android.R.attr.textColorSecondary));
            }
        }
    }

    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        final TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = SkinCompatResources.getInstance().getColorStateList(value.resourceId);

        int colorPrimary = SkinCompatResources.getInstance().getColor(mDefaultTintResId);
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{
                DISABLED_STATE_SET,
                CHECKED_STATE_SET,
                EMPTY_STATE_SET
        }, new int[]{
                baseColor.getColorForState(DISABLED_STATE_SET, defaultColor),
                colorPrimary,
                defaultColor
        });
    }

    private int resolveColorPrimary() {
        final TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(
                R.attr.colorPrimary, value, true)) {
            return INVALID_ID;
        }
        return value.resourceId;
    }

    @Override
    public void applySkin() {
        applyItemIconTintResource();
        applyItemTextColorResource();
    }

}
