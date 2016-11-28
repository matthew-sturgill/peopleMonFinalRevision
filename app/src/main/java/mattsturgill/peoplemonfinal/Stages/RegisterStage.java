package mattsturgill.peoplemonfinal.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;

import mattsturgill.peoplemonfinal.PeoplemonApplication;
import mattsturgill.peoplemonfinal.R;
import mattsturgill.peoplemonfinal.Riggers.SlideRigger;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class RegisterStage extends IndexedStage{
    private final SlideRigger rigger;

    public RegisterStage(Application context){
        super(RegisterStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public RegisterStage(){this(PeoplemonApplication.getInstance());}

    @Override
    public int getLayoutId() {
        return R.layout.register_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}
