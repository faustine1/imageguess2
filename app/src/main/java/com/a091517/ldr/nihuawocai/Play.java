package com.a091517.ldr.nihuawocai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by ldr on 2017/12/21.
 */

public class Play extends Activity{
    private TextView currentPlayer;
    private Paint drawPaint;
    private float posX, posY;
    private float paintWidth = 12;
    private static final float TOUCH_TOLERANCE = 4; // 在屏幕上移动4个像素后响应
    private static final float ERASE_WIDTH = 100;
    private static LinearLayout paletteView;
    private TextView currentWord;
    private TextView timeShow;
    private TextView currentRoomNumber;
    private EditText answer;
    private Button sendAnswerButton;
    private JSONObject jsonObject;
    private static final String CREATE_ROOM="com.a091517.ldr.nihuawocai.create_room";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        currentWord = (TextView)findViewById(R.id.currentWord);
        currentRoomNumber = (TextView)findViewById(R.id.currentRoomNumber);
        currentRoomNumber.setText(this.getIntent().getStringExtra(CREATE_ROOM));
        currentPlayer=(TextView)findViewById(R.id.playerNumber);
        timeShow = (TextView)findViewById(R.id.timer);
        paletteView = (LinearLayout) findViewById(R.id.paletteView);
        paletteView.addView(new GameView(this));
        currentPlayer.setText("1");
        currentWord.setText("苹果");
        answer=(EditText)findViewById(R.id.answer);
        sendAnswerButton=(Button)findViewById(R.id.sendAnswerButton);
        init();
        //
        ImageView menu_icon = new ImageView(this);
        Drawable menu_img = ContextCompat.getDrawable(this, R.drawable.icon_menu);
        menu_icon.setImageDrawable(menu_img);
        final FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(menu_icon)
                .setPosition(FloatingActionButton.POSITION_RIGHT_CENTER).build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView red_Icon = new ImageView(this);
        Drawable red_img = ContextCompat.getDrawable(this, R.drawable.icon_red);
        red_Icon.setImageDrawable(red_img);
        SubActionButton red_button = itemBuilder.setContentView(red_Icon).build();

        ImageView yellow_Icon = new ImageView(this);
        Drawable yellow_img = ContextCompat.getDrawable(this, R.drawable.icon_yellow);
        yellow_Icon.setImageDrawable(yellow_img);
        SubActionButton yellow_button = itemBuilder.setContentView(yellow_Icon).build();

        ImageView blue_Icon = new ImageView(this);
        Drawable blue_img = ContextCompat.getDrawable(this, R.drawable.icon_blue);
        blue_Icon.setImageDrawable(blue_img);
        SubActionButton blue_button = itemBuilder.setContentView(blue_Icon).build();

        ImageView green_Icon = new ImageView(this);
        Drawable green_img = ContextCompat.getDrawable(this, R.drawable.icon_green);
        green_Icon.setImageDrawable(green_img);
        SubActionButton green_button = itemBuilder.setContentView(green_Icon).build();

        ImageView black_Icon = new ImageView(this);
        Drawable black_img = ContextCompat.getDrawable(this, R.drawable.icon_black);
        black_Icon.setImageDrawable(black_img);
        SubActionButton black_button = itemBuilder.setContentView(black_Icon).build();

        ImageView erase_Icon = new ImageView(this);
        Drawable erase_img = ContextCompat.getDrawable(this, R.drawable.icon_erase);
        erase_Icon.setImageDrawable(erase_img);
        SubActionButton erase_button = itemBuilder.setContentView(erase_Icon).build();

        ImageView width_Icon = new ImageView(this);
        Drawable width_img = ContextCompat.getDrawable(this, R.drawable.icon_width);
        width_Icon.setImageDrawable(width_img);
        SubActionButton width_button = itemBuilder.setContentView(width_Icon).build();

        final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(erase_button)
                .addSubActionView(width_button)
                .addSubActionView(red_button)
                .addSubActionView(yellow_button)
                .addSubActionView(blue_button)
                .addSubActionView(green_button)
                .addSubActionView(black_button)
                .setRadius(300)
                .setStartAngle(90)
                .setEndAngle(270)
                .attachTo(actionButton).build();

        red_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: red_button");
                drawPaint.setColor(Color.RED);
                drawPaint.setStrokeWidth(paintWidth);
                drawPaint.setXfermode(null);
                actionMenu.close(true);
            }
        });

        yellow_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: yellow_button");
                drawPaint.setColor(Color.YELLOW);
                drawPaint.setStrokeWidth(paintWidth);
                drawPaint.setXfermode(null);
                actionMenu.close(true);
            }
        });

        blue_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: blue_button");
                drawPaint.setColor(Color.BLUE);
                drawPaint.setStrokeWidth(paintWidth);
                drawPaint.setXfermode(null);
                actionMenu.close(true);
            }
        });

        green_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: green_button");
                drawPaint.setColor(Color.GREEN);
                drawPaint.setStrokeWidth(paintWidth);
                drawPaint.setXfermode(null);
                actionMenu.close(true);
            }
        });

        black_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: black_button");
                drawPaint.setColor(Color.BLACK);
                drawPaint.setStrokeWidth(paintWidth);
                drawPaint.setXfermode(null);
                actionMenu.close(true);
            }
        });

        erase_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: erase_button");
                drawPaint.setStrokeWidth(ERASE_WIDTH);
                drawPaint.setColor(Color.WHITE);
                //drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                actionMenu.close(true);
            }
        });

        width_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: width_button");
                final WidthSeekBar widthSeekBar = new WidthSeekBar(Play.this, (int) paintWidth);
                widthSeekBar.widthSeekBar(new WidthSeekBar.WidthListener() {
                    @Override
                    public void transWidth(){
                        paintWidth = widthSeekBar.getWidth();
                        drawPaint.setStrokeWidth(paintWidth);
                    }
                });
                widthSeekBar.show();
                drawPaint.setXfermode(null);
                actionMenu.close(true);
            }
        });

        //
        timer.start();

    }

    private CountDownTimer timer=new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timeShow.setText(millisUntilFinished/1000+"秒");
        }

        @Override
        public void onFinish() {
            timeShow.setText("时间到！");
            timeShow.setTextColor(Color.RED);
        }
    };



    private void init() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK); // 设置颜色
        drawPaint.setStrokeWidth(paintWidth); //设置笔宽
        drawPaint.setAntiAlias(true); // 抗锯齿
        drawPaint.setDither(true); // 防抖动
        drawPaint.setStyle(Paint.Style.STROKE); // 设置画笔类型，STROKE空心
        drawPaint.setStrokeJoin(Paint.Join.ROUND); // 设置连接处样式
        drawPaint.setStrokeCap(Paint.Cap.ROUND); // 设置笔头样式
    }

    public class GameView extends View {
        private Bitmap drawBitmap;
        private Canvas drawCanvas;
        private Path drawPath;
        private Paint drawBitmapPaint;

        public GameView(Context context) {
            super(context);
            drawPath = new Path();
            drawBitmapPaint = new Paint(Paint.DITHER_FLAG); // 抗抖动选项
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
            posX = x;
            posY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - posX);
            float dy = Math.abs(y - posY);
            if (dx >= TOUCH_TOLERANCE  || dy >= TOUCH_TOLERANCE) {
                drawPath.quadTo(posX, posY, (x + posX) / 2, (y + posY) / 2);
                posX = x;
                posY = y;
            }
        }

        private void touch_up() {
            drawPath.lineTo(posX, posY);
            drawCanvas.drawPath(drawPath, drawPaint);
            drawPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_down(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    break;
            }
            invalidate();
            return true;
        }
    }

}

