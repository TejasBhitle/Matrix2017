package spit.matrix2017.Activities;

import android.content.Intent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.support.v7.graphics.Palette;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import spit.matrix2017.R;

public class EventDetails
        extends AppCompatActivity
{
    private boolean isFavouriteEvent;
    private boolean isReminderSet;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //get intent from eventlist adapter
        if (getIntent().getStringExtra("name") != null && getSupportActionBar() != null)
            this.setTitle(getIntent().getStringExtra("name"));
        else
            this.setTitle("Some event");

        // TODO: 11/1/2016 Add logic to get selected event from db (FIXED [get it from intent instead])
        //setDescription(getString(R.string.fake_description));
        setDescription(getIntent().getStringExtra("description"));
        
        setVenueAndTime(getIntent().getStringExtra("venue"), getIntent().getStringExtra("time"));

        setRules();
        setPrizes();
        
        //setContacts("Contact Person 1", 9874563210L, "Contact Person 2", 3216549870L);
        setContacts(getIntent().getStringExtra("contact1name"), getIntent().getLongExtra("contact1no", 9999999999l), getIntent().getStringExtra("contact2name"), getIntent().getLongExtra("contact2no", 9999999999l));

        //isFavouriteEvent = false; // TODO: 11/1/2016 according to event set this value (FIXED below)
        isFavouriteEvent = getIntent().getIntExtra("favorite", 0)==1 ? true : false;
        // TODO: 11/11/2016 Set the action bar icon based on the value of isFavouriteEvent

        //isReminderSet = false;  // TODO: 11/1/2016 according to event set this value (FIXED below)
        isReminderSet = getIntent().getIntExtra("reminder", 0)==1 ? true : false;
        // TODO: 11/11/2016 Set the action bar icon based on the value of isReminderSet

        ImageView mainImageView = (ImageView) findViewById(R.id.main_imageView);
        assert mainImageView != null;

        //Picasso.with(this).load(R.drawable.virtual_stock_market).into(mainImageView); // TODO: 11/14/2016 use Picasso to set background image
        mainImageView.setImageResource(getIntent().getIntExtra("image", R.drawable.virtual_stock_market));

        /*Code To generate Colors according to imageview*/
        Bitmap mainImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.virtual_stock_market);
        Palette.from(mainImage).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Get the "vibrant" color swatch based on the bitmap
                Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                if (vibrant != null) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(vibrant.getRgb()));
                    fab.setRippleColor(vibrant.getTitleTextColor());
                }
            }
        });



        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        if(isFavouriteEvent)
                fab.setImageResource(R.drawable.svg_favorite_white_48px);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                isFavouriteEvent = !isFavouriteEvent;
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://spit.matrix2017.provider");
                String selection = "name = ?";
                String[] selectionArgs = {getIntent().getStringExtra("name")};
                ContentValues cv = new ContentValues();
                
                if(isFavouriteEvent)
                {
                    cv.put("favorite", 1);
                    contentResolver.update(uri, cv, selection, selectionArgs);
                    Toast.makeText(EventDetails.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    fab.setImageResource(R.drawable.svg_favorite_white_48px);
                }
                else
                {
                    cv.put("favorite", 0);
                    contentResolver.update(uri, cv, selection, selectionArgs);
                    fab.setImageResource(R.drawable.ic_favorite_border_white_48px);
                    Toast.makeText(EventDetails.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
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
        setReminder(menu.findItem(R.id.action_set_reminder), isReminderSet);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_reminder:
                setReminder(item, !isReminderSet); //used as toggle
                break;
            case R.id.action_share:
                // TODO: 11/2/2016 create method to share event
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
        venueTimeTextView.setText(venue+" ("+time+")");
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

    private void setReminder(MenuItem menuItem, boolean isReminderSet) {
        this.isReminderSet = isReminderSet;

        // TODO: 11/2/2016 logic to update db (FIXED below)
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://spit.matrix2017.provider");
        String selection = "name = ?";
        String[] selectionArgs = {getIntent().getStringExtra("name")};
        ContentValues cv = new ContentValues();

        if (isReminderSet)
        {
            cv.put("reminder", 1);
            contentResolver.update(uri, cv, selection, selectionArgs);
            menuItem.setIcon(R.drawable.svg_alarm_on_white_48px);
        }
        else
        {
            cv.put("reminder", 0);
            contentResolver.update(uri, cv, selection, selectionArgs);
            menuItem.setIcon(R.drawable.svg_alarm_add_white_48px);
        }
    }
}
