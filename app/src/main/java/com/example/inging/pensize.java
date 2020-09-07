/*package com.example.ex3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class pensize extends Dialog {
	private OnDismissListener listener;
	static int pensize;
	
	public pensize(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
   *//** Called when the activity is first created. *//*
	@Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.pensize);
    	setTitle("PenSize");
      
    	SeekBar seekBar = (SeekBar)findViewById(R.id.seekbar);
    	final TextView seekBarValue = (TextView)findViewById(R.id.seekbarvalue);
    	Button btn_ok = (Button)findViewById(R.id.btn_ok);
      
    	seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
    		@Override
    		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    			// TODO Auto-generated method stub
    			seekBarValue.setText("�� ũ�� : " + progress);
    			pensize = progress;
    			//seekBarValue.setText(String.valueOf(progress));
    		}
    		@Override
    		public void onStartTrackingTouch(SeekBar seekBar) {
    			// TODO Auto-generated method stub
    		}
    		@Override
    		public void onStopTrackingTouch(SeekBar seekBar) {
    			// TODO Auto-generated method stub
    		}
    	});
    	btn_ok.setOnClickListener(new View.OnClickListener() {
		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			if(listener == null){}
    			else {
    				listener.onDismiss(pensize.this);
    			}
    			dismiss();
    		}
    	});
	}
	public void setOnDismissListener( OnDismissListener $Listener){
		listener = $Listener;
	}
	public int getsize(){
		return pensize;
	}
}*/


package com.example.inging;

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