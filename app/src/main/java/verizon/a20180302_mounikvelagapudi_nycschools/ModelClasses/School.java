package verizon.a20180302_mounikvelagapudi_nycschools.ModelClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mounikvelagapudi on 03/03/18.
 */

public class School {

private String schoolName;
private String website;
private String writingScore;
private String mathScore;
private String readingScore;

public School(JSONObject schoolObject, String type){
    try {
        if (type.equalsIgnoreCase("school")){
            this.schoolName = schoolObject.getString("school_name");
            this.website = schoolObject.getString("website");
        } else {
            this.schoolName = schoolObject.getString("school_name");
            this.writingScore = schoolObject.getString("sat_writing_avg_score");
            this.mathScore = schoolObject.getString("sat_math_avg_score");
            this.readingScore = schoolObject.getString("sat_critical_reading_avg_score");
        }

    } catch (JSONException e) {
        e.printStackTrace();
    }
}

    public String getWritingScore() {
        return writingScore;
    }

    public void setWritingScore(String writingScore) {
        this.writingScore = writingScore;
    }

    public String getMathScore() {
        return mathScore;
    }

    public void setMathScore(String mathScore) {
        this.mathScore = mathScore;
    }

    public String getReadingScore() {
        return readingScore;
    }

    public void setReadingScore(String readingScore) {
        this.readingScore = readingScore;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
