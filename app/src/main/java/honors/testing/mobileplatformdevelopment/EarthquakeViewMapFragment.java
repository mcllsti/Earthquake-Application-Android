package honors.testing.mobileplatformdevelopment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;



import honors.testing.mobileplatformdevelopment.models.Earthquake;
import honors.testing.mobileplatformdevelopment.utils.SharedViewModel;


public class EarthquakeViewMapFragment extends Fragment implements OnMapReadyCallback {


    public EarthquakeViewMapFragment() {
        // Required empty public constructor
    }

    private GoogleMap map;
    private SharedViewModel viewModel;
    private Earthquake e;

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
        EarthquakeView parent = (EarthquakeView)getActivity();
        e = parent.getEarthquake();
        mapFragment.getMapAsync(this);


        return rootView;
    }

    @Override
    ///GoogleMap Function
    ///For map set up
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.addMarker(new MarkerOptions().position(e.getLatLng()).title(e.getLocationPretty()));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(e.getLatLng(), 7));

    }
}
