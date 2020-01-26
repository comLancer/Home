package home.example.home;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Model {

    Model mUser;
    private String firebaseUserId;
    private String email;
    private String Tag;
    private int phone;
    private String imageUrl;
    private ImageView changeProfileImage;
    private EditText editProfileName;
    private EditText editProfileTage;
    private EditText editProfileInfo;

    public static boolean isValidEmail(String email) {
        if (email.contains("@")) {
            return true;
        }
        return false;
    }

    public static void displayMessageToast(Context context, String displayMessage) {
        Toast.makeText(context, displayMessage, Toast.LENGTH_LONG).show();
    }

    public ImageView getChangeProfileImage() {
        return changeProfileImage;
    }

    public void setChangeProfileImage(ImageView changeProfileImage) {
        this.changeProfileImage = changeProfileImage;
    }

    public EditText getEditProfileName() {
        return editProfileName;
    }

    public void setEditProfileName(EditText editProfileName) {
        this.editProfileName = editProfileName;
    }

    public EditText getEditProfileTage() {
        return editProfileTage;
    }

    public void setEditProfileTage(EditText editProfileTage) {
        this.editProfileTage = editProfileTage;
    }

    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        this.Tag = tag;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public EditText getEditProfileInfo() {
        return editProfileInfo;
    }

    public void setEditProfileInfo(EditText editProfileInfo) {
        this.editProfileInfo = editProfileInfo;
    }
}




