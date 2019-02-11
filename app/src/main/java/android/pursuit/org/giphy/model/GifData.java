package android.pursuit.org.giphy.model;

public class GifData {

    private Images images;
    private String title;

    public Images getImages() {
        return images;
    }
    public String getTitle() {
        return title;
    }

    //remember you can always modify your objects to give you the info you want easier.
    public String getDownsizedUrl() {
        return getImages().getDownsized().geturl();
    }

}
