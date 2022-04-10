package uqac.dim.crypturmess.utils.looper;

import uqac.dim.crypturmess.CrypturMessApplication;
import uqac.dim.crypturmess.databaseAccess.room.AppLocalDatabase;

public class DeleteMessagesLooper implements Runnable {
    private AppLocalDatabase database= AppLocalDatabase.getInstance(CrypturMessApplication.getContext());
    @Override
    public void run() {
        while (true) {
            database.messageDao().deleteOldMessages(System.currentTimeMillis()-(1000*60*60)); //J-1
            try {
                Thread.sleep(3600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
