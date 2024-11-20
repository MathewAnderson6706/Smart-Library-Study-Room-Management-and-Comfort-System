package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
public class RoomUtils {

    public static void setupRoom(Context context, ImageButton roomButton, String roomId, DatabaseReference databaseReference, RoomActionListener listener) {
        databaseReference.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String status = snapshot.child("status").getValue(String.class);
                    boolean isOccupied = "occupied".equals(status);

                    roomButton.setEnabled(isOccupied);

                    if (isOccupied) {
                        roomButton.setImageResource(R.drawable.roombooked);
                        roomButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        roomButton.setOnClickListener(v -> listener.onRoomSelected(roomId));
                    }
                } else {
                    Toast.makeText(context, "Room data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface RoomActionListener {
        void onRoomSelected(String roomId);
    }
}
