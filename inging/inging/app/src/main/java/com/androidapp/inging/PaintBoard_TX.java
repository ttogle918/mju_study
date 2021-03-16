package com.androidapp.inging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class PaintBoard_TX extends View {


    static Bitmap mBitmap = null;
    private Bitmap mBackBitmap = null;
    static Canvas mCanvas = null;
    //private Canvas mCanvas = null;
    static Path mPath;
    static Paint mBitmapPaint;

    static Paint mPaint;
    static Paint mPaint1;

    static int mMode = 0;  // 0 : draw, 1 : clear
    static int mMode1 = 0;

    private int mPenMode = 0;
    private int mPenMode1 = 0;

    static int input_color = Color.RED;
    static int out_color = Color.RED;

    private static float stroke = 10;
    private static float stroke1 = 10;

    private int baseWidth;
    private int baseHeight;

    private ImageView backImage = null;
    MotionEvent event;

//    static EmbossMaskFilter filter0 = new EmbossMaskFilter(new float[]{0, 0, 0}, 0.5f, 0, 0);
//    static EmbossMaskFilter filter1 = new EmbossMaskFilter(new float[]{2, 2, 2}, 0.1f, 1, 10); // neon
//    static EmbossMaskFilter filter2 = new EmbossMaskFilter(new float[]{2, 2, 10}, 0.5f, 1, 10); // neon
    static BlurMaskFilter filter0 = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID); // spray
    static BlurMaskFilter filter1 = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID); // spray
    static BlurMaskFilter filter2 = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID); // spray

    static BlurMaskFilter filter3 = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID); // spray
    static BlurMaskFilter filter4 = new BlurMaskFilter(10, BlurMaskFilter.Blur.OUTER); // out_spray
    TextView t;

    static int toX;
    static int toY;

    Path path = new Path();

    public PaintBoard_TX(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        baseWidth = display.getWidth();
        baseHeight = display.getHeight();

        mBitmap = Bitmap.createBitmap(baseWidth, baseHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        //paint setting
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(input_color);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(stroke);

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setDither(true);
        mPaint1.setColor(input_color);
        mPaint1.setStrokeJoin(Paint.Join.ROUND);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);
        mPaint1.setStrokeWidth(stroke);
        //mPaint.setStyle(Paint.Style.STROKE);


    }

    public void input() {
        //if(Launcher.)
    }

    public void init(int x, int y) {
        mBitmap.recycle();
        mBitmap = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        invalidate();
    }

    public int getScreenWidth() {
        return baseWidth;
    }

    public int getScreenHeight() {
        return baseHeight;
    }

    //paint color
    public void setColor(int color) {
        mMode = 0;
        mPaint.setXfermode(null);
        mPaint.setStrokeWidth(stroke);
        mPaint.setColor(color);
        input_color = color;
    }

    //paint strong
    public void setStroke(float input_stroke) {
        stroke = input_stroke;
        mPaint.setStrokeWidth(stroke);
        mPaint.setColor(input_color);
    }

    public int getStroke() {
        return (int) stroke;
    }

    //bitmap background image
    public void setBackImage(ImageView IV) {
        backImage = IV;
    }

    //background bitmap
    public void setBackBitmap(Bitmap back) {
        if (mBackBitmap != null) {
            mBackBitmap.recycle();
            mBackBitmap = null;
        }
        mBackBitmap = back;
    }

    public void setreturnMode() {
        mMode = 0;
    }

    //Clear paint
    public void setClearMode() {
        mMode = 1;
        mPaint.setStrokeWidth(stroke);
        //mPaint1 = mPaint;
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void StartClear() {
        mPaint.setStrokeWidth(stroke + 20);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void EndClear() {
        if (mMode == 0) {
            mPaint.setXfermode(null);
            mPaint.setStrokeWidth(stroke);
            //mPaint.setColor(0xffff00ff);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0x00000000);
        if (mBitmap != null)
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);
    }

    static int mX;
    static int mY;
    private static int preX;
    private static int preY;
    private static final float TOUCH_TOLERANCE = 4;


    long downTime = SystemClock.uptimeMillis();
    long eventTime = SystemClock.uptimeMillis();
    MotionEvent down_event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, 0, 0, 0);
    MotionEvent move_event = MotionEvent.obtain(downTime, eventTime + 1000, MotionEvent.ACTION_MOVE, 0, 0, 0);
    MotionEvent up_event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP, 0, 0, 0);


    public static void touch_start(int x, int y) {
        //Launcher.tv_ondraw.setText("Start");
        //Launcher.cycle = 0;
        com.androidapp.inging.MainActivity.mode(x, y, x, y, "0", input_color, stroke, com.androidapp.inging.MainActivity.PenStatus, com.androidapp.inging.MainActivity.pcount);
        com.androidapp.inging.MainActivity.pcount++;
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        preX = x;
        preY = y;


        //Toast.makeText(getContext(), "touch_start", 1).show();
        //MainActivity.delayedTime(10);
    }

    public static void touch_move(int x, int y) {

        com.androidapp.inging.MainActivity.pcount++;

        if (mMode == 0 || mMode == 1) {
            com.androidapp.inging.MainActivity.mode(mX, mY, x, y, "1", input_color, stroke, com.androidapp.inging.MainActivity.PenStatus, com.androidapp.inging.MainActivity.pcount);
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
            if (mMode == 1) {
                mPath.lineTo(mX, mY);
                if (mCanvas != null)
                    mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
            }
            //Toast.makeText(getContext(), mX+" "+mY, 1).show();

        }
    }

    public static void touch_up() {
        com.androidapp.inging.MainActivity.pcount++;

        if (preX == mX && preY == mY) {
            com.androidapp.inging.MainActivity.mode(mX, mY, mX, mY, "3", input_color, stroke, com.androidapp.inging.MainActivity.PenStatus, com.androidapp.inging.MainActivity.pcount);
            if (mCanvas != null) {
                mCanvas.drawPoint(preX, preY, mPaint);
            }
        } else {
            com.androidapp.inging.MainActivity.mode(mX, mY, mX, mY, "2", input_color, stroke, com.androidapp.inging.MainActivity.PenStatus, com.androidapp.inging.MainActivity.pcount);
            mPath.lineTo(mX, mY);
            if (mCanvas != null)
                mCanvas.drawPath(mPath, mPaint);
        }
        mPath.reset();
    }

    static void touch_up_LINE() {
        if (toX == mX && toY == mY) {
            if (mCanvas != null) {
                mCanvas.drawPoint(toX, toY, mPaint);
            }
        } else {
            mPath.lineTo(toX, toY);
            if (mCanvas != null)
                mCanvas.drawPath(mPath, mPaint);
        }
        mPath.reset();
    }

    static void touch_up_RECT() {
        if (toX == mX && toY == mY) {
            if (mCanvas != null) {
                mCanvas.drawPoint(toX, toY, mPaint);
            }
        } else {
            mPath.addRect(mX, mY, toX, toY, Direction.CW);
            if (mCanvas != null)
                mCanvas.drawPath(mPath, mPaint);
        }
        mPath.reset();
    }

    static void touch_up_CIRCLE() {
        if (toX == mX && toY == mY) {
            if (mCanvas != null) {
                mCanvas.drawPoint(toX, toY, mPaint);
            }
        } else {
            mPath.addCircle(mX, mY,
                    (float) Math.sqrt((Math.pow(Math.abs(toX - mX), 2) + Math.pow(Math.abs(toY - mY), 2)))
                    , Direction.CW);
            if (mCanvas != null)
                mCanvas.drawPath(mPath, mPaint);
        }
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                switch (com.androidapp.inging.MainActivity.Status) {
                    case com.androidapp.inging.MainActivity.PEN:
                        // action_down - pen
                        if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode0) {
                            //if(mPaint1 != null) mPaint = mPaint1;
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter0);
                            //Toast.makeText(getContext(), "mode0", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode1) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter1);
                            //Toast.makeText(getContext(), "mode1", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode2) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter2);
                            //Toast.makeText(getContext(), "mode2", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode3) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter3);
                            //Toast.makeText(getContext(), "mode3", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode4) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter4);
                            //Toast.makeText(getContext(), "mode4", 1).show();
                            invalidate();
                        }
            	        /*touch_start(x, y);
            	        invalidate();*/
                        break;
                    case com.androidapp.inging.MainActivity.LINE:
                        // action_down - line
                        if (com.androidapp.inging.MainActivity.mode0 == com.androidapp.inging.MainActivity.PenStatus) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter0);
                            //Toast.makeText(getContext(), "mode0", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.mode1 == com.androidapp.inging.MainActivity.PenStatus) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter1);
                            //Toast.makeText(getContext(), "mode1", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.mode2 == com.androidapp.inging.MainActivity.PenStatus) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter2);
                            //Toast.makeText(getContext(), "mode2", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.mode3 == com.androidapp.inging.MainActivity.PenStatus) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter3);
                            //Toast.makeText(getContext(), "mode3", 1).show();
                            invalidate();
                        } else if (com.androidapp.inging.MainActivity.mode4 == com.androidapp.inging.MainActivity.PenStatus) {
                            mPaint.setStyle(Paint.Style.STROKE);
                            touch_start(x, y);
                            mPaint.setMaskFilter(filter4);
                            //Toast.makeText(getContext(), "mode4", 1).show();
                            invalidate();
                        }

            	         /*touch_start(x, y);
            	         invalidate();*/
                        break;

                    case com.androidapp.inging.MainActivity.RECT:
                        // action_down - rect
                        switch (com.androidapp.inging.MainActivity.PenStatus) {
                            case com.androidapp.inging.MainActivity.mode0:

                                if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.nomal) {
                                    mPaint.setStyle(Paint.Style.STROKE);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter0);
                                    //Toast.makeText(getContext(), "mode0_n", 1).show();
                                    invalidate();
                                } else if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.fill) {
                                    mPaint.setStyle(Paint.Style.FILL);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter0);
                                    //Toast.makeText(getContext(), "mode0_f", 1).show();
                                    invalidate();
                                }
                                    /*touch_start(x, y);
                                    mPaint.setMaskFilter(filter0);
                                    Toast.makeText(getContext(), "mode0", 1).show();
                                    invalidate();*/
                                break;
                            case com.androidapp.inging.MainActivity.mode1:
                                if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.nomal) {
                                    mPaint.setStyle(Paint.Style.STROKE);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter1);
                                    //Toast.makeText(getContext(), "mode1_n", 1).show();
                                    invalidate();
                                } else if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.fill) {
                                    mPaint.setStyle(Paint.Style.FILL);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter1);
                                    //Toast.makeText(getContext(), "mode1_f", 1).show();
                                    invalidate();

                                }
                                /*touch_start(x, y);
                                mPaint.setMaskFilter(filter1);
                                Toast.makeText(getContext(), "mode1", 1).show();
                                invalidate();*/
                                break;
                            case com.androidapp.inging.MainActivity.mode2:
                                if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.nomal) {
                                    mPaint.setStyle(Paint.Style.STROKE);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter2);
                                    //Toast.makeText(getContext(), "mode2_n", 1).show();
                                    invalidate();
                                } else if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.fill) {
                                    mPaint.setStyle(Paint.Style.FILL);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter2);
                                    //Toast.makeText(getContext(), "mode2_f", 1).show();
                                    invalidate();
                                }
                                /*touch_start(x, y);
                                mPaint.setMaskFilter(filter2);
                                Toast.makeText(getContext(), "mode2", 1).show();
                                invalidate();*/
                                break;
                            case com.androidapp.inging.MainActivity.mode3:
                                if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.nomal) {
                                    mPaint.setStyle(Paint.Style.STROKE);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter3);
                                    //Toast.makeText(getContext(), "mode3_n", 1).show();
                                    invalidate();
                                } else if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.fill) {
                                    mPaint.setStyle(Paint.Style.FILL);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter3);
                                    //Toast.makeText(getContext(), "mode3_f", 1).show();
                                    invalidate();
                                }
                                /*touch_start(x, y);
                                mPaint.setMaskFilter(filter3);
                                Toast.makeText(getContext(), "mode3", 1).show();
                                invalidate();*/
                                break;
                            case com.androidapp.inging.MainActivity.mode4:
                                if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.nomal) {
                                    mPaint.setStyle(Paint.Style.STROKE);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter4);
                                    //Toast.makeText(getContext(), "mode4_n", 1).show();
                                    invalidate();
                                } else if (com.androidapp.inging.MainActivity.PenStyle == com.androidapp.inging.MainActivity.fill) {
                                    mPaint.setStyle(Paint.Style.FILL);
                                    touch_start(x, y);
                                    mPaint.setMaskFilter(filter4);
                                    //Toast.makeText(getContext(), "mode4_f", 1).show();
                                    invalidate();
                                }
                                /*touch_start(x, y);
                                mPaint.setMaskFilter(filter4);
                                Toast.makeText(getContext(), "mode4", 1).show();
                                invalidate();*/
                                break;
                        }
                        /*touch_start(x, y);
                        invalidate();*/
                        break;
                    case com.androidapp.inging.MainActivity.CIRCLE:
                        // action_down - circle
                        if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode0) {

                            if (MainActivity.PenStyle == MainActivity.nomal) {
                                mPaint.setStyle(Paint.Style.STROKE);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter0);
                                //Toast.makeText(getContext(), "mode0_n", 1).show();
                                invalidate();
                            } else if (MainActivity.PenStyle == MainActivity.fill) {
                                mPaint.setStyle(Paint.Style.FILL);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter0);
                                //Toast.makeText(getContext(), "mode0_f", 1).show();
                                invalidate();
                            }
                            /*touch_start(x, y);
                            mPaint.setMaskFilter(filter0);
                            Toast.makeText(getContext(), "mode0", 1).show();
                            invalidate();*/
                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode1) {
                            if (MainActivity.PenStyle == MainActivity.nomal) {
                                mPaint.setStyle(Paint.Style.STROKE);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter1);
                                //Toast.makeText(getContext(), "mode1_n", 1).show();
                                invalidate();
                            } else if (MainActivity.PenStyle == MainActivity.fill) {
                                mPaint.setStyle(Paint.Style.FILL);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter1);
                                //Toast.makeText(getContext(), "mode1_f", 1).show();
                                invalidate();
                            }
                            /*touch_start(x, y);
                            mPaint.setMaskFilter(filter1);
                            Toast.makeText(getContext(), "mode1", 1).show();
                            invalidate();*/

                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode2) {
                            if (MainActivity.PenStyle == MainActivity.nomal) {
                                mPaint.setStyle(Paint.Style.STROKE);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter2);
                                //Toast.makeText(getContext(), "mode2_n", 1).show();
                                invalidate();
                            } else if (MainActivity.PenStyle == MainActivity.fill) {
                                mPaint.setStyle(Paint.Style.FILL);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter2);
                                //Toast.makeText(getContext(), "mode2_f", 1).show();
                                invalidate();
                            }
                            /*touch_start(x, y);
                            mPaint.setMaskFilter(filter2);
                            Toast.makeText(getContext(), "mode2", 1).show();
                            invalidate();*/
                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode3) {
                            if (MainActivity.PenStyle == MainActivity.nomal) {
                                mPaint.setStyle(Paint.Style.STROKE);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter3);
                                //Toast.makeText(getContext(), "mode3_n", 1).show();
                                invalidate();
                            } else if (MainActivity.PenStyle == MainActivity.fill) {
                                mPaint.setStyle(Paint.Style.FILL);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter3);
                                //Toast.makeText(getContext(), "mode3_f", 1).show();
                                invalidate();
                            }
                        } else if (com.androidapp.inging.MainActivity.PenStatus == com.androidapp.inging.MainActivity.mode4) {
                            if (MainActivity.PenStyle == MainActivity.nomal) {
                                mPaint.setStyle(Paint.Style.STROKE);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter4);
                                //Toast.makeText(getContext(), "mode4_n", 1).show();
                                invalidate();
                            } else if (MainActivity.PenStyle == MainActivity.fill) {
                                mPaint.setStyle(Paint.Style.FILL);
                                touch_start(x, y);
                                mPaint.setMaskFilter(filter4);
                                //Toast.makeText(getContext(), "mode4_f", 1).show();
                                invalidate();
                            }
                            /*touch_start(x, y);
                            mPaint.setMaskFilter(filter4);
                            Toast.makeText(getContext(), "mode4", 1).show();
                            invalidate();*/
                        }
                        /*touch_start(x, y);
            	        invalidate();*/
                        break;
                }
            case MotionEvent.ACTION_MOVE:
                if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.PEN) {
                    touch_move(x, y);
                    invalidate();
                } else if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.LINE) {

                } else if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.RECT) {

                } else if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.CIRCLE) {

                }
                invalidate();
                break;


            case MotionEvent.ACTION_UP:
                toX = (int) event.getX();
                toY = (int) event.getY();
                if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.PEN) {
                    touch_up();
                    invalidate();
                } else if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.LINE) {
                    touch_up_LINE();
                    invalidate();
                } else if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.RECT) {
                    touch_up_RECT();
                    invalidate();
                } else if (com.androidapp.inging.MainActivity.Status == com.androidapp.inging.MainActivity.CIRCLE) {
                    touch_up_CIRCLE();
                    invalidate();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
