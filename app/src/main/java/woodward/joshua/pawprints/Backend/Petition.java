package woodward.joshua.pawprints.Backend;

/**
 * Created by Joshua on 1/20/2015.
 */
public class Petition {

    private String mTitle;
    private String mDescription;
    private String mAuthor;
    private long mSubmitted;
    private int mVotes;
    private int mMinimumVotes;
    private String mId;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public long getSubmitted() {
        return mSubmitted;
    }

    public void setSubmitted(long submitted) {
        mSubmitted = submitted;
    }

    public int getVotes() {
        return mVotes;
    }

    public void setVotes(int votes) {
        mVotes = votes;
    }

    public int getMinimumVotes() {
        return mMinimumVotes;
    }

    public void setMinimumVotes(int minimumVotes) {
        mMinimumVotes = minimumVotes;
    }
}
