package authority;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tests the WordCheckPermission class.
 * Created by zjh on 2016/10/23.
 */
public class PermissionTest {
    public static void main(String[] args) {
        //System.setProperty("java.security.policy", "authority/PermissionTest.policy");
        System.setSecurityManager(new SecurityManager());
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new PermissionTestFrame();
                frame.setTitle("PermissionTest");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class PermissionTestFrame extends JFrame {
    private JTextField textField;
    private WordCheckTextArea textArea;
    private static final int TEXT_ROWS = 20;
    private static final int TEXT_COLUMNS = 60;

    public PermissionTestFrame() {
        textField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(textField);
        JButton openButton = new JButton("Insert");
        panel.add(openButton);
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertWords(textField.getText());
            }
        });

        add(panel, BorderLayout.NORTH);

        textArea = new WordCheckTextArea();
        textArea.setRows(TEXT_ROWS);
        textArea.setColumns(TEXT_COLUMNS);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        pack();
    }

    public void insertWords(String words) {
        try {
            textArea.append(words + "\n");
        } catch (SecurityException se) {
            JOptionPane.showMessageDialog(this, "Bad word typed in!");
            se.printStackTrace();
        }
    }
}

class WordCheckTextArea extends JTextArea {
    public void append(String text) {
        WordCheckPermission p = new WordCheckPermission(text, "insert");
        SecurityManager manager = System.getSecurityManager();
        if (manager != null) {
        	System.out.println("get");
            manager.checkPermission(p);
        }
        super.append(text);
    }
}