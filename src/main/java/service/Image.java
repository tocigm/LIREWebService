package service;

/**
 * Created by kidio on 3/17/17.
 */
public class Image {

    private String url;
    private String name;
    private float   score;

    public Image(String url, String name, float score) {
        this.url = url;
        this.name = name;
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
