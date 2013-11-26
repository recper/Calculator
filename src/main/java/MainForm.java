import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 18.11.13
 * Time: 9:48
 * To change this template use File | Settings | File Templates.
 */
public class MainForm extends JFrame {
    JButton button;
    JTextArea label;
    JTextField text;
    Calculator calculator;

    public MainForm() {
        super();
        calculator = new Calculator();
        initWindow();
        initListeners();
    }

    private void initListeners() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonListenerMethod();
            }
        });
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                textListenerMethod(e);
            }
        });
    }

    private void workWithConsole() {
        this.setVisible(false);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            if (!s.equals("window")) {
                Float res = calculator.calculate(s);
                if (calculator.errorMessage == "") {
                    System.out.print("\nResult is : " + res + '\n');
                } else {
                    System.out.print(calculator.errorMessage);
                }
            } else {
                break;
            }
        }
        this.setVisible(true);
    }

    private void initWindow() {
        this.setBounds(30, 30, 300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(270, 270));
        this.setMaximumSize(new Dimension(270, 270));

        FlowLayout grid = new FlowLayout(1);
        Container content = this.getContentPane();
        content.setLayout(grid);

        button = new JButton();
        label = new JTextArea();
        text = new JTextField();

        label.setEditable(false);
        label.setEnabled(false);

        text.setPreferredSize(new Dimension(200, 20));
        button.setPreferredSize(new Dimension(20, 20));
        label.setPreferredSize(new Dimension(220, 220));

        label.setBackground(this.getBackground());
        label.setDisabledTextColor(new Color(0, 0, 0));

        content.add(text);
        content.add(button);
        content.add(label);
    }

    private void buttonListenerMethod() {
        if (!text.getText().equals("console")) {
            String t = text.getText();
            Float res = calculator.calculate(text.getText());
            if (res == null) {
                label.setText(calculator.errorMessage);
                if (calculator.indexOfError >= 0) {
                    text.setCaretPosition(calculator.indexOfError);
                    text.grabFocus();
                }
            } else {
                label.setText(res.toString());
            }
        } else {
            workWithConsole();
        }
    }

    private void textListenerMethod(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == 10) {
            buttonListenerMethod();
        }
    }
}
