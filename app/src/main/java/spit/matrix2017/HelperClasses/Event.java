package spit.matrix2017.HelperClasses;

import spit.matrix2017.R;

/**
 * Created by Rohit on 13/11/16.
 */

public class Event {

    //////////Custom data type for events//////////
    private String name, description, category, venue, time, registration, prizes, contact1_name, contact1_no, contact2_name, contact2_no, color;
    private int image;
    private long favourite, reminder;

    public String getVenue() {
            return venue;
        }

    public String getTime() {
            return time;
        }

    public String getRegistration(){
        return registration;
    }

    public String getPrizes(){
        return prizes;
    }

    public String getContact1_name() {
            return contact1_name;
        }

    public String getContact2_name() {
            return contact2_name;
        }

    public String getContact1_no() {
            return contact1_no;
        }

    public String getContact2_no() {
            return contact2_no;
        }

    public String getCategory() {
            return category;
        }

    public String getName() {
            return name;
        }

    public long getFavourite() {
            return favourite;
        }

    public long getReminder() {
            return reminder;
        }

    public String getDescription() {
            return description;
        }

    public int getImage(){
        return image;
    }

    public String getColor() {
        return color;
    }


    public Event(String name, String description, String category, String venue, String time, String registration, String prizes, String contact1_name, String contact1_no, String contact2_name, String contact2_no, String color) {
            this.name = name;
            this.description = description;
            this.category = category;
            this.image = extractImage(name);
            this.venue = venue;
            this.time = time;
            this.registration = registration;
            this.prizes = prizes;
            this.contact1_name = contact1_name;
            this.contact1_no = contact1_no;
            this.contact2_name = contact2_name;
            this.contact2_no = contact2_no;
            this.favourite=0;
            this.reminder=0;
            this.color=color;
        }

        public Event(String name, String description, String category, String venue, String time, String registration, String prizes, String contact1_name, String contact1_no, String contact2_name, String contact2_no, int favourite, int reminder, String color)
        {
            //used while fetching data
            this.name = name;
            this.description = description;
            this.category = category;
            this.image = extractImage(name);
            this.venue = venue;
            this.time = time;
            this.registration = registration;
            this.prizes = prizes;
            this.contact1_name = contact1_name;
            this.contact1_no = contact1_no;
            this.contact2_name = contact2_name;
            this.contact2_no = contact2_no;
            this.favourite=favourite;
            this.reminder=reminder;
            this.color=color;
        }

    private int extractImage(String name) {
        switch(name){
            case "VSM": return R.drawable.virtual_stock_market;
            case "Codatron": return R.drawable.codatron;
            case "Laser Maze": return R.drawable.laser_maze;
            case "Laser Tag": return R.drawable.laser_tag;

            case "Tech Charades": return R.drawable.tech_charades;
            case "Battle Frontier": return R.drawable.battle_frontier;
            case "Escape Plan": return R.drawable.escape_plan;
            case "Technovanza": return R.drawable.technovanza;
            case "TechXplosion": return R.drawable.tech_xplosion;
            case "No Escape": return R.drawable.no_escape;
            case "Techeshi's Castle": return R.drawable.techeshis_castle;
            case "Tesseract": return R.drawable.tesseract;

            case "Human Foosball": return R.drawable.human_foosball;
            case "Battle of Brains": return R.drawable.battle_of_brains;
            case "LAN Gaming": return R.drawable.lan_gaming;
            case "Pokemon Showdown": return R.drawable.pokemon_showdown;
            case "LAN Mafia": return R.drawable.lan_mafia;
            case "Mind That Word": return R.drawable.mind_that_word;
        }
        return 0;
    }
    }