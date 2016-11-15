package spit.matrix2017.Activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import spit.matrix2017.R;

public class EventDetails
        extends AppCompatActivity
{
    private boolean isFavouriteEvent;
    private boolean isReminderSet;
    FloatingActionButton fab;
    private String event_name;
    private MenuItem mi_reminder;
    private long mEventID;
    private boolean visitedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        visitedCalendar = false;

        //get intent from eventlist adapter
        if (getIntent().getStringExtra("name") != null && getSupportActionBar() != null) {
            event_name = getIntent().getStringExtra("name");
            this.setTitle(event_name);
        }
        else
            this.setTitle("Some event");

        setDescription(getIntent().getStringExtra("description"));
        setVenueAndTime(getIntent().getStringExtra("venue"), getIntent().getStringExtra("time"));
        setRules();
        setPrizes();
        setContacts(getIntent().getStringExtra("contact1name"), getIntent().getLongExtra("contact1no", 9999999999l), getIntent().getStringExtra("contact2name"), getIntent().getLongExtra("contact2no", 9999999999l));

        isFavouriteEvent = getIntent().getIntExtra("favorite", 0) == 1;
        isReminderSet = getIntent().getIntExtra("reminder", 0) == 1;

        ImageView mainImageView = (ImageView) findViewById(R.id.main_imageView);
        assert mainImageView != null;
        mainImageView.setImageResource(getIntent().getIntExtra("image", R.drawable.virtual_stock_market));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        if (isFavouriteEvent)
            fab.setImageResource(R.drawable.svg_favorite_white_48px);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavouriteEvent = !isFavouriteEvent;
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://spit.matrix2017.provider");
                String selection = "name = ?";
                String[] selectionArgs = {getIntent().getStringExtra("name")};
                ContentValues cv = new ContentValues();

                if (isFavouriteEvent) {
                    cv.put("favorite", 1);
                    contentResolver.update(uri, cv, selection, selectionArgs);
                    Toast.makeText(EventDetails.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    fab.setImageResource(R.drawable.svg_favorite_white_48px);
                } else {
                    cv.put("favorite", 0);
                    contentResolver.update(uri, cv, selection, selectionArgs);
                    fab.setImageResource(R.drawable.ic_favorite_border_white_48px);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        mi_reminder = menu.findItem(R.id.action_set_reminder);
        if(isReminderSet)
            mi_reminder.setIcon(R.drawable.svg_alarm_on_white_48px);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_reminder:
                setReminder();
                break;
            case R.id.action_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Check out the event '"+event_name+"' at Matrix 17! For more details, download the official app from here:\n"+getResources().getString(R.string.playstore_link));
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share event via"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitle(final String title) {
        /*Code to make title visible only in collapsed state*/
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_event);

        assert collapsingToolbarLayout != null;
        assert appBarLayout != null;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(title); // setting title of page
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    private void setDescription(String description) {
        AppCompatTextView descriptionTextView = (AppCompatTextView) findViewById(R.id.description_textView);
        assert descriptionTextView != null;
        descriptionTextView.setText(description);
    }

    private void setVenueAndTime(String venue, String time) {
        AppCompatTextView venueTimeTextView = (AppCompatTextView) findViewById(R.id.venue_time_textView);
        assert venueTimeTextView != null;
        venueTimeTextView.setText(venue + " (" + time + ")");
    }

    private void setRules() {

        ArrayList<String> rulesList = new ArrayList<>();
        // TODO: 11/1/2016 add logic to get rules into above arraylist
        rulesList.add("Some Rule");
        rulesList.add("Much Rule");
        rulesList.add("Wow Rule");

        StringBuilder textViewString = new StringBuilder();

        for (String rule : rulesList) {
            textViewString.append("\u2022"); //bullet
            textViewString.append(" ");
            textViewString.append(rule);
            textViewString.append("\n");
        }
        textViewString.deleteCharAt(textViewString.length() - 1);

        TextView rulesTextView = (TextView) findViewById(R.id.rules_textView);
        assert rulesTextView != null;
        rulesTextView.setText(textViewString);
    }

    private void setPrizes() {
        ArrayList<String> prizesList = new ArrayList<>();
        // TODO: 11/1/2016 Add logic to get prizes into above arraylist
        prizesList.add("Some Prize");
        prizesList.add("Much Gift");
        prizesList.add("Wow Cash Prize");

        StringBuilder textViewString = new StringBuilder();

        for (String rule : prizesList) {
            textViewString.append("\u25BA"); //right arrow
            textViewString.append(" ");
            textViewString.append(rule);
            textViewString.append("\n");
        }
        textViewString.deleteCharAt(textViewString.length() - 1);

        TextView prizesTextView = (TextView) findViewById(R.id.prizes_textView);
        assert prizesTextView != null;
        prizesTextView.setText(textViewString);
    }

    private void setContacts(final String name1, final long number1, final String name2, final long number2) {
        TextView contactTextViewOne = (TextView) findViewById(R.id.contact_name_one);
        TextView contactTextViewTwo = (TextView) findViewById(R.id.contact_name_two);

        ImageButton callOne = (ImageButton) findViewById(R.id.call_contact_person_one);
        ImageButton saveOne = (ImageButton) findViewById(R.id.save_contact_person_one);
        ImageButton callTwo = (ImageButton) findViewById(R.id.call_contact_person_two);
        ImageButton saveTwo = (ImageButton) findViewById(R.id.save_contact_person_two);

        assert contactTextViewOne != null;
        contactTextViewOne.setText(name1 + "\n" + number1);
        assert contactTextViewTwo != null;
        contactTextViewTwo.setText(name2 + "\n" + number2);

        View.OnClickListener callOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                switch (v.getId()) {
                    case R.id.call_contact_person_one:
                        intent.setData(Uri.parse("tel:" + number1));
                        break;
                    case R.id.call_contact_person_two:
                        intent.setData(Uri.parse("tel:" + number2));
                        break;
                }
                startActivity(intent);
            }
        };

        View.OnClickListener saveOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                switch (v.getId()) {
                    case R.id.save_contact_person_one:
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name1);
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "" + number1);
                        break;
                    case R.id.save_contact_person_two:
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name2);
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "" + number2);
                        break;
                }
                startActivity(intent);
            }
        };

        assert callOne != null;
        callOne.setOnClickListener(callOnClickListener);
        assert callTwo != null;
        callTwo.setOnClickListener(callOnClickListener);
        assert saveOne != null;
        saveOne.setOnClickListener(saveOnClickListener);
        assert saveTwo != null;
        saveTwo.setOnClickListener(saveOnClickListener);
    }

    private void setReminder()
    {
        if(ContextCompat.checkSelfPermission(EventDetails.this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 1);
        else
        {
            if (isReminderSet)
                Toast.makeText(this, "Reminder already set", Toast.LENGTH_SHORT).show();
            else {
                final Calendar beginTime = Calendar.getInstance();
                final Calendar endTime = Calendar.getInstance();
                beginTime.set(2017, 0, 20, 9, 0);
                endTime.set(2017, 0, 20, 17, 0);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Remind on?");
                builder.setItems(new String[]{"Day 1", "Day 2"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 1) {
                            beginTime.set(2017, 0, 21, 9, 0);
                            endTime.set(2017, 0, 21, 17, 0);

                            goToCalendar(beginTime, endTime);
                        } else
                            goToCalendar(beginTime, endTime);
                    }
                });
                builder.show();
            }
        }
    }

    private void goToCalendar(Calendar beginTime, Calendar endTime)
    {
        mEventID = getLastEventId(getContentResolver())+1;

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events._ID, mEventID)
                .putExtra(CalendarContract.Events.TITLE, event_name)
                .putExtra(CalendarContract.Events.DESCRIPTION, "Event at Matrix 17")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, getIntent().getStringExtra("venue")+", S.P.I.T.")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        visitedCalendar = true;
        startActivity(intent);
    }

    private long getLastEventId(ContentResolver cr)
    {
        if(ContextCompat.checkSelfPermission(EventDetails.this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = cr.query(CalendarContract.Events.CONTENT_URI, new String[]{"MAX(_id) as max_id"}, null, null, "_id");
            assert cursor != null;
            cursor.moveToFirst();
            long max_val = cursor.getLong(cursor.getColumnIndex("max_id"));
            cursor.close();
            return max_val;
        }
        return 0;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(grantResults[0] != 0)
            Toast.makeText(EventDetails.this, "Reminder cannot be added without permission to read calendar", Toast.LENGTH_SHORT).show();
        else
            setReminder();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (visitedCalendar)
        {
            if (getLastEventId(getContentResolver()) == mEventID)
            {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://spit.matrix2017.provider");
                String selection = "name = ?";
                String[] selectionArgs = {getIntent().getStringExtra("name")};
                ContentValues cv = new ContentValues();
                cv.put("reminder", 1);
                contentResolver.update(uri, cv, selection, selectionArgs);

                isReminderSet = true;
                mi_reminder.setIcon(R.drawable.svg_alarm_on_white_48px);
                Toast.makeText(EventDetails.this, "Successfully added reminder", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Reminder not added", Toast.LENGTH_SHORT).show();
        }
    }
}
