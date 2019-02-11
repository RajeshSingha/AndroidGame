package com.sust.cse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button newGame, exit, musicToggle;
	SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		newGame = (Button) findViewById(R.id.newgameId);
		newGame.setOnClickListener(this);

		exit = (Button) findViewById(R.id.exitbtn);
		exit.setOnClickListener(this);
		
		musicToggle = (Button) findViewById(R.id.musicOnOff);
		musicToggle.setOnClickListener(this);
		
		sp = getSharedPreferences("com.sust.cse", Context.MODE_PRIVATE);
		
		if(!sp.getBoolean(Constants.music_pref, true)){
			musicToggle.setBackground(getResources().getDrawable(R.drawable.music_off));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.newgameId:
			Intent i = new Intent(this, GameActivity.class);
			i.putExtra("MUSIC_STATE", sp.getBoolean(Constants.music_pref, true));
			startActivity(i);
			break;

		case R.id.exitbtn:
			onExitButtonClick();
			break;
			
		case R.id.musicOnOff:
			boolean s = sp.getBoolean(Constants.music_pref, true);
			if(s){
				musicToggle.setBackground(getResources().getDrawable(R.drawable.music_off));
			} else {
				musicToggle.setBackground(getResources().getDrawable(R.drawable.music_on));
			}
			sp.edit().putBoolean(Constants.music_pref, !s).apply();
			break;

		default:
			break;
		}
	}

	private void onExitButtonClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Exit");
		builder.setMessage("Are You sure?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.create().show();

	}
}
