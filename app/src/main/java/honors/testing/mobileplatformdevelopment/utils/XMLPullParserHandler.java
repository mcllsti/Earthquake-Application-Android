package honors.testing.mobileplatformdevelopment.utils;
//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import honors.testing.mobileplatformdevelopment.models.Earthquake;

///Util class that is designed to help with parsing of XML Feed
///This class was designed with help from the following resource:
/// http://theopentutorials.com/tutorials/android/xml/android-simple-xmlpullparser-tutorial/
public class XMLPullParserHandler {
    List<Earthquake> earthquakes;
    private Earthquake earthquake;
    private String text;

    public XMLPullParserHandler() {
        earthquakes = new ArrayList<Earthquake>();
    }

    public List<Earthquake> getEarthquakes() {
        return earthquakes;
    }

    public List<Earthquake> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try { //encapsulate all in a try for safty
            //Setting our new instances
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) { //While not end of document
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            // create a new instance of earthquake
                            earthquake = new Earthquake();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText(); //get Text here
                        break;

                    case XmlPullParser.END_TAG: //Hit the end tag
                        if(earthquake != null) //Only as long as earthquake is not null
                        {
                            //Block If-IfElse where the tag will be used to add data
                            if (tagname.equalsIgnoreCase("item")) {
                                // add employee object to list
                                earthquakes.add(earthquake);
                            } else if (tagname.equalsIgnoreCase("title")) {
                                earthquake.setTitle(text);
                            } else if (tagname.equalsIgnoreCase("description")) {
                                earthquake.setDescription(text);
                            } else if (tagname.equalsIgnoreCase("link")) {
                                earthquake.setLink(text);
                            } else if (tagname.equalsIgnoreCase("pubDate")) {
                                earthquake.setPubDate(text);
                            } else if (tagname.equalsIgnoreCase("category")) {
                                earthquake.setCategory(text);
                            } else if (tagname.equalsIgnoreCase("lat")) {
                                earthquake.setGeoLat(text);
                            } else if (tagname.equalsIgnoreCase("long")) {
                                earthquake.setGeoLong(text);
                            }
                        }


                        break;

                    default:
                        break;
                }
                eventType = parser.next(); //get next one
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return earthquakes; //returns our datastructure with our data
    }
}