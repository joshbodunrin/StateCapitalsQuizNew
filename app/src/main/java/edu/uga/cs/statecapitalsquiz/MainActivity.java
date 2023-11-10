package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private QuestionData questionData = null;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled( true );
        drawerToggle.syncState();

        // Find the drawer view
        navigationView = findViewById( R.id.nvView );
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem( menuItem );
                    return true;
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            questionData = new QuestionData( this.getApplicationContext());
            if (questionData != null)
                questionData.open();

            InputStream in_s = getAssets().open("StateCapitals.csv");

            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextRow;
            while ((nextRow = reader.readNext()) != null) {

                String state = nextRow[0];
                String capital = nextRow[1];
                String cityOne = nextRow[2];
                String cityTwo = nextRow[3];
                Question question = new Question(state, capital, cityOne, cityTwo);
                new QuestionDBwriter().execute(question);

            }
        } catch (Exception e) {
            Log.e("testing", e.toString());
        }
        questionData.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (questionData != null)
            questionData.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (questionData != null)
            questionData.close();

    }


    public void selectDrawerItem( MenuItem menuItem ) {
        Fragment fragment = null;

        // Create a new fragment based on the used selection in the nav drawer
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_add) {
            fragment = new TakeQuiz();
        } else if (itemId == R.id.menu_review) {
            fragment = new ViewQuizzes();
        } else if (itemId == R.id.menu_help) {
            fragment = new HomeScreen();
        } else if (itemId == R.id.menu_close) {
            finish();
        } else {
            return;
        }

        // Set up the fragment by replacing any existing fragment in the main activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();

        /*
        // this is included here as a possible future modification
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked( true );
        // Set action bar title
        setTitle( menuItem.getTitle());
         */

        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close );
    }

    // onPostCreate is called when activity start-up is complete after onStart()
    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged( @NonNull Configuration newConfig ) {
        super.onConfigurationChanged( newConfig );
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged( newConfig );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        if( drawerToggle.onOptionsItemSelected( item ) ) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    public class QuestionDBwriter extends AsyncTask<Question, Question> {

        @Override
        protected Question doInBackground(Question... questions) {
            questionData.storeQuestion(questions[0]);
            return questions[0];
        }

        @Override
        protected void onPostExecute(Question question) {
            //dont think we need anything for this occasion
        }
    }
}