package honors.testing.mobileplatformdevelopment;

//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.TextView;




import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import honors.testing.mobileplatformdevelopment.models.Earthquake;
import honors.testing.mobileplatformdevelopment.models.EarthquakeArrayAdapter;
import honors.testing.mobileplatformdevelopment.utils.SharedViewModel;
import honors.testing.mobileplatformdevelopment.R;

///Fragment for list of earthquales
public class EQListFragment extends android.support.v4.app.ListFragment implements AdapterView.OnItemClickListener {


    private ArrayList<String> arrayList = new ArrayList<>();
    private Format formatter = new SimpleDateFormat("dd-MM-yy");
    private SharedViewModel viewModel;

    @Override
    //Boilerplate onCreateView for fragments
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getOutputList().observe(getViewLifecycleOwner(), new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(@Nullable List<Earthquake> charSequence) {
                // if our viewmodel has changed, change our display
                settingAdapter();
            }
        });
    }

    public void settingAdapter()
    {

        arrayList.clear(); //do not keep same output

        if(viewModel.getOutputList().getValue() != null){ //Do not try set null as it may happen when loading

            for (Earthquake e : viewModel.getOutputList().getValue()) {
                arrayList.add(e.getLocationPretty() + "\n" + e.getMagnitude() + "\n" + formatter.format(e.toDate()));
        }}

        EarthquakeArrayAdapter adapter = new EarthquakeArrayAdapter(getActivity(), R.layout.list_earthquake, viewModel.getOutputList().getValue());
        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this); //set click listner

    }

    @Override
    ///Handles the click of an earthquake
    ///Takes user to Earthquakkeview Activity
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Intent myIntent = new Intent(getActivity(), EarthquakeView.class);

        //The following code allows a single earthquake to be passed
        Earthquake toView = null;
        String locationMatch = ((TextView) view.findViewById(R.id.txtLocation)).getText().toString();
        String magMatch = ((TextView) view.findViewById(R.id.txtMag)).getText().toString();
        String date = ((TextView) view.findViewById(R.id.txtDate)).getText().toString();

        for(Earthquake e : viewModel.getOutputList().getValue()){

            if(e.getLocationPretty().equals(locationMatch)
                    && e.getMagDouble() == (Double.parseDouble(magMatch))
                    && e.getPubDate().equals(date))
            {
                toView = e;
            }

        }

        myIntent.putExtra("clickedEarthquake", (Serializable) toView); //Optional parameters
        getActivity().startActivity(myIntent);
    }
}



