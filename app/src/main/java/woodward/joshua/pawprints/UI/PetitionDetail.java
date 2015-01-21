package woodward.joshua.pawprints.UI;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.okhttp.Call;

import woodward.joshua.pawprints.Backend.PrintsAPI;
import woodward.joshua.pawprints.R;

public class PetitionDetail extends Activity {

    private final String TAG=PetitionDetail.class.getSimpleName();
    private PrintsAPI mPrintsAPI;

    private String mPetitionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition_detail);

        //get the petition id of the petition we will be examining
        mPetitionId=getIntent().getExtras().getString("PetitionId");

        //create a new API object
        mPrintsAPI=new PrintsAPI(PetitionDetail.this);

        //get a call object
        Call petitionDetailCall=mPrintsAPI.getPetitionDetail(mPetitionId);

        Log.d(TAG, "Petition Detail Call Built");


        Toast.makeText(PetitionDetail.this, "loaded up w/ petition id: " + mPetitionId, Toast.LENGTH_LONG).show();
    }
}
