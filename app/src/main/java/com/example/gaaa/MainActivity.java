package com.example.gaaa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gv = (GridView) findViewById(R.id.gridView1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;

        public MyGridAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        Integer[] posterID = {
                R.drawable.enhon, R.drawable.got,
                R.drawable.hride, R.drawable.gimulkal,
                R.drawable.jusul, R.drawable.patisiel,
                R.drawable.inuyasa, R.drawable.jinguk,
                R.drawable.haiku
        };

        Integer[] musicID = {
                R.raw.enhon, R.raw.got,
                R.raw.hride, R.raw.gimulkal,
                R.raw.jusul, R.raw.patisiel,
                R.raw.inuyasa, R.raw.jinguk,
                R.raw.haiku
        };

        String[] posterTitle = {"은혼", "스즈미야 하루히의 우울", "아오하라이드", "귀멸의 칼날",
                "주술회전", "꿈빛 파티시엘", "이누야샤", "진격의 거인", "하이큐"};

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageview;
            if (convertView == null) {
                imageview = new ImageView(context);
                imageview.setLayoutParams(new GridView.LayoutParams(400, 550));
                imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageview.setPadding(14, 14, 14, 14);
            } else {
                imageview = (ImageView) convertView;
            }

            imageview.setImageResource(posterID[position]);

            final int pos = position;
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    mediaPlayer = MediaPlayer.create(context, musicID[pos]);
                    mediaPlayer.start();

                    View dialogView = View.inflate(MainActivity.this, R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    ImageView ivPoster = dialogView.findViewById(R.id.ivPoster);
                    ivPoster.setImageResource(posterID[pos]);

                    dlg.setTitle(posterTitle[pos]);
                    dlg.setIcon(R.drawable.movie_icon);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", (dialog, which) -> {
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    });
                    dlg.show();
                }
            });

            return imageview;
        }
    }
}
