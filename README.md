# StepsView
Android StepView,安卓步骤控件,可以高度自定义.

使用方式

1.xml代码
  <wking.views.steps.StepsView
    android:id="@+id/steps1"
    android:layout_width="match_parent"
    android:layout_height="80dp"
    android:background="#fff"></wking.views.steps.StepsView>
            
2.java代码
  String[] mSteps = {"步骤1", "步骤2", "完成"};
  StepsView stepsView1=findViewById(R.id.steps1);
  stepsView1.setSteps(mSteps).drawSteps();


可以在这里看一下效果再决定是否需要下载代码:
CSDN : https://blog.csdn.net/jenlyser/article/details/108242292
