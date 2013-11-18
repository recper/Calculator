import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 18.11.13
 * Time: 9:48
 * To change this template use File | Settings | File Templates.
 */
public class MainForm extends JFrame{
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
                Float res = calculator.calculate(text.getText());
                if(res == null){
                    label.setText(calculator.errorMessage);
                    text.setCaretPosition(calculator.indexOfError);
                    text.grabFocus();
                }
                else {
                    label.setText(res.toString());
                }
            }
        });
    }

    private void initWindow(){
        this.setBounds(30, 30, 300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(270,270));
        this.setMaximumSize(new Dimension(270,270));

        FlowLayout grid = new FlowLayout(1);
        Container content = this.getContentPane();
        content.setLayout(grid);

        button = new JButton();
        label = new JTextArea();
        text = new JTextField();

        label.setEditable(false);
        label.setEnabled(false);

        text.setPreferredSize(new Dimension(200,20));
        button.setPreferredSize(new Dimension(20,20));
        label.setPreferredSize(new Dimension(220,220));

        label.setBackground(this.getBackground());
        label.setDisabledTextColor(new Color(0, 0, 0));

        content.add(text);
        content.add(button);
        content.add(label);
    }
}
