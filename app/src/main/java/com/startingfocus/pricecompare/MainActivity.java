package com.startingfocus.pricecompare;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button mButtonCompare;
	private TextView mQuantity1;
	private TextView mQuantity2;
	private TextView mTotalPrice1;
	private TextView mTotalPrice2;
	
	private long waitTime = 2000;
	private long touchTime = 0;
	
	private SoundPool snd;
	private AudioManager am;
	private int soundcalc;
	private int sounderror;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mButtonCompare = (Button)findViewById(R.id.CalcButton);
		mQuantity1 = (TextView)findViewById(R.id.Quantity1);
		mQuantity2 = (TextView)findViewById(R.id.Quantity2);
		mTotalPrice1 = (TextView)findViewById(R.id.TotalPrice1);
		mTotalPrice2 = (TextView)findViewById(R.id.TotalPrice2);
		
		//mQuantity1.setText("123.00");
		//mQuantity2.setText("100.00");
		//mTotalPrice1.setText("9.99");
		//mTotalPrice2.setText("5.99");//
		
   		am = (AudioManager)this.getSystemService(MainActivity.AUDIO_SERVICE);
        snd = new SoundPool(3,AudioManager.STREAM_SYSTEM,5);
        soundcalc = snd.load(this,R.raw.s_calc,0);
        sounderror = snd.load(this,R.raw.s_error,0);
		
		mButtonCompare.setOnClickListener(new Button.OnClickListener(){
           	public void onClick(View v)
        	{
           		float Quantity1=0;
           		float Quantity2=0;
           		float TotalPrice1=0;
           		float TotalPrice2=0;
           		float UnitPrice1=0;
           		float UnitPrice2=0;
           		String Result="";
           		
           	    float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
           	    float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
           	    float volumnRatio = audioCurrentVolumn/audioMaxVolumn;
           		
           		if (mQuantity1.getText().toString().length() > 0) {
           			Quantity1 = Float.parseFloat(mQuantity1.getText().toString());
           		}
           		
           		if (mQuantity2.getText().toString().length() > 0) {
           			Quantity2 = Float.parseFloat(mQuantity2.getText().toString());
           		}
           		
           		if (mTotalPrice1.getText().toString().length() > 0) {
               		TotalPrice1 = Float.parseFloat(mTotalPrice1.getText().toString());
           		}

           		if (mTotalPrice2.getText().toString().length() > 0) {
           			TotalPrice2 = Float.parseFloat(mTotalPrice2.getText().toString());
           		}
           		
           		if (Quantity1 == 0 || Quantity2 == 0) {
           			Result = "Quantity cannot be zero/empty, please modify it.";
               		snd.play(sounderror,volumnRatio,volumnRatio,0,0,1);
           		}
           		else {
           			UnitPrice1 = TotalPrice1/Quantity1;
           			UnitPrice2 = TotalPrice2/Quantity2;
           			
           			if (UnitPrice1 < UnitPrice2) {
           				Result = "Goods1 is cheaper than Goods2.";
           			}
           			else if (UnitPrice1 > UnitPrice2) {
           				Result = "Goods2 is cheaper than Goods1.";
           			}
           			else {
           				Result = "Goods1 is the same with Goods2.";
           			}
           			
           			snd.play(soundcalc,volumnRatio,volumnRatio,0,0,1);
           		}           		

           		ShowMsgBox(Result);
        	}        	

        });
		
	}
	
	public void ShowMsgBox(String msg)
	{
		new AlertDialog.Builder(this)
		.setTitle("Result")
		.setMessage(msg)
		.setPositiveButton("OK",null)
		.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		if (item.getItemId() == R.id.action_about ){
			
			new AlertDialog.Builder(this)
			.setTitle("ABOUT")
			.setMessage("Price Compare V1.00\n" +
					"Copyright (c) 2015\n\n" +
					"rdryan@sina.com\n")
			.setPositiveButton("OK",null)
			.show();
		}
		else if (item.getItemId() == R.id.action_exit){
			super.onBackPressed();
		}
	
		return true;
	}	

	@Override  
	public void onBackPressed() {  
	    long currentTime = System.currentTimeMillis();  
	    if((currentTime-touchTime) >= waitTime) {  
	        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();  
	        touchTime = currentTime;  
	    }else {  
	        super.onBackPressed();  
	    }  
	}  

}
