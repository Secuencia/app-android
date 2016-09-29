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

    public ArrayList<Story> stories;

    // Empty constructor for the ORM
    public Content(){

    }

}
