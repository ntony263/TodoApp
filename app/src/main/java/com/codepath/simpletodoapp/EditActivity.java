package com.codepath.simpletodoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {
    String id;
    TextView tvNewItem0;
    String yearString, monthString, dayString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit");
        String eTaskName = getIntent().getStringExtra("EtaskName");
        String ePriority = getIntent().getStringExtra("Epriority");
        String edueDate = getIntent().getStringExtra("EdueDate");
        id = getIntent().getStringExtra("Eid");
        EditText etNewItem0 = (EditText)findViewById(R.id.etEdit0);
        tvNewItem0 = (TextView)findViewById(R.id.textView4);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"High", "Medium", "Low"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        if (eTaskName != null){
            etNewItem0.append(eTaskName);
        }
        else {
            etNewItem0.append("");
        }
        if (ePriority != null){
            //etNewItem1.append(ePriority);
            if (ePriority.equals("High")){
                dropdown.setSelection(0);
            }
            else if (ePriority.equals("Medium")){
                dropdown.setSelection(1);
            }
            else {
                dropdown.setSelection(2);
            }
        }
        else {
            dropdown.setSelection(2);
        }
        if (edueDate != null){
            tvNewItem0.setText(edueDate);
        }
        else {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH)+1;
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            String currentDate = Integer.toString(mDay)+"/"+Integer.toString(mMonth)+"/"+Integer.toString(mYear);
            tvNewItem0.setText(currentDate);
        }
    }

    public void onSaveItem(View view) {
        EditText etNewItem0 = (EditText)findViewById(R.id.etEdit0); //TaskName findView
        //EditText etNewItem1 = (EditText)findViewById(R.id.etEdit1); //
        TextView tvNewItem0 = (TextView)findViewById(R.id.textView4); //
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);

        String eTaskName = etNewItem0.getText().toString();
        String ePriority = dropdown.getSelectedItem().toString();
        String edueDate = tvNewItem0.getText().toString();

        Intent intent=new Intent();
        intent.putExtra("EtaskName",eTaskName);
        intent.putExtra("EdueDate",edueDate);
        intent.putExtra("Epriority",ePriority);
        //Toast.makeText(getApplicationContext(),id, Toast.LENGTH_LONG).show();
        if (id != null){
            intent.putExtra("Eid",id);
            intent.putExtra("ESAVE","save");
        }
        else {
            intent.putExtra("ESAVE","create");
        }

        setResult(1,intent);
        finish();
    }

    public void toCancel(View view) {
        Intent intent=new Intent();
        intent.putExtra("ESAVE","cancel");
        setResult(1,intent);
        finish();
    }


    public void toEditDate(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }



    class SelectDateFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, mYear, mMonth,
                    mDay);//it will return dialog setting date with mYear,MMonth and MDay

        }
        @Override
        public void onDateSet(android.widget.DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            System.out.println("year=" + year + "day=" + dayOfMonth + "month="
                    + monthOfYear);
            yearString = Integer.toString(year);
            monthString = Integer.toString(monthOfYear+1);
            dayString = Integer.toString(dayOfMonth);
            String newText = dayString+"/"+monthString+"/"+ yearString;
            tvNewItem0 = (TextView)findViewById(R.id.textView4);
            tvNewItem0.setText(newText);
        }
    }


}
