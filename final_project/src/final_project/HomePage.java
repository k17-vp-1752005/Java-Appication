package final_project;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage {
    JLabel jlab;
    JFrame jfrm;
    
    HomePage() {
        jfrm = new JFrame("Login");
        jfrm.setLayout(new BoxLayout(jfrm, BoxLayout.Y_AXIS));
        jfrm.setSize(220, 200);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Create a label that will display the menu selection.
        jlab = new JLabel();
// Create the menu bar.
        JMenuBar jmb = new JMenuBar();
        JMenu jmHP = new JMenu("Homepage");
        JMenu jmLI = new JMenu("Log in");
        JMenu jmSU = new JMenu("Sign up");
        jmb.add(jmHP);
        jmb.add(jmLI);
        jmb.add(jmSU);

        JPanel name = new JPanel();
        JLabel label1 = new JLabel("Username: ");
        JTextArea username = new JTextArea();
        name.add(label1);
        name.add(username);

        JPanel pwd = new JPanel();
        JLabel label2 = new JLabel("Password: ");
        JPasswordField pass = new JPasswordField();
        pwd.add(label2);
        pwd.add(pass);

        jfrm.add(jmb);
        jfrm.add(name);
        jfrm.add(pwd);
    }
}
