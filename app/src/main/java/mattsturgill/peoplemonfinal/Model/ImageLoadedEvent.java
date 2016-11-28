package mattsturgill.peoplemonfinal.Model;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class ImageLoadedEvent {

    public String selectedImage;

    public ImageLoadedEvent(String selectedImage) {
        this.selectedImage = selectedImage;
    }

    public String getSelectedImage() {
        return selectedImage;
    }
}
