package moviles.isaacs.com.isaacs.models;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by Nicolas on 9/27/16.
 */

public class Story extends SugarRecord {

    public String brief;

    public String date_created;

    public String last_modified;

    public String title;

    public ArrayList<Content> contents;

    // Empty constructor for the ORM
    public Story(){

    }


}
