package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
public class AccessCodeUtils {

    public static void promptForAccessCode(Context context, String roomId, DatabaseReference databaseReference, AccessCodeListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.enter_access_code);

        final EditText input = new EditText(context);
        builder.setView(input);

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            String enteredCode = input.getText().toString();
            validateAccessCode(context, roomId, enteredCode, databaseReference, listener);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private static void validateAccessCode(Context context, String roomId, String enteredCode, DatabaseReference databaseReference, AccessCodeListener listener) {
        databaseReference.child(roomId).child("accessCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String correctCode = snapshot.getValue(String.class);
                    if (correctCode != null && correctCode.equals(enteredCode)) {
                        listener.onAccessGranted();
                    } else {
                        Toast.makeText(context, R.string.invalid_code, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.access_code_not_set, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface AccessCodeListener {
        void onAccessGranted();
    }
}
