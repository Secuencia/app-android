package moviles.isaacs.com.isaacs;

import java.util.List;

import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.models.Story;

/**
 * Created by Nicolas on 10/6/16.
 */

public class PersistenceManager { //CRUD class

    // Should be a singleton that tries to create a new database in the device
    // If there is already a database created, it simply starts a connection

    private static PersistenceManager mInstance = null;

    // Internal properties

    private String mString;

    // Initialization

    private PersistenceManager() {
        mString = "hello";
    }

    public static PersistenceManager getInstance() {
        if(mInstance == null){
            mInstance = new PersistenceManager();
        }
        return mInstance;
    }

    /*
    CRUD: Create; Retrieve; Update; Delete
     */

    // C: Create

    public Content createContent() {
        return null;
    }

    public Story createStory() {
        return null;
    }

    // R: Retrieve

    public Content getContent(int id) {
        return null;
    }

    public List<Content> getContents() {
        return null;
    }

    public List<Content> getStory(int idStory) {
        return null;
    }

    public List<Story> getStories(){
        return null;
    }

    // U: Update

    public Content updateContent(Content modifiedContent) {
        return null;
    }

    public Story updateStory(Story modifiedStory) {
        return null;
    }

    // D: Delete

    public Content removeContent(Content content) {
        return null;
    }

    public Story removeStory(Story story) {
        return null;
    }


    //////



}
