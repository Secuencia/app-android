package moviles.isaacs.com.isaacs.models;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nicolas on 9/27/16.
 */

public class Story implements Serializable{

    private int _id;

    private String title;

    private String brief;

    private Date datecreated;

    private Date lastupdated;

    private ArrayList<Content> contents; // Correct way to manage collections with SugarORM?

    // Empty constructor for the ORM
    public Story(){
        datecreated = Calendar.getInstance().getTime(); //Have to obtain current date
        lastupdated = Calendar.getInstance().getTime();
        this.contents = new ArrayList<>();
    }

    public Story(String brief, String title){
        this.brief = brief;
        this.title = title;
        datecreated = Calendar.getInstance().getTime(); //Have to obtain current date
        lastupdated = Calendar.getInstance().getTime(); //Have to obtain current date
        this.contents = new ArrayList<>();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Date getDateCreated() {
        return datecreated;
    }

    public void setDateCreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getLastUpdated() {
        return lastupdated;
    }

    public void setLastUpdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }

    public boolean hasContent(Content content){
        for (Content c : contents){
            if(c.get_id() == content.get_id()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Title: " + this.title + ", Brief: " + this.brief;
    }
}
