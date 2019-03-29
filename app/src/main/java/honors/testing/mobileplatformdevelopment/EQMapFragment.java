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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;

import honors.testing.mobileplatformdevelopment.models.Earthquake;
import honors.testing.mobileplatformdevelopment.utils.SharedViewModel;


///Google Map Fragment
public class EQMapFragment extends Fragment implements OnMapReadyCallback {


    public EQMapFragment() {
        // Required empty public constructor
}

    private GoogleMap map;
    private SharedViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getOutputList().observe(getViewLifecycleOwner(), new Observer<List<Earthquake>>() {

            @Override
            public void onChanged(@Nullable List<Earthquake> charSequence) { //on view model change - update display
                if(map != null){
                    map.clear(); //Clear current map items
                    if(viewModel.getOutputList().getValue() != null) { //Check as not null for loading purposes

                        for (Earthquake e : viewModel.getOutputList().getValue()) {
                            map.addMarker(new MarkerOptions().position(e.getLatLng()).title(e.getLocationPretty()).snippet(e.getPubDate()));
                        }

                        LatLngBounds UK = new LatLngBounds(
                                new LatLng(49.52, -8.22), new LatLng(59.19, -0.54));


                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(UK.getCenter(), 5));
                    }

                }
            }
        });

        mapFragment.getMapAsync(this);


        return rootView;
    }

    @Override
    ///GoogleMap Function
    ///For map set up
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        for(Earthquake e : viewModel.getOutputList().getValue())
        {
            //Setting marker for each map object
            Marker mark = map.addMarker(new MarkerOptions().position(e.getLatLng()).title(e.getLocationPretty()).snippet(e.getPubDate()));
            mark.setTag(e);
        }

        LatLngBounds UK = new LatLngBounds(
                new LatLng(49.52,   -8.22), new LatLng(59.19, -0.54));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(UK.getCenter(), 5));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                try {
                    Earthquake e = (Earthquake) marker.getTag();
                    Intent myIntent = new Intent(getActivity(), EarthquakeView.class);
                    myIntent.putExtra("clickedEarthquake", (Serializable) e); //Optional parameters
                    getActivity().startActivity(myIntent);

                } catch (ArrayIndexOutOfBoundsException e) {

                }

            }
        });

    }



}
