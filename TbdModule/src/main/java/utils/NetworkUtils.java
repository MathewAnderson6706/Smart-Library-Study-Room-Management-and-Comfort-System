package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

public class NetworkUtils {
    /**
     * Check if the device is offline.
     * @param context The context of the calling component.
     * @return True if there is no active network, false otherwise.
     */
    public static boolean isOffline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager != null ? connectivityManager.getActiveNetwork() : null;
        return network == null; // Returns true if no active network is available
    }
}
