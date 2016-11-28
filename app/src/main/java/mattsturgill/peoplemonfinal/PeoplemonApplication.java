package mattsturgill.peoplemonfinal;

import android.app.Application;

import flow.Flow;
import flow.History;
import mattsturgill.peoplemonfinal.Stages.MapStage;

/**
 * Created by matthewsturgill on 11/24/16.
 */

public class PeoplemonApplication extends Application {
    private static PeoplemonApplication application;
    public final Flow mainFlow = new Flow(History.single(new MapStage()));//history is stack information

    public static final String API_BASE_URL =
            "https://efa-peoplemon-api.azurewebsites.net:443/";

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static PeoplemonApplication getInstance() {
        return application;
    }

    public static Flow getMainFlow() {
        return getInstance().mainFlow;
    }
}
