
/**
* Main method for launching the shopping cart application
*/

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginWindow();
            }
        });
    }
}
