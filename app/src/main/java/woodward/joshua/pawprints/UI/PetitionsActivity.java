package woodward.joshua.pawprints.UI;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import woodward.joshua.pawprints.Backend.Petition;
import woodward.joshua.pawprints.Backend.PrintsAPI;
import woodward.joshua.pawprints.R;


public class PetitionsActivity extends ListActivity {

    private final String TAG=PetitionsActivity.class.getSimpleName();

    protected ListView mPetitionsListView;
    protected TextView mEmptyTextView;

    protected Petition[] mPetitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petitions);

        //get views
        mPetitionsListView=getListView();
        mEmptyTextView=(TextView)findViewById(android.R.id.empty);

        //set mEmptyTextView as the empty text for mPetitionsListView
        mPetitionsListView.setEmptyView(mEmptyTextView);


        //create PrintsAPI object to make requests to backend
        //new PrintsAPI(Context)
        PrintsAPI printsAPI=new PrintsAPI(PetitionsActivity.this);

        //get a request for petitions
        Call petitionCall=printsAPI.getPetitions();

        //if call is not null, make a call to get the petitions
        if(petitionCall!=null){
            //make a call to get the petitions
            petitionCall.enqueue(new Callback() {

                @Override
                public void onFailure(Request request, IOException e) {
                    //failure state for request
                    Log.d(TAG, "There was an unsuccessful request made to the api");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    //check to make sure that response is successful
                    if(response.isSuccessful()){
                        //success state for request
                        try {
                            String petitionData=response.body().string();
                            JSONArray petitionJsonData=new JSONArray(petitionData);

                            Log.v(TAG,petitionJsonData.toString());

                            setPetitionObjects(petitionJsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String[] mPetitionNames=new String[mPetitions.length];
                                    for(int i=0;i<mPetitions.length;i++){
                                        mPetitionNames[i]=mPetitions[i].getTitle();
                                    }

                                    ArrayAdapter<String> petitionsAdapter=new ArrayAdapter<String>(PetitionsActivity.this,android.R.layout.simple_list_item_1,mPetitionNames);
                                    mPetitionsListView.setAdapter(petitionsAdapter);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //alert user that the response was not successful
                    }
                }
            });
        }else{
            Toast.makeText(PetitionsActivity.this,"Request Broken",Toast.LENGTH_LONG).show();
        }
    };//end getPetitions() function



    protected void setPetitionObjects(JSONArray petitionJsonData) throws JSONException{

        //initialize mPetitions as array of objects that hold petition information
        mPetitions=new Petition[petitionJsonData.length()];

        for(int i=0;i<petitionJsonData.length();i++){
            JSONObject currentJSONPetition=petitionJsonData.getJSONObject(i);

            Petition petition=new Petition();

            String title=currentJSONPetition.getString("title");
            String description=currentJSONPetition.getString("description");
            String author=currentJSONPetition.getString("author");
            long submitted=currentJSONPetition.getLong("submitted");
            int votes=currentJSONPetition.getInt("votes");
            int minimumVotes=currentJSONPetition.getInt("minimumVotes");

            petition.setTitle(title);
            petition.setDescription(description);
            petition.setAuthor(author);
            petition.setSubmitted(submitted);
            petition.setVotes(votes);
            petition.setMinimumVotes(minimumVotes);

            mPetitions[i]=petition;
        }

        Log.v(TAG,"Length of mPetitions array: " + mPetitions.length);
    }

}
