Twitter的like动画安卓版本 （2015-12-26）
英文原文：http://frogermcs.github.io/twitters-like-animation-in-android-alternative/

作者使用的是星星的图标，暂时按作者的来，以后可以拓展

实现过程：

创建一个LikeButtonView的View，它是将由三个子View构成的FrameLayout
    -CircleView：显示星星图标下面的圆
    -ImageView：（星星）或者是其他图标
    -DotsView：图标周围的浮点

1，CircleView
    --这个视图负责绘制星星图标下面的大圆，本可以用shape="oval"实现，但是我们应该考虑按钮下面的北京颜色


