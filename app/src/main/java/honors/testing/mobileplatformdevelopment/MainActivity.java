package honors.testing.mobileplatformdevelopment;

//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import android.arch.lifecycle.ViewModelProviders;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import honors.testing.mobileplatformdevelopment.models.Earthquake;
import honors.testing.mobileplatformdevelopment.utils.SharedViewModel;

//Main Acitivty that drives the APP
public class MainActivity extends AppCompatActivity
{
    private FragmentTransaction ft;
    private SharedViewModel viewModel;
    private Spinner spinner;
    private ArrayAdapter<String> dataAdapter;
    private MenuItem item;
    private MenuInflater menuInflater;
    private MenuItem switcher;
    private ToggleButton fragmentChanger;


    private EQListFragment EQListFragment = new EQListFragment();
    private EQMapFragment mapFragment = new EQMapFragment();


    //Shows the datepicker dialog box fragment
    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    //Deals with the creation of the Action Bar
    public boolean onCreateOptionsMenu(Menu menu){


        menuInflater = getMenuInflater(); //inflate menu
        menuInflater.inflate(R.menu.toolbar_menu,menu);

        //Obtain spinner and switch
        item = menu.findItem(R.id.spinner);
        switcher = menu.findItem(R.id.myswitch);

        //For dealing with permiated isChecked on the switch
        boolean checked = false;


        //SETS UP SWITCH BUTTON -----------------------------------------
        if(fragmentChanger != null && fragmentChanger.isChecked()){
            checked = true;
        }
        fragmentChanger = (ToggleButton)switcher.getActionView().findViewById(R.id.toggleButton1);
        fragmentChanger.setChecked(checked);

        if(fragmentChanger.isChecked()){
            //Set ICON to map

            fragmentChanger.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_mapmode, 0);
        }
        else{
            //Set ICON to list

            fragmentChanger.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_sort_by_size, 0, 0, 0);
        }

        //Listner for changer
        fragmentChanger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Changes to the map Fragment
                    fragmentChanger.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_menu_mapmode, 0);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framlayoutMap, new EQMapFragment());
                    ft.commit();

                }
                else
                {
                    //Changes to the list fragment
                    fragmentChanger.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_sort_by_size, 0, 0, 0);
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framlayoutMap, EQListFragment);
                    ft.commit();
                }
            }
        });

        //END SWITCH BUTTON SET UP-----------------------------------------


        //SETS UP SPINNER BUTTON -----------------------------------------
        //Setting our dataadpater and creating custom view method
        dataAdapter = new ArrayAdapter<String>(this, R.layout.action_search,  getResources().getStringArray(R.array.selector)){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.action_search, parent, false);

                return view;
            }
        };

        //If it is null then it is inital startup
        if(spinner == null){
            spinner = (Spinner) item.getActionView();
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //set ICON
            spinner.setAdapter(dataAdapter);
            spinner.setBackground(getResources().getDrawable(android.R.drawable.ic_menu_search));
        }

        //If not null then a permiated session
        if(spinner != null){
            //Still have to set adapter
            dataAdapter = new ArrayAdapter<String>(this, R.layout.action_search,  getResources().getStringArray(R.array.selector)){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    LayoutInflater inflater=getLayoutInflater();
                    View view=inflater.inflate(R.layout.action_search, parent, false);

                    return view;
                }
            };

            String temp = spinner.getSelectedItem().toString(); //Get current spinner
            spinner = (Spinner) item.getActionView();
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //set ICON
            spinner.setAdapter(dataAdapter);
            spinner.setBackground(getResources().getDrawable(android.R.drawable.ic_menu_search));
            spinner.setSelection(dataAdapter.getPosition(temp)); //Reset selection


        }

        //on Click for our spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                ChangeView(selectedItem); //Change our view based on selected item
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
                //Do nothing
            }
        });
        //END SPINNER BUTTON SET UP-----------------------------------------
        return true;
    }


    @Override
    //Method for dealing with exclusive click of the date in actionbar
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.limitdate:
                //Call our datepicker method
                showDatePickerDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    //On Activity Create
    protected void onCreate(Bundle savedInstanceState)
    {
        viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        viewModel.UpdateMaster(getApplicationContext()); // Call master to start prasing inital data and our refresh

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar); // Set our toolbar


            //Set our inital list view
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.framlayoutMap, EQListFragment);
            ft.commit();

        Toast.makeText(getApplicationContext(),R.string.Copyright,Toast.LENGTH_LONG).show();

    }


    ///Deals with the changing of output based on selected choice from spinner
    private void ChangeView(String selectedItem) {

        switch(selectedItem) //Case based on selected item, calls appropriate method and sets output to that return
        {
            case "All":
                viewModel.setOutputList(viewModel.getMasterList().getValue());
                break;
            case "Largest Magnitute":
                viewModel.setOutputList(new ArrayList<Earthquake>(Arrays.asList(LargestMag())));
                break;
            case "Smallest Magnitute":
                viewModel.setOutputList(new ArrayList<Earthquake>(Arrays.asList(SmallestMag())));
                break;
            case "Most Northerly":
                viewModel.setOutputList(new ArrayList<Earthquake>(Arrays.asList(MostNorth())));
                break;
            case "Most Southerly":
                viewModel.setOutputList(new ArrayList<Earthquake>(Arrays.asList(MostSouth())));
                break;
            case "Most Easterly":
                viewModel.setOutputList(new ArrayList<Earthquake>(Arrays.asList(MostEast())));
                break;
            case "Most Westernly":
                viewModel.setOutputList(new ArrayList<Earthquake>(Arrays.asList(MostWest())));
                break;
            case "Scottish Earthquakes":
                viewModel.setOutputList(new ArrayList<Earthquake>(getEarthquakesByCountry("Scotland")));
                break;
            case "English Earthquakes":
                viewModel.setOutputList(new ArrayList<Earthquake>(getEarthquakesByCountry("England")));
                break;
            case "Welsh Earthquakes":
                viewModel.setOutputList(new ArrayList<Earthquake>(getEarthquakesByCountry("Wales")));
                break;
            default:
                System.out.println("no match");
        }

    }


    //Obtains the earthquake with the largest magnituide
    private Earthquake LargestMag(){

        Earthquake toSet;
        toSet = viewModel.getMasterList().getValue().get(0);

        for(Earthquake e : viewModel.getMasterList().getValue()){
            if(toSet.getMagDouble() < e.getMagDouble())
            {
                toSet = e;
            }
        }
        return toSet;
    }

    //Obtains the earthquake with the smallest magnituide
    private Earthquake SmallestMag(){

        Earthquake toSet = new Earthquake();
        toSet = viewModel.getMasterList().getValue().get(0);

        for(Earthquake e : viewModel.getMasterList().getValue()){
            if(toSet.getMagDouble() > e.getMagDouble())
            {
                toSet = e;
            }
        }
        return toSet;
    }

    //Obtains the earthquake that is most north
    private Earthquake MostNorth(){
        Earthquake toSet = new Earthquake();
        toSet = viewModel.getMasterList().getValue().get(0);

        for(Earthquake e : viewModel.getMasterList().getValue()){
            if(toSet.getLatDouble() < e.getLatDouble())
            {
                toSet = e;
            }
        }
        return toSet;
    }
    //Obtains the earthquake that is most south
    private Earthquake MostSouth(){
        Earthquake toSet = new Earthquake();
        toSet = viewModel.getMasterList().getValue().get(0);

        for(Earthquake e : viewModel.getMasterList().getValue()){
            if(toSet.getLatDouble() > e.getLatDouble())
            {
                toSet = e;
            }
        }
        return toSet;
    }
    //Obtains the earthquake that is most east
    private Earthquake MostEast(){
        Earthquake toSet = new Earthquake();
        toSet = viewModel.getMasterList().getValue().get(0);

        for(Earthquake e : viewModel.getMasterList().getValue()){
            if(toSet.getLonDouble() < e.getLonDouble())
            {
                toSet = e;
            }
        }
        return toSet;
    }
    //Obtains the earthquake that is most west
    private Earthquake MostWest(){
        Earthquake toSet = new Earthquake();
        toSet = viewModel.getMasterList().getValue().get(0);

        for(Earthquake e : viewModel.getMasterList().getValue()){
            if(toSet.getLonDouble() > e.getLonDouble())
            {
                toSet = e;
            }
        }
        return toSet;
    }
    //Obtains a list of earthquakes that are within a specified country
    private List<Earthquake> getEarthquakesByCountry(String country){
        List<Earthquake> toSet = new ArrayList<>();

        for(Earthquake e : viewModel.getMasterList().getValue()){
            if(getCountry(e).equals(country))
            {
                toSet.add(e);
            }
        }
        return toSet;
    }
    //Method that can determine the country of a earthquake and returns country name
    private String getCountry(Earthquake e) {
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        String countryName = "";
        try{

            List<Address> addresses = gcd.getFromLocation(e.getLatDouble(), e.getLonDouble(), 1);

            if (addresses.size() > 0 && !addresses.get(0).getAdminArea().equals(null))
            {
                countryName = addresses.get(0).getAdminArea(); //Admin area is imporatnt for UK earthquakes as UK is considered Country

            }
            return countryName;
        }
        catch(Exception y){
            countryName = "null"; //Must set a name no matter what
        }
        return countryName;

    }


}