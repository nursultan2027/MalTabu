package com.proj.changelang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;
import com.proj.changelang.R;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Image;

public class PdfActivity extends AppCompatActivity {
    private PDFView pdfView;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_activity);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("rules.pdf").load();
        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
