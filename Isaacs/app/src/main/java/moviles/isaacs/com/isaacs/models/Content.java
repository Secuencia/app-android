package moviles.isaacs.com.isaacs.models;



import java.util.ArrayList;

/**
 * Created by Nicolas on 9/27/16.
 */

public class Content {

    private int _id;

    private String data;

    private String date_created;

    private String type;

    private ArrayList<Story> stories; // Correct way to manage collections with SugarORM?

    // Empty constructor
    public Content(){

    }

    public Content(String data, String type) {
        this.data = data;
        this.type = type;
        this.date_created = null; //Have to obtain current date
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

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Story> stories) {
        this.stories = stories;
    }
}
