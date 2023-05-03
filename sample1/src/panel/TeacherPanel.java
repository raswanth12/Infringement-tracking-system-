package sample1.src.panel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherPanel extends JPanel {
    public TeacherPanel() {
        setLayout(new BorderLayout());

        // TODO: Display uploaded files in a list, table, or other suitable component

        JButton generateReportButton = new JButton("Generate Report");
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Generate report for uploaded files
            }
        });

        add(generateReportButton, BorderLayout.SOUTH);
    }
}
