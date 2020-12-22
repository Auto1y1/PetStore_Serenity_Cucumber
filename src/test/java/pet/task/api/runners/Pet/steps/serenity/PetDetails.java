package pet.task.api.runners.Pet.steps.serenity;

/**
 * Created by Bapu Joshi on 12/20/2020.
 */

public class PetDetails {
    private String name;
    private String status;
    private int id;
    private String photoURL;
    public PetDetails(){
        this.name = name;
        this.status = status;
        this.photoURL = photoURL;
        this.id=id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
