package com.wking.stepsview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import wking.views.steps.StepsView;

public class MainActivity extends AppCompatActivity {

    private String[] mSteps = {"步骤1", "步骤2", "完成"};
    private String[] mSteps2 = {"步骤1", "步骤2", "步骤3", "步骤4", "完成"};
    private String[] mSteps3 = {"动画", "缩放动画", "透明动画", "动画颜色", "动画时长"};
    private String[] mSteps4 = {"文字","文字少能居中", "文字多会换行文字多会换行","字体颜色","文字大小"};
    private String[] mSteps5 = {"步骤圆形", "设置颜色", "圆形线宽", "圆形大小", "连接线大小"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadSteps();
    }

    private void loadSteps(){
        StepsView stepsView1=findViewById(R.id.steps1);
        stepsView1.setSteps(mSteps).drawSteps();


        StepsView stepsView2=findViewById(R.id.steps2);
        stepsView2.setSteps(mSteps2)
                .setAnimationType(StepsView.AnimationType.Scale)
                .setTextMarginTop(30)
                .setStepsColor(Color.GRAY)
                .setProgressColor(Color.RED)
                .setCurrentColor(Color.RED)
                .setCurrentPosition(1)
                .drawSteps();

        StepsView stepsView3=findViewById(R.id.steps3);
        stepsView3.setSteps(mSteps3)
                .setAnimationType(StepsView.AnimationType.Alpha)
                .setTextMarginTop(20)
                .setStepsColor(Color.GRAY)
                .setProgressColor(Color.RED)
                .setCurrentColor(Color.RED)
                .setAnimationColor(Color.DKGRAY)
                .setCurrentPosition(2)
                .drawSteps();

        StepsView stepsView4=findViewById(R.id.steps4);
        stepsView4.setSteps(mSteps4)
                .setStepPadding(60)
                .setAnimationType(StepsView.AnimationType.Scale)
                .setTextMarginTop(20)
                .setStepsColor(Color.GRAY)
                .setProgressColor(Color.parseColor("#ff4040"))
                .setCurrentColor(Color.parseColor("#ff4040"))
                .setAnimationColor(Color.WHITE)
                .setTextMaxLine(2)
                .setTextSize(16)
                .setCurrentPosition(3)
                .drawSteps();

        StepsView stepsView5=findViewById(R.id.steps5);
        stepsView5.setSteps(mSteps5)
                .setStepPadding(60)
                .setStepBarHeight(80)
                .setStepsColor(Color.parseColor("#787878"))
                .setProgressColor(Color.parseColor("#0f9933"))
                .setCurrentColor(Color.parseColor("#0f9933"))
                .setAnimationType(StepsView.AnimationType.Scale)
                .setTextMarginTop(30)
                .setCircleRadius(20)
                .setCircleStrokeWidth(12)
                .setLineHeight(12)
                .setTextMaxLine(2)
                .setCurrentPosition(3)
                .drawSteps();

        StepsView stepsView6=findViewById(R.id.steps6);
        stepsView6.setSteps(mSteps5)
                .setStepPadding(60)
                .setAnimationType(StepsView.AnimationType.Scale)
                .setTextMarginTop(30)
                .setStepsColor(Color.parseColor("#515151"))
                .setProgressColor(Color.parseColor("#1296db"))
                .setCurrentColor(Color.parseColor("#d4237a"))
                .setAnimationColor(Color.WHITE)
                .setTextMaxLine(2)
                .setCurrentPosition(1)
                .drawSteps();

        StepsView stepsView7=findViewById(R.id.steps7);
        stepsView7.setSteps(mSteps5)
                .setStepPadding(60)
                .setAnimationType(StepsView.AnimationType.Alpha)
                .setTextMarginTop(30)
                .setStepsColor(Color.parseColor("#515151"))
                .setProgressColor(Color.parseColor("#1296db"))
                .setCurrentColor(Color.parseColor("#d4237a"))
                .setStepTextColor(Color.BLACK)
                .setStepCurrentTextColor(Color.BLACK)
                .setStepProgressTextColor(Color.BLACK)
                .setAnimationColor(Color.WHITE)
                .setLineHeight(3)
                .setTextMaxLine(2)
                .setCurrentPosition(1)
                .drawSteps();
    }
}