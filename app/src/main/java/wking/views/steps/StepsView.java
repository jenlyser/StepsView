package wking.views.steps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StepsView extends FrameLayout {

    private StepsDraw mStepDraw;
    private String[] mSteps = new String[0];//所有步骤
    private int mStepTextColor = Integer.MIN_VALUE;//默认步骤的颜色
    private int mStepProgressTextColor = Integer.MIN_VALUE;//已运行步骤的颜色
    private int mStepCurrentTextColor = Integer.MIN_VALUE;//当前步骤的颜色
    private int mCurrentPosition = 0;//当前运行的步骤

    private float mStepBarHeight = 56;//步骤条的高度
    private float mTextMarginTop = 10;//距离步骤高度
    private float mTextTop = 10;//距离顶部高度,步骤条的高度加上距离步骤的高度
    private float mTextSize = 12;//字体大小
    private int mTextMaxLine = 1;//行数

    private AnimationType mAnimationType = AnimationType.None;//动画类型
    private int mAnimationColor = Integer.MIN_VALUE;//动画控件的颜色
    private int mAnimationDuration = 1100;//动画时长
    private View mAnimationView;//动态控件
    private LinearLayout mContainsView;//包裹动态控件,为了修复位移效果
    private List<TextView> mStepTextviews = new ArrayList<>();//所有的textView


    public StepsView(@NonNull Context context) {
        super(context);
        initView();
    }

    public StepsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StepsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        mStepDraw = new StepsDraw();
        mStepDraw.setStepBarHeight(mStepBarHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStepDraw.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mStepDraw.onDraw(canvas);
        drawStepText();
        delayDrawAnimation();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }


    //region 属性方法

    /**
     * 设置所有步骤
     *
     * @param steps
     */
    public StepsView setSteps(String[] steps) {
        this.mSteps = steps;
        if (mSteps == null) {
            mSteps = new String[0];
        }
        mStepDraw.setStepsSize(this.mSteps.length);
        return this;
    }

    /**
     * 获取所有步骤
     *
     * @return
     */
    public String[] getSteps() {
        return this.mSteps;
    }

    /**
     * 设置动画时长
     *
     * @param animationDuration
     * @return
     */
    public StepsView setAnimationDuration(int animationDuration) {
        this.mAnimationDuration = animationDuration;
        return this;
    }

    /**
     * 设置动画按钮的颜色.
     *
     * @param mAnimationColor
     * @return
     */
    public StepsView setAnimationColor(int mAnimationColor) {
        this.mAnimationColor = mAnimationColor;
        return this;
    }

    /**
     * 设置步骤文本颜色
     *
     * @param mStepTextColor
     */
    public StepsView setStepTextColor(int mStepTextColor) {
        this.mStepTextColor = mStepTextColor;
        return this;
    }

    /**
     * 设置已运行步骤文本颜色
     *
     * @param mStepProgressTextColor
     */
    public StepsView setStepProgressTextColor(int mStepProgressTextColor) {
        this.mStepProgressTextColor = mStepProgressTextColor;
        return this;
    }

    /**
     * 设置当前步骤文本颜色
     *
     * @param mStepCurrentTextColor
     */
    public StepsView setStepCurrentTextColor(int mStepCurrentTextColor) {
        this.mStepCurrentTextColor = mStepCurrentTextColor;
        return this;
    }

    /**
     * 设置文字间距
     *
     * @param mTextMarginTop
     */
    public StepsView setTextMarginTop(float mTextMarginTop) {
        this.mTextMarginTop = mTextMarginTop;
        return this;
    }

    /**
     * 设置动画方式
     *
     * @param animationType
     */
    public StepsView setAnimationType(AnimationType animationType) {
        this.mAnimationType = animationType;
        return this;
    }

    /**
     * 设置文字大小
     *
     * @param mTextSize
     */
    public StepsView setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        return this;
    }

    /**
     * 设置文字显示行数
     *
     * @param mTextMaxLine
     */
    public StepsView setTextMaxLine(int mTextMaxLine) {
        this.mTextMaxLine = mTextMaxLine;
        return this;
    }

    /**
     * 设置当前进行到哪一步
     *
     * @param mCurrentPosition
     */
    public StepsView setCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
        mStepDraw.setCurrentPosition(mCurrentPosition);
        return this;

    }

    /**
     * 设置已经进行的步骤的颜色
     *
     * @param mProgressColor
     */
    public StepsView setProgressColor(int mProgressColor) {
        mStepDraw.setProgressColor(mProgressColor);
        return this;
    }

    /**
     * 设置当前步骤的颜色
     *
     * @param mCurrentColor
     */
    public StepsView setCurrentColor(int mCurrentColor) {
        mStepDraw.setCurrentColor(mCurrentColor);
        return this;
    }

    /**
     * 设置未进行步骤的颜色
     *
     * @param mStepsColor
     */
    public StepsView setStepsColor(int mStepsColor) {
        mStepDraw.setStepsColor(mStepsColor);
        return this;
    }

    /**
     * 设置步骤条高度
     *
     * @param stepBarHeight
     */
    public StepsView setStepBarHeight(float stepBarHeight) {
        this.mStepBarHeight = stepBarHeight;
        mStepDraw.setStepBarHeight(mStepBarHeight);
        return this;
    }

    /**
     * 设置线条高度
     *
     * @param mLineHeight
     */
    public StepsView setLineHeight(float mLineHeight) {
        mStepDraw.setLineHeight(mLineHeight);
        return this;
    }

    /**
     * 设置步骤圆形的半径
     *
     * @param mCircleRadius
     */
    public StepsView setCircleRadius(float mCircleRadius) {
        mStepDraw.setCircleRadius(mCircleRadius);
        return this;
    }

    /**
     * 设置圆角圆形线宽度
     *
     * @param mCircleStrokeWidth
     */
    public StepsView setCircleStrokeWidth(float mCircleStrokeWidth) {
        mStepDraw.setCircleStrokeWidth(mCircleStrokeWidth);
        return this;
    }

    /**
     * 设置内距
     *
     * @param mPadding
     */
    public StepsView setStepPadding(float mPadding) {
        mStepDraw.setStepPadding(mPadding);
        return this;
    }


    /**
     * 绘制
     */
    public void drawSteps() {
        if (mSteps == null) {
            throw new IllegalArgumentException("steps must not be null.");
        }

        if (mCurrentPosition < 0 || mCurrentPosition > mSteps.length - 1) {
            throw new IndexOutOfBoundsException(String.format("Index : %s, Size : %s", mCurrentPosition, mSteps.length));
        }
        TextView mItemView;
        //移除已存在的控件
        while (mStepTextviews.size() > 0) {
            mItemView = mStepTextviews.get(0);
            removeView(mItemView);
            mStepTextviews.remove(0);
        }
        if (mContainsView != null) {
            mContainsView.removeAllViews();
            removeView(mContainsView);
            mAnimationView = null;
            mContainsView=null;
        }
        int width = getWidth();
        int height = getHeight();
        if (width > 0 || height > 0) {
            mStepDraw.onSizeChanged(width, height, width, height);
        }
        this.postInvalidate();
    }

    //endregion


    /**
     * 添加TextView
     */
    private synchronized void drawStepText() {
        //添加过不需要再添加
        if (mStepTextviews != null && mStepTextviews.size() > 0) return;
        List<Float> stepsPosition = mStepDraw.getStepContainerXPosition();
        if (mSteps == null || mSteps.length != stepsPosition.size()) {
            return;
        }
        TextView mItemView;
        float itemWidth = mStepDraw.getItemWidth();
        mTextTop = mStepBarHeight + mTextMarginTop;
        ViewGroup.LayoutParams itemLayout;
        double textWidth;
        int stepTextColor = mStepTextColor == Integer.MIN_VALUE ? mStepDraw.getStepsColor() : mStepTextColor;
        int stepProgressTextColor = mStepTextColor == Integer.MIN_VALUE ? mStepDraw.getProgressColor() : mStepProgressTextColor;
        int stepCurrentTextColor = mStepTextColor == Integer.MIN_VALUE ? mStepDraw.getCurrentColor() : mStepCurrentTextColor;
        int textColor;
        for (int i = 0; i < mSteps.length; i++) {
            mItemView = new TextView(getContext());
            mItemView.setText(mSteps[i]);
            //设置颜色
            textColor = i < mCurrentPosition ? stepProgressTextColor : stepTextColor;
            mItemView.setTextColor(textColor);
            if (i == mCurrentPosition) {
                mItemView.setTextColor(stepCurrentTextColor);
            }
            //设置文字显示效果
            mItemView.setTextSize(mTextSize);
            mItemView.setMaxLines(mTextMaxLine);
//            mItemView.setTypeface(null, Typeface.NORMAL);

            //设置位置
            mItemView.setX(stepsPosition.get(i) - (itemWidth / 2));
            mItemView.setY(mTextTop);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mItemView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            } else {
                textWidth = getTextWidth(mItemView.getPaint(), mSteps[i]);
                //文本坐标位置微调,设置为居中
                if (textWidth < itemWidth) {
                    float addLeft = (float) ((itemWidth - textWidth) / 2);
                    mItemView.setX(stepsPosition.get(i) - (itemWidth / 2) + addLeft);
                }
            }
            //设置宽高
            itemLayout = new ViewGroup.LayoutParams((int) itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            mItemView.setLayoutParams(itemLayout);

            //添加显示
            addView(mItemView);
            //添加控件记录
            mStepTextviews.add(mItemView);
        }
    }

    /**
     * 添加动态控件
     */
    private void drawAnimationView() {
        if (mAnimationView != null || mAnimationType == AnimationType.None) {
            return;
        }
        float stepCircleRadius = mStepDraw.getCircleRadius();
        int width = (int) (stepCircleRadius * 2);

        //创建包裹控件
        mContainsView=new LinearLayout(getContext());
        FrameLayout.LayoutParams containsLayoutParams = new FrameLayout.LayoutParams(width, width);
        mContainsView.setY(mStepDraw.getCenterY() - stepCircleRadius);
        mContainsView.setX(mStepDraw.getStepsXPosition(mCurrentPosition) - stepCircleRadius);
        mContainsView.setLayoutParams(containsLayoutParams);
        this.addView(mContainsView);

        //创建动画控件
        mAnimationView = new View(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, width);
//        layoutParams.leftMargin = (int) (mStepDraw.getStepsXPosition(mCurrentPosition) - stepCircleRadius);
//        layoutParams.topMargin = (int) (mStepDraw.getCenterY() - stepCircleRadius);
        mAnimationView.setLayoutParams(layoutParams);
        mContainsView.addView(mAnimationView);
        //创建动画控件背景
        GradientDrawable gd = new GradientDrawable();
        int animationViewBg = mAnimationColor == Integer.MIN_VALUE ? Color.WHITE : mAnimationColor;
        gd.setColor(animationViewBg);
        gd.setCornerRadius((int) stepCircleRadius);
        mAnimationView.setBackgroundDrawable(gd);

        //开始动画
        startAnimation();
    }

    //缩放动画
    private ScaleAnimation mAnimation;
    //透明动画
    private AlphaAnimation mAlphaAnimation;

    /**
     * 开始动画
     */
    public void startAnimation() {
        if (mAnimationType == AnimationType.None || mAnimationView == null) {
            return;
        }
        if (mAnimation != null) {
            mAnimation.cancel();
        }
        if (mAlphaAnimation != null) {
            mAlphaAnimation.cancel();
        }
        //清理掉动画
        mAnimationView.clearAnimation();
        switch (mAnimationType) {
            case Alpha:
                mAlphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                mAlphaAnimation.setDuration(mAnimationDuration);
                mAlphaAnimation.setRepeatCount(Animation.INFINITE);
                mAlphaAnimation.setRepeatMode(Animation.RESTART);
                mAnimationView.setAnimation(mAlphaAnimation);
                mAlphaAnimation.start();
                break;
            case Scale:
                mAnimation = new ScaleAnimation(0.2f, 0.8f, 0.2f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mAnimation.setDuration(mAnimationDuration);
                mAnimation.setRepeatCount(Animation.INFINITE);
                mAnimation.setRepeatMode(Animation.RESTART);
                mAnimationView.setAnimation(mAnimation);
                mAnimation.start();
                break;
        }
    }

    /**
     * 停止动画
     */
    private void stopAnimation() {
        if (mAnimation != null) {
            mAnimation.cancel();
            mAnimation = null;
        }
        if (mAlphaAnimation != null) {
            mAlphaAnimation.cancel();
            mAlphaAnimation = null;
        }
    }

    /**
     * 计算textView 文本的宽度.
     *
     * @param paint
     * @param str
     * @return
     */
    public double getTextWidth(Paint paint, String str) {
        double iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * 如果不延时一下会导致缩放动画只能从左上角缩放,而不是居中
     */
    private void delayDrawAnimation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                drawAnimationView();
            }
        }, 1000);
    }

    /**
     * 动画类型
     */
    public enum AnimationType {
        None,//无动画
        Scale,//缩放动画
        Alpha;//透明动画
    }
}
