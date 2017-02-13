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

public class Event {

    //////////Custom data type for events//////////
    private String name, description, category, venue, time, registration, prizes, contact1_name, contact1_no, contact2_name, contact2_no, color;
    private int image, favourite, reminder;

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

    String getCategory() {
            return category;
        }

    public String getName() {
            return name;
        }

    public int getFavourite() {
            return favourite;
        }

    public int getReminder() {
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


    Event(String name, String description, String category, String venue, String time, String registration, String prizes, String contact1_name, String contact1_no, String contact2_name, String contact2_no, String color) {
        //Used for inserting events in database on first launch
            this.name = name;
            this.description = description;
            this.category = category;
            this.venue = venue;
            this.time = time;
            this.registration = registration;
            this.prizes = prizes;
            this.contact1_name = contact1_name;
            this.contact1_no = contact1_no;
            this.contact2_name = contact2_name;
            this.contact2_no = contact2_no;
            this.color=color;
        }

    Event(String name, String description, String category, int image, String venue, String time, String registration, String prizes, String contact1_name, String contact1_no, String contact2_name, String contact2_no, int favourite, int reminder, String color)
    {
        //Used while fetching data
        this.name = name;
        this.description = description;
        this.category = category;
        this.image = image;
        this.venue = venue;
        this.time = time;
        this.registration = registration;
        this.prizes = prizes;
        this.contact1_name = contact1_name;
        this.contact1_no = contact1_no;
        this.contact2_name = contact2_name;
        this.contact2_no = contact2_no;
        this.favourite = favourite;
        this.reminder = reminder;
        this.color = color;
    }
    }