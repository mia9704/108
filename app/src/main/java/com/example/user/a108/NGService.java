package com.example.user.a108;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class NGService extends Service {
    public NGService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {

    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        Bundle bundle = intent.getExtras();
        Deck setOfCards = (Deck) bundle.getSerializable("deck");
        boolean myTurn = bundle.getBoolean("myTurn");
        if(setOfCards.gameOver())
        {
            Intent i = new Intent(NGService.this, MainActivity.class);
            startActivity(i);
        }
        /*View view =
        final Button go = (Button) View.findViewById(R.id.go);
        final Button reset = (Button) findViewById(R.id.reset);*/
        if(myTurn)
        {

        }
        else
        {

        }
        return 0;
    }
}
