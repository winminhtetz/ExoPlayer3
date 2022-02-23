package com.wmh.exoplayer3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetVideoActivity extends AppCompatActivity {
    private Button btnPlayVideo;
    private EditText etGetLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_video);

        btnPlayVideo = findViewById(R.id.btnPlayVideo);
        etGetLink    = findViewById(R.id.etGetLink);

        btnPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = etGetLink.getText().toString().trim();
                String VideoId = getVideoId(link);
                String videoLink = "https://www.googleapis.com/drive/v3/files/"+ VideoId +"?alt=media&key=AIzaSyA1v-_D-0AnoNCGO-l1ce4DCRjIUEDTi7E";

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("vdLink" , videoLink);
                startActivity(intent);
                finish();

            }
        });

    }

    private String getVideoId(String link){
        return link.substring(32, 65);
    }
}