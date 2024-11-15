/*
Mathew Anderson-Saavedra n01436706
Nicole Chlea Manaoat N01565017
Medi Muamba Nzambi N01320883
Section RCA
Safah Virk N01596470
Section RCB
 */
package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

public class FeedbackHelper {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String comment;
    private float rating;

    public FeedbackHelper() {
    }

    public FeedbackHelper(String fullName, String phoneNumber, String email, String comment, float rating) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters and Setters
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getComment() { return comment; }
    public float getRating() { return rating; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setComment(String comment) { this.comment = comment; }
    public void setRating(float rating) { this.rating = rating; }
}
