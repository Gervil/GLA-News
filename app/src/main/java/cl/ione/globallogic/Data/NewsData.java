package cl.ione.globallogic.Data;

public class NewsData {

    //Variables
    private final String image;
    private final String title;
    private final String description;

    //Inicialization
    public NewsData(String image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    //Properties
    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
