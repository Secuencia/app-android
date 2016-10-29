package moviles.isaacs.com.isaacs.Modules.Contents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import moviles.isaacs.com.isaacs.R;
import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.services.MyDBHandler;

public class ContentsActivity extends AppCompatActivity {

    private ArrayList<Content> listItems;
    private ContentAdapter adapter;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private RecyclerView recyclerView;
    private MyDBHandler handler;
    private boolean showAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
        handler = new MyDBHandler(this, null, null, 1);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(20, 1));
        showAll = false;
        setAllContents();
    }

    private void setAllContents(){
        listItems = handler.getContents();
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
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContentAdapter(this, listItems);
        recyclerView.setAdapter(adapter);
    }

    private void sortByType(){
        ArrayList<Content> textList = handler.getContentsByType(Content.TEXT);
        final ArrayList<Content> imageList = handler.getContentsByType(Content.PICTURE);
        final ArrayList<Content> audioList = handler.getContentsByType(Content.AUDIO);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        sectionAdapter.addSection(new ContentSection(this, Content.TEXT ,R.layout.cell_display_text, textList));
        sectionAdapter.addSection(new ContentSection(this, Content.PICTURE ,R.layout.cell_display_photo, imageList));
        sectionAdapter.addSection(new ContentSection(this, Content.AUDIO ,R.layout.cell_display_audio, audioList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionAdapter);
    }

    public void switchDisplayMode(View view){
        Button btn = (Button)view.findViewById(R.id.content_display);
        showAll = !showAll;
        if(showAll){
            btn.setText("Mostrar Todos");
            sortByType();
        }
        else{
            btn.setText("Filtrar por tipo");
            setAllContents();
        }
    }
}
