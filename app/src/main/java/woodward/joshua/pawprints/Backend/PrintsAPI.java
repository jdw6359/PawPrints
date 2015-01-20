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

    public JSONObject getPetitions(){

        if(isNetworkAvailable()){

            //create url for the getPetitions request
            //structure: /v1/petitions?key={key}&limit={limit}
            String petitionsURL=mPawPrintsURL + "/v1/petitions?key=" + mApiKey;

            //create http client
            OkHttpClient client=new OkHttpClient();
            Request petitionsRequest=new Request.Builder().url(petitionsURL).build();

            Call call=client.newCall(petitionsRequest);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    //failure state for request
                    Log.d(TAG,"There was an unsuccessful request made to the api");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    //success state for request
                    Log.d(TAG, response.toString());
                }
            });
        }else{
            Toast.makeText(mContext,"network is unavailable",Toast.LENGTH_LONG).show();
        }
        return new JSONObject();
    }

}
