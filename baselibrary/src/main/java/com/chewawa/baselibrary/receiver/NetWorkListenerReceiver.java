package com.chewawa.baselibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 监听网络变化的广播接收器
 * nanfeifei
 * 2018/2/24 10:30
 *
 * @version 1.0
 */
public class NetWorkListenerReceiver extends BroadcastReceiver {
  public static final String ACTION_NETWORK_CHANGE = "action_network_change";
  public static final String KEY_NETWORK_STATE = "isConnect";
  @Override public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
      //**判断当前的网络连接状态是否可用*/
      ConnectivityManager connectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo info = connectivityManager.getActiveNetworkInfo();
      if ( info != null && info.isAvailable()){
        //当前网络状态可用
        sendMessage(context, true);
        int netType = info.getType();
        if (netType == ConnectivityManager.TYPE_WIFI){
          Log.e("NETSTATUE", "当前网络状态为-wifi");
        }else if (netType == ConnectivityManager.TYPE_MOBILE ){
          Log.e("NETSTATUE", "当前网络状态为-mobile");
        }
      }else {
        //当前网络不可用
        Log.e("NETSTATUE", "无网络连接");
        sendMessage(context, false);
      }
    }

  }

  /**
   * 网络变化
   * @param context
   * @param isConnect
   */
  private void sendMessage(Context context, boolean isConnect) {

    Intent mIntent=new Intent(ACTION_NETWORK_CHANGE);
    mIntent.putExtra(KEY_NETWORK_STATE, isConnect);
    context.sendBroadcast(mIntent);
  }
}
