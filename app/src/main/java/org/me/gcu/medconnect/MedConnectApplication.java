package org.me.gcu.medconnect;

import android.app.Application;
import org.me.gcu.medconnect.network.AwsClientProvider;
import org.me.gcu.medconnect.utils.TableCreator;

public class MedConnectApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AwsClientProvider.initialize(this);

        TableCreator.createTables();
    }
}
