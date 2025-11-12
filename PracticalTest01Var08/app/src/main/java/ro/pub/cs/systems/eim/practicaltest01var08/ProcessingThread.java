package ro.pub.cs.systems.eim.practicaltest01var08;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;
public class ProcessingThread extends Thread {

    private final Context context;
    private boolean isRunning = true;

    private final Random random = new Random();
    private static String ans;

    public ProcessingThread(Context context, String firstNumber) {
        this.context = context;
        ans = firstNumber;

    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    public static String build_mes() {


        StringBuilder sb = new StringBuilder();
        String s = "";
        Random r= new Random();

        // Generate random integers in range 0 to 999
        int r1 = r.nextInt(ans.length());
        for (int i = 0;i < ans.length();i++) {
            if (i == r1) {
                sb.append(ans.charAt(i));
            } else {
                sb.append('*');
            }

        }


        s = sb.toString();
        return  s;
    }
    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                build_mes());
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
