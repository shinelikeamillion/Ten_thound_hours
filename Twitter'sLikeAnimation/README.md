Twitter的like动画安卓版本
===

英文原文：http://frogermcs.github.io/twitters-like-animation-in-android-alternative/

作者使用的是星星的图标，暂时按作者的来，以后可以拓展

思路：

创建一个LikeButtonView的View，它是将由三个子View构成的FrameLayout
    -CircleView：显示星星图标下面的圆
    -ImageView：（星星）或者是其他图标
    -DotsView：图标周围的浮点

1，CircleView
    --使用Clear模式绘制颜色清除画布，然后根据给定的进度（进度独立）绘制内圆和外圆

设置画笔的风格：
    .setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

根据进度改变外圆的颜色(渐变)：
    .setColor((Integer) argbEvaluator.evaluate(colorProgress, START_COLOR, END_COLOR));

2，DotsView
    --绘制浮动在图标周围的圆点（通过重写来做这件事）

基于currentProgress绘制圆点，再次使用argbEvaluator实现圆点的颜色动画

3，将图标，CircleView，DotsView放在FrameLayout里面

使用Merge消除多余的标签，避免过度绘制

使用AnimatorSet将各个View的动画组合起来，注意时间的间隔和合适的插值器

给LikeButtonView添加触摸响应（图标缩放）


