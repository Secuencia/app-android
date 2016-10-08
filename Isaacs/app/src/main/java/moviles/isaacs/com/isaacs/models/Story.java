package moviles.isaacs.com.isaacs.models;



import java.util.ArrayList;

/**
 * Created by Nicolas on 9/27/16.
 */

public class Story{

    private int _id;

    private String brief;

    private String dateCreated;

    private String lastModified;

    private String title;

    private ArrayList<Content> contents; // Correct way to manage collections with SugarORM?

    // Empty constructor for the ORM
    public Story(){

    }

    public Story(String brief, String title){
        this.brief = brief;
        this.title = title;
        dateCreated = null; //Have to obtain current date
        lastModified = null; //Have to obtain current date
        this.contents = new ArrayList<>();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }
}
