package mattsturgill.peoplemonfinal.Stages;

import android.app.Application;

import mattsturgill.peoplemonfinal.PeoplemonApplication;
import mattsturgill.peoplemonfinal.R;
import mattsturgill.peoplemonfinal.Riggers.SlideRigger;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class NearStage extends IndexedStage {
    private final SlideRigger rigger;

    public NearStage(Application context) {
        super(NearStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public NearStage() {this(PeoplemonApplication.getInstance());}

    @Override
    public int getLayoutId() {
        return R.layout.near_view;
    }

    @Override
    public Rigger getRigger() {return rigger;}
}
