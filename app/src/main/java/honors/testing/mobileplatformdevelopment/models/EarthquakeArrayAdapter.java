package honors.testing.mobileplatformdevelopment.models;

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

public class EarthquakeArrayAdapter extends ArrayAdapter {

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<Earthquake> earthquakes;

    public EarthquakeArrayAdapter(Context context, int resource, List<Earthquake> earthquakes){
        super(context,resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.earthquakes = earthquakes;

    }

    @Override
    public int getCount(){
        return earthquakes.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = layoutInflater.inflate(layoutResource,parent,false);

        TextView tvMag = (TextView) view.findViewById(R.id.txtMag);
        TextView tvLocation = (TextView) view.findViewById(R.id.txtLocation);
        TextView tvDesc = (TextView) view.findViewById(R.id.txtDesc);
        TextView tvDate = (TextView) view.findViewById(R.id.txtDate);
        Drawable mDrawable = getContext().getResources().getDrawable(R.drawable.circular_textview);


        Earthquake currentEarthquake = earthquakes.get(position);



        tvMag.setText(Double.toString(currentEarthquake.getMagDouble()));
        tvLocation.setText(currentEarthquake.getLocationPretty());
        tvDesc.setText(currentEarthquake.getDepth());

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
