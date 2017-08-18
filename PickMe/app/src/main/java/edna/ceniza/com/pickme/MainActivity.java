package edna.ceniza.com.pickme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int PICK_IMAGE_REQUEST = 1;
    Button btn;
    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayShowHomeEnabled(false);
        ab.setHomeAsUpIndicator(R.drawable.navbar);
        ab.setDisplayHomeAsUpEnabled(true);

        Button btn;
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        btn = (Button) findViewById(R.id.button);
        share = (Button) findViewById(R.id.share);

        btn.setVisibility(View.GONE);
        share.setVisibility(View.VISIBLE);

        View mainsrc = findViewById(R.id.mainsrc);
        //View root = mainsrc.getRootView();
        //root.setBackgroundResource();

        final Uri selectimg = data.getData();
        Drawable bg = null;
        try{
            InputStream inputStream = getContentResolver().openInputStream(selectimg);
            bg = Drawable.createFromStream(inputStream, selectimg.toString());
        }catch (FileNotFoundException e){
        }
        mainsrc.setBackground(bg);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendintent = new Intent();
                sendintent.setAction(Intent.ACTION_SEND);
                sendintent.putExtra(Intent.EXTRA_STREAM, selectimg);
                sendintent.setType("image/jpeg");
                startActivity(Intent.createChooser(sendintent, getResources().getText(R.string.send_to)));
            }
        });
    }
}
