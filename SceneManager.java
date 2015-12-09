public class SceneManager {

    private static SceneManager myObj;

    private SceneManager(){

    }

    public static SceneManager getInstance(){
        if(myObj == null){
            myObj = new SceneManager();
        }
        return myObj;
    }

    public void getSomeThing(){
        // do something here

    }
}
