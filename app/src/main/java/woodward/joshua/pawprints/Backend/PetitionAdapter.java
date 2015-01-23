package woodward.joshua.pawprints.Backend;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import woodward.joshua.pawprints.R;

/**
 * Created by Joshua on 1/22/2015.
 */
public class PetitionAdapter extends ArrayAdapter<Petition> {

    protected Context mContext;
    protected List<Petition> mPetitions;

    public PetitionAdapter(Context context, List<Petition> petitions){
        super(context, R.layout.petition_item, petitions);

        mContext=context;
        mPetitions=petitions;
    }

    //we need to override this so that we are utilizing and inflating message_item.xml
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //follow 'ViewHolder' design pattern to adapt custom list layout
        ViewHolder holder;
        if(convertView==null){
            //create it for the first time
            convertView= LayoutInflater.from(mContext).inflate(R.layout.petition_item,null);
            holder=new ViewHolder();
            holder.petitionNameLabel=(TextView)convertView.findViewById(R.id.petitionNameTextView);
            holder.petitionAuthorLabel=(TextView)convertView.findViewById(R.id.petitionAuthorTextView);
            holder.votesLabel=(TextView)convertView.findViewById(R.id.petitionVotesTextView);

            convertView.setTag(holder);
        }else{
            //it already exists, we just need to change the data
            holder=(ViewHolder)convertView.getTag();
        }

        //get the message object in question
        Petition petition=mPetitions.get(position);

        //set petitionNameLabel
        holder.petitionNameLabel.setText(petition.getTitle());

        //set petitionAuthorLabel
        holder.petitionAuthorLabel.setText(petition.getAuthor());

        //format string for votes label
        String votesText=petition.getVotes() + "/" + petition.getMinimumVotes();
        holder.votesLabel.setText(votesText);

        return convertView;
    }

    //follows view holder pattern such that view holder object will hold the state
    private static class ViewHolder{
        TextView petitionNameLabel;
        TextView petitionAuthorLabel;
        TextView votesLabel;
    }

    public void refill(List<Petition> messages){
        //clear current data
        mPetitions.clear();
        mPetitions.addAll(messages);
        notifyDataSetChanged();
    }
}
