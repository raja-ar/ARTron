

package com.RaceAr.broadcast;


        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;

        import com.RaceAr.service.AdService;
        import com.RaceAr.widget.NetworkUtil;


public class AdStartReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(final Context context, Intent intent) {


        if(NetworkUtil.getConnectivityStatus(context)!=0){
            Intent AdService=new Intent(context, AdService.class);
            context.startService(AdService);
        }
    }


}
