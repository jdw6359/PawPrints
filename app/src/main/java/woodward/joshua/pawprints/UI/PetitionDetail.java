package woodward.joshua.pawprints.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import woodward.joshua.pawprints.R;

public class PetitionDetail extends Activity {

    private String mPetitionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petition_detail);

        mPetitionId=getIntent().getExtras().getString("PetitionId");


        Toast.makeText(PetitionDetail.this, "loaded up w/ petition id: " + mPetitionId, Toast.LENGTH_LONG).show();
    }
}
