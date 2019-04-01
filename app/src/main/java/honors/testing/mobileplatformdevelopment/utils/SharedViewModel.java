package honors.testing.mobileplatformdevelopment.utils;
//
// Name                DARYL MCALLISTER
// Student ID           S1222204
// Programme of Study   Computing
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import honors.testing.mobileplatformdevelopment.MainActivity;
import honors.testing.mobileplatformdevelopment.models.Earthquake;

import static java.security.AccessController.getContext;

///Class that acts as a wrapper around datastructure containing Earthquakes
///This ViewModel will allow the activiy and fragments to share common data
///This is used to ahear to the lifecycle and be more conventional
///Class also calls code that allows the data to be constantly updated
public class SharedViewModel extends ViewModel {
    //MasterList is a constant track of ALL earthquakes
    private MutableLiveData<List<Earthquake>> masterList = new MutableLiveData<>();
    //OutputList is only earthquakes that should be showen in output
    private MutableLiveData<List<Earthquake>> outputList = new MutableLiveData<>();


    public void setMasterList(List<Earthquake> input){ masterList.setValue(input); }
    public void postMasterList(List<Earthquake> input){ masterList.postValue(input); }
    public LiveData<List<Earthquake>> getMasterList(){
        return masterList;
    }


    public void setOutputList(List<Earthquake> input){
        outputList.setValue(input);
    }
    public void postOutputList(List<Earthquake> input){
        outputList.postValue(input);
    }
    public LiveData<List<Earthquake>> getOutputList(){
        return outputList;
    }

Context context;





    //Method that is public to allow the start of Async parsing of XML feed
    //Called only once at program start, will self call through AsyncTask
    public void UpdateMaster(Context con){

        if(con != null){
            context = con;
        }

        AsyncObtainData task = new AsyncObtainData();
        task.execute(); //Execute the task

    }

    private void ErrorOccured(){


        new Handler(Looper.getMainLooper()).post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Could not get earthquakes. Please check internet connection. The app will refresh after 20 seconds",Toast.LENGTH_LONG).show();
                    }});


        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        UpdateMaster(null);
                    }
                },
                20000
        );
    }

    ///Private as should only be ascessed through UpdateMaster()
    ///AscynTask that updates the MasterList.
    ///Post Execute has timer set 1 minute and will call UpdateMaster() to start again
    private class AsyncObtainData extends AsyncTask<Void,Void,Void> {
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        List<Earthquake> earthquakes = null;


        @Override
        ///Executes after doInBackground
        ///Used to only set masterList and outpustList
        ///Calls UpdateMaster() on timer to continually update
        protected  void onPostExecute(Void result) {
            masterList.setValue(earthquakes);

            //Only null at program start, do no want to continually overwrite afterwords
            if(outputList.getValue() == null || outputList.getValue().size() == 0){
                outputList.setValue(masterList.getValue());
            }


            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            UpdateMaster(null);
                        }
                    },
                    60000
            );
        }

        @Override
        ///Async is done in background
        /// Uses XMLPullParserHandler class to parse XML Feed
        /// Feed is parsed into earthquakkes variable
        protected Void doInBackground(Void... params) {
            try
            {

                aurl = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                ///Class to encapsulate our prasing
                XMLPullParserHandler parser = new XMLPullParserHandler();
                earthquakes = parser.parse(yc.getInputStream());

                in.close();
            }
            catch (IOException ae)
            {
                //If we hit here, there is an error
                Log.e("ERROR! ERROR! ERROR!", ae.getLocalizedMessage());
                earthquakes = new ArrayList<Earthquake>();
                ErrorOccured();
            }

            return null;
        }


    }
}
