package mattsturgill.peoplemonfinal.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;

import mattsturgill.peoplemonfinal.PeoplemonApplication;
import mattsturgill.peoplemonfinal.R;
import mattsturgill.peoplemonfinal.Riggers.SlideRigger;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class MapStage extends IndexedStage{
    private final SlideRigger rigger;

    public MapStage(Application context){
        super(MapStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public MapStage(){
        this(PeoplemonApplication.getInstance());

    }

    @Override
    public int getLayoutId()  {
        return R.layout.map_page_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}
