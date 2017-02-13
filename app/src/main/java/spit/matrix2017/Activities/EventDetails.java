/*
 * *
 *  * This file is part of Matrix2017
 *  * Created for the annual technical festival of Sardar Patel Institute of Technology
 *  *
 *  * The original contributors of the software include:
 *  * - Adnan Ansari (psyclone20)
 *  * - Tejas Bhitle (TejasBhitle)
 *  * - Mithil Gotarne (mithilgotarne)
 *  * - Rohit Nahata (rohitnahata)
 *  * - Akshay Shah (akshah1997)
 *  *
 *  * Matrix2017 is free software: you can redistribute it and/or modify
 *  * it under the terms of the MIT License as published by the Massachusetts Institute of Technology
*/

package spit.matrix2017.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

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
    private boolean visitedCalendar, isFirstLaunch;
    ImageView mainImageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CardView organizers_card,prizes_card,registration_card,venue_time_card;
    TextView hardcodedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        final AppCompatTextView textViews[] = {
                (AppCompatTextView) findViewById(R.id.tv_event_description),
                (AppCompatTextView) findViewById(R.id.tv_event_venue_time),
                (AppCompatTextView) findViewById(R.id.tv_event_registration),
                (AppCompatTextView) findViewById(R.id.tv_event_prizes),
                (AppCompatTextView) findViewById(R.id.tv_event_organizers)
        };

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar_event);
        organizers_card =(CardView)findViewById(R.id.organizers_card);
        prizes_card = (CardView)findViewById(R.id.prizes_card);
        registration_card =(CardView)findViewById(R.id.registration_card);
        venue_time_card=(CardView)findViewById(R.id.venue_time_card);

        hardcodedDate=(TextView)findViewById(R.id.hardcodedDate);

        visitedCalendar = false;
        isFirstLaunch = true;

        //get intent from eventlist adapter
        if (getIntent().getStringExtra("name") != null && getSupportActionBar() != null) {
            event_name = getIntent().getStringExtra("name");
            this.setTitle(event_name);
        }
        else
            this.setTitle("Some event");

        if(Objects.equals(event_name, "Daniel Fernandes")||Objects.equals(event_name, "Techshiksha")||Objects.equals(event_name, "Sky Observation")||Objects.equals(event_name, "Hackathon")||Objects.equals(event_name, "Ethical Hacking")){
            hardcodedDate.setVisibility(View.GONE);
        }

        setDescription(getIntent().getStringExtra("description"));
        setVenueAndTime(getIntent().getStringExtra("venue"), getIntent().getStringExtra("time"));
        setRegistration(getIntent().getStringExtra("registration"));
        setPrizes(getIntent().getStringExtra("prizes"));
        setContacts(getIntent().getStringExtra("contact1name"), getIntent().getStringExtra("contact1no"), getIntent().getStringExtra("contact2name"), getIntent().getStringExtra("contact2no"));

        isFavouriteEvent = getIntent().getIntExtra("favorite", 0) == 1;
        isReminderSet = getIntent().getIntExtra("reminder", 0) == 1;

        mainImageView = (ImageView) findViewById(R.id.main_imageView);
        assert mainImageView != null;
        mainImageView.setImageResource(getIntent().getIntExtra("image", R.drawable.event_vsm));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        if (isFavouriteEvent)
            fab.setImageResource(R.drawable.svg_favorite_white_48px);

        Bitmap bitmap = ((BitmapDrawable)mainImageView.getDrawable()).getBitmap();

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener()
        {
            @Override
            public void onGenerated(Palette palette)
            {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if(swatch == null)
                    swatch = palette.getMutedSwatch();
                if(swatch != null)
                {
                    int color = swatch.getRgb();
                    if(Color.red(color)+Color.green(color)+Color.green(color) > 420)
                        color = Color.rgb((int)(Color.red(color)*0.8), (int)(Color.green(color)*0.8), (int)(Color.blue(color)*0.8));

                    fab.setBackgroundTintList(ColorStateList.valueOf(color));
                    fab.setRippleColor(swatch.getTitleTextColor());
                    collapsingToolbarLayout.setContentScrimColor(color);
                    collapsingToolbarLayout.setBackgroundColor(color);
                    collapsingToolbarLayout.setStatusBarScrimColor(color);

                    if(Build.VERSION.SDK_INT >= 21){
                        getWindow().setStatusBarColor(color);
                    }

                    for (AppCompatTextView textView : textViews) textView.setTextColor(color);
                }
            }
        });

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
                    fab.setImageResource(R.drawable.svg_favorite_border_white_48px);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Slide slide = new Slide(Gravity.BOTTOM);

            if(isFirstLaunch) {
                fab.hide();
                isFirstLaunch = false;
            }

            slide.addTarget(R.id.description_card);
            slide.addTarget(R.id.venue_time_card);
            slide.addTarget(R.id.registration_card);
            slide.addTarget(R.id.prizes_card);
            slide.addTarget(R.id.organizers_card);
            slide.setInterpolator(new LinearOutSlowInInterpolator());
            getWindow().setEnterTransition(slide);
            getWindow().setExitTransition(slide);
            getWindow().setReenterTransition(slide);

            setupEnterAnimation();
        }
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mi_reminder = menu.findItem(R.id.action_set_reminder);
        if(isReminderSet)
            mi_reminder.setIcon(R.drawable.svg_alarm_on_white_48px);
        return super.onPrepareOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.transition);
        transition.setDuration(300);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }
            @Override
            public void onTransitionEnd(Transition transition) {
                fab.show();
            }
            @Override
            public void onTransitionCancel(Transition transition) {
            }
            @Override
            public void onTransitionPause(Transition transition) {
            }
            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
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
                intent.putExtra(Intent.EXTRA_TEXT, "Check out the event '"+event_name+"' at Matrix 17! For more details, download the official app here:\n"+getResources().getString(R.string.playstore_link));
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share event via"));
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitle(final String title) {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_event);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

    }

    private void setDescription(String description) {
        AppCompatTextView descriptionTextView = (AppCompatTextView) findViewById(R.id.description_textView);
        assert descriptionTextView != null;
        descriptionTextView.setText(description);
    }

    private void setVenueAndTime(String venue, String time) {
        AppCompatTextView venueTimeTextView = (AppCompatTextView) findViewById(R.id.venue_time_textView);
        assert venueTimeTextView != null;
        System.out.println(venue+time);
        if(Objects.equals(venue, "None")&&Objects.equals(time, "None")){
            venue_time_card.setVisibility(View.GONE);
            return;
        }
        if(Objects.equals(venue, "None")){
            venueTimeTextView.setText(time);
            return;
        }
        if(Objects.equals(time, "None")) {
            venueTimeTextView.setText(venue);
            return;
        }
        venueTimeTextView.setText(venue + "\n" + time);
    }

    private void setRegistration(String registration) {
        AppCompatTextView registrationTextView = (AppCompatTextView) findViewById(R.id.registration_textView);
        assert registrationTextView != null;
        registrationTextView.setText(registration);
    }

    private void setPrizes(String prizes) {
        AppCompatTextView prizesTextView = (AppCompatTextView) findViewById(R.id.prizes_textView);
        assert prizesTextView != null;
        if(Objects.equals(prizes, "None"))
            prizes_card.setVisibility(View.GONE);
        prizesTextView.setText(prizes);

    }

    private void setContacts(final String name1, final String number1, final String name2, final String number2) {
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
                    case android.R.id.home:
                        EventDetails.super.onBackPressed();
                        break;
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

    private void setReminder() {
        if(Objects.equals(event_name, "Ethical Hacking")){
            Toast.makeText(this,"Date and time have not yet been finalized!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(ContextCompat.checkSelfPermission(EventDetails.this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 1);
        else
        {
            if (isReminderSet)
                Toast.makeText(this, "Reminder already set", Toast.LENGTH_SHORT).show();
            else {
                final Calendar beginTime = Calendar.getInstance();
                final Calendar endTime = Calendar.getInstance();

                if(event_name != null && event_name.equals("VSM"))
                {
                    beginTime.set(2017, 1, 15, 13, 0);
                    endTime.set(2017, 1, 15, 14, 0);
                }
                else if(event_name != null && event_name.equals("Daniel Fernandes"))
                {
                    beginTime.set(2017, 1, 15, 16, 0);
                    endTime.set(2017, 1, 15, 17, 0);
                }
                else if(event_name != null && event_name.equals("Techshiksha"))
                {
                    beginTime.set(2017, 1, 15, 18, 0);
                    endTime.set(2017, 1, 15, 19, 0);
                }
                else if(event_name != null && event_name.equals("Sky Observation"))
                {
                    beginTime.set(2017, 1, 16, 18, 0);
                    endTime.set(2017, 1, 16, 21, 0);
                }
                else
                {
                    beginTime.set(2017, 1, 15, 9, 0);
                    endTime.set(2017, 1, 15, 18, 0);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Remind on?");
                CharSequence day[];
                if(Objects.equals(event_name, "Daniel Fernandes")||Objects.equals(event_name,"Techshiksha"))
                    day=new CharSequence[]{"Day 1"};
                else if(Objects.equals(event_name,"Sky Observation"))
                    day=new CharSequence[]{"Day 2"};
                else
                    day=new CharSequence[]{"Day 1","Day 2"};
                builder.setItems(day, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i == 0){
                            goToCalendar(beginTime,endTime);
                        }
                        else
                        {
                            if(event_name != null && event_name.equals("VSM"))
                            {
                                beginTime.set(2017, 1, 16, 13, 0);
                                endTime.set(2017, 1, 16, 14, 0);
                            }
                            else if((event_name!=null) && (event_name.equals("Battle Frontier")||event_name.equals("TechXplosion")||event_name.equals("Techeshi's Castle"))){
                                beginTime.set(2017, 1, 16, 9, 0);
                                endTime.set(2017, 1, 16, 15, 0);
                            }
                            else{
                                beginTime.set(2017, 1, 16, 9, 0);
                                endTime.set(2017, 1, 16, 18, 0);
                            }

                            goToCalendar(beginTime, endTime);
                        }
                    }
                });
                builder.show();
            }
        }
    }

    private void goToCalendar(Calendar beginTime, Calendar endTime) {
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

    private long getLastEventId(ContentResolver cr) {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] != 0)
            Toast.makeText(EventDetails.this, "Reminder cannot be added without permission to read calendar", Toast.LENGTH_SHORT).show();
        else
            setReminder();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (visitedCalendar)
        {
            if (getLastEventId(getContentResolver()) == mEventID)
            {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://spit.matrix2017.provider");
                String selection = "name = ?";
                String[] selectionArgs = {getIntent().getStringExtra("name")+", S.P.I.T."};
                ContentValues cv = new ContentValues();
                cv.put("reminder", 1);
                contentResolver.update(uri, cv, selection, selectionArgs);

                isReminderSet = true;
                mi_reminder.setIcon(R.drawable.svg_alarm_on_white_48px);
                Toast.makeText(EventDetails.this, "Successfully added reminder", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(EventDetails.this, "Reminder not added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(fab.isShown())
            mainImageView.bringToFront();
        fab.setVisibility(View.GONE);
        registration_card.setVisibility(View.GONE);
        prizes_card.setVisibility(View.GONE);
        organizers_card.setVisibility(View.GONE);
        super.onBackPressed();
    }
}