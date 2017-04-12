package com.bnation.progress;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * <p>
 *     Test Example, shows customizable parameters
 * </p>
 *
 * Created by ahmed.bah7ini on Mar 15, 2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

	private SeekBar mSeekBar;
	private BaseProgressView mProgressView;

	private Button mAnimateBtn;
	private Button mChangeProgressColorBtn;
	private Button mChangeProgressBGColorBtn;
	private Button mChangeProgressStrokeColorBtn;

	private Switch mChangeDirectionSwitch;
	private TextView mChangeDirectionLbl;

	private RadioGroup mFontRadioGroup;

	private void inflateViews(){
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		mProgressView = (BaseProgressView) findViewById(R.id.progressView);

		mAnimateBtn = (Button) findViewById(R.id.btn_animate);
		mChangeProgressColorBtn = (Button) findViewById(R.id.btn_changeProgressColor);
		mChangeProgressBGColorBtn = (Button) findViewById(R.id.btn_changeBgColor);
		mChangeProgressStrokeColorBtn = (Button) findViewById(R.id.btn_changeStrokeColor);

		mChangeDirectionSwitch = (Switch) findViewById(R.id.switch_changeDirection);
		mChangeDirectionLbl = (TextView) findViewById(R.id.tv_changeDirectionSwitchLabel);

		mFontRadioGroup = (RadioGroup) findViewById(R.id.font_group);
	}

	private void initListeners(){
		mAnimateBtn.setOnClickListener(this);
		mChangeProgressStrokeColorBtn.setOnClickListener(this);
		mChangeProgressBGColorBtn.setOnClickListener(this);
		mChangeProgressColorBtn.setOnClickListener(this);

		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mProgressView.setProgress(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		mFontRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
				switch (checkedId){
					case R.id.font_april:
						mProgressView.setProgressFont(R.string.font_april_flowers);
						break;

					case R.id.font_winter_calligraphy:
						mProgressView.setProgressFont(R.string.font_winter_calligraphy);
						break;

					case R.id.earth_2073:
						mProgressView.setProgressFont(R.string.font_earth_2073);
						break;
				}
			}
		});

		mChangeDirectionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mProgressView.setProgressDirection(View.LAYOUT_DIRECTION_LTR);
					mChangeDirectionLbl.setText(R.string.progressTitleLTR);
				} else {
					mProgressView.setProgressDirection(View.LAYOUT_DIRECTION_RTL);
					mChangeDirectionLbl.setText(R.string.progressTitleRTL);

				}
			}
		});
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inflateViews();
		initListeners();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_animate:
				UIUtils.animateProgress(mProgressView, 0, mSeekBar.getProgress());
				break;
			case R.id.btn_changeBgColor:
				mProgressView.setProgressBackgroundColor(UIUtils.getRandomColor());
				break;

			case R.id.btn_changeProgressColor:
				mProgressView.setProgressColor(UIUtils.getRandomColor());
				break;

			case R.id.btn_changeStrokeColor:
				mProgressView.setProgressStrokeColor(UIUtils.getRandomColor());
				break;
		}
	}
}
