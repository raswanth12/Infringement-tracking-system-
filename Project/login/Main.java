import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }

    @Override
    public String toString() {
        return "Main []";
    }
}
