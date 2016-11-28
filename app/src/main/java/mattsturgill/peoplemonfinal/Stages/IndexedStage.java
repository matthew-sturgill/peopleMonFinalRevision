package mattsturgill.peoplemonfinal.Stages;

import com.davidstemmer.screenplay.stage.XmlStage;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public abstract class IndexedStage extends XmlStage {

    public String id;

    protected IndexedStage(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexedStage that = (IndexedStage) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
