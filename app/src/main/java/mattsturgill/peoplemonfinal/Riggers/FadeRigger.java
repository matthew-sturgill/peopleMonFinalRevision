package mattsturgill.peoplemonfinal.Riggers;

import android.app.Application;

import com.davidstemmer.screenplay.stage.rigger.AnimResources;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;

import mattsturgill.peoplemonfinal.R;

/**
 * Created by matthewsturgill on 10/31/16.
 */

public class FadeRigger extends TweenRigger {
    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn = R.anim.fade_in;
        params.backIn = -1;
        params.backOut = R.anim.fade_out;
        params.forwardOut = -1;
    }

    public FadeRigger(Application context){
        super(context, params);
    }
}
