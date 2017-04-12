package com.bnation.progress;

import android.content.Context;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

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
public class ProgressView extends BaseProgressView {

	private final Region textRegion = new Region();
	private final Region progressRegion = new Region();
	private final Region region = new Region();

	private final Region textRegionRTL = new Region();
	private final Region progressRegionRTL = new Region();
	private final Region regionRTL = new Region();

	public ProgressView(Context context) {
		this(context, null);
	}
	public ProgressView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void computeCroppedProgressPath() {
		if(mProgressDirection == LAYOUT_DIRECTION_LTR){
			region.set(0, 0, getProgressPos(), height);
			progressRegion.setPath(progressStrokePath, region); // INTERSECT
			textRegion.setPath(textPath, region);
			progressRegion.op(textRegion, Region.Op.DIFFERENCE); // DIFFERENCE
			croppedProgressPath.rewind();
			progressRegion.getBoundaryPath(croppedProgressPath);
		} else {
			regionRTL.set(width - getProgressPos(), 0, width , height);
			progressRegionRTL.setPath(progressStrokePath, regionRTL); // INTERSECT
			textRegionRTL.setPath(textPathRTL, regionRTL);
			progressRegionRTL.op(textRegionRTL, Region.Op.DIFFERENCE); // DIFFERENCE
			croppedProgressPathRTL.rewind();
			progressRegionRTL.getBoundaryPath(croppedProgressPathRTL);
		}
	}

	@Override
	public void computeCroppedTextPath() {
		if(mProgressDirection == LAYOUT_DIRECTION_LTR){
			region.set(getProgressPos(), 0, width, height);
			textRegion.setPath(textPath, region); // INTERSECT
			croppedTextPath.rewind();
			textRegion.getBoundaryPath(croppedTextPath);
		} else {
			regionRTL.set(0, 0, width - getProgressPos(), height);
			textRegionRTL.setPath(textPathRTL, regionRTL); // INTERSECT
			croppedTextPathRTL.rewind();
			textRegionRTL.getBoundaryPath(croppedTextPathRTL);
		}
	}
}