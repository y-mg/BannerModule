# BannerModule
<img width="250px" height="500px" src="/sample/sample.gif" />
<br/>
<br/>



## 1. AutoBannerViewPager

> 이것은 AutoBannerIndicator 와 함께 사용되는 AutoBannerViewPager 입니다.<br/>
> This is the AutoBannerViewPager used with AutoBannerIndicator.


### Kotlin Function

```kotlin
/**
 * - 자동 스크롤을 시작한다.
 * - Start automatic scrolling.
 */
fun setStartAutoScroll()

/**
 * - 자동 스크롤을 시작하고 지연시간을 설정한다.
 * - Start automatic scrolling and set the delay time.
 *
 * @param delayTimeInMills -> Delay Time
 */
fun setStartAutoScroll(delayTimeInMills: Int)

/**
 * - 배너를 보여줄 지속시간을 설정한다.
 * - Set the duration to show the banner.
 *
 * @param duration -> Duration to show the banner
 */
fun setBannerDuration(duration: Int)

/**
 * - 스와프 시 애니메이션 속도를 설정한다.
 * - Set animation speed when swapping.
 *
 * @param swipeAnimationDuration -> Animation speed when swiping.
 */
fun setSwipeScrollAnimationDuration(swipeAnimationDuration: Double)

/**
 * - 스크롤 시 애니메이션 속도를 설정한다.
 * - Set animation speed when scrolling.
 *
 * @param scrollAnimationDuration -> Animation speed when scrolling
 */
fun setAutoScrollAnimationDuration(scrollAnimationDuration: Double)

/**
 * - 스크롤 방향을 설정한다.
 * - Set the scroll direction.
 *
 * @param direction -> AUTO_SCROLL_DIRECTION_LEFT or AUTO_SCROLL_DIRECTION_RIGHT
 */
fun setDirection(direction: Int)
```


### Directions For Use

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewBinding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(viewBinding.root)

    val mainAdapter = MainAdapter(this)

    // 왼쪽 또는 오른쪽으로 무한 스크롤이 되도록 currentItem 옵션을 주어 현재 위치를 가운데쯤에 위치하도록 설정할 것을 권장한다.
    // It is recommended that the current position be set to be centered by giving
    // the currentItem option to scroll left or right indefinitely.
    viewBinding.autoBannerViewPager.apply {
        adapter = mainAdapter
        setBannerDuration(1000)
        setDirection(AUTO_SCROLL_DIRECTION_RIGHT)
        setAutoScrollAnimationDuration(5.0)
        setSwipeScrollAnimationDuration(2.5)
        currentItem = 3 * 100
    }.run {
        setStartAutoScroll(5000)
    }
}
```
<br/>
<br/>



## 2. AutoBannerIndicator

> 이것은 AutoBannerViewPager 와 함께 사용되는 AutoBannerIndicator 입니다.
> This is the AutoBannerIndicator used with AutoBannerViewPager.


### XML

```xml
<com.ymg.bannermodule.auto.AutoBannerIndicator
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:abiSpace="5dp"
    app:abiSelected="@drawable/indicator_selected"
    app:abiUnSelected="@drawable/indicator_un_selected" />
```


### Kotlin Function

```kotlin
/**
 * - AutoBannerViewPager 를 연결한다.
 * - Attach to AutoBannerViewPager.
 *
 * @param viewPager -> AutoBannerViewPager
 *
 * @param realCount -> Number of items in ViewPager
 */
fun setAttachAutoBannerViewPager(viewPager: ViewPager, realCount: Int)

/**
 * - 인디케이터를 동작시킨다.
 * - Start the indicator.
 */
fun setStartIndicator()
```


### Directions For Use

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    ...

    viewBinding.autoBannerIndicator.apply {
        setAttachAutoBannerViewPager(viewBinding.autoBannerViewPager, 3)
        setStartIndicator()
    }
}
```
<br/>
<br/>


