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
import java.util.Objects;

import spit.matrix2017.R;

import static android.R.attr.id;

public class MatrixContentProvider extends ContentProvider {
    //////////Database strings//////////
    private static final String DB_NAME = "MatrixDB.sqlite";
    private static final int DB_VERSION = 2;

    //////////Strings to help create the events table//////////
    private static final String TABLE_EVENTS = "events";

    private static final String COL_EVENT_ID = "_id";
    private static final String COL_EVENT_NAME = "name";
    private static final String COL_EVENT_DESCRIPTION = "description";
    private static final String COL_EVENT_CATEGORY = "category";
    private static final String COL_EVENT_VENUE = "venue";
    private static final String COL_EVENT_TIME = "time";
    private static final String COL_EVENT_REGISTRATION = "registration";
    private static final String COL_EVENT_PRIZES = "prizes";
    private static final String COL_EVENT_CONTACT1_NAME = "contact1_name";
    private static final String COL_EVENT_CONTACT1_NO = "contact1_no";
    private static final String COL_EVENT_CONTACT2_NAME = "contact2_name";
    private static final String COL_EVENT_CONTACT2_NO = "contact2_no";
    private static final String COL_EVENT_FAVORITE = "favorite";
    private static final String COL_EVENT_REMINDER = "reminder";
    private static final String COL_COLOR="color";
    private static final String CREATE_TABLE_EVENTS_QUERY = String.format
            ("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s TEXT NOT NULL);",
                    TABLE_EVENTS, COL_EVENT_ID, COL_EVENT_NAME, COL_EVENT_DESCRIPTION, COL_EVENT_CATEGORY, COL_EVENT_VENUE, COL_EVENT_TIME, COL_EVENT_REGISTRATION, COL_EVENT_PRIZES, COL_EVENT_CONTACT1_NAME, COL_EVENT_CONTACT1_NO, COL_EVENT_CONTACT2_NAME, COL_EVENT_CONTACT2_NO, COL_EVENT_FAVORITE, COL_EVENT_REMINDER,COL_COLOR);

    //////////Data of each event//////////
    private final Event[] events = new Event[]{
            new Event("VSM", "Stock market for all the wolves out there.\nWin. Spend. Party!", "Mega", "College entrance", "1pm to 2pm", "\u20B9120 per team", "Prizes worth \u20B910000!", "Riya Bakhtiani", "8888006180", "Jainam Soni", "9619100569", "#EEE618"),
            new Event("Codatron", "Can you code your way to the top?\nParticipate in 3 coding or 3 non-coding rounds to find out!", "Mega", "Lab 406", "9am to 1pm\n2pm to 6pm", "\u20B930 per person\n\u20B950 for a team of 2", "\u2022 1st Place: \u20B91500\n\u2022 2nd Place: \u20B91000", "Kaustubh Toraskar", "9930468296", "Saral Uttamani", "9930510003", "#29A4D5"),
            new Event("Laser Maze", "Sharpen your ninja skills as you twist, duck and crawl your way through an intricate web of lasers.", "Mega", "Room 203 (ED Lab)", "9am to 1pm\n2pm to 5pm", "\u20B930 per person\n\u20B940 for a team of 2", "Team:\n\u2022 1st Place: \u20B9500\n\u2022 2nd Place: \u20B9300\n\nIndividual:\n\u2022 1st Place: \u20B9300\n\u2022 2nd Place: \u20B9100", "Ameya Nambisan", "9892510777", "Subhiksha Mukuntharaj", "9833667741", "#08C55A"),
            new Event("Laser Tag", "An action-packed game where participants wear electronic vests and tag each other with phasors to score points.", "Mega", "Room 202 (ED Lab)", "9am to 1pm\n2pm to 6pm", "\u20B9100 per person (pre-registration)\n\u20B9120 per person (on the spot registration)", "\u2022 1st Place: \u20B91000\n\u2022 2nd Place: \u20B9500", "Gurpreet Kaur Saimy", "9029553799", "Madhura Gore", "7208450172", "#F6FF00"),

            new Event("Tech Charades", "Let your actions speak louder than your words!\n\nList of games:\n\u2022 Pictionary\n\u2022 Blind racing\n\u2022 Tech dumb charades\n\u2022 Tech quiz", "Tech", "FE Comps", "9am to 1pm\n2pm to 6pm", "\u20B960 for a team of 3", "\u2022 1st Place: \u20B91500\n\u2022 2nd Place: \u20B9900\n\u2022 3rd Place: \u20B9600", "Meet Gopani", "9969763551", "Harsh Jain", "9004885565", "#C05058"),
            new Event("Battle Frontier", "The Ultimate Race:\nRace your bots through hurdles\n\nStryker:\nShow off your robo soccoer skills", "Tech", "Quadrangle", "9am to 1pm\n2pm to 6pm", "The Ultimate Race:\n\u20B950 per team of 2\n\nStryker:\n\u20B950 per person", "Prizes worth \u20B91500!", "Anirudh Sharma", "7038844719", "Alan Jacob", "9167347037", "#CDBD6A"),
            new Event("Escape Plan", "Use your technical skills to complete all the tasks against a ticking clock.\nThe quickest to escape wins!", "Tech", "FE EXTC", "9am to 1pm\n2pm to 6pm", "\u20B950 for a team of 2", "\u2022 1st Place: \u20B9500\n\u2022 2nd Place: \u20B9400", "Rishi Vedula", "9967009530", "Ameya Vatkar", "9920666361", "#D10808"),
            new Event("TechXplosion", "\u2022 Stage 1: Brainstorm\n\u2022 Stage 2: Amphi hovercraft / Laser barricades / Robo-swachta abhiyaan\n\u2022 Stage 3: Steady hands\n\u2022 Stage 4: Bomb defusion", "Tech", "Gymkhana", "9am to 1pm\n2pm to 6pm", "\u20B960 for a team of 2", "\u2022 1st Place: \u20B9500\n\u2022 2nd Place: \u20B9300", "Yash Gaba", "9167014679", "Aastha Shah", "8767488885", "#F62008"),
            new Event("No Escape", "Ever got locked up in a room? Well, you're about to be.\nComplete a series of mind-boggling tasks in a limited time period to get the key and escape the room as soon as you can!", "Tech", "FE IT", "9am to 1pm\n2pm to 6pm", "\u20B940 for a team of 2", "\u20B9750", "Sushmen Chaudhari", "9022536467", "Kundan Patel", "9923416796", "#CD3973"),
            new Event("Techeshi's Castle", "Vengeance hits home!\n\nLine following bot:\nMake your own line following robot\n\nTecheshi's Castle:\nPlay 3 levels of robogames", "Tech", "Quadrangle", "9am to 1pm\n2pm to 6pm", "Line following bot:\n\u20B920 per person\n\nTecheshi's Castle:\n\u20B940 for a team of 2 (pre-registration)\n\u20B950 for a team of 2 (on the spot registration)", "Prizes worth \u20B910000!", "Ananya Navelkar", "9221702015", "Ananya Ojha", "9766120020", "#751523"),
            new Event("Tesseract", "Balance the flexibility of your mind and body with (or without) your partner to win this fast-paced, spine-chilling, pump-up-adrenaline adventure!", "Tech", "FE ETRX", "9am to 1pm\n2pm to 6pm", "\u20B940 per person\n\u20B960 for a team of 2", "\u2022 1st Place: \u20B9600 and a pen drive \n\u2022 2nd Place: \u20B9400 and a pen drive", "Hrohaan Malhotra", "9987037511", "Divit Karmiani", "9892302788", "#2068B0"),

            new Event("Human Foosball", "Table football, commonly called foosball, is a table-top game. So what’s this Human Foosball thing?\nYou might have guessed it: The players themselves are foos men!", "Fun", "Volleyball Court", "9am to 1pm\n2pm to 6pm", "\u20B950 per person", "\u2022 1st Place: \u20B94000\n\u2022 2nd Place: \u20B92000", "Shlok Gujar", "9930556978", "Rutvij Mehta", "9930018260", "#CDB441"),
            new Event("Battle of Brains", "Take your brain on an exciting roller coaster ride!\nSmash two rounds to win exciting prizes", "Fun", "Room 402", "9am to 1pm\n2pm to 6pm", "\u20B920 per person\n\u20B930 for a team of 2", "\u2022 1st Place: \u20B9500\n\u2022 2nd Place: \u20B9200\n\u2022 3rd Place: \u20B9100", "Shreya Ail", "9665817304", "Utkarsha Pawar", "7507416309", "#00CDCD"),
            new Event("LAN Gaming", "Compete for glory in 6 different games: FIFA 16, Counter-Strike 1.6, Need for Speed, Mini Militia, WWE and Mortal Kombat, and take home exciting prizes!", "Fun", "Lab 007", "9am to 1pm\n2pm to 6pm", "Counter-Strike 1.6:\n₹250 for a team of 5\n\nFIFA 16:\n₹60 per person\n\nNFS Most Wanted:\n\u20B950 per person\n\nMini Militia:\n\u20B9100 for a team of 5\n\nWWE:\n\u20B940 per person\n\nMortal Kombat:\n\u20B940 per person", "Counter-Strike 1.6:\n\u20B95000\n\nFIFA 16:\n\u20B93000\n\nNFS:\n\u20B91000\n\nMini Militia:\n\u20B91000\n\nWWE:\n\u20B9500\n\nMortal Kombat:\n\u20B9500", "Sai Yerremreddy", "8652259207", "Manas Shukla", "9987014677", "#D50000"),
            new Event("Pokemon Showdown", "The mages and warlocks of Westeros have opened up a portal and Pokémon have landed here from nowhere!\nDefeat anyone who comes in your way to the Iron Throne, and fight your way against the other false claimants.\nFor there can be only one champion, there will be two great wars.", "Fun", "Lab 403", "9am to 1pm\n2pm to 6pm", "\u20B940 per person", "\u2022 1st Place: \u20B91500\n\u2022 2nd Place: \u20B91000", "Rajorshi Chaudhuri", "7738413449", "Sarvesh Patil", "8898698546", "#A49013"),
            new Event("LAN Mafia", "Never let anyone know what you are thinking.\n\n\u2022 Each of the students will be assigned specific roles in the game by 'God'\n\u2022 3 of the best players who outplay others in the GDs will be selected from the slot\n\u2022 There will be a finale of all the people shortlisted from previous games", "Fun", "Lab 404", "9am to 1pm\n2pm to 6pm", "\u20B920 per person", "10000mAh power bank", "Krishna Kancharla", "9022057698", "Nishchint Jagdale", "8425097544", "#AC2018"),
            new Event("Mind That Word", "In this maze of words, trust your grandmaster to hunt down your words. Be wary of the trickster as he tries to deter you. The quickest and the smartest prevails.\nJust 'mind that word' all along!", "Fun", "Lab 402", "9am to 1pm\n2pm to 6pm", "\u20B920 per person", "\u20B91000 for winning team", "Anirvin Vishwanatan", "9167957770", "Ankit Sawant", "9869549744", "#F6EE00")
    };

//
    //new mega events
    private final Event[] eventsNew=new Event[]{
            new Event("Project Mania","Showcase your innovative projects that you have done during your engineering life and get an opportunity to be an intern in TCS and Emtron Technologies", "Mega", "Room 105", "9am to 6pm","\u20B9400 for a maximum of 5 team members", "Top winning Projects will get the internship in Tata Consultancy Services (TCS) and Emtron Technologies.","Gaurav","7738009849","Sharvika","9420772761","#799399"),
            new Event("Startup Showcase","Present your ideas with your objectives and get a chance to interact and learn different models and solutions from other great minds.", "Mega", "Room 009", "9am to 6pm","\u20B9150 per person", "The winner will get a chance to be incubated at Sardar Patel Technology Business Incubator (SP-TBI)","Devanshi","8898629895","Janit","7208121331","#C80000"),
            new Event("Ethical Hacking","IEEE-SPIT brings a certified workshop on Ethical Hacking and Cyber Security conducted by Professor Dayanand Ambawade.\nLunch and refreshments will be provided.", "Mega", "None", "None","\u20B9600 for IEEE members\n\u20B9750 for non members", "None","Ankesh","9029162041","Pratik","9619020970","#605026"),
            new Event("Sky Observation","Sky Observation Program organized by Star Gazers SPIT-SPCE", "Mega", "SP Jain ground", "16th Feb 2017\n6pm to 9pm ","Free for members\n\u20B930 for non-members", "None","Michelle","7506180370","Kautuk","7738321484","#186088"),
            new Event("Hackathon","The Hackathon is an overnight competition organised by ITSA along with CSI(Mumbai Chapter) Cerelabs and AI Mumbai.\nParticipants must select from the OCR, IoT and Block Chain from which a problem statement will be given to them.", "Mega", "Room 408, 407, 410, 008", "Starts on 15th Feb at 9am and will continue throughout upto the next morning","\u20B950 per team", "Participants are required to pitch their solution to the judges. All participants will receive a participating certificate. The winners will get the winning certificate.","Prem Raheja","9004654497","Ayesha Kazi","9029933484","#73730C"),
            new Event("Techshiksha","An intriguing talk by Mr. Henry R.P on Big data analytics. An expert speaker from TOGAF- Enterprise Architecture Map and certified Hadoop cluster administrator.", "Mega", "Quadrangle", "15th Feb 2017\n6pm to 7pm","Free Entry", "None","Tanisha","9769678461","Maitri","9167608808","#0070E8"),
            new Event("Daniel Fernandes","Get ready to have your funny bones tickled by Mr. Daniel Fernandes!", "Mega", "Quadrangle", "15th Feb 2017\n4 pm","\u20B9100 per person", "None","Surmeet Kaur","9869112609","Shreya S","8652707799","#737986"),

    };

    private static final int[] images = {
            R.drawable.event_vsm,
            R.drawable.event_codatron,
            R.drawable.event_laser_maze,
            R.drawable.event_laser_tag,

            R.drawable.event_tech_charades,
            R.drawable.event_battle_frontier,
            R.drawable.event_escape_plan,
            R.drawable.event_tech_xplosion,
            R.drawable.event_no_escape,
            R.drawable.event_techeshis_castle,
            R.drawable.event_tesseract,

            R.drawable.event_human_foosball,
            R.drawable.event_battle_of_brains,
            R.drawable.event_lan_gaming,
            R.drawable.event_pokemon_showdown,
            R.drawable.event_lan_mafia,
            R.drawable.event_mind_that_word,

            R.drawable.event_project_mania,
            R.drawable.event_startup_showcase,
            R.drawable.event_ethical_hacking,
            R.drawable.event_sky_observation,
            R.drawable.event_hackathon,
            R.drawable.event_techshiksha,
            R.drawable.event_daniel_fernandes
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
                cv.put(COL_EVENT_CATEGORY, event.getCategory());
                cv.put(COL_EVENT_VENUE, event.getVenue());
                cv.put(COL_EVENT_TIME, event.getTime());
                cv.put(COL_EVENT_REGISTRATION, event.getRegistration());
                cv.put(COL_EVENT_PRIZES, event.getPrizes());
                cv.put(COL_EVENT_CONTACT1_NAME, event.getContact1_name());
                cv.put(COL_EVENT_CONTACT1_NO, event.getContact1_no());
                cv.put(COL_EVENT_CONTACT2_NAME, event.getContact2_name());
                cv.put(COL_EVENT_CONTACT2_NO, event.getContact2_no());
                cv.put(COL_COLOR,event.getColor());
                db.insert(TABLE_EVENTS, null, cv);
            }
            for (Event event : eventsNew) {
                cv.put(COL_EVENT_NAME, event.getName());
                cv.put(COL_EVENT_DESCRIPTION, event.getDescription());
                cv.put(COL_EVENT_CATEGORY, event.getCategory());
                cv.put(COL_EVENT_VENUE, event.getVenue());
                cv.put(COL_EVENT_TIME, event.getTime());
                cv.put(COL_EVENT_REGISTRATION, event.getRegistration());
                cv.put(COL_EVENT_PRIZES, event.getPrizes());
                cv.put(COL_EVENT_CONTACT1_NAME, event.getContact1_name());
                cv.put(COL_EVENT_CONTACT1_NO, event.getContact1_no());
                cv.put(COL_EVENT_CONTACT2_NAME, event.getContact2_name());
                cv.put(COL_EVENT_CONTACT2_NO, event.getContact2_no());
                cv.put(COL_COLOR,event.getColor());
                db.insert(TABLE_EVENTS, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if(oldVersion < 2)
            {
                ContentValues cv = new ContentValues();
                cv.put(COL_EVENT_DESCRIPTION,"Compete for glory in 6 different games: FIFA 16, Counter-Strike 1.6, Need for Speed, Mini Militia, WWE and Mortal Kombat, and take home exciting prizes!");
                cv.put(COL_EVENT_PRIZES,"Counter-Strike 1.6:\n\u20B95000\n\nFIFA 16:\n\u20B93000\n\nNFS:\n\u20B91000\n\nMini Militia:\n\u20B91000\n\nWWE:\n\u20B9500\n\nMortal Kombat:\n\u20B9500");

                db.update(TABLE_EVENTS, cv, COL_EVENT_NAME + " = ?", new String[]{"LAN Gaming"});

                cv.put(COL_EVENT_FAVORITE, 0);
                cv.put(COL_EVENT_REMINDER, 0);
                for (Event event : eventsNew) {
                    cv.put(COL_EVENT_NAME, event.getName());
                    cv.put(COL_EVENT_DESCRIPTION, event.getDescription());
                    cv.put(COL_EVENT_CATEGORY, event.getCategory());
                    cv.put(COL_EVENT_VENUE, event.getVenue());
                    cv.put(COL_EVENT_TIME, event.getTime());
                    cv.put(COL_EVENT_REGISTRATION, event.getRegistration());
                    cv.put(COL_EVENT_PRIZES, event.getPrizes());
                    cv.put(COL_EVENT_CONTACT1_NAME, event.getContact1_name());
                    cv.put(COL_EVENT_CONTACT1_NO, event.getContact1_no());
                    cv.put(COL_EVENT_CONTACT2_NAME, event.getContact2_name());
                    cv.put(COL_EVENT_CONTACT2_NO, event.getContact2_no());
                    cv.put(COL_COLOR, event.getColor());
                    db.insert(TABLE_EVENTS, null, cv);
                }
            }
        }

        public MatrixDBConnectionHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }


        public List<Event> getData(String name, int db_position)
        /*here position is the column no according to which we want to fetch data. For sorting according to category,
         position is 4(column array position), name is Category name. For fetching using name, we use position 1 and get(position) will get us
         the respective value in the list
        */

        {
            List<Event> list = new ArrayList<>();

            SQLiteDatabase db = getReadableDatabase();
            String[] columns = {COL_EVENT_ID, COL_EVENT_NAME, COL_EVENT_DESCRIPTION, COL_EVENT_CATEGORY, COL_EVENT_VENUE, COL_EVENT_REGISTRATION, COL_EVENT_PRIZES, COL_EVENT_TIME, COL_EVENT_CONTACT1_NAME, COL_EVENT_CONTACT1_NO, COL_EVENT_CONTACT2_NAME, COL_EVENT_CONTACT2_NO, COL_EVENT_FAVORITE, COL_EVENT_REMINDER,COL_COLOR};
            Cursor cursor;
            if(Objects.equals(name, "Mega"))
                cursor = db.query(TABLE_EVENTS, columns, columns[db_position] + " = '" + name + "'", null, null, null, COL_EVENT_ID+" DESC");
            else
                cursor = db.query(TABLE_EVENTS, columns, columns[db_position] + " = '" + name + "'", null, null, null, null);

            int index0 = cursor.getColumnIndex(COL_EVENT_ID);
            int index1 = cursor.getColumnIndex(COL_EVENT_NAME);
            int index2 = cursor.getColumnIndex(COL_EVENT_DESCRIPTION);
            int index3 = cursor.getColumnIndex(COL_EVENT_CATEGORY);
            int index4 = cursor.getColumnIndex(COL_EVENT_VENUE);
            int index5 = cursor.getColumnIndex(COL_EVENT_TIME);
            int index6 = cursor.getColumnIndex(COL_EVENT_REGISTRATION);
            int index7 = cursor.getColumnIndex(COL_EVENT_PRIZES);
            int index8 = cursor.getColumnIndex(COL_EVENT_CONTACT1_NAME);
            int index9 = cursor.getColumnIndex(COL_EVENT_CONTACT1_NO);
            int index10 = cursor.getColumnIndex(COL_EVENT_CONTACT2_NAME);
            int index11 = cursor.getColumnIndex(COL_EVENT_CONTACT2_NO);
            int index12 = cursor.getColumnIndex(COL_EVENT_FAVORITE);
            int index13 = cursor.getColumnIndex(COL_EVENT_REMINDER);
            int index14 = cursor.getColumnIndex(COL_COLOR);

            String name_event, desc, category, venue, time, registration, prizes, contact_name1, contact_name2, contact_no1, contact_no2, color;
            int img, fav, reminder;

            while (cursor.moveToNext()) {
                name_event = cursor.getString(index1);
                desc = cursor.getString(index2);
                category = cursor.getString(index3);
                img = images[cursor.getInt(index0)-1];
                venue = cursor.getString(index4);
                time = cursor.getString(index5);
                registration = cursor.getString(index6);
                prizes = cursor.getString(index7);
                contact_name1 = cursor.getString(index8);
                contact_no1 = cursor.getString(index9);
                contact_name2 = cursor.getString(index10);
                contact_no2 = cursor.getString(index11);
                fav = cursor.getInt(index12);
                reminder = cursor.getInt(index13);
                color = cursor.getString(index14);
                Event event = new Event(name_event, desc, category, img, venue, time, registration, prizes, contact_name1, contact_no1, contact_name2, contact_no2, fav, reminder, color);
                list.add(event);
            }
            cursor.close();
            return list;
        }
    }
}