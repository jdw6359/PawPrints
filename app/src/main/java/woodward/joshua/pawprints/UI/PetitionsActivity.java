package woodward.joshua.pawprints.UI;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.List;

import woodward.joshua.pawprints.Backend.Petition;
import woodward.joshua.pawprints.Backend.PetitionAdapter;
import woodward.joshua.pawprints.Backend.PrintsAPI;
import woodward.joshua.pawprints.R;


public class PetitionsActivity extends ListActivity {

    private final String TAG=PetitionsActivity.class.getSimpleName();

    protected ListView mPetitionsListView;
    protected TextView mEmptyTextView;

    protected PrintsAPI mPrintsAPI;

    protected List<Petition> mPetitions;

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
        mPrintsAPI=new PrintsAPI(PetitionsActivity.this);

        //get a call object for petitions
        Call petitionCall=mPrintsAPI.getPetitions();

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

                            setPetitionObjects(petitionJsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String[] mPetitionNames=new String[mPetitions.size()];
                                    for(int i=0;i<mPetitions.size();i++){
                                        mPetitionNames[i]=mPetitions.get(i).getTitle();
                                    }

                                    if(getListView().getAdapter()==null){
                                        PetitionAdapter recipientsAdapter =new PetitionAdapter(PetitionsActivity.this,mPetitions);
                                        getListView().setAdapter(recipientsAdapter);
                                    }else{
                                        ((PetitionAdapter)getListView().getAdapter()).refill(mPetitions);
                                    }

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

        mPetitionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String petitionId=mPetitions.get(i).getId();

                //starts new activity, passing in petition id as reference
                Intent petitionDetailIntent=new Intent(PetitionsActivity.this, PetitionDetail.class);
                petitionDetailIntent.putExtra("PetitionId",petitionId);
                startActivity(petitionDetailIntent);

            }
        });
        //end on click listener for Petitions List View


    };//end onCreate()


    /* Takes json and creates list of Petition objects */
    protected void setPetitionObjects(JSONArray petitionJsonData) throws JSONException{

        //initialize mPetitions as array of objects that hold petition information
        mPetitions=new ArrayList<Petition>();

        for(int i=0;i<petitionJsonData.length();i++){
            JSONObject currentJSONPetition=petitionJsonData.getJSONObject(i);

            Petition petition=new Petition();

            String id=currentJSONPetition.getString("_id");
            String title=currentJSONPetition.getString("title");
            String description=currentJSONPetition.getString("description");
            String author=currentJSONPetition.getString("author");
            long submitted=currentJSONPetition.getLong("submitted");
            int votes=currentJSONPetition.getInt("votes");
            int minimumVotes=currentJSONPetition.getInt("minimumVotes");

            petition.setId(id);
            petition.setTitle(title);
            petition.setDescription(description);
            petition.setAuthor(author);
            petition.setSubmitted(submitted);
            petition.setVotes(votes);
            petition.setMinimumVotes(minimumVotes);

            mPetitions.add(petition);
        }
    }



}
