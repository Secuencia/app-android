package moviles.isaacs.com.isaacs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import moviles.isaacs.com.isaacs.Adapters.ContentAdapter;
import moviles.isaacs.com.isaacs.Adapters.DividerItemDecoration;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

public class ContentsActivity extends AppCompatActivity {

    private ArrayList<Content> listItems;
    private ContentAdapter adapter;
    private MyDBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MyDBHandler(this, null, null, 1);
        listItems = handler.getContents();
        setContentView(R.layout.activity_contents);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(listItems.get(position).getType() == Content.PICTURE){
                    return 1;
                }
                return 2;
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(20, 1));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContentAdapter(this, listItems);
        recyclerView.setAdapter(adapter);

    }
}
