package com.a091517.ldr.nihuawocai;

import android.app.Dialog;
import android.content.Context;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class WidthSeekBar extends Dialog {
    private SeekBar width_seek;
    private TextView width_info;
    private int width;

    protected WidthSeekBar(Context context, int defaultWidth) {
        super(context);
        width = defaultWidth;
        setContentView(R.layout.width_seek_bar);
        width_info = (TextView)findViewById(R.id.width_info);
        width_seek = (SeekBar)findViewById(R.id.width_seek);
        width_seek.setProgress(width);
        width_info.setText("画笔宽度: " + width + "pts");
    }
    public int getWidth(){
        return width;
    }

    public void widthSeekBar(final WidthListener widthListener){
        width_seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                widthListener.transWidth();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                width_info.setText("画笔宽度: " + progress + "pts");
                width = progress;
            }
        });
    }
    public interface WidthListener{
        void transWidth();
    }
}