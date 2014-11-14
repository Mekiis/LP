package lp.kazuya.mediaview;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import lp.kazuya.mediaview.Fragments.MediaFragment;
import lp.kazuya.mediaview.Fragments.Menu.DrawerItemCustomAdapter;
import lp.kazuya.mediaview.Fragments.Menu.ObjectDrawerItem;


public class MainActivity extends Activity implements MediaFragment.OnElementSelectedListener {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // Initialize properties for Drawer Layout
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Add items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_images, (getResources().getStringArray(R.array.navigation_drawer_items_array))[0]);
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_texts, (getResources().getStringArray(R.array.navigation_drawer_items_array))[1]);
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_audios, (getResources().getStringArray(R.array.navigation_drawer_items_array))[2]);
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_videos, (getResources().getStringArray(R.array.navigation_drawer_items_array))[3]);

        // Adapter to Navigation Drawer
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.menu_listview_item, drawerItem);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_menu,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onElementSelected() {
        // TODO : Function call by fragment
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        private void selectItem(int position) {

            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new MediaFragment();
                    ( (MediaFragment) fragment).setType(MediaFragment.EType.Image);
                    break;
                case 1:
                    fragment = new MediaFragment();
                    ( (MediaFragment) fragment).setType(MediaFragment.EType.Text);
                    break;
                case 2:
                    fragment = new MediaFragment();
                    ( (MediaFragment) fragment).setType(MediaFragment.EType.Audio);
                    break;
                case 3:
                    fragment = new MediaFragment();
                    ( (MediaFragment) fragment).setType(MediaFragment.EType.Video);
                    break;
                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}
