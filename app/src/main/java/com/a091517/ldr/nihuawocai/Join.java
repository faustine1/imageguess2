package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/12/22.
 */

public class Join extends Activity {

    private Paint drawPaint;
    private float posX, posY;
    private float paintWidth = 12;
    private int paintColor = Color.BLACK;
    private String gameData;
    private String localIP;
    private String remoteIP;
    private int localPort = 8001;
    private int remotePort = 8002;
    private int actionState = 10000;
    private static ClientSocket clientSocket;
    private static JSONObject gameDataJSON;
    private static final float ERASE_WIDTH = 150;
    private static final int ACTION_DOWN = 10000;
    private static final int ACTION_MOVE = 10001;
    private static final int ACTION_UP = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final JoinView joinView = new JoinView(this);
        setContentView(joinView);
        clientSocket = new ClientSocket(this);
        init();
        localIP = clientSocket.getIp(this);
        remoteIP = "192.168.43.137";
        clientSocket.InfoReceiver(localPort, new ClientSocket.DataListener() {
            @Override
            public void transData() {
                gameData = clientSocket.getGameData();
                Log.i(TAG, gameData);
                synchronized (this){
                    try{
                        gameDataJSON = new JSONObject(gameData);
                        posX = Float.valueOf(gameDataJSON.get("posX").toString());
                        posY = Float.valueOf(gameDataJSON.get("posY").toString());
                        paintWidth = Integer.valueOf(gameDataJSON.get("width").toString());
                        paintColor = Integer.valueOf(gameDataJSON.get("color").toString());
                        drawPaint.setColor(paintColor);
                        drawPaint.setStrokeWidth(paintWidth);
                        if (paintColor == Color.WHITE){
                            drawPaint.setStrokeWidth(ERASE_WIDTH);
                        }
                        actionState = Integer.valueOf(gameDataJSON.get("actionState").toString());
                        joinView.RefreshView(actionState);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                //Log.i(TAG, gameData);
            }
        });
    }

    private void init() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor); // 设置颜色
        drawPaint.setStrokeWidth(paintWidth); //设置笔宽
        drawPaint.setAntiAlias(true); // 抗锯齿
        drawPaint.setDither(true); // 防抖动
        drawPaint.setStyle(Paint.Style.STROKE); // 设置画笔类型，STROKE空心
        drawPaint.setStrokeJoin(Paint.Join.ROUND); // 设置连接处样式
        drawPaint.setStrokeCap(Paint.Cap.ROUND); // 设置笔头样式
    }

    public class JoinView extends View {
        private Bitmap drawBitmap;
        private Canvas drawCanvas;
        private Path drawPath;
        private Paint drawBitmapPaint;

        public JoinView(Context context) {
            super(context);
            drawPath = new Path();
            drawBitmapPaint = new Paint(Paint.DITHER_FLAG); // 抗抖动选项
            //new Thread(new RefreshView()).start();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            drawBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); // 每个像素8bytes存储
            drawCanvas = new Canvas(drawBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE); // 设置背景颜色
            canvas.drawBitmap(drawBitmap, 0, 0, drawBitmapPaint);
            canvas.drawPath(drawPath, drawPaint);
        }

        private void touch_down(float x, float y) {
            drawPath.reset();
            drawPath.moveTo(x, y);
        }

        private void touch_move(float x, float y) {
            if (!drawPath.isEmpty()) {
                drawPath.quadTo(posX, posY, (x + posX) / 2, (y + posY) / 2);
            }
        }

        private void touch_up() {
            if (!drawPath.isEmpty()){
                drawPath.lineTo(posX, posY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
            }
        }

        public void RefreshView (int actionState){
            switch (actionState) {
                case ACTION_DOWN:
                    touch_down(posX, posY);
                    break;
                case ACTION_MOVE:
                    touch_move(posX, posY);
                    break;
                case ACTION_UP:
                    touch_up();
                    break;
            }
            postInvalidate();
        }
    }
}
