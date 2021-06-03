import java.util.Iterator;
import java.util.TreeMap;

public class Main
{

    public static void main(String[] args) {



        State state = new State();
        try {
            state.parse("logs.txt");
        } catch (LinhaIncorretaException e) {
            System.out.println(e.getMessage());
        }
        Controller controller = new Controller(state);
        controller.run();





    }
}


