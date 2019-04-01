package honors.testing.mobileplatformdevelopment.models;
//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import org.junit.Test;

import static org.junit.Assert.*;

//Testing class for Earthquake class
public class EarthquakeTest {

    //Setting up a test earthquake
    private Earthquake SetUp(){
        Earthquake e = new Earthquake();
        e.setCategory("EQUK");
        e.setDescription("Origin date/time: Sat, 01 Jan 1999 00:00:00 ; Location: SOUTHERN NORTH SEA ; Lat/long: 53.684,1.139 ; Depth: 10 km ; Magnitude: 2.4");
        e.setGeoLat("53.684");
        e.setGeoLong("1.139");
        e.setLink("http://earthquakes.bgs.ac.uk/earthquakes/recent_events/20190330050239.html");
        e.setPubDate("Sat, 01 Jan 1999 00:00:00");
        e.setTitle("UK Earthquake alert : M 2.4 :SOUTHERN NORTH SEA, Sat, 01 Jan 1999 00:00:00");
        return e;
    }

    //Method testing follows:

    @Test
    public void getMagnitude() {

        Earthquake e = SetUp();
        assertEquals("Magnitude: 2.4",e.getMagnitude());
     }

    @Test
    public void getDepth() {

        Earthquake e = SetUp();
        assertEquals("Depth: 10 km ",e.getDepth());

    }

    @Test
    public void toDate() {

        Earthquake e = SetUp();


        assertEquals(e.toDate(),e.toDate());
    }

    @Test
    public void getLocationPretty() {

        Earthquake e = SetUp();
        assertEquals("SOUTHERN NORTH SEA ",e.getLocationPretty());
    }

    @Test
    public void getMagDouble() {

        Earthquake e = SetUp();
        assertEquals(2.4,e.getMagDouble(),0);
    }

}