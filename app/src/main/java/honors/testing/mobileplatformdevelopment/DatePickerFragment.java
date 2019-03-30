package honors.testing.mobileplatformdevelopment;
//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import honors.testing.mobileplatformdevelopment.models.Earthquake;
import honors.testing.mobileplatformdevelopment.utils.SharedViewModel;

///Handles our Date Picker Dialouge Fragment
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener,    DatePickerDialog.OnCancelListener {

    private ArrayList<String> arrayList = new ArrayList<>();
    DatePickerDialog picker;
    private SharedViewModel viewModel;
    public List<Earthquake> displays;
    int year;
    int month;
    int day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        if(year == 0 & month == 0 & day == 0){ //Only if there is not a previously selected date
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        picker =  new DatePickerDialog(getActivity(), this, year, month, day);
        picker.setOnCancelListener(this);

        // Create a new instance of DatePickerDialog and return it
        return picker;
    }

    public void onCancel(DialogInterface dialog) {
        viewModel.setOutputList(viewModel.getMasterList().getValue());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Get our viewmodel for use
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    public void onCancel() {

        //set us as a date so we can compare
        Date compare = new GregorianCalendar(year, month, day).getTime();

        displays = new ArrayList<>();

        for(Earthquake e : viewModel.getOutputList().getValue()){

            //For all of our earthquake dates
            //Check to see if match chosen date
            //If so, add to new list and set as output
            Date date2 = e.toDate();
            int help = compare.compareTo(date2);
            if(compare.compareTo(date2) == 0){
                displays.add(e);
            }
        }
        if(displays.size() < 1){
            Toast.makeText(getContext(),R.string.NoEarthquakeDate,Toast.LENGTH_SHORT).show();
        }
        else{
            viewModel.setOutputList(displays);
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        //set us as a date so we can compare
        Date compare = new GregorianCalendar(year, month, day).getTime();

        displays = new ArrayList<>();

        for(Earthquake e : viewModel.getOutputList().getValue()){

            //For all of our earthquake dates
            //Check to see if match chosen date
            //If so, add to new list and set as output
            Date date2 = e.toDate();
            int help = compare.compareTo(date2);
            if(compare.compareTo(date2) == 0){
                displays.add(e);
            }
        }
        if(displays.size() < 1){
            Toast.makeText(getContext(),R.string.NoEarthquakeDate,Toast.LENGTH_SHORT).show();
        }
        else{
            viewModel.setOutputList(displays);
        }
    }
}