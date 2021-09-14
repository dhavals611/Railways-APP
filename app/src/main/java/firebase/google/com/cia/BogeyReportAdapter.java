package firebase.google.com.cia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Dhaval on 26-03-2018.
 */

public class BogeyReportAdapter extends RecyclerView.Adapter<BogeyReportAdapter.ViewHolder>{
    private final ArrayList<CardFiles> Mvalues;
    private final ArrayList<StoreCard>ReportValues = new ArrayList<StoreCard>();
    private ArrayList<String> Spinner_list = null;
    public int SELECT_PICTURE = 100;
    public StoreCard ss = new StoreCard();
    ArrayAdapter<String> spinner_adapter;
    Context context;
    int flag =1;
    public BogeyReportAdapter() {
        Mvalues = null;

    }

    public BogeyReportAdapter(ArrayList mvalues, ArrayList<String> spinner_list, Context c) {
        Mvalues = mvalues;
        Spinner_list =spinner_list;
        context = c;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bogey_cards, parent, false);
        return new ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.seat_no.setText(Mvalues.get(position).getSeat_no());
        holder.comment.setText(Mvalues.get(position).getComment());
        holder.comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ss.setIn_comment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.type.setText(Mvalues.get(position).getType());
        holder.in_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String typevalue = parent.getItemAtPosition(position).toString();
                    if (!typevalue.equals(null)) {
                        ss.setIn_type(typevalue);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mvalues.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.camera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        holder.mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return Mvalues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
   {
        final TextView seat_no,comment,type;
        final EditText in_seat_no,in_comment;
        final Spinner in_type;
        final ImageButton remove,camera,mic;

       public ViewHolder(View itemView) {
           super(itemView);
           seat_no = (TextView)itemView.findViewById(R.id.seat_no);
           comment = (TextView)itemView.findViewById(R.id.comment);
           type = (TextView)itemView.findViewById(R.id.type);
           remove = (ImageButton)itemView.findViewById(R.id.remove);
           camera = (ImageButton)itemView.findViewById(R.id.camera);
           mic = (ImageButton)itemView.findViewById(R.id.mic);

           in_seat_no = (EditText)itemView.findViewById(R.id.in_seat_no);
           in_comment = (EditText)itemView.findViewById(R.id.in_comment);
           in_type = (Spinner) itemView.findViewById(R.id.in_type);
           spinner_adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,Spinner_list);
           spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           in_type.setAdapter(spinner_adapter);
       }
   }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void openImageChooser() {

        Activity gallery = (Activity)context;
        Intent intent = new Intent();
        gallery.getIntent().setType("images/*");
        gallery.getIntent().setAction(Intent.ACTION_PICK_ACTIVITY);
        gallery.startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
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

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    public interface OnClickImageListener{
        void openImageChooser();
    }
}
