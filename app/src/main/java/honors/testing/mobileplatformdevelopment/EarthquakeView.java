package honors.testing.mobileplatformdevelopment;
//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import honors.testing.mobileplatformdevelopment.models.Earthquake;

public class EarthquakeView extends AppCompatActivity {
    private TextView output;
    private FragmentTransaction ft;
    private Earthquake toDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_view);
        output = (TextView)findViewById(R.id.txtDetails);

        Intent i = getIntent();
        toDisplay = (Earthquake)i.getSerializableExtra("clickedEarthquake");

        output.setText( toDisplay.getLocationPretty() + "\n" +  "\n" +
                        toDisplay.getMagnitude() + "\n" +
                        "Date/Time: " + toDisplay.getPubDate() + "\n" +
                        "Category: " + toDisplay.getCategory() + "\n" +
                        toDisplay.getDepth() +"\n" +
                        "Lat/Lon: " + toDisplay.getGeoLat() + " " + toDisplay.getGeoLong() +"\n" + "matric: S1222204"



        );

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mapView, new EarthquakeViewMapFragment());
        ft.commit();

    }

    public Earthquake getEarthquake(){
        return toDisplay;
    }
}
