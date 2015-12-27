#一些笔记：

### PorterDuffXfermode

### Paint
>
- setAntiAlias: 设置画笔的锯齿效果
- setArgb： 设置画笔的ARGB值
- setAlpha： 设置画笔的透明度
- setStyle：设置画笔的风格Fill实心/Stroke空心
- setStrokeWidth: 设置空心的边框宽度
- getColor： 获得画笔的颜色
- getAlpha: 得到画笔的Alpha值

### Bitmap.Config: 图片的色彩细节（存储方法）
    -- A(Alpha): 透明度; R(Red)/G(Green)/B(Blue)三原色
    -- 颜色越多，可存储的色彩信息越多，图片也就越逼真
>
- Bitmap.Config ALPHA_8: 8位Alpha的位图
- Bitmap.Config ARGB_4444: 4个4位-16位的ARGB位图
- Bitmap.Config ARGB_8888: 4个8位-32位的ARGB位图
- Bitmap.Config RGB_565: 8位RGB位图 没有透明度

### Property Animation属性动画:
>
- Duration: 动画的持续时间
- TimeInterpolation: 属性值的计算方式，先快后慢...
- TypeEvaluator: 更具属性的开始，结束值与TimeInterpolation计算的因子计算出当前时间的属性值
- Repeat Count and behavior: 重复次数与方式
- AnimationSets: 动画的集合，可同时播放也可以设置startDelay偏移量
- Frame refresh delay: 多久刷新一次

### TypeEvaluators:
>
- IntEvaluator: 属性值类型为int
- FloatEvaluator: 属性值为float
- ArgbEvaluator: 属性值为十六进制的颜色值
- TypeEvaluator：一个接口，可以通过实现该接口自定义Evaluator

### TimeInterpolator:
>
- AccelerateInterpolator: 加速开始慢，中间加速
- DecelerateInterpolator: 减速，开始时快然后减速
- AccelerateDecelerateIn: 先加速后减速，开始结束时慢，中间加速
- AnticipateInterpolator: 反向 ，先向相反方向改变一段再加速播放
- AnticipateOvershootInterpolator: 反向加回弹，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值
- BounceInterpolator: 跳跃，快到目的值时值会跳跃
- CycleInterpolator: 循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 * mCycles * Math.PI * input)
- LinearInterpolator: 线性，线性均匀改变
- OvershotInterpolator: 回弹，最后超出目的值然后缓慢改变到目的值
- TimeInterpolator: 一个接口，允许你自定义interpolator，以上几个都是实现了这个接口