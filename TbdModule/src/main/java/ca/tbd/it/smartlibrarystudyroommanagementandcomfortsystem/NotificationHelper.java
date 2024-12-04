package ca.tbd.it.smartlibrarystudyroommanagementandcomfortsystem;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
public class NotificationHelper {

    private static final String CHANNEL_ID = "FEEDBACK_CHANNEL";
    private static final CharSequence CHANNEL_NAME = "Feedback Notifications";
    private static final String CHANNEL_DESCRIPTION = "Notifications for feedback submission availability";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
