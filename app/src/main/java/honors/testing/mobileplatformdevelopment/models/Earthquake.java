package honors.testing.mobileplatformdevelopment.models;
//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


///Class that is used to serilize the data from the XML Feed
///Contains methods to also display data
public class Earthquake implements Serializable {

    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    private String category;
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    private String link;
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    private String pubDate;
    public String getPubDate() {
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    private String geoLat;
    public String getGeoLat() {
        return geoLat;
    }
    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    private String geoLong;
    public String getGeoLong() {
        return geoLong;
    }
    public void setGeoLong(String geoLong) {
        this.geoLong = geoLong;
    }



    ///Gets magnatuide of an earthquake
    ///Returns String magnituide
    public String getMagnitude()
    {
        //Extract the magnituide form the description
        String[] parts = this.getDescription().split(";");
        return parts[4].substring(1);
    }

    public String getDepth(){

        String[] parts = this.getDescription().split(";");
        return parts[3].substring(1);

    }

    ///Gets the date of a earthquake
    ///Returns Date earthquake date
    public Date toDate() {
        String date = this.getPubDate();

        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("E, dd MMM yyyy").parse(date);
        } catch (ParseException e) {
            //No Catch code as no return
        }

        return date1;
    }

    ///Gets the location of a earthquake and returns it correctly formatted
    ///Returns String earthquake location
    public String getLocationPretty()
    {
        String[] parts = this.getDescription().split(";");

            parts = parts[1].split(":");
            parts = parts[1].substring(1).split(",");

            if(parts.length > 1) //Some areas only have 1 location
            {

                for (int i = 0; i < parts.length; i++) { //Formatting
                    parts[i] = parts[i].toLowerCase();
                    parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
                }

                return (parts[0] + ", " + parts[1]);
            }
            else
            {
                parts[0].toLowerCase();
                return (parts[0].substring(0,1).toUpperCase() + parts[0].substring(1));
            }

    }

    ///Gets magnatuide of an earthquake
    ///Returns double magnituide
    public double getMagDouble(){
        String[] parts = this.getMagnitude().split("\\s+");
        Double mag = Double.parseDouble(parts[1]);
        return mag;
    };

    ///Gets latituide of an earthquake
    ///Returns double magnituide
    public double getLatDouble()
    {
        return Double.parseDouble(this.geoLat);
    }

    ///Gets longituide of an earthquake
    ///Returns double magnituide
    public double getLonDouble()
    {
        return Double.parseDouble(this.geoLong);
    }


    ///Gets LatLon of an earthquake
    ///For use in google maps
    ///Returns LatLon object
    public LatLng getLatLng()
    {
        LatLng converted = new LatLng(this.getLatDouble(),this.getLonDouble());
        return converted;
    }




}