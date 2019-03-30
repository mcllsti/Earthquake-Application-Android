package honors.testing.mobileplatformdevelopment.models;
//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import honors.testing.mobileplatformdevelopment.R;

//Class that allows a custom layout to be used in the list view making the application more visually appealing
public class EarthquakeArrayAdapter extends ArrayAdapter {

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Earthquake> earthquakes;

    //Default constructor
    public EarthquakeArrayAdapter(Context context, int resource, List<Earthquake> earthquakes){
        super(context,resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.earthquakes = earthquakes;

    }

    //Override of getCount which returns the size of the list of earthquakes
    @Override
    public int getCount(){
        return earthquakes.size();
    }

    //Overrideing View to utilize custom view
    //Returns the view
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = layoutInflater.inflate(layoutResource,parent,false);

        //Get all custom elements from the layout resource
        TextView tvMag = (TextView) view.findViewById(R.id.txtMag);
        TextView tvLocation = (TextView) view.findViewById(R.id.txtLocation);
        TextView tvDesc = (TextView) view.findViewById(R.id.txtDesc);
        TextView tvDate = (TextView) view.findViewById(R.id.txtDate);
        Drawable mDrawable = getContext().getResources().getDrawable(R.drawable.circular_textview); //get the drawable


        Earthquake currentEarthquake = earthquakes.get(position); //get our current earthquake



        tvMag.setText(Double.toString(currentEarthquake.getMagDouble()));
        tvLocation.setText(currentEarthquake.getLocationPretty());
        tvDesc.setText(currentEarthquake.getDepth());

        //Sets our drawable and color depending on Magnitutide size
        if(currentEarthquake.getMagDouble() < 0.5){
            mDrawable.setColorFilter((new
                    PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY)));
        }
        else if(currentEarthquake.getMagDouble() > 0.5 && currentEarthquake.getMagDouble() < 2.1){
            mDrawable.setColorFilter((new
                    PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY)));
        }
        else if(currentEarthquake.getMagDouble() > 2.1){
            mDrawable.setColorFilter((new
                    PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)));
        }

        tvMag.setBackground(mDrawable);

        tvDate.setText(currentEarthquake.getPubDate());

        return view;
    }



}
