package mattsturgill.peoplemonfinal.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;

import mattsturgill.peoplemonfinal.PeoplemonApplication;
import mattsturgill.peoplemonfinal.R;
import mattsturgill.peoplemonfinal.Riggers.SlideRigger;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class CaughtListStage extends IndexedStage {
    private final SlideRigger rigger;

    public CaughtListStage(Application context) {
        super(CaughtListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.caught_list_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }

    public CaughtListStage() {
        this(PeoplemonApplication.getInstance());
    }
}
