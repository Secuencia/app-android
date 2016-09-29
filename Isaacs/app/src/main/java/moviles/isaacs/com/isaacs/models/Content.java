package moviles.isaacs.com.isaacs.models;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by Nicolas on 9/27/16.
 */

public class Content extends SugarRecord {

    public String data;

    public String date_created;

    public String type;

    public ArrayList<Story> stories; // Correct way to manage collections with SugarORM?

    // Empty constructor for the ORM
    public Content(){

    }

    public Content(String data, String type) {
        this.data = data;
        this.type = type;
        this.date_created = null; //Have to obtain current date
        this.stories = new ArrayList<>();
    }

}
