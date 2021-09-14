package firebase.google.com.cia;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class InspectionBogeyReportActivity extends AppCompatActivity {
    ArrayList<CardFiles> cards = new ArrayList<CardFiles>();
    ArrayList<String> spinner_list = new ArrayList<String>();

    Button add;
    BogeyReportAdapter a;
    String[] permissions={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    TextView train_name;
    CardFiles c;
    public int SELECT_PICTURE = 100;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inpection_bogey_report);
        setResult(RESULT_OK);
        spinner_list.add("Technical");
        spinner_list.add("Cleanliness");
        final RecyclerView card = (RecyclerView)findViewById(R.id.card_list);
        a = new BogeyReportAdapter(cards,spinner_list,InspectionBogeyReportActivity.this);
        RecyclerView.LayoutManager mlayoutmanager = new LinearLayoutManager(getApplicationContext());
        card.setLayoutManager(mlayoutmanager);
        card.setAdapter(a);

        card.setItemAnimator(new DefaultItemAnimator());

        train_name = (TextView)findViewById(R.id.train_name);
        train_name.setText("   "+"12111"+"             "+" |        "+"S2");
        train_name.setTextSize(25);
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = new CardFiles("Seat no:","Comment:","Type:");
                cards.add(c);
                a.notifyDataSetChanged();



            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    //  Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    //((ImageView) findViewById(R.id.imgView)).setImageURI(selectedImageUri);
                }
            }
        }
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
