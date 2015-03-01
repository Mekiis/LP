package banand.lionel.com.tpandroid.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import banand.lionel.com.tpandroid.Service.TpAndroidService;

public class TpAndroidReceiver extends BroadcastReceiver {
    public TpAndroidReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action != null) {
            Log.e("Action : ", action);
            context.startService(new Intent(context, TpAndroidService.class));
        }
    }
}
