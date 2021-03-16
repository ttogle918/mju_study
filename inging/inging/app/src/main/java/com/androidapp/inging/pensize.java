package com.androidapp.inging;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class pensize extends Activity {
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.pensize);
      
       SeekBar seekBar = (SeekBar)findViewById(R.id.seekbar);
       final TextView seekBarValue = (TextView)findViewById(R.id.seekbarvalue);
      
       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

   public void onProgressChanged(SeekBar seekBar, int progress,
     boolean fromUser) {
    // TODO Auto-generated method stub
    seekBarValue.setText(String.valueOf(progress));
   }

   public void onStartTrackingTouch(SeekBar seekBar) {
    // TODO Auto-generated method stub
   }

   public void onStopTrackingTouch(SeekBar seekBar) {
    // TODO Auto-generated method stub
   }
       });
   }
}