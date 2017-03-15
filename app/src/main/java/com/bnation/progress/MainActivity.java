package com.bnation.progress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

	SeekBar seekbar;
	BaseProgressView progressView;
	Button progressBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		seekbar = (SeekBar) findViewById(R.id.seekBar);
		progressView = (BaseProgressView) findViewById(R.id.fillShapeViewApi1);
		progressBtn = (Button) findViewById(R.id.progressBtn);

		progressBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*UIUtils.animateProgress(progressView ,0 ,seekbar.getProgress(), progressView.getProgressMax(), R.color.result_red);*/
				UIUtils.animateProgress(progressView, 0, seekbar.getProgress());
			}
		});

		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progressView.setProgress(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}
}
