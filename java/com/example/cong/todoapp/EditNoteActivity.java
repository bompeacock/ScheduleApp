package com.example.cong.todoapp;
import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.DatePicker;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.cong.model.Note;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;

public class EditNoteActivity extends AppCompatActivity {

    TextView txtName,txtTask,txtDate;
    ImageButton btnDate;

    Spinner spLevel,spStatus;
    ArrayList<String> listLevel,listStatus;
    ArrayAdapter<String> adapterLvl,adapterStt;

    Calendar calendar = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    Intent intent;

    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtName = (TextView) findViewById(R.id.txtNam);
        txtTask = (TextView) findViewById(R.id.txtTask);
        txtDate= (TextView) findViewById(R.id.txtDate);

        spLevel= (Spinner) findViewById(R.id.spLevel);
        listLevel = new ArrayList<>();
        listLevel.add("High");listLevel.add("Medium");listLevel.add("Low");
        adapterLvl =new ArrayAdapter<String>(EditNoteActivity.this,android.R.layout.simple_list_item_1,listLevel);
        adapterLvl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLevel.setAdapter(adapterLvl);

        spStatus= (Spinner) findViewById(R.id.spStatus);
        listStatus = new ArrayList<>();
        listStatus.add("DONE");listStatus.add("TO-DO");
        adapterStt =new ArrayAdapter<String>(EditNoteActivity.this,android.R.layout.simple_list_item_1,listStatus);
        adapterStt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapterStt);

        btnDate = (ImageButton) findViewById(R.id.btnDate);



        intent = getIntent();

        note = (Note) intent.getSerializableExtra("note");
        if(note!=null){
            txtName.setText(note.getName());
            txtDate.setText(note.getTime());
            txtTask.setText(note.getTask());
            spLevel.setSelection(2);
            switch (note.getLevel()){
                case "High":
                    spLevel.setSelection(0);
                    break;
                case "Medium":
                    spLevel.setSelection(1);
                    break;
                case "Low":
                    spLevel.setSelection(2);
                    break;
            }
            if(note.getStatus().equals("DONE")){
                spStatus.setSelection(0);
            }else spStatus.setSelection(1);
        }

    }

    private void addEvents() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });
    }

    private void pickDate() {
        DatePickerDialog.OnDateSetListener callback =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                txtDate.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                EditNoteActivity.this,
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        if(id == R.id.mnSave){
            String check = txtName.getText().toString();
            if(!check.isEmpty()) {
                note.setName(txtName.getText().toString());
                note.setTask(txtTask.getText().toString());
                note.setTime(simpleDateFormat.format(calendar.getTime()));
                note.setLevel(spLevel.getSelectedItem().toString());
                note.setStatus(spStatus.getSelectedItem().toString());
                intent.putExtra("note",note);

                setResult(66,intent);
                finish();

            }else{
                Toast.makeText(EditNoteActivity.this,"you must input task name",Toast.LENGTH_LONG).show();
                txtName .setSelectAllOnFocus(true);
            }

        }
        if(id == R.id.mnX){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
