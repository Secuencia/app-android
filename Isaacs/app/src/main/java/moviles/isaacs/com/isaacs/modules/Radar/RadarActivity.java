package moviles.isaacs.com.isaacs.modules.Radar;

/**
 * Created by sfrsebastian on 10/29/16.
 */

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import moviles.isaacs.com.isaacs.R;

public class RadarActivity extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Location location;
    private MapRadarFragment mapFragment;
    private FeedFragment feedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            location =  (Location)extras.get("location");
        }
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle fragmentBundle = new Bundle();
        if(location != null){
            fragmentBundle.putDouble("latitude", location.getLatitude());
            fragmentBundle.putDouble("longitude", location.getLongitude());
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mapFragment = new MapRadarFragment();
        mapFragment.setArguments(fragmentBundle);
        feedFragment = new FeedFragment();
        feedFragment.setArguments(fragmentBundle);
        adapter.addFragment(mapFragment, "Mapa");
        adapter.addFragment(feedFragment, "Feed");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void refreshMap(View view){
        mapFragment.refreshMap(view);
    }
}