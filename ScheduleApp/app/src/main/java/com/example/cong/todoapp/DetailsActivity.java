package com.example.cong.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cong.model.Note;

public class DetailsActivity extends AppCompatActivity {

    Intent intent;
    Note note;
    TextView txtName,txtDate,txtTask,txtLvl,txtStt;
    boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
    }

    private void addControls() {
        txtName = (TextView) findViewById(R.id.txtName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTask = (TextView) findViewById(R.id.txtTask);
        txtLvl = (TextView) findViewById(R.id.txtLvl);
        txtStt= (TextView) findViewById(R.id.txtStt);

        intent = getIntent();

        note = (Note) intent.getSerializableExtra("note");
        if(note!=null){
            txtName.setText(note.getName());
            txtDate.setText(note.getTime());
            txtTask.setText(note.getTask());
            txtLvl.setText(note.getLevel());
            txtStt.setText(note.getStatus());
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.mnEdit) {
           updateNote();

        }
        if (id == R.id.mnDelete) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(DetailsActivity.this);
            builder1.setMessage("Do you really want to delete Task?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(DetailsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            setResult(11,intent);
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void updateNote() {
       intent = new Intent(DetailsActivity.this,EditNoteActivity.class);
        intent.putExtra("note",note);
        startActivityForResult(intent,2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==66){
            check = true;
            note = (Note) data.getSerializableExtra("note");
            txtName.setText(note.getName());
            txtDate.setText(note.getTime());
            txtTask.setText(note.getTask());
            txtLvl.setText(note.getLevel());
            txtStt.setText(note.getStatus());


        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        if(check){
            intent.putExtra("noteUpdate",note);
            setResult(23,intent);
            check = false;
        }



        finish();
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(check){
                intent.putExtra("noteUpdate",note);
                setResult(23,intent);
                check = false;
            }
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
