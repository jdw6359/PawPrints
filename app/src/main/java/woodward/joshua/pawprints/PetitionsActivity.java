package woodward.joshua.pawprints;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class PetitionsActivity extends ListActivity {

    protected ListView mPetitionsListView;
    protected TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petitions);

        //get views
        mPetitionsListView=getListView();
        mEmptyTextView=(TextView)findViewById(android.R.id.empty);

        //set mEmptyTextView as the empty text for mPetitionsListView
        mPetitionsListView.setEmptyView(mEmptyTextView);

    }
}
