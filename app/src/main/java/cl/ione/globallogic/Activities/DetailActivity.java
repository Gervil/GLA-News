package cl.ione.globallogic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cl.ione.globallogic.R;
import cl.ione.globallogic.Utilities.Util;

public class DetailActivity extends AppCompatActivity {

    //Controls
    public ImageView mImage;
    public TextView mTitle, mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImage = findViewById(R.id.detail_image);
        mTitle = findViewById(R.id.detail_title);
        mDescription = findViewById(R.id.detail_description);

        //Extractor
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Util.downloadImage(extras.getString("image"), mImage);
            mTitle.setText(extras.getString("title"));
            mDescription.setText(extras.getString("description"));
        }
    }
}