# ProgressPercent
Android Custom progress, with progress written inside.

<p align="center">
<img alt="demo gif" src="preview/progress_percent.png" width=300 />
</p>

<h5>Can change direction (LTR, RTL):<h5/>
- From XML: use 
```xml
app:direction="rtl"
```
- From code: use
```java
mProgressView.setProgressDirection(View.LAYOUT_DIRECTION_LTR);
mProgressView.setProgressDirection(View.LAYOUT_DIRECTION_RTL);
```

<p align="center">
<img alt="demo gif" src="preview/progress_preview.gif" width=300 />
</p>

<h5>To change Font of progress text.<h5/>
- From XML: use 
```xml
app:textFont="font_winter_calligraphy.ttf"
```
- From code: use
```java
// Font should be added to assets folder before doing this
mProgressView.setProgressFont("font_winter_calligraphy.ttf");
```

<p align="center">
<img alt="demo gif" src="preview/progress_preview2.gif" width=300 />
</p>

<h5>To change color of (progress, background, stroke).</h5>
- From XML: use

```xml
app:backgroundColor="@color/colorGray"
app:progressColor="@color/result_red"
app:strokeColor="@color/result_blue"
```

- From code: use

```java
mProgressView.setProgressBackgroundColor(getResources().getColor(R.color.colorGray));
mProgressView.setProgressColor(getResources().getColor(R.color.colorGray));
mProgressView.setProgressStrokeColor(getResources().getColor(R.color.colorGray));
```

<p align="center">
<img alt="demo gif" src="preview/progress_preview3.gif" width=300 />
</p>

<h5>Other Parameters:</h5>
- set max

```xml
app:max="100"
```

```java
setProgressMax(float progressMax)
```

- set progress

```xml
app:progress="50"
```

```java
setProgress(final float _progress)
```

- set radius "corners"

```xml
app:radius="10dp"
```

- set progress text size
```xml
app:textSize="30sp"
```

`Inspired by:`
[Rakshak R.Hegde](https://github.com/rakshakhegde/Diffre "Rakshak R.Hegde's Differ Project")
