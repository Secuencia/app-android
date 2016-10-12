package moviles.isaacs.com.isaacs.models;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nicolas on 9/27/16.
 */

public class Content {

    // Constants

    public static final int TEXT = 0;

    public static final int PICTURE = 1;

    public static final int AUDIO = 2;

    // Variables

    private int _id;

    private int type;

    private String data;

    private Date dateCreated;

    private Date lastUpdated;

    private Double lat;

    private Double lon;

    private ArrayList<Story> stories; // Correct way to manage collections with SugarORM?

    // Empty constructor
    public Content(){

    }

    public Content(String data, int type) {
        this.data = data;
        this.type = type;
        this.dateCreated = Calendar.getInstance().getTime();
        this.lastUpdated = Calendar.getInstance().getTime();
        this.stories = new ArrayList<>();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Story> stories) {
        this.stories = stories;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
