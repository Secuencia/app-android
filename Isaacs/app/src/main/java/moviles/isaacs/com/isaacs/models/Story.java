package moviles.isaacs.com.isaacs.models;



import java.util.ArrayList;

/**
 * Created by Nicolas on 9/27/16.
 */

public class Story{

    private int _id;

    private String brief;

    private String date_created;

    private String last_modified;

    private String title;

    private ArrayList<Content> contents; // Correct way to manage collections with SugarORM?

    // Empty constructor for the ORM
    public Story(){

    }

    public Story(String brief, String title){
        this.brief = brief;
        this.title = title;
        date_created = null; //Have to obtain current date
        last_modified = null; //Have to obtain current date
        this.contents = new ArrayList<>();
    }


}
