package org.acacha.ebre_escool.ebre_escool_app.accounts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.acacha.ebre_escool.ebre_escool_app.accounts.EbreEscoolAuthenticator;

/**
 * Sergi Tur Badenas
 */
public class EbreEscoolAuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        EbreEscoolAuthenticator authenticator = new EbreEscoolAuthenticator(this);
        return authenticator.getIBinder();
    }
}
