/**
 * Created by Support on 20.10.2015.
 */
public class InfoSystem {
    public static void main(String[] args) {
        Model thisModel = new Model();
        View thisView = new View();
        Controller thisController = new Controller(thisModel, thisView);

        thisController.run();
    }
}
