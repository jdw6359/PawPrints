package woodward.joshua.pawprints.UI;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import woodward.joshua.pawprints.Backend.Petition;
import woodward.joshua.pawprints.Backend.PrintsAPI;
import woodward.joshua.pawprints.R;

public class PetitionDetail extends Activity {

    private final String TAG = PetitionDetail.class.getSimpleName();
    private PrintsAPI mPrintsAPI;
    private String mPetitionId;
    private Petition mPetition;

    @InjectView(R.id.titleTextView) TextView mTitleTextView;
    @InjectView(R.id.authorTextView) TextView mAuthorTextView;
    @InjectView(R.id.votesTextView) TextView mVotesTextView;
    @InjectView(R.id.minVotesTextView) TextView mMinVotesTextView;
    @InjectView(R.id.descriptionTextView) TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition_detail);

        ButterKnife.inject(this);

        //get the petition id of the petition we will be examining
        mPetitionId = getIntent().getExtras().getString("PetitionId");

        //create a new API object
        mPrintsAPI = new PrintsAPI(PetitionDetail.this);

        //get a call object
        Call petitionDetailCall = mPrintsAPI.getPetitionDetail(mPetitionId);

        //check to make sure we have a Call object to work with
        if (petitionDetailCall != null) {

            petitionDetailCall.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    //handle failure state
                    Log.d(TAG, "There was an unsuccessful request made to the api");
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    //handle success state
                    if (response.isSuccessful()) {

                        try {

                            //create a new JSON object with the response data
                            String petitionDetail = response.body().string();
                            JSONObject petitionJSONObject = new JSONObject(petitionDetail);

                            setPetition(petitionJSONObject);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {
                        //alert user that response is not successful
                    }
                }
            });
        } else {
            Toast.makeText(PetitionDetail.this, "Request Broken", Toast.LENGTH_LONG).show();
        }
    }//end on create

    protected void setPetition(JSONObject petitionJSONObject) throws JSONException{

        mPetition=new Petition();

        mPetition.setId(petitionJSONObject.getString("_id"));
        mPetition.setTitle(petitionJSONObject.getString("title"));
        mPetition.setAuthor(petitionJSONObject.getString("author"));
        mPetition.setVotes(petitionJSONObject.getInt("votes"));
        mPetition.setMinimumVotes(petitionJSONObject.getInt("minimumVotes"));
        mPetition.setSubmitted(petitionJSONObject.getLong("submitted"));
        mPetition.setDescription(petitionJSONObject.getString("description"));

    }

    protected void updateDisplay(){
        mTitleTextView.setText(mPetition.getTitle());
        mAuthorTextView.setText(mPetition.getAuthor());
        mDescriptionTextView.setText(mPetition.getDescription());

        String votes=mPetition.getVotes()+"";
        String minVotes=mPetition.getMinimumVotes()+"";

        mVotesTextView.setText(votes);
        mMinVotesTextView.setText(minVotes);
    }

}
