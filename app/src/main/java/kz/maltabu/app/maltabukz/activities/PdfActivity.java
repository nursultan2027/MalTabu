package kz.maltabu.app.maltabukz.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;

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
                CustomAnimator.animateViewBound(img);
                onBackPressed();
            }
        });
    }
}
