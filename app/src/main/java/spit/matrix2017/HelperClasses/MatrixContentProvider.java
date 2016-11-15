package spit.matrix2017.HelperClasses;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import spit.matrix2017.R;

public class MatrixContentProvider extends ContentProvider{
    //////////Database strings//////////
    private static final String DB_NAME = "MatrixDB.sqlite";
    private static final int DB_VERSION = 1;

    //////////Strings to help create the events table//////////
    private static final String TABLE_EVENTS = "events";

    private static final String COL_EVENT_ID = "_id";
    private static final String COL_EVENT_NAME = "name";
    private static final String COL_EVENT_DESCRIPTION = "description";
    private static final String COL_EVENT_IMAGE = "image";
    private static final String COL_EVENT_CATEGORY = "category";
    private static final String COL_EVENT_VENUE = "venue";
    private static final String COL_EVENT_TIME = "time";
    private static final String COL_EVENT_CONTACT1_NAME = "contact1_name";
    private static final String COL_EVENT_CONTACT1_NO = "contact1_no";
    private static final String COL_EVENT_CONTACT2_NAME = "contact2_name";
    private static final String COL_EVENT_CONTACT2_NO = "contact2_no";
    private static final String COL_EVENT_FAVORITE = "favorite";
    private static final String COL_EVENT_REMINDER = "reminder";

    private static final String CREATE_TABLE_EVENTS_QUERY = String.format
            ("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL);",
                    TABLE_EVENTS, COL_EVENT_ID, COL_EVENT_NAME, COL_EVENT_DESCRIPTION, COL_EVENT_IMAGE, COL_EVENT_CATEGORY, COL_EVENT_VENUE, COL_EVENT_TIME, COL_EVENT_CONTACT1_NAME, COL_EVENT_CONTACT1_NO, COL_EVENT_CONTACT2_NAME, COL_EVENT_CONTACT2_NO, COL_EVENT_FAVORITE, COL_EVENT_REMINDER);

    //////////Custom data type for events//////////
    class Event
    {
        String name, description, category, venue, time, contact1_name, contact2_name;
        int image;
        long contact1_no, contact2_no;

        Event(String name, String description, int image, String category, String venue, String time, String contact1_name, long contact1_no, String contact2_name, long contact2_no)
        {
            this.name = name;
            this.description = description;
            this.image = image;
            this.category = category;
            this.venue = venue;
            this.time = time;
            this.contact1_name = contact1_name;
            this.contact1_no = contact1_no;
            this.contact2_name = contact2_name;
            this.contact2_no = contact2_no;
        }
    }

    //////////Data of each event//////////
    private final Event[] events = new Event[]{
            new Event("Battle of Brains", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.battle_of_brains, "Fun", "Quadrangle", "9am to 5pm", "Shreya Ail", 9665817304l, "Utkarsha Pawar", 7507416309l),
            new Event("Blind Car Racing", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.blind_car_racing, "Fun", "Quadrangle", "9am to 5pm", "Vaishnavi Sidhamshettiwar", 9765696994l, "Shriya Khatri", 8806060736l),
            new Event("Human Foosball", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.human_foosball, "Fun", "Quadrangle", "9am to 5pm", "Shlok Gujar", 9930556978l, "Rutvij Mehta", 9930018260l),
            new Event("LAN Gaming", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.lan_gaming, "Fun", "Quadrangle", "9am to 5pm", "Sai Yerremreddy", 8652259207l, "Manas Shukla", 9987014677l),
            new Event("LAN Mafia", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.lan_mafia, "Fun", "Quadrangle", "9am to 5pm", "Krinshna Kancharla", 9022057698l, "Nishchint Jagdale", 8425097544l),
            new Event("Mind That Word", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.mind_that_word, "Fun", "Quadrangle", "9am to 5pm", "Anirvin Vishwanatan", 9167957770l, "Ankit Sawant", 9869549744l),
            new Event("Pokemon Showdown", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.pokemon_showdown, "Fun", "Quadrangle", "9am to 5pm", "Rajorshi Chaudhuri", 7738413449l, "Sarvesh Patil", 8898698546l),
            
            new Event("Codatron", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.codatron, "Mega", "Quadrangle", "9am to 5pm", "Kaustubh Toraskar", 9930468296l, "Saral Uttamani", 9930510003l),
            new Event("Laser Maze", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.laser_maze, "Mega", "Quadrangle", "9am to 5pm", "Ameya Nambisan", 9892510777l, "Subhiksha Mukuntharaj", 9833667741l),
            new Event("Laser Tag", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.laser_tag, "Mega", "Quadrangle", "9am to 5pm", "Gurpreet Kaur Saimy", 9029553799l, "Madhura Gore", 7208450172l),
            new Event("Virtual Stock Market", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.virtual_stock_market, "Mega", "Quadrangle", "9am to 5pm", "Riya Bakhtiani", 8888006180l, "Jainam Soni", 9619100569l),
            
            new Event("Battle Frontier", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.battle_frontier, "Tech", "Quadrangle", "9am to 5pm", "Anirudh Sharma", 7038844719l, "Alan Jacob", 9167347037l),
            new Event("Escape Plan", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.escape_plan, "Tech", "Quadrangle", "9am to 5pm", "Rishi Vedula", 9967009530l, "Ameya Vatkar", 9920666361l),
            new Event("Tech Charades", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.tech_charades, "Tech", "Quadrangle", "9am to 5pm", "Meet Gopani", 9969763551l, "Harsh Jain", 9004885565l),
            new Event("Tech Xplosion", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.tech_xplosion, "Tech", "Quadrangle", "9am to 5pm", "Yash Gaba", 9167014679l, "Aastha Shah", 8767488885l),
            new Event("No Escape", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.no_escape, "Tech", "Quadrangle", "9am to 5pm", "Sushmen Chaudhari", 9022536467l, "Kundan Patel", 9923416796l),
            new Event("Techeshi's Castle", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.techeshis_castle, "Tech", "Quadrangle", "9am to 5pm", "Ananya Navelkar", 9221702015l, "Ananya Ojha", 9766120020l),
            new Event("Technovanza", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.technovanza, "Tech", "Quadrangle", "9am to 5pm", "Ashish Kulkarni", 9920874465l, "Shoaib Mansoori", 7715087254l),
            new Event("Tesseract", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.tesseract, "Tech", "Quadrangle", "9am to 5pm", "Hrohaan Malhotra", 9987037511l, "Divit Karmiani", 9892302788l)
    };

    private SQLiteDatabase db;

    @Override
    public boolean onCreate()
    {
        MatrixDBConnectionHelper helper = new MatrixDBConnectionHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return db.query(TABLE_EVENTS, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        if(db.insert(TABLE_EVENTS, null, values) == -1)
            throw new RuntimeException("Error while adding event");
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return db.delete(TABLE_EVENTS, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return db.update(TABLE_EVENTS, values, selection, selectionArgs);
    }

    //////////Connection helper//////////
    class MatrixDBConnectionHelper
            extends SQLiteOpenHelper
    {
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TABLE_EVENTS_QUERY);

            ContentValues cv = new ContentValues();
            cv.put(COL_EVENT_FAVORITE, 0);
            cv.put(COL_EVENT_REMINDER, 0);
            for(Event event: events)
            {
                cv.put(COL_EVENT_NAME, event.name);
                cv.put(COL_EVENT_DESCRIPTION, event.description);
                cv.put(COL_EVENT_IMAGE, event.image);
                cv.put(COL_EVENT_CATEGORY, event.category);
                cv.put(COL_EVENT_VENUE, event.venue);
                cv.put(COL_EVENT_TIME, event.time);
                cv.put(COL_EVENT_CONTACT1_NAME, event.contact1_name);
                cv.put(COL_EVENT_CONTACT1_NO, event.contact1_no);
                cv.put(COL_EVENT_CONTACT2_NAME, event.contact2_name);
                cv.put(COL_EVENT_CONTACT2_NO, event.contact2_no);

                db.insert(TABLE_EVENTS, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
        }

        public MatrixDBConnectionHelper(Context context)
        {
            super(context, DB_NAME, null, DB_VERSION);
        }
    }
}
