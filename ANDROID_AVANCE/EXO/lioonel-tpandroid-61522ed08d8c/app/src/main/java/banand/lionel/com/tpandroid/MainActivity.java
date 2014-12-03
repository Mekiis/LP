package banand.lionel.com.tpandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import banand.lionel.com.tpandroid.Adapters.MenuAdapter;
import banand.lionel.com.tpandroid.Fragments.ImagesFragment;
import banand.lionel.com.tpandroid.Items.MenuMediasItem;
import banand.lionel.com.tpandroid.Service.TpAndroidService;


public class MainActivity extends Activity implements ImagesFragment.OnElementSelectedListener{

    //menu array
    private ArrayList<MenuMediasItem> mMenuMediasItem;
    private DrawerLayout mDrawerLayout;
    private ListView mLVMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        startService(new Intent(this, TpAndroidService.class));
        //init properties
        mMenuMediasItem = new ArrayList<MenuMediasItem>();
        mMenuMediasItem.add(new MenuMediasItem(R.drawable.ic_action_picture, getResources().getString(R.string.images)));
        mMenuMediasItem.add(new MenuMediasItem(R.drawable.ic_action_email, getResources().getString(R.string.textes)));
        mMenuMediasItem.add(new MenuMediasItem(R.drawable.ic_action_video, getResources().getString(R.string.videos)));

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        //to allow clic on icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer1,
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

        mDrawerTitle = getResources().getText(R.string.drawer_open);
        mLVMenu = (ListView) findViewById(R.id.lvMediaType);

        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), mMenuMediasItem);
        mLVMenu.setAdapter(menuAdapter);

        mLVMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = null;

                switch (i) {
                    case 0:
                        fragment = new ImagesFragment();
                        break;
                    case 1:
                        fragment = new ImagesFragment();
                        ((ImagesFragment)fragment).setHelloParameters(mMenuMediasItem.get(1).mName,
                                mMenuMediasItem.get(1).mIcon);
                        break;
                    case 2:
                        fragment = new ImagesFragment();
                        ((ImagesFragment)fragment).setHelloParameters(mMenuMediasItem.get(2).mName,
                                mMenuMediasItem.get(2).mIcon);
                        break;
                    default:
                        break;

                }

                if(fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                    ft.replace(R.id.main_content, fragment).commit();
                    mLVMenu.setItemChecked(i, true);
                    mLVMenu.setSelection(i);
                    mTitle = mMenuMediasItem.get(i).mName;
                    getActionBar().setTitle(mTitle);
                    mDrawerLayout.closeDrawers();
                }
            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getActionBar().setTitle(getResources().getString(R.string.drawer_close));
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onElementSelected() {
        getActionBar().setTitle("Button Pressed");
    }
}
