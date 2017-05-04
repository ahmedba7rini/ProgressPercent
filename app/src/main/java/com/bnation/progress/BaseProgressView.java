package com.bnation.progress;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * <p>
 * Thanks to :
 * (Rakshak R.Hegde) "https://github.com/rakshakhegde"
 * </p>
 *
 * <p>
 * Heavily inspired by :
 * https://github.com/rakshakhegde/Diffre
 * <p>
 *
 * Created by ahmed.bah7ini on Mar 15, 2017.
 */

public abstract class BaseProgressView extends View {

	protected int mProgressDirection = LAYOUT_DIRECTION_RTL;

	private static RectF rectF = new RectF();

	private int mProgressColor = 0xFF679E38; // green by default
	private int mProgressBackgroundColor = 0xFFEDECED; // gray by default
	private int mProgressStrokeColor = 0x00000000; // Transparent by default

	private String textString = "0";
	private String mTextFont = "";
	private float mProgressMax = 100F;

	protected final Path textPath = new Path();
	protected final Path croppedProgressPath = new Path();
	protected final Path croppedTextPath = new Path();

	protected final Path textPathRTL = new Path();
	protected final Path croppedProgressPathRTL = new Path();
	protected final Path croppedTextPathRTL = new Path();

	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	protected int width;
	protected int height;
	private int textWidth;
	private int textHeight;
	private float radius;
	private float mPercent = 0.1F;
	private float mProgress;
	private int textHorizontalPadding;
	protected Path progressStrokePath = new Path();
	private Rect textBounds = new Rect();

	public BaseProgressView(Context context) {
		this(context, null);
	}

	public BaseProgressView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		final Resources res = context.getResources();
		obtainAttributes(context, attrs);

		paint.setTextAlign(Paint.Align.CENTER);
		textString = String.format("%.2f",getProgress());
	}

	private void obtainAttributes(Context context, AttributeSet attrs){
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressView, 0, 0);
		try {
			// get font size
			final float paintTextSize = ta.getDimensionPixelSize(R.styleable.ProgressView_textSize, dpToPx(15));
			paint.setTextSize(paintTextSize);

			// Add Font
			mTextFont = ta.getString(R.styleable.ProgressView_textFont);
			try {
				Typeface tf = Typeface.createFromAsset(context.getAssets(),mTextFont);
				if(!TextUtils.isEmpty(mTextFont) && tf != null){
					paint.setTypeface(tf);
				}
			} catch (Exception e){
					/*do nothing, just ignore font*/
			}

			textHorizontalPadding = ta.getDimensionPixelSize(R.styleable.ProgressView_textHorizontalPadding, dpToPx(10));

			radius = ta.getDimensionPixelSize(R.styleable.ProgressView_radius, dpToPx(10));

			mProgressMax = ta.getInt(R.styleable.ProgressView_max, 100);

			mProgress = ta.getFloat(R.styleable.ProgressView_progress,0F);

			mProgressBackgroundColor = ta.getColor(R.styleable.ProgressView_backgroundColor, mProgressBackgroundColor);

			mProgressColor = ta.getColor(R.styleable.ProgressView_progressColor, mProgressColor);

			mProgressStrokeColor = ta.getColor(R.styleable.ProgressView_strokeColor, mProgressStrokeColor);

			mProgressDirection = ta.getInt(R.styleable.ProgressView_direction, LAYOUT_DIRECTION_RTL);

		} finally {
			ta.recycle();
		}
	}

	protected static Path getRoundRectPath(float left, float top, float right, float bottom, float radius) {
		Path roundRectPath = new Path();
		rectF.set(left, top, right, bottom);
		roundRectPath.addRoundRect(rectF, radius, radius, Path.Direction.CW);
		return roundRectPath;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		paint.getTextBounds(textString, 0, textString.length(), textBounds);

		textWidth = textBounds.width();
		textHeight = textBounds.height();

		width = textWidth;
		height = textHeight + textHorizontalPadding;

		//Measure Width : this to use size enter in layout_width if (wrap content or match parent)
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		if (widthMode == MeasureSpec.EXACTLY) {
			//Must be this size
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			//Can't be bigger than...
			width = Math.min(width, widthSize);
		}
		// end of Measure Width

		initProgressPaint(mProgress);

		progressStrokePath = getRoundRectPath(0, 0, width, height, radius);

		computePaths();

		// Adding 1 to prevent artifacts
		setMeasuredDimension(width + 1, height + 1);
	}

	private int cx = 0;
	private void initProgressPaint(final float _progress){
		assert _progress >= 0F && _progress <= mProgressMax;

		mProgress = _progress;

		mPercent = _progress / mProgressMax;

		textString = String.format("%.2f", getProgress());

		if(mProgressDirection == LAYOUT_DIRECTION_LTR){
			// CX: LTR Text coordinates calculation
			int minProgressLTR = 2* textWidth;
			if(getProgressPos() > minProgressLTR) // stop moving progress LTR
			{
				cx = (getProgressPos() - textWidth);
			} else {
				cx = (textWidth);
			}
			paint.getTextPath(textString, 0, textString.length(), cx, (height + textHeight) / 2, textPath);
		} else {
			// CX: RTL Text coordinates calculation
			int minProgressRTL = width - (textWidth); // RTL
			if((width - getProgressPos() + textWidth) < minProgressRTL){ // RTL
				cx = (width - getProgressPos() + textWidth);
			} else {
				cx = width - (textWidth) ;
			}
			paint.getTextPath(textString, 0, textString.length(), cx, (height + textHeight) / 2, textPathRTL); // RTL
		}

		paint.setFakeBoldText(true);

	}

	public void setProgress(final float _progress) {
		initProgressPaint(_progress);
		computePaths();
		invalidate();
	}

	/**
	 * return actual position in px.
	 * @return width * mPercent
     */
	protected int getProgressPos() {
		return (int) (mPercent *width);
	}

	/**
	 * return progress
	 * @return progress (1 to 100)
     */
	public float getProgress(){
		return mProgress;
	}

	private void computePaths() {
		computeCroppedProgressPath();
		computeCroppedTextPath();
	}

	protected abstract void computeCroppedProgressPath();

	protected abstract void computeCroppedTextPath();

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(mProgressBackgroundColor);
		canvas.drawPath(progressStrokePath, paint);

		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(mProgressStrokeColor);
		canvas.drawPath(progressStrokePath, paint);

		paint.setColor(mProgressColor);
		paint.setStyle(Paint.Style.FILL);

		if(mProgressDirection == LAYOUT_DIRECTION_LTR){
			canvas.drawPath(croppedProgressPath, paint);
			canvas.drawPath(croppedTextPath, paint);
		} else {
			canvas.drawPath(croppedProgressPathRTL, paint);
			canvas.drawPath(croppedTextPathRTL, paint);
		}
	}

	/**
	 * set progress bar color
	 * @param color
     */
	public void setProgressColor(int color) {
		this.mProgressColor = color;
		invalidate();
	}

	/**
	 * set progress background color
	 * @param color
     */
	public void setProgressBackgroundColor(int color) {
		this.mProgressBackgroundColor = color;
		invalidate();
	}

	/**
	 * set progress stroke (boarder) color
	 * @param color
     */
	public void setProgressStrokeColor(int color) {
		this.mProgressStrokeColor = color;
		invalidate();
	}

	/**
	 * set progress direction use: <br>
	 * {@link View#LAYOUT_DIRECTION_LTR} <br>{@link View#LAYOUT_DIRECTION_RTL}
	 * @param direction
     */
	public void setProgressDirection(int direction){
		mProgressDirection = direction;
		setProgress(getProgress());
	}

	/**
	 * set progress max
	 * @param progressMax
     */
	public void setProgressMax(float progressMax){
		mProgressMax = progressMax;
		invalidate();
	}

	public float getProgressMax() {
		return mProgressMax;
	}

	/**
	 * set font name as is in Assets, Font should be pre-loaded to Assets Folder.
	 * @param textFontNameInAssetsRes
	 */
	public void setProgressFont(int textFontNameInAssetsRes){
		setProgressFont(getContext().getString(textFontNameInAssetsRes));
	}
	/**
	 * set font name as is in Assets, Font should be pre-loaded to Assets Folder.
	 * @param textFontNameInAssets
	 */
	public void setProgressFont(String textFontNameInAssets){
		try {
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(),textFontNameInAssets);
			if(!TextUtils.isEmpty(textFontNameInAssets) && tf != null){
				paint.setTypeface(tf);
			}
			requestLayout();
			setProgress(getProgress());
		} catch (Exception e){
					/*do nothing, just ignore font*/
		}

	}

	private int dpToPx(int dp) {
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
}
