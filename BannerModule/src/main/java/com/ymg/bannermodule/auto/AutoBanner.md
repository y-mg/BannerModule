# AutoBanner

```xml
    <com.ymg.bannermodule.auto.AutoBannerViewPager
        android:id="@+id/autoBannerViewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

    <com.ymg.bannermodule.auto.AutoBannerIndicator
        android:id="@+id/autoBannerIndicator"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|left|center_horizontal"
        android:layout_marginLeft="10dp"
        android:layout_marginBottom="10dp"
        app:abiSpace="5dp"
        app:abiSelectedDrawable="@drawable/indicator_selected"
        app:abiUnSelectedDrawable="@drawable/indicator_un_selected"
        tools:ignore="RtlHardcoded" />
```
<br/>

| <center>Option</center> | <center>Description</center> |
|:--------|:--------:|
| <center>abiSpace</center> | <center>인디케이터 사이 공간</center> |
| <center>abiSelectedDrawable</center> | <center>인디케이터 선택인 경우 Drawable</center> |
| <center>abiUnSelectedDrawable</center> | <center>인디케이터 미선택일 경우 Drawable</center> |
<br/>
<br/>


```kotlin
class MainActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainAdapter = MainAdapter(this)

        autoBannerViewPager.apply {
            adapter = mainAdapter
            setShowBannerDuration(1000)
            setAutoScrollAnimationDuration(5.0)
            setSwipeScrollAnimationDuration(2.5)
            currentItem = 3 * 100
        }.run {
            setStartAutoScroll(5000)
        }

        autoBannerIndicator.setConnectViewPager(autoBannerViewPager, 3)
        autoBannerIndicator.setStartIndicator()
    }
}
```
<br/>

| <center>Option</center> | <center>Description</center> |
|:--------|:--------:|
| <center>setShowBannerDuration(duration: Int)</center> | <center>배너 보여줄 지속 시간</center> |
| <center>setAutoScrollDirection(AUTO_SCROLL_DIRECTION_LEFT or AUTO_SCROLL_DIRECTION_RIGHT)</center> | <center>배너 자동 스크롤 시 방향</center> |
| <center>setAutoScrollAnimationDuration(duration: Double)</center> | <center>스크롤 시 애니메이션 지속 시간</center> |
| <center>setSwipeScrollAnimationDuration(duration: Double)</center> | <center>스와이프 시 애니메이션 지속 시간</center> |
| <center>setStartAutoScroll(duration: Double or Void)</center> | <center>자동 스크롤 시작, 인자를 줄 경우 첫 배너를 보여줄 지속시간 설정(Default: 5000 ms)</center> |
<br/>

| <center>Option</center> | <center>Description</center> |
|:--------|:--------:|
| <center>setConnectViewPager(AutoBannerViewPager, Banner List Size)</center> | <center>인디케이터를 뷰페이저에 연결</center> |
| <center>setStartIndicator()</center> | <center>배너 자동 스크롤 시 방향</center> |
<br/>
<br/>


#### 권장사항
왼쪽 or 오른쪽으로 무한 스크롤이 되도록 currentItem 옵션을 주어 현재 위치를 가운데쯤에 위치하도록 설정할 것을 권장함.
<br/>
<br/>