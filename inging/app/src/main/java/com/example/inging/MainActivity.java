package com.example.inging;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inging.colorpicker_test.OnColorSelectedListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.FROYO)
public class MainActivity extends Activity implements OnClickListener, OnTouchListener{
	int MX;
	int MY;
	private int lastX;
	private int lastY;
	private Dialog mDialog = null;
	public static String SERVER_IP = "116.33.27.85";
	public static int SERVER_PORT = 15460;
	static int color_receive;
	ImageButton sendButton;
	static Button btn_reLogin;
	EditText inputField;
	static TextView textView;
	String name,rename;
	static ScrollView sc;
	Socket socket;
	static DataInputStream input;
	static DataOutputStream output;
	private static final String TEMP_PHOTO_FILE = "temp.jpg";
	private int mBackgroundMode = 0;
	public static int Status = 0;
	public static final int PEN = 0;
    public static final int LINE = 1;
	public static final int RECT = 2;
    public static final int CIRCLE = 3;
    public static int PenStatus = 0;
    public static final int mode0 = 0;
    public static final int mode1 = 1;
    public static final int mode2 = 2;
    public static final int mode3 = 3;
    public static final int mode4 = 4;
    public static int PenStyle = 0;
	public static final int fill = 1;
    public static final int nomal = 0;
	public static final AttributeSet AttributeSet = null;
	public static final MotionEvent MotionEvent = null;
	static View view, view1;
	Canvas canvas1;
    public static int mo;
    static String clear = "clear_NO";
    static int cycle = 0;
    int color = Color.RED;
	float stroke = 10;
	int penstyle = 0;
	int repenstyle = 0;
	int old = 0;
    /*float xx;
	float yy;*/
	static int pcount = 0;
	static int count = 0;
	static int count1 = 0;
    int X;
    int Y;
    static PaintBoard_TX pb;
    static TextView tv_noLogin;
    static String received;
    static String[] buffer;
    static String[] xy;
    int sta = 0;
    static Path mPath;
    static Path mPath1;
    private int mX=0;
	private int mY=0;
	int preX;
	int preY;
	int toX;
	int toY;
	private int bold = 10;
	private int mNumber = 2;
	private static final int DIALOG_CUSTOM_LAYOUT = 0;
	private static final int REQ_CODE_PICK_IMAGE = 0;
	final int CROP_REQUEST = 2;
	final int REQUEST = 1;
	private SeekBar mSeekBarPenWidth;
	private TextView seekBarValue;
	ImageView back;
	private int baseWidth;
	private int baseHeight;
	Matrix matrix = new Matrix();
	Matrix saveMattrix = new Matrix();
	PointF start = new PointF();
	PointF mid = new PointF();
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	float oldDist = 1f;
	private static final String TAG = "Touch";
	
	//static ArrayList<String> alist;
	
	ImageButton  b_color, b_bold, b_clear, b_save, b_load, b_pen, exit;
	ImageButton s_nomal, s_neon, s_neon1, s_spray, s_spray1;
	ImageButton b_board;
    LinearLayout drop, drop_draw;
	private String mName;
    //RelativeLayout ll;
    //redraw rd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		//alist = new ArrayList<String>();
		drop = (LinearLayout)findViewById(R.id.layout_menu);

		drop_draw = (LinearLayout)findViewById(R.id.layout_style);
		s_nomal = (ImageButton)findViewById(R.id.pen_nomal);
		s_neon = (ImageButton)findViewById(R.id.pen_neon);
		s_neon1 = (ImageButton)findViewById(R.id.pen_neon1);
		s_spray = (ImageButton)findViewById(R.id.pen_spray);
		s_spray1 = (ImageButton)findViewById(R.id.pen_spray1);
		b_pen = (ImageButton)findViewById(R.id.menu_pen_select);
		b_color = (ImageButton)findViewById(R.id.menu_color);
		b_bold = (ImageButton)findViewById(R.id.menu_bold);
		b_load = (ImageButton)findViewById(R.id.menu_load);
		b_clear = (ImageButton)findViewById(R.id.menu_clear);
		b_board = (ImageButton)findViewById(R.id.menu_paintboard);
		b_save = (ImageButton)findViewById(R.id.menu_save);
		sendButton = (ImageButton) findViewById(R.id.sendButton);
		exit = (ImageButton) findViewById(R.id.exit);
		s_nomal.setOnClickListener(this);
		s_neon.setOnClickListener(this);
		s_neon1.setOnClickListener(this);
		s_spray.setOnClickListener(this);
		s_spray1.setOnClickListener(this);
		b_pen.setOnClickListener(this);
		b_color.setOnClickListener(this);
		b_bold.setOnClickListener(this);
		b_load.setOnClickListener(this);
		b_clear.setOnClickListener(this);
		b_board.setOnClickListener(this);
		b_save.setOnClickListener(this);
		sendButton.setOnClickListener(this);
		exit.setOnClickListener(this);

		inputField = (EditText) findViewById(R.id.inputField);
		inputField.requestFocus();
		textView = (TextView) findViewById(R.id.textView);
		view = (View)findViewById(R.id.draw_board);
		//view1 = (View)findViewById(R.id.draw_board1);
		view.setVisibility(View.INVISIBLE);
		sc = (ScrollView)findViewById(R.id.textScroller);
		tv_noLogin = (TextView)findViewById(R.id.text_first);

		
		pb = (PaintBoard_TX)findViewById(R.id.draw_board);
		mPath = new Path();
		ImageButton clear = (ImageButton)findViewById(R.id.claer);
		clear.setOnClickListener(this);
		
		baseWidth = pb.getScreenWidth();
        baseHeight = pb.getScreenHeight();
		back = (ImageView)findViewById(R.id.backimage);
        back.setOnTouchListener(this);
        pb.setBackImage(back);
		intro();

	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		ImageView view = (ImageView) v;
		dumpEvent(event);
		
		switch(event.getAction() & android.view.MotionEvent.ACTION_MASK){
		case android.view.MotionEvent.ACTION_DOWN:
			saveMattrix.set(matrix);
			start.set(event.getX(), event.getY());
			Log.d(TAG, "mode=DRAG");
			mode = DRAG;
			break;
		case android.view.MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
	         Log.d(TAG, "oldDist=" + oldDist);
	         if (oldDist > 10f) {
	            saveMattrix.set(matrix);
	            midPoint(mid, event);
	            mode = ZOOM;
	            Log.d(TAG, "mode=ZOOM");
	         }
	         break;
		case android.view.MotionEvent.ACTION_UP:
	    case android.view.MotionEvent.ACTION_POINTER_UP:
	         mode = NONE;
	         Log.d(TAG, "mode=NONE");
	         break;
	    case android.view.MotionEvent.ACTION_MOVE:
	         if (mode == DRAG) {
	        	 matrix.set(saveMattrix);
	        	 matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
	         }
	         else if (mode == ZOOM) {
	            float newDist = spacing(event);
	            Log.d(TAG, "newDist=" + newDist);
	            if (newDist > 10f) {
	            	matrix.set(saveMattrix);
	            	float scale = newDist / oldDist;
	            	matrix.postScale(scale, scale, mid.x, mid.y);
	            }
	         }
	         break;
		}
		view.setImageMatrix(matrix);
		return true;
	}


	private void dumpEvent(MotionEvent event) {
	      String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
	            "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
	      StringBuilder sb = new StringBuilder();
	      int action = event.getAction();
	      int actionCode = action & MotionEvent.ACTION_MASK;
	      sb.append("event ACTION_").append(names[actionCode]);
	      if (actionCode == MotionEvent.ACTION_POINTER_DOWN
	            || actionCode == MotionEvent.ACTION_POINTER_UP) {
	         sb.append("(pid ").append(
	               action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
	         sb.append(")");
	      }
	      sb.append("[");
	      for (int i = 0; i < event.getPointerCount(); i++) {
	         sb.append("#").append(i);
	         sb.append("(pid ").append(event.getPointerId(i));
	         sb.append(")=").append((int) event.getX(i));
	         sb.append(",").append((int) event.getY(i));
	         if (i + 1 < event.getPointerCount())
	            sb.append(";");
	      }
	      sb.append("]");
	      Log.d(TAG, sb.toString());
	   }

	   /** Determine the space between the first two fingers */
	   private float spacing(MotionEvent event) {
	      float x = event.getX(0) - event.getX(1);
	      float y = event.getY(0) - event.getY(1);
	      return FloatMath.sqrt(x * x + y * y);
	   }

	   /** Calculate the mid point of the first two fingers */
	   private void midPoint(PointF point, MotionEvent event) {
	      float x = event.getX(0) + event.getX(1);
	      float y = event.getY(0) + event.getY(1);
	      point.set(x / 2, y / 2);
	   }

	@Override
	public void onDestroy() {
		try {
			super.onDestroy();
			socket.close();
			input.close();
			output.close();
		} catch (IOException e) {
		}
	}
	 @Override
	    protected Dialog onCreateDialog(int id){
	    	switch(id){	    		
	    	case DIALOG_CUSTOM_LAYOUT:
	    		final CharSequence[] selectBold = getResources().getStringArray(R.array.bold);
	    		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    		final View viewInDialog = inflater.inflate(R.layout.pensize, null);
	    		AlertDialog.Builder penDialog = new AlertDialog.Builder(this);
	    		penDialog.setTitle("Select Bold");
	    		penDialog.setIcon(R.drawable.ic_launcher);
	    		penDialog.setSingleChoiceItems(selectBold, mNumber, new DialogInterface.OnClickListener() {	    			
	    			public void onClick(DialogInterface dialog, int item) {
	    				String bold_size = selectBold[item].toString();
	    				pb.setStroke(Integer.parseInt(bold_size));
	    				mNumber = item;
	    				bold = Integer.parseInt(bold_size);
	    				mSeekBarPenWidth.setProgress(Integer.parseInt(bold_size));
	    			}
	    		});
	    		
	    		penDialog.setView(viewInDialog);
	    		penDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						//b.setText("("+bold+")");
						dialog.dismiss();
					}
				});
	    		
	    		mSeekBarPenWidth = (SeekBar) viewInDialog.findViewById(R.id.seekbar);
	    		mSeekBarPenWidth.setProgress(bold);
	    		seekBarValue = (TextView)viewInDialog.findViewById(R.id.seekbarvalue);
	    		seekBarValue.setText("펜 굵기: " + String.valueOf(bold));
	    		mSeekBarPenWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					
					public void onStopTrackingTouch(SeekBar seekBar) { }
					
					public void onStartTrackingTouch(SeekBar seekBar) { }
					
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						seekBarValue.setText("펜 굵기: " + String.valueOf(progress));
						pb.setStroke(progress);
						bold = progress;
						//b.setText("("+bold+")");
					}
				});	    		
	    		AlertDialog bl_bold = penDialog.create();
	    		bl_bold.show();	    		
	    	}
	    	return null;
	    }
	 
	//save bitmap
		public void DrawBoardSave() {
			RelativeLayout r = (RelativeLayout)findViewById(R.id.drawimage1);
			RayoutToBitmap(r);
		}
		
		public void RayoutToBitmap(RelativeLayout rl) {
			rl.invalidate();
			rl.setDrawingCacheEnabled(true);
		
			Bitmap b = null;
			b = rl.getDrawingCache();
			mName = getCurrentTime() + ".jpeg";
			String dir = Environment.getExternalStorageDirectory()+"/DrawTest_image/";
			
	        File folder = new File(dir); 
	        
	        if(!folder.exists()) {	
	        	folder.mkdirs();          	
	        }
	        folder = null;
			File file = new File(dir, mName); 
			FileOutputStream outputStream = null;
			try {        
				outputStream = new FileOutputStream(file);
				b.compress(CompressFormat.JPEG, 100, outputStream);
			} 
			catch (FileNotFoundException e) {         
				e.printStackTrace();   
			}
			b = null;
			file = null;
			outputStream = null;
		}
		
		//save image in current time
	public String getCurrentTime() {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		Date dd = new Date(time);
		String strTime = sdf.format(dd);
		return strTime;
	}

		// image re-mediascan
		private void MediaScan(){
			String stringpath = Environment.getExternalStorageDirectory()+"/DrawTest_image/"+mName;
			Log.e("stringpath","stringpath = " + stringpath);
			
			ContentValues values = new ContentValues(2);
			values.put(Media.MIME_TYPE, "image/jpeg"); 
			values.put(MediaStore.Images.Media.DATA, stringpath);
			
			ContentResolver resolver = getContentResolver();
			Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

			Toast.makeText(MainActivity.this, "이미지 저장",Toast.LENGTH_SHORT).show();
		}
	//input background image
		private void LoadImage(){
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");              // 모든 이미지

			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
			//Intent intent = new Intent(Intent.ACTION_GET_CONTENT,      // ¶ЗґВ ACTION_PICK
	        //        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        //intent.setType("image/*");              // ёрµз АМ№МБц
	        //intent.putExtra("crop", "true");        // Crop±вґЙ И°јєИ­
	        //intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // АУЅГЖДАП »эјє
	        //intent.putExtra("outputFormat",         // ЖчёЛ№жЅД
	               // Bitmap.CompressFormat.JPEG.toString());

	        //startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
	        // REQ_CODE_PICK_IMAGE == requestCode);
			/*Intent intent = new Intent(
	                Intent.ACTION_GET_CONTENT,      // ¶ЗґВ ACTION_PICK
	                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        intent.setType("image/*");              // ёрµз АМ№МБц
	        intent.putExtra("crop", "true");        // Crop±вґЙ И°јєИ­
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // АУЅГЖДАП »эјє
	        intent.putExtra("outputFormat",         // ЖчёЛ№жЅД
	                Bitmap.CompressFormat.JPEG.toString());

	        startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
	        // REQ_CODE_PICK_IMAGE == requestCode
	    }
	));*/
			/*Intent intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, REQUEST);*/
		}
		private Uri getTempUri() {
	        return Uri.fromFile(getTempFile());
	    }

	    private File getTempFile() {
	        if (isSDCARDMOUNTED()) {
	            File f = new File(Environment.getExternalStorageDirectory(), // їЬАеёЮёрё® °ж·О
	                    TEMP_PHOTO_FILE);
	            try {
	                f.createNewFile();      // їЬАеёЮёрё®їЎ temp.jpg ЖДАП »эјє
	            } catch (IOException e) {
	            }
	 
	            return f;
	        } else
	            return null;
	    }

	    private boolean isSDCARDMOUNTED() {
	        String status = Environment.getExternalStorageState();
	        if (status.equals(Environment.MEDIA_MOUNTED))
	            return true;
	 
	        return false;
	    }
	    
	    @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			switch (requestCode) {
			case REQ_CODE_PICK_IMAGE:
	            if (resultCode == RESULT_OK) {
					try {
						Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
						ImageView image = (ImageView) findViewById(R.id.backimage);
						//배치해놓은 ImageView에 set
						image.setImageBitmap(image_bitmap);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mBackgroundMode = 1;
	                //if (data != null) {
	                    //String filePath = Environment.getExternalStorageDirectory()
	                            //+ "/temp.jpg";
	 
	                    //System.out.println("path" + filePath); // logCatАё·О °ж·ОИ®АО.
	                    //BitmapFactory.Options bfo = new BitmapFactory.Options();
	                    //bfo.inSampleSize = 2;
	                    //Bitmap selectedImage = BitmapFactory.decodeFile(filePath/*, bfo*/);
	                    //Bitmap resized = Bitmap.createScaledBitmap(selectedImage, baseWidth, baseHeight, true);
	                    // temp.jpgЖДАПА» BitmapАё·О µрДЪµщЗСґЩ.
	 
	                    //ImageView _image = (ImageView) findViewById(R.id.backimage);
	                    //_image.setImageBitmap(selectedImage);
	                    //_image.setImageBitmap(selectedImage);
	                    //mBackgroundMode = 1;
	                    // temp.jpgЖДАПА» АМ№МБцєдїЎ ѕєїоґЩ.
	                    
	                    /*BitmapFactory.Options bfo = new BitmapFactory.Options();
	                    bfo.inSampleSize = 2;
	                    ImageView iv = (ImageView)findViewById(R.id.imageView);
	                    Bitmap bm = BitmapFactory.decodeFile(imgPath, bfo);
	                    Bitmap resized = Bitmap.createScaledBitmap(bm, imgWidth, imgHeight, true);
	                    iv.setImageBitmap(resized);*/
	                //}
	            }
	            break;
			
			case REQUEST:
				if(data != null){
					final float scale = getResources().getDisplayMetrics().density;
					 Uri Data_uri = data.getData();
					 Intent cropIntent = new Intent("com.android.camera.action.CROP");
					 cropIntent.setDataAndType(Data_uri, "image/*");
					 cropIntent.putExtra("crop", "true");
					 cropIntent.putExtra("noFaceDetection", true);
					 cropIntent.putExtra("faceDetection", false);
					 //cropIntent.putExtra( "outputFormat", Bitmap.CompressFormat.JPEG.toString() );
					 cropIntent.putExtra("outputX", (int)baseWidth / scale);
					 cropIntent.putExtra("outputY", (int)baseHeight / scale);
					 /*cropIntent.putExtra("outputX", (int)(baseWidth / scale));
					 cropIntent.putExtra("outputY", (int)(baseHeight / scale));*/
					 cropIntent.putExtra("aspectX", 0);
					 cropIntent.putExtra("aspectY", 0);
					 cropIntent.putExtra("scale", true);
					 cropIntent.putExtra("return-data", true);
					 startActivityForResult(cropIntent, CROP_REQUEST);
				}
				


				break;
				
			case CROP_REQUEST:
				if(resultCode == RESULT_OK){
					Bitmap bitmap = (Bitmap)data.getParcelableExtra("data");
					pb.setBackBitmap(bitmap);
					RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams((int)pb.getScreenWidth(), (int)pb.getScreenHeight());
					//RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(300, 500 );
					param.addRule(RelativeLayout.CENTER_IN_PARENT);
					back = (ImageView)findViewById(R.id.backimage);
					
					back.setLayoutParams(param);
					back.setImageBitmap(bitmap);
					back.setVisibility(View.VISIBLE);
					back.setOnTouchListener(this);
					mBackgroundMode = 1;
				}
				break;
				
			default:
				break;
			}
		}

	public void intro() {
		print("잉Ing에 오신 걸 환영합니다!\n");
		mPath.reset();
	}

	public static void print(Object message) {
		Log.i("RECEIVE_SPLIT", (String)message);
		String a = (String)message;
		textView.append(""+ a + "\n");
		//tv_test.setText(pcount);
	}
	
	public static void clear(Object message) {
		if(message == "clear_OK"){

			int baseWidth = pb.getScreenWidth();
			int baseHeight = pb.getScreenHeight();
			pb.init(baseWidth, baseHeight);
			clear = "clear_NO";
		}
		//textView.append(message + "\n");
	}
	
	
	
	//Clear
	private void createDialog() {
		final View innerView = getLayoutInflater().inflate(R.layout.delete_dialog, null);
		
		mDialog = new Dialog(this);
		mDialog.setTitle("Title");
		mDialog.setContentView(innerView);
		mDialog.setCancelable(true);
		mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, 
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
		mDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				/*Toast.makeText(MainActivity.this, "cancle listener", 
						Toast.LENGTH_SHORT).show();*/
			}
		});
		mDialog.setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				/*Toast.makeText(MainActivity.this, "show listener", 
						Toast.LENGTH_SHORT).show();*/
			}
		});
		mDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				/*Toast.makeText(MainActivity.this, "dismiss listener", 
						Toast.LENGTH_SHORT).show();*/
			}
		});
		
		mDialog.show();
	
	}
	private void dismissDialog() {
		if(mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}
		

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.menu_save:
			DrawBoardSave();
			MediaScan();
			break;
		case R.id.menu_paintboard:
			if(pb.getVisibility() == View.VISIBLE){
				b_board.setImageDrawable(getResources().getDrawable(R.drawable.locked));
				pb.setVisibility(View.INVISIBLE);
			}
			else{
				b_board.setImageDrawable(getResources().getDrawable(R.drawable.unlocked));
				pb.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.btn_clean:
			Toast.makeText(getApplicationContext(), "1", 1).show();
			pb.setClearMode();
			dismissDialog();
			break;
			
		case R.id.btn_clear:
			Toast.makeText(getApplicationContext(), "2", 1).show();
			pb.init(baseWidth, baseHeight);
			dismissDialog();
			break;

		case R.id.menu_clear:
			createDialog();
			break;

		case R.id.menu_pen_select:
			drop.setVisibility(View.INVISIBLE);
			drop_draw.setVisibility(View.VISIBLE);
			break;

		case R.id.pen_nomal:
			PenStatus = mode0;
			drop.setVisibility(View.VISIBLE);
			drop_draw.setVisibility(View.INVISIBLE);
			break;

		case R.id.pen_neon:
			PenStatus = mode1;
			drop.setVisibility(View.VISIBLE);
			drop_draw.setVisibility(View.INVISIBLE);
			break;

		case R.id.pen_neon1:
			PenStatus = mode2;
			drop.setVisibility(View.VISIBLE);
			drop_draw.setVisibility(View.INVISIBLE);
			break;

		case R.id.pen_spray:
			PenStatus = mode3;
			drop.setVisibility(View.VISIBLE);
			drop_draw.setVisibility(View.INVISIBLE);
			break;

		case R.id.pen_spray1:
			PenStatus = mode4;
			drop.setVisibility(View.VISIBLE);
			drop_draw.setVisibility(View.INVISIBLE);
			break;

		case R.id.menu_color:
			colorpicker();
			break;

		case R.id.menu_bold:
			showDialog(DIALOG_CUSTOM_LAYOUT);
			break;

		case R.id.claer:
			clear = "clear_OK";
			try {
				//output.writeInt(12);
				output.writeUTF("a`" + clear
				);
				output.flush();
			} catch (IOException e) {
				view.setVisibility(View.INVISIBLE);
				tv_noLogin.setVisibility(View.VISIBLE);
				print("그림지우기 실패");
			}
			break;
		case R.id.menu_load:

			if(mBackgroundMode == 0) {
				LoadImage();
			}
			else if(mBackgroundMode == 1) {
				DeleteImage();
				mBackgroundMode = 0;
			}
			break;

		case R.id.sendButton:
			if (inputField.getText().toString() == "")
				return;
			if (name == null) {
				String text = inputField.getText().toString();
				inputField.setText("");
				inputField.setHint("");
				name = text;
				connect();
			} else {
				String text = inputField.getText().toString();
				inputField.setText("");
				sc.fullScroll( ScrollView.FOCUS_DOWN );
				try {
					output.writeUTF("c`" + text);
					output.flush();
				} catch (IOException e) {
					view.setVisibility(View.INVISIBLE);
					tv_noLogin.setVisibility(View.VISIBLE);
					print("전송실패.");
				}
			}
			break;
			case R.id.exit:
				String alertTitle = "EXIT";
				String buttonMessage = "종료하시겠습니까?";
				String buttonYes = "Yes";
				String buttonNo = "No";

				new AlertDialog.Builder(this)
						.setTitle(alertTitle)
						.setMessage(buttonMessage)
						.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								moveTaskToBack(true);
								finish();
							}
						})
						.setNegativeButton(buttonNo, null)
						.show();
				break;
		}
	}
	

	//delete background image
	private void DeleteImage(){
		BitmapDrawable stamp_drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.whitebag);
		Bitmap temp = stamp_drawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
		ImageView back = (ImageView)findViewById(R.id.backimage);
        back.setImageBitmap(null);
		back.setBackgroundColor(0xffffffff);
	}

	public void colorpicker() {
		colorpicker_test cpd = new colorpicker_test(this, 0xFF00FF00, new OnColorSelectedListener() {
			
			public void colorSelected(Integer color) {
				// TODO Auto-generated method stub
				colorChanged(color);
				color_receive = color;
			}
		});
		cpd.setTitle( "Pick a color" );
		cpd.setNoColorButton( R.string.no_color );
		cpd.show();
    }

	public void colorChanged(int color) {
		// TODO Auto-generated method stub
		PaintBoard_TX.mPaint.setColor(color);
		PaintBoard_TX.input_color = PaintBoard_TX.mPaint.getColor();
		pb.setColor(PaintBoard_TX.input_color);
		//AmbilWarnaDialog.OnAmbilWarnaListener(mColor);
	}

	public static int mode(int datamx, int datamy, int datax, int datay, String sta, int color, float stroke, int penstyle, int pcount){

    	String dd = String.valueOf(pcount);

    	try {
			//output.writeInt(12);
    		count++;
			output.writeUTF("d`" + 
					datamx + "," +
					datamy + "," +
					datax + "," + 
					datay+","+
					sta+","+
					color+"," +
					stroke+"," +
					penstyle
					//PenStatus+","+
					//filter
			);
			output.flush();
		} catch (IOException e) {
			view.setVisibility(View.INVISIBLE);
			tv_noLogin.setVisibility(View.VISIBLE);
			print("로그인실패.");
		}
    	delayedTime(40);
    	//intro();
    	return 0;
    }

	public static void delayedTime(int delayTime){
			long saveTime = System.currentTimeMillis();
			long currTime = 0;
			while(currTime - saveTime < delayTime){
				currTime = System.currentTimeMillis();
			}
		}

	public void connect() {
		try {
			//print(SERVER_IP + ":" + "연결하는중...");
			socket = new Socket(SERVER_IP, SERVER_PORT);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

			while (socket != null) {
				if (socket.isConnected()) {
					output.writeUTF("r`1`1`" + name + "`");
					output.flush();
					view.setVisibility(View.VISIBLE);
					tv_noLogin.setVisibility(View.INVISIBLE);
					break;
				}
			}
			MessageReciver messageReceiver = new MessageReciver();
			messageReceiver.start();
		} catch (Exception e) {
			print("연결실패.");
			this.finish();
		}
	}

	public String chatMessage;
	public String drawMessage;
	Message message;
	int a;

	public class MessageReciver extends Thread {
		
		public void run() {
			try {
				while ((received = input.readUTF()) != null) {
					Log.i("RECEIVE", received);
					buffer = received.split("`");
					switch (buffer[0].charAt(0)) {
					case 'd':
						
						
						count1++;
						String[] bf = buffer[2].split(",");
						MX = Integer.parseInt(bf[0]);
						MY = Integer.parseInt(bf[1]);
						X = Integer.parseInt(bf[2]);
						Y = Integer.parseInt(bf[3]);
						a = Integer.parseInt(bf[4]);
						color = Integer.parseInt(bf[5]);
						stroke = Float.parseFloat(bf[6]);
						penstyle = Integer.parseInt(bf[7]);
						
						//alist.add(X+","+Y);
						
						rename = buffer[1];
						chatMessage = buffer[1] + ": " + 
						"mx좌표: "+ MX +
						"my좌표: "+ MY +
						"x좌표: "+ X +
						"y좌표: "+ Y +
						"상태: " + a +
						"색: " + color +
						"굵기: " + stroke +
						"스타일: " + penstyle;
						message = handler1.obtainMessage(1, received);
						handler1.sendMessage(message);	
						
						break;
					case 'n':
						chatMessage = "--" + buffer[1] + "--";
						message = handler.obtainMessage(1, received);

						handler.sendMessage(message);
						/*sc.fullScroll( ScrollView.FOCUS_DOWN );*/

						break;
					case 'c':
						//chatMessage = buffer[1] + ": " + bf[0] + ",,," + bf[1];
						chatMessage = buffer[1] + ": " + buffer[2];
						//clear = Integer.parseInt(buffer[2]);
						message = handler.obtainMessage(1, received);
						handler.sendMessage(message);
						
						/*sc.fullScroll( ScrollView.FOCUS_DOWN );*/
						break;
					case 'a':
						rename = buffer[1];
						clear = buffer[2];
						//chatMessage = buffer[1] + ": " + clear;
						
						message = handler2.obtainMessage(1, received);
						handler2.sendMessage(message);

						break;

					
					case 'x':
						chatMessage = "--" +  buffer[1] + "--";
						message = handler.obtainMessage(1, received);

						handler.sendMessage(message);
						/*sc.fullScroll( ScrollView.FOCUS_DOWN );*/
						break;
					}

					/*Message message = handler.obtainMessage(1, received);

					handler.sendMessage(message);
					sc.fullScroll( ScrollView.FOCUS_DOWN );*/
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	

	Handler handler = new Handler() {

		public void handleMessage(Message message) {
			super.handleMessage(message);
			print(chatMessage);

			sc.fullScroll( sc.FOCUS_DOWN );
			sc.fullScroll( ScrollView.FOCUS_DOWN );

		}
	};

	
	Handler handler2 = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			String cclear = rename +" 님이 그림을 지웠습니다.";
			if(clear.equals("clear_OK")){
				count = 0;
				count1 = 0;
				pcount = 0;

				//PaintBoard.pcount = 0;
				//Toast.makeText(getApplicationContext(), "", 1).show();
				mPath.reset();
				pb = (PaintBoard_TX)findViewById(R.id.draw_board);
				int baseWidth = pb.getScreenWidth();
		        int baseHeight = pb.getScreenHeight();
				pb.init(baseWidth, baseHeight);
				clear = "clear_NO";
				print(cclear);
				
			}
			sc.fullScroll( sc.FOCUS_DOWN );
			sc.fullScroll( ScrollView.FOCUS_DOWN );

		}
	};	
	
	Handler handler1 = new Handler() {

		public void handleMessage(Message message) {
			super.handleMessage(message);
			if(rename.equals(name)){}
			else {
				PaintBoard_TX.mPaint1.setAntiAlias(true);
				PaintBoard_TX.mPaint1.setDither(true);
				PaintBoard_TX.mPaint1.setStrokeJoin(Paint.Join.ROUND);
				PaintBoard_TX.mPaint1.setStrokeCap(Paint.Cap.ROUND);
				PaintBoard_TX.mPaint1.setStyle(Paint.Style.STROKE);
				PaintBoard_TX.mPaint1.setStrokeWidth(stroke);
				PaintBoard_TX.mPaint1.setColor(color);
				if(penstyle != repenstyle){
					switch(penstyle){
					case 0:
						PaintBoard_TX.mPaint1.setMaskFilter(PaintBoard_TX.filter0);
						break;
					case 1:
						PaintBoard_TX.mPaint1.setMaskFilter(PaintBoard_TX.filter1);
						break;
					case 2:
						PaintBoard_TX.mPaint1.setMaskFilter(PaintBoard_TX.filter2);
						break;
					case 3:
						PaintBoard_TX.mPaint1.setMaskFilter(PaintBoard_TX.filter3);
						break;
					case 4:
						PaintBoard_TX.mPaint1.setMaskFilter(PaintBoard_TX.filter4);
						break;
					}
					repenstyle = penstyle;
				}
				
				//if(a==0 && cycle == 0){
				if(a==0){
					touch_start(X, Y);
					view.invalidate();
					cycle = 1;
				}
				//else if(a.equals("1") && cycle == 1){
				//else if(a==1 && cycle == 1){
				else if(a==1){
					touch_move(X, Y);
					view.invalidate();
				}
				//else if(a.equals("2")){
				else if(a==2 || a==3){
					touch_up(X, Y);
					view.invalidate();
					cycle = 0;
				}

				lastX = X;
				lastY = Y;

			}

		}
	};
	
	public void touch_start(int x, int y){	
		mPath.moveTo(x, y);
			mX = x;
			mY = y;
			preX = x;
			preY = y;
	}
	
	public void touch_move(int x, int y){
			mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
			mX = x;
			mY = y;
			if(PaintBoard_TX.mCanvas != null)
				PaintBoard_TX.mCanvas.drawPath(mPath, PaintBoard_TX.mPaint1);			
	}
	
	public void touch_up(int x, int y){		
		if(preX == mX && preY ==mY){
			if(PaintBoard_TX.mCanvas != null ){
				PaintBoard_TX.mCanvas.drawPoint(preX, preY, PaintBoard_TX.mPaint1);
			}
		}
		else{
			//mPath.lineTo(mX, mY);
			if(PaintBoard_TX.mCanvas != null)
				PaintBoard_TX.mCanvas.drawPath(mPath, PaintBoard_TX.mPaint1);
		}
		mPath.reset();
	}
}

