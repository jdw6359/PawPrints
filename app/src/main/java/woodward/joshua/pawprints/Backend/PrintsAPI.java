package woodward.joshua.pawprints.Backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Joshua on 1/19/2015.
 */
public class PrintsAPI {

    private final String TAG=PrintsAPI.class.getSimpleName();

    //API key for https://pawprints.rit.edu
    private final String mApiKey="1cb28003690a42bc96e7b5267dddbb79";
    private final String mPawPrintsURL="https://pawprints.rit.edu";

    private Context mContext;

    /* Constructor for the PrintsAPI */
    public PrintsAPI(Context context){
        mContext=context;
    };

    //determines if the network is available
    private boolean isNetworkAvailable() {
        ConnectivityManager manager=(ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        Boolean isAvailable=false;
        if(networkInfo != null && networkInfo.isConnected()){
            //we have a network and it is connected
            isAvailable=true;
        }
        return isAvailable;
    }

    public Call getPetitions(){

        if(isNetworkAvailable()) {

            //create url for the getPetitions request
            //structure: /v1/petitions?key={key}&limit={limit}
            String petitionsURL = mPawPrintsURL + "/v1/petitions?key=" + mApiKey;

            Log.d(TAG,"Petitions URL: " + petitionsURL);

            //create http client
            OkHttpClient client = new OkHttpClient();
            Request petitionsRequest = new Request.Builder().url(petitionsURL).build();

            Call call=client.newCall(petitionsRequest);

            return call;
        }else{
            return null;
        }
    }

    public Call getPetitionDetail(String petitionId){

        if(isNetworkAvailable()){

            //create url for the getPetitionsDetail requist
            //structure: /v1/petitions/{petitionId}?key={key}
            String petitionDetailURL=mPawPrintsURL + "/v1/petitions/" + petitionId + "?key=" + mApiKey;

            Log.d(TAG,"Petition Detail URL: " + petitionDetailURL);

            //create Http client
            OkHttpClient client=new OkHttpClient();
            Request petitionDetailRequest=new Request.Builder().url(petitionDetailURL).build();

            Call call=client.newCall(petitionDetailRequest);
            return call;
        }else{
            return null;
        }

    }

}
