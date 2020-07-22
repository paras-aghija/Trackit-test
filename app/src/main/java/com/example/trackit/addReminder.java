package com.example.trackit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class addReminder extends Fragment implements TimePickerDialog.OnTimeSetListener {
    public static final String EXTRA_ID =
            "com.example.trackit.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.trackit.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.trackit.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE =
            "com.example.trackit.EXTRA_DATE";
    public static final String EXTRA_TIME =
            "com.example.trackit.EXTRA_TIME";
    private EditText title;
    private EditText description;
    //private EditText dDate;
    private NoteViewModel noteViewModel;
    private EditText dTime;
    private RadioButton r1;
    private RadioButton r2;
    Spinner s;
    String s1, s2, s3, s4;
    int k;
    int i1 = -1;
    int tpHour, tpMinute;
    Calendar cal = Calendar.getInstance();
    Calendar c = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View r = inflater.inflate(R.layout.add_reminder, container, false);

        title = r.findViewById(R.id.reminder_title);
        description = r.findViewById(R.id.description);
        dTime = r.findViewById(R.id.time);
        r1 = r.findViewById(R.id.r1);
        r2 = r.findViewById(R.id.r2);

        String[] arraySpinner = {
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        };
        s = (Spinner) r.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getActivity(), s.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                String str5 = s.getSelectedItem().toString();

                switch (str5) {
                    case "Monday":
                        k = 2;
                        break;
                    case "Tuesday":
                        k = 3;
                        break;
                    case "Wednesday":
                        k = 4;
                        break;
                    case "Thursday":
                        k = 5;
                        break;
                    case "Friday":
                        k = 6;
                        break;
                    case "Saturday":
                        k = 7;
                        break;
                    case "Sunday":
                        k = 1;
                        break;
                }
                // Toast.makeText(getActivity(), "" + k, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        noteViewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication())).get(NoteViewModel.class);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            i1 = bundle.getInt("key1", -1);
            s1 = bundle.getString("key2");
            s2 = bundle.getString("key3");
            s3 = bundle.getString("key4");
            s4 = bundle.getString("key5");
            title.setText(s1);
            description.setText(s2);
            dTime.setText(s4);
        }
        // Toast.makeText(getActivity(), ""+i1, Toast.LENGTH_SHORT).show();
        FloatingActionButton f2 = r.findViewById(R.id.saveReminder);
        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        ImageView img2 = r.findViewById(R.id.btnGoTime);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        return r;

    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(), this, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), false);
        tpHour = cal.get(Calendar.HOUR_OF_DAY);
        tpMinute = cal.get(Calendar.MINUTE);
        timePickerDialog.show();
    }

    private void saveNote() {
        String str1 = title.getText().toString();
        String str2 = description.getText().toString();
        String str3 = dTime.getText().toString();
        Boolean b1 = r1.isChecked();
        Boolean b2 = r2.isChecked();
        String str4;
        if(b1==true){
            str4 = "Weekly";
        }
        else {
            str4 = "Daily";
        }
        //String str4 = dTime.getText().toString();

        if (i1 != -1) {
            Note note = new Note(str1, str2, str3, k,str4);
            note.setId(i1);
            noteViewModel.update(note);
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new home()).commit();
            Toast.makeText(getActivity(), "Note Updated", Toast.LENGTH_SHORT).show();
            i1 = -1;
        } else {
            if (str1.trim().isEmpty() || str2.trim().isEmpty() || str3.trim().isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all entries!", Toast.LENGTH_SHORT).show();
                return;
            }

            //if (r1.isSelected()==true) {

            noteViewModel.insert(new Note(str1, str2, str3, k,str4));
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new home()).commit();
            Toast.makeText(getActivity(), "Note saved succesfully", Toast.LENGTH_SHORT).show();
            if (b1 == true) {
                startAlarmWeekly(c);
            } else if (b2 == true) {
                startAlarmDaily(c);
                //Toast.makeText(getActivity(), "daily is selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Please Select Alarm Type!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = DateFormat.getTimeInstance(android.icu.text.DateFormat.SHORT).format(c.getTime());
        dTime.setText(timeText);
    }

    private void startAlarmWeekly(Calendar c) {

        switch (k) {
            case 1:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;

            case 2:
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;

            case 3:
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;

            case 4:
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;

            case 5:
                c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;

            case 6:
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;

            case 7:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;

            //default:
              //  c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReciever.class);
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 24 * 7 * 60 * 60 * 1000, pendingIntent);

    }

    private void startAlarmDaily(Calendar c) {

        switch (k) {
            case 1:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;

            case 2:
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;

            case 3:
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;

            case 4:
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;

            case 5:
                c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;

            case 6:
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;

            case 7:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;

            default:
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        AlarmManager alarmManage = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent inten = new Intent(getContext(), AlarmReciever.class);
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingInten = PendingIntent.getBroadcast(getContext(), id, inten, 0);
        alarmManage.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingInten);
    }
}

