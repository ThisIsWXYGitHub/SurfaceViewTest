package com.example.lenovo.surfaceviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Created by lenovo on 29/5/2016.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    private SurfaceHolder holder;
    private Canvas canvas;
    private boolean misdrawing;
    Path path;
    private  Surface sf;
    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init()
    {
        holder=getHolder();
        sf=holder.getSurface();
        holder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(false);
        this.setKeepScreenOn(true);
        holder.setFormat(PixelFormat.OPAQUE);
        path=new Path();
        path.moveTo(0,250);
        for (int i=10;i<800;i++)
        {
           int y=(int)(100*Math.sin(i*2*Math.PI/180)+400);
            path.lineTo(i,y);
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        misdrawing=true;
        new Thread(this).start();
        Toast.makeText(getContext(), "create", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Toast.makeText(getContext(), "change", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        misdrawing=false;
        Toast.makeText(getContext(), "Destroy", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void run() {
        draw();
    }
    public void draw()
    {
        Paint p=new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setTextAlign(Paint.Align.CENTER);
       try {
           synchronized(holder) {
               canvas = holder.lockCanvas(new Rect());
               canvas.drawColor(Color.WHITE);
               canvas.drawText("Android", 200, 200, p);
               // canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 80, p);
           }
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        finally {
           holder.unlockCanvasAndPost(canvas);
       }
    }
}
