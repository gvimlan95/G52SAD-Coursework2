public class SceneManager {

    private SceneManager instance;

    private SceneManager(){
    }

    public SceneManager getInstance(){
        if(instance == null){
            instance = new SceneManager();
        }
        return instance;
    }
}
