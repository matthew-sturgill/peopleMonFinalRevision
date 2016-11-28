package mattsturgill.peoplemonfinal.Stages;

import android.app.Application;

import mattsturgill.peoplemonfinal.PeoplemonApplication;
import mattsturgill.peoplemonfinal.R;
import mattsturgill.peoplemonfinal.Riggers.SlideRigger;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class EditProfileStage extends IndexedStage {
    private final SlideRigger rigger;

    @Override
    public int getLayoutId() {
        return R.layout.edit_profile_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    public EditProfileStage(Application context) {
        super(MapStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public EditProfileStage() {
        this(PeoplemonApplication.getInstance());
    }
}
