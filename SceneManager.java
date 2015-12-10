public class SceneManager {

    private static SceneManager firstInstance = null;

    private SceneManager(){}

    public SceneManager getInstance(){

        if(firstInstance == null){

            firstInstance = new SceneManager();
        }
        return firstInstance;
    }
}
