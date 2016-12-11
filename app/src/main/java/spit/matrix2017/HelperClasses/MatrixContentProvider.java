package spit.matrix2017.HelperClasses;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import spit.matrix2017.R;

public class MatrixContentProvider extends ContentProvider {
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
            ("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL);",
                    TABLE_EVENTS, COL_EVENT_ID, COL_EVENT_NAME, COL_EVENT_DESCRIPTION, COL_EVENT_IMAGE, COL_EVENT_CATEGORY, COL_EVENT_VENUE, COL_EVENT_TIME, COL_EVENT_CONTACT1_NAME, COL_EVENT_CONTACT1_NO, COL_EVENT_CONTACT2_NAME, COL_EVENT_CONTACT2_NO, COL_EVENT_FAVORITE, COL_EVENT_REMINDER);

    //////////Data of each event//////////
    private final Event[] events = new Event[]{
            new Event("Human Foosball", "Table football, commonly called foosball, is a table-top game. So what’s this Human Foosball thing?\nYou might have guessed it: The players themselves are foos men!", R.drawable.human_foosball, "Fun", "Gymkhana", "9am to 1pm\n2pm to 6pm", "Shlok Gujar", "9930556978", "Rutvij Mehta", "9930018260"),
            new Event("Battle of Brains", "Take your brain on an exciting roller coaster ride!\nSmash two rounds to win exciting prizes", R.drawable.battle_of_brains, "Fun", "SY MCA, 4th floor", "9am to 1pm\n2pm to 6pm", "Shreya Ail", "9665817304", "Utkarsha Pawar", "7507416309"),
            new Event("LAN Gaming", "Compete for glory in 4 different games: FIFA, Counter-Strike, Need for Speed and Mini Militia, and take home exciting prizes!", R.drawable.lan_gaming, "Fun", "Room 007", "9am to 1pm\n2pm to 6pm", "Sai Yerremreddy", "8652259207", "Manas Shukla", "9987014677"),
            new Event("LAN Mafia", "Never let anyone know what you are thinking.\n\n\u2022 Each of the students will be assigned specific roles in the game by 'God'\n\u2022 3 of the best players who outplay others in the GDs will be selected from the slot\n\u2022 There will be a finale of all the people shortlisted from previous games", R.drawable.lan_mafia, "Fun", "Room 404", "9am to 1pm\n2pm to 6pm", "Krishna Kancharla", "9022057698", "Nishchint Jagdale", "8425097544"),
            new Event("Mind That Word", "A word-based game between two teams of four members each.\nA team selects a gamemaster, who is provided with 10 codenames which the team has to guess.", R.drawable.mind_that_word, "Fun", "Room 310", "9am to 1pm\n2pm to 6pm", "Anirvin Vishwanatan", "9167957770", "Ankit Sawant", "9869549744"),
            new Event("Pokemon Showdown", "The mages and warlocks of Westeros have opened up a portal and Pokémon have landed here from nowhere!\nDefeat anyone who comes in your way to the Iron Throne, and fight your way against the other false claimants.\nFor there can be only one champion, there will be two great wars.", R.drawable.pokemon_showdown, "Fun", "MCA Lab, 4th floor", "9am to 1pm\n2pm to 6pm", "Rajorshi Chaudhuri", "7738413449", "Sarvesh Patil", "8898698546"),

            new Event("VSM", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.", R.drawable.virtual_stock_market, "Mega", "College entrance", "1pm to 2pm", "Riya Bakhtiani", "8888006180", "Jainam Soni", "9619100569"),
            new Event("Codatron", "Can you code your way to the top?\nParticipate in 3 coding or 3 non-coding rounds to find out!", R.drawable.codatron, "Mega", "Rooms 406 and 408", "9am to 1pm\n2pm to 6pm", "Kaustubh Toraskar", "9930468296", "Saral Uttamani", "9930510003"),
            new Event("Laser Maze", "Sharpen your ninja skills as you twist, duck and crawl your way through an intricate web of lasers.", R.drawable.laser_maze, "Mega", "Room 202", "9am to 1pm\n2pm to 5pm", "Ameya Nambisan", "9892510777", "Subhiksha Mukuntharaj", "9833667741"),
            new Event("Laser Tag", "An action-packed game where participants wear electronic vests and tag each other with phasors to score points.", R.drawable.laser_tag, "Mega", "Room 203", "9am to 1pm\n2pm to 6pm", "Gurpreet Kaur Saimy", "9029553799", "Madhura Gore", "7208450172"),

            new Event("Tech Charades", "Let your actions speak louder than your words!\n\nList of games:\n\u2022 Pictionary\n\u2022 Blind racing\n\u2022 Tech dumb charades\n\u2022 Tech quiz", R.drawable.tech_charades, "Tech", "FE ETRX", "9am to 1pm\n2pm to 6pm", "Meet Gopani", "9969763551", "Harsh Jain", "9004885565"),
            new Event("Battle Frontier", "\u2022 The Ultimate Race:\nRace your bots through hurdles\n\n\u2022 Stryker:\nShow off your robo soccoer skills", R.drawable.battle_frontier, "Tech", "Quadrangle", "9am to 1pm\n2pm to 6pm", "Anirudh Sharma", "7038844719", "Alan Jacob", "9167347037"),
            new Event("Escape Plan", "Cut loose or lose.\n\n\u2022 Level 1: Picture clues\n\u2022 Level 2: Technical quiz\n\u2022 Level 3: Online treasure hunt\n\u2022 Level 4: Lazer Maze", R.drawable.escape_plan, "Tech", "Room 301", "9am to 1pm\n2pm to 6pm", "Rishi Vedula", "9967009530", "Ameya Vatkar", "9920666361"),
            new Event("Technovanza", "Beat the clock!", R.drawable.technovanza, "Tech", "FE EXTC / Room 407", "9am to 1pm\n2pm to 6pm", "Ashish Kulkarni", "9920874465", "Shoaib Mansoori", "7715087254"),
            new Event("TechXplosion", "\u2022 Stage 1: Brainstorm\n\u2022 Stage 2: Amphi hovercraft / Laser barricades / Robo-swachta abhiyaan\n\u2022 Stage 3: Steady hands\n\u2022 Stage 4: Bomb defusion", R.drawable.tech_xplosion, "Tech", "Quadrangle", "9am to 5pm", "Yash Gaba", "9167014679", "Aastha Shah", "8767488885"),
            new Event("No Escape", "Team up to break out!", R.drawable.no_escape, "Tech", "FE IT", "9am to 1pm\n2pm to 6pm", "Sushmen Chaudhari", "9022536467", "Kundan Patel", "9923416796"),
            new Event("Techeshi's Castle", "Vengeance hits home!\n\n\u2022 Line following bot: Make your own line following robot\n\u2022 Techeshi's Castle: Play 3 levels of robogames", R.drawable.techeshis_castle, "Tech", "Quadrangle", "9am to 1pm\n2pm to 6pm", "Ananya Navelkar", "9221702015", "Ananya Ojha", "9766120020"),
            new Event("Tesseract", "Collect as much virtual money as you can.\n\n\u2022 Stage 1: Dart game\n\u2022 Stage 2: Twister\n\u2022 Stage 3: Seek and ye shall do\n\u2022 Stage 4: Maze runner", R.drawable.tesseract, "Tech", "FE Comps", "9am to 1pm\n2pm to 6pm", "Hrohaan Malhotra", "9987037511", "Divit Karmiani", "9892302788")
    };

    SQLiteDatabase db;
    MatrixDBConnectionHelper helper;

    @Override
    public boolean onCreate() {
        helper = new MatrixDBConnectionHelper(getContext());
        db = helper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return db.query(TABLE_EVENTS, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (db.insert(TABLE_EVENTS, null, values) == -1)
            throw new RuntimeException("Error while adding event");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return db.delete(TABLE_EVENTS, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return db.update(TABLE_EVENTS, values, selection, selectionArgs);
    }

    //////////Connection helper//////////
    public class MatrixDBConnectionHelper
            extends SQLiteOpenHelper {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_EVENTS_QUERY);

            ContentValues cv = new ContentValues();
            cv.put(COL_EVENT_FAVORITE, 0);
            cv.put(COL_EVENT_REMINDER, 0);
            for (Event event : events) {
                cv.put(COL_EVENT_NAME, event.getName());
                cv.put(COL_EVENT_DESCRIPTION, event.getDescription());
                cv.put(COL_EVENT_IMAGE, event.getImage());
                cv.put(COL_EVENT_CATEGORY, event.getCategory());
                cv.put(COL_EVENT_VENUE, event.getVenue());
                cv.put(COL_EVENT_TIME, event.getTime());
                cv.put(COL_EVENT_CONTACT1_NAME, event.getContact1_name());
                cv.put(COL_EVENT_CONTACT1_NO, event.getContact1_no());
                cv.put(COL_EVENT_CONTACT2_NAME, event.getContact2_name());
                cv.put(COL_EVENT_CONTACT2_NO, event.getContact2_no());

                db.insert(TABLE_EVENTS, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        public MatrixDBConnectionHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }


        public List<Event> getData(String name, int db_position)
        /*here position is the column no according to which we want to fetch data. For sorting according to category,
         position is 4(coumn array position), name is Category name. For fetching using name, we use position 1 and get(position) will get us
         the respective value in the list
        */

        {
            List<Event> list = new ArrayList<>();

            SQLiteDatabase db = getReadableDatabase();
            String[] columns = {COL_EVENT_ID, COL_EVENT_NAME, COL_EVENT_DESCRIPTION, COL_EVENT_IMAGE, COL_EVENT_CATEGORY, COL_EVENT_VENUE, COL_EVENT_TIME, COL_EVENT_CONTACT1_NAME, COL_EVENT_CONTACT1_NO, COL_EVENT_CONTACT2_NAME, COL_EVENT_CONTACT2_NO, COL_EVENT_FAVORITE, COL_EVENT_REMINDER};
            Cursor cursor= db.query(TABLE_EVENTS, columns, columns[db_position] + " = '" + name + "'", null, null, null, null);

            int index1 = cursor.getColumnIndex(COL_EVENT_NAME);
            int index2 = cursor.getColumnIndex(COL_EVENT_DESCRIPTION);
            int index3 = cursor.getColumnIndex(COL_EVENT_IMAGE);
            int index4 = cursor.getColumnIndex(COL_EVENT_CATEGORY);
            int index5 = cursor.getColumnIndex(COL_EVENT_VENUE);
            int index6 = cursor.getColumnIndex(COL_EVENT_TIME);
            int index7 = cursor.getColumnIndex(COL_EVENT_CONTACT1_NAME);
            int index8 = cursor.getColumnIndex(COL_EVENT_CONTACT1_NO);
            int index9 = cursor.getColumnIndex(COL_EVENT_CONTACT2_NAME);
            int index10 = cursor.getColumnIndex(COL_EVENT_CONTACT2_NO);
            int index11 = cursor.getColumnIndex(COL_EVENT_FAVORITE);
            int index12 = cursor.getColumnIndex(COL_EVENT_REMINDER);


            String name_event, desc, category, venue, time, contact_name1, contact_name2, contact_no1, contact_no2;
            int img, fav, reminder;

            while (cursor.moveToNext()) {
                name_event = cursor.getString(index1);
                desc = cursor.getString(index2);
                img = cursor.getInt(index3);
                category = cursor.getString(index4);
                venue = cursor.getString(index5);
                time = cursor.getString(index6);
                contact_name1 = cursor.getString(index7);
                contact_no1 = cursor.getString(index8);
                contact_name2 = cursor.getString(index9);
                contact_no2 = cursor.getString(index10);
                fav = cursor.getInt(index11);
                reminder = cursor.getInt(index12);

                Event event = new Event(name_event, desc, img, category, venue, time, contact_name1, contact_no1, contact_name2, contact_no2, fav, reminder);
                list.add(event);
            }
            cursor.close();
            return list;
        }

    }
}
