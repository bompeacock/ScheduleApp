package com.example.cong.todoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cong.adapter.NoteAdapter;
import com.example.cong.model.Compare;
import com.example.cong.model.Note;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView lvItem;
    ArrayList<Note> listItem;
    NoteAdapter adapterItem;
    EditText txtItem;


    int lastselected = -1;
    private final int REQUEST_CODE = 20;

    Intent intent;

    public static String DATABASE_NAME = "Note.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public  static SQLiteDatabase database=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copySqliteIntoSystem();
//        init();


        addControls();
        addEvents();

        showNoteInSqlite();


    }

    private void showNoteInSqlite() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("Note",null,null,null,null,null,null);
        listItem.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String task = cursor.getString(3);
            String level = cursor.getString(4);
            String status = cursor.getString(5);

            Note note  =  new Note();
            note.setId(id);
            note.setName(name);
            note.setTime(date);
            note.setTask(task);
            note.setLevel(level);
            note.setStatus(status);
            listItem.add(note);

        }
        adapterItem.notifyDataSetChanged();

    }

    private void copySqliteIntoSystem() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()){
            try{
                CopyDataBaseFromAsset();
            }        catch (Exception e){
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = layDuongDanLuuTru();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }
        catch (Exception e){
            Log.e("Loi sao chep",e.toString());
        }
    }

    private String layDuongDanLuuTru() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnAdd){
            Intent intent = new Intent(MainActivity.this,AddItemActivity.class);
            startActivityForResult(intent,20);
        }
        if(id==R.id.subMnDate){
            sortByDate();
        }
        if(id==R.id.subMnLevel){
            sortByLevel();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortByLevel() {

        Collections.sort(listItem, new Compare());
        adapterItem.notifyDataSetChanged();
    }

    private void sortByDate() {
        Collections.sort(listItem);
        adapterItem.notifyDataSetChanged();
    }


    private void addControls() {
        lvItem = (ListView) findViewById(R.id.lvItem);
        listItem = new ArrayList<>();
        adapterItem = new NoteAdapter(MainActivity.this,R.layout.item,listItem);
        lvItem.setAdapter(adapterItem);





    }

    private void addEvents() {
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastselected =i;;
                intent = new Intent(MainActivity.this,DetailsActivity.class);
                Note note = listItem.get(lastselected);
                intent.putExtra("note",note);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //add
        if(requestCode == REQUEST_CODE && resultCode ==33){
                Note note = (Note) data.getSerializableExtra("addNote");
                listItem.add(note);
                addTaskIntoSqlite(note);
                adapterItem.notifyDataSetChanged();


        }

        //update
        if(requestCode==REQUEST_CODE&& resultCode ==23){
            Note note = (Note) data.getSerializableExtra("noteUpdate");
            listItem.set(lastselected, note);
            updateNote(note);
            adapterItem.notifyDataSetChanged();
        }
//        delete
        if(requestCode==REQUEST_CODE&& resultCode ==11){
            Note note = listItem.get(lastselected);
            deleteNote(note);
            listItem.remove(note);
            adapterItem.notifyDataSetChanged();
        }
    }
    private int updateNote(Note note){
        ContentValues contentValues = new ContentValues();
        String id = String.valueOf(note.getId());
        contentValues.put("name",note.getName());
        contentValues.put("dueDate", note.getTime());
        contentValues.put("task",note.getTask());
        contentValues.put("level",note.getLevel());
        contentValues.put("status",note.getStatus());
        int i = database.update("Note",contentValues,"id=?",new String[]{id});
        return i;

    }
    private void deleteNote(Note note) {

        String id = String.valueOf(note.getId());
        database.delete("Note","id=?",new String[]{id});
    }

    private long addTaskIntoSqlite(Note note){

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",note.getId());
        contentValues.put("name",note.getName());
        contentValues.put("dueDate", note.getTime());
        contentValues.put("task",note.getTask());
        contentValues.put("level",note.getLevel());
        contentValues.put("status",note.getStatus());

        long i = database.insert("Note",null,contentValues);
        return i;
    }

}
