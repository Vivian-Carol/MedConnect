package org.me.gcu.medconnect;

import android.app.Application;
import org.me.gcu.medconnect.network.AwsClientProvider;

public class MedConnectApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AwsClientProvider.initialize(this);
    }
}
