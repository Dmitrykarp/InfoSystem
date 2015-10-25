
public class InfoSystem {
    public static void main(String[] args) {
        Model thisModel = new Model();
        View thisView = new View();
        Controller thisController = new Controller(thisModel, thisView);

        thisController.run();
    }
}
