package tech.onpaper.victor.onpaper;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    setUpViewPager(viewPager);


    TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

    tabs.addTab(tabs.newTab().setText("Right Now"));
    tabs.addTab(tabs.newTab().setText("Out there"));
    tabs.addTab(tabs.newTab().setText("Your Salve"));


  }

  public void setUpViewPager(ViewPager viewPager) {

    CustomeAdapter adapter = new CustomeAdapter(getSupportFragmentManager());
    adapter.addFragment(new ListContentFragment(), "List");
    adapter.addFragment(new TileContentFragment(), "Tile");
    adapter.addFragment(new CardContentFragment(), "Card");
    viewPager.setAdapter(adapter);
  }

  static class CustomeAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public CustomeAdapter(FragmentManager manager) {
      super(manager);
    }

    @Override public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    @Override public int getCount() {
      return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
      mFragmentList.add(fragment);
      mFragmentTitleList.add(title);
    }

    @Override public CharSequence getPageTitle(int position) {
      return mFragmentTitleList.get(position);
    }
  }
}
