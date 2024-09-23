package com.xvdong.rebot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 系统调用
 */
public class TCWManager {
    private static Context mContext;
    public static TCWManager instance;
    private static PowerManager pm;
    private static WakeLock wakeLock;

    public static void acquireWakeLock(Context context) {
        //Log.d("SystemUI", "Here is acquireWakeLock.");
        pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SystemUI");
        wakeLock.acquire();
    }

    public TCWManager(Context context) {
        mContext = context;
    }

    public static TCWManager getInstance(Context context) {
        if (instance == null) {
            instance = new TCWManager(context);
        }
        return instance;
    }

    public void hideStatusBar(boolean ishide) {
        Intent mIntent = new Intent();
        if (ishide) mIntent.setAction("android.intent.action.hidebar");
        else mIntent.setAction("android.intent.action.showbar");
        mContext.sendBroadcast(mIntent);
    }

    public String getVersion() {

        String version = do_exec("getprop ro.product.firmware");
        return version;
    }

    public String getDeviceId() {

        String version = do_exec("getprop ro.serialno");
        return version;
    }

    public static void setbrightness(int brightness) {
        String cmd = "settings put system screen_brightness " + brightness;
        execShell(cmd);
    }

    public int getbrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return systemBrightness;
    }

    public static void killbackgroundapk(String cmd) {

        execShell("am force-stop " + cmd);
    }

    public static void backLightoff() {
        acquireWakeLock(mContext);
        execShell("input keyevent 223");
	/*	try {
			FileOutputStream out = new FileOutputStream("/sys/devices/virtual/disp/disp/opera_bl");
			out.write("close".getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// hdmi
		execShell("echo 0 > /sys/devices/virtual/hdmi/hdmi/attr/enable");*/


    }

    public static void backLighton() {
        wakeLock.release();
        execShell("input keyevent 224");
		/*try { 
			FileOutputStream out = new FileOutputStream("/sys/devices/virtual/disp/disp/opera_bl"); 
			out.write("open".getBytes()); 
			out.close(); 
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
				// hdmi
				execShell("echo 1 > /sys/devices/virtual/hdmi/hdmi/attr/enable");*/
    }

    public static void takeScreenshot(String path) {
        String cmd = "screencap -p " + path;
        execShell(cmd);
    }

    public static void shutDown() {
        Intent mIntent = new Intent();
        mIntent.setAction("android.intent.action.shutdown");
        mContext.sendBroadcast(mIntent);
    }

    public static void reboot() {
        Intent mIntent = new Intent();
        mIntent.setAction("android.intent.action.reboot");
        mContext.sendBroadcast(mIntent);

    }

    public static void setRotationScreen(int rotation) {

        Intent mIntent = new Intent();
        mIntent.setAction("android.action.rotation");
        mIntent.putExtra("rotation", rotation);
        mContext.sendBroadcast(mIntent);

    }

    public int getRotationScreen() {

        String rotation = do_exec("getprop persist.sys.rotation");
        int i = Integer.parseInt(rotation);
        return i;


    }

    public static void execSuCmd(String cmd) {
        execShell(cmd);
    }

    public static void silentInstallApk(String cmd0, String cmd1) {
        String cmd = "pm install -r " + cmd0 + " \n ";
        execShell(cmd);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        String cmd2 = "am start -n " + cmd1 + " \n ";
        execShell(cmd2);
    }

    public static void upgradeSystem(String cmd) {
        //
    }

    public static void reboot_4g_module() {

        //execShell("echo -en 'AT+CFUN=1,1\r\n' >/dev/ttyUSB1");
        execShell("echo -en \"AT+CFUN=1,1\r\n\" >/dev/ttyUSB1");

    }

    public void setTime(int year, int month, int monthDay, int hour, int minute) {
        Intent mIntent = new Intent();
        mIntent.setAction("wits.action.setsystimes");
        mIntent.putExtra("year", year);
        mIntent.putExtra("month", month);
        mIntent.putExtra("monthDay", monthDay);
        mIntent.putExtra("hour", hour);
        mIntent.putExtra("minute", minute);
        mContext.sendBroadcast(mIntent);

    }

    public static void setPowerUp(long time) {
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.android.settings.action.REQUEST_POWER_ON");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Toast.makeText(mContext, time + "after how time poweron ", Toast.LENGTH_SHORT).show();
        long timeNow = System.currentTimeMillis();
        //Log.e("wc","=nick=====setPowerUp time =" + (timeNow + (time*1000)));
        am.set(4, timeNow + (time * 1000), pendingIntent);
    }

    public static long timeToSec(int[] time) {
        long sec = 0;
        String Y = String.valueOf(time[0]);
        String M = String.valueOf(time[1]);
        if (M.length() < 2) M = '0' + M;
        String D = String.valueOf(time[2]);
        if (D.length() < 2) D = '0' + D;
        String h = String.valueOf(time[3]);
        if (h.length() < 2) h = '0' + h;
        String m = String.valueOf(time[4]);
        if (m.length() < 2) m = '0' + m;
        String s = "00";


        String dString = Y + '-' + M + '-' + D + ' ' + h + ':' + m + ':' + s;
        //Log.i("wc","==dString========="+dString);
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sDate.parse(dString);
            sec = date.getTime();
            sec = sec - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sec = sec / 1000;
        return sec;
    }

    public void setPowerOn(int[] poweron) {
        long sec = timeToSec(poweron);
        //Log.i("wc","setPowerOn sec" + sec);

        if (sec > 0) {
            setPowerUp(sec);
        } else {
            //Log.i("wc","==time < 0 =========");
        }
    }

    public void setPowerOff(int[] poweroff) {
        long sec = timeToSec(poweroff);

        if (sec > 0) {
            setShutdown(sec);
        } else {
            //Log.i("wc","==time < 0 =========");
        }

    }

    public void setShutdown(long time) {
        AlarmManager mAlarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        int alarmType = AlarmManager.RTC_WAKEUP;

        Intent intent = new Intent("android.intent.action.shutdown");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        mAlarm.cancel(pendingIntent);

        Toast.makeText(mContext, time + "after second shutoff ", Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();//������Ҫ��������˵�ǰ��Calendar�����������ʹ�ü�¼��Ӱ�쵼��ʱ�䲻׼��
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, (int) time);
        mAlarm.set(alarmType, calendar.getTimeInMillis(), pendingIntent);
    }

    String do_exec(String cmd) {
        String s = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                s = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }


    public static void execShell(String cmd) {
        try {
            //获取root权限
            Process p = Runtime.getRuntime().exec("su");
            //获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            //写入要执行的命令
            dataOutputStream.writeBytes(cmd);
            //刷新缓冲区
            dataOutputStream.flush();
            //关闭数据输出流
            dataOutputStream.close();
            outputStream.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


}
