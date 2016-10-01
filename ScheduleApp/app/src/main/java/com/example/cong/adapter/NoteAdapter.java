package com.example.cong.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cong.model.Note;
import com.example.cong.todoapp.R;

import java.util.List;

/**
 * Created by Cong on 27/09/2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    Activity context;
    int resource;
    List objects;

    public NoteAdapter(Activity context, int resource, List objects) {
        super(context, resource, objects);
        this.context =context;
        this.resource = resource;
        this.objects =objects;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        TextView txtName = (TextView) view.findViewById(R.id.txtNam);
        TextView txtLevel = (TextView) view.findViewById(R.id.txtLevel);
        TextView txtLine  = (TextView) view.findViewById(R.id.txtLine);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDate);

        Note note = (Note) this.objects.get(position);
        txtName.setText(note.getName());
        txtLevel.setText(note.getLevel());
        txtDate.setText(note.getTime());
        if(txtLevel.getText().toString().equals("High")){
            txtLevel.setTextColor(Color.parseColor("#FF6A6A"));
        }
        if(txtLevel.getText().toString().equals("Medium")){
            txtLevel.setTextColor(Color.parseColor("#EEAD0E"));

        }
        if(txtLevel.getText().toString().equals("Low")){
            txtLevel.setTextColor(Color.parseColor("#66CD00"));

        }

        return view;
    }
}
