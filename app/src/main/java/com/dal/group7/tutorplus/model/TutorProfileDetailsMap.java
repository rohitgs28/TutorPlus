package com.dal.group7.tutorplus.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TutorProfileDetailsMap {

    public   String tutorDesc;
    public   String tutorName;
    public   String Qualification;
    public  static String callInformation;
    public  static String emailInformation;
    public  static String messageContent;

    public TutorProfileDetailsMap(JSONObject tutorDetailHomePage) {

        try{
            this.tutorDesc = (String) tutorDetailHomePage.get("Short Desc");
            this.tutorName = (String) tutorDetailHomePage.get("Tutor Name");
            this.Qualification = (String) tutorDetailHomePage.get("Qualification");
            this.callInformation = (String) tutorDetailHomePage.get("Mobile information");
            this.emailInformation = (String) tutorDetailHomePage.get("Email information");
            this.messageContent = (String) tutorDetailHomePage.get("Message Content");

        }catch (JSONException e) {
          e.printStackTrace();
        }
    }

    public static String getCallInformation(){
        return callInformation;
    }


    public static String getEmailInformation(){
        return emailInformation;
    }
}
