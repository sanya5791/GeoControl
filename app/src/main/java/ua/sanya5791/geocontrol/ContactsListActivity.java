package ua.sanya5791.geocontrol;


import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactsListActivity
//        extends ActionBarActivity
                                    extends ListActivity
                                    implements LoaderManager.LoaderCallbacks<Cursor>,
                                                SearchView.OnQueryTextListener{

        // This is the Adapter being used to display the list's data
        SimpleCursorAdapter mAdapter;

        // These are the Contacts rows that we will retrieve
        static final String[] PROJECTION = new String[] {
                ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME,
//                ContactsContract.Data.DATA1,
//                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };

        // This is the select criteria
        static final String SELECTION = "((" +
                ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
                ContactsContract.Data.DISPLAY_NAME + " != '' ))";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Create a progress bar to display while the list loads
            ProgressBar progressBar = new ProgressBar(this);
//            progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//                    LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            progressBar.setIndeterminate(true);
            getListView().setEmptyView(progressBar);

            // Must add the progress bar to the root of the layout
            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            root.addView(progressBar);

            // For the cursor adapter, specify which columns go into which views
            String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME,
//                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER
            };
            int[] toViews = {android.R.id.text1,
                             android.R.id.text2}; // The TextView in simple_list_item_1

            // Create an empty adapter we will use to display the loaded data.
            // We pass null for the cursor, then update it in onLoadFinished()
            mAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2, null,
                    fromColumns, toViews, 0);
            setListAdapter(mAdapter);

            // Prepare the loader.  Either re-connect with an existing one,
            // or start a new one.
            getLoaderManager().initLoader(0, null, this);
        }

        // Called when a new Loader needs to be created
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            Loader<Cursor> c = new CursorLoader(this,
                    ContactsContract.Contacts.CONTENT_URI,
//                    ContactsContract.Data.CONTENT_URI,
                    null, null, null, null);
//                    PROJECTION, SELECTION, null, null);
            return c;
        }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

//    // Called when a previously created loader has finished loading
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        // Swap the new cursor in.  (The framework will take care of closing the
//        // old cursor once we return.)
//        mAdapter.swapCursor(data);
//    }

    // Called when a previously created loader is reset, making the data unavailable
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
        Cursor cursor = (Cursor) mAdapter.getItem(position);
        Toast.makeText(this, cursor.getString(1), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return false;
    }
}
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/