package com.honeywell.ui;

import static com.honeywell.ui.SwingConsole.run;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.apache.commons.lang.StringUtils;

import com.honeywell.gb.copq.App;

public class MainFrame extends JFrame implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel1 = new JPanel();
	private JPanel panel12 = new JPanel();
	
	private JButton jButton = new JButton("Run");
	private JLabel label_1 = new JLabel("Source File Path:");
	private JTextField txt_1 = new JTextField(25);
	private JLabel label_2 = new JLabel("Program:");
	private JTextField txt_2 = new JTextField(25);
	
	private JLabel label_3 = new JLabel("Functions:");
	private JTextField txt_3 = new JTextField(25);
	private JLabel label_4 = new JLabel("Template File Path:");
	private JTextField txt_4 = new JTextField(25);
	
	public MainFrame() {
		//setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Administrator\\Desktop\\favicon.ico"));
		panel1.setBounds(10, 105, 378, 210);
		panel1.setLayout(null);
		label_1.setBounds(10, 25, 115, 23);
		panel1.add(label_1);
		txt_1.setBounds(135, 25, 230, 23);
		panel1.add(txt_1);
		label_2.setBounds(10, 64, 115, 23);
		panel1.add(label_2);
		txt_2.setBounds(135, 64, 230, 23);
		panel1.add(txt_2);
		label_3.setBounds(10, 109, 115, 23);
		panel1.add(label_3);
		txt_3.setBounds(135, 109, 230, 23);
		panel1.add(txt_3);
		label_4.setBounds(10, 157, 156, 23);
		panel1.add(label_4);
		txt_4.setBounds(135, 157, 230, 23);
		panel1.add(txt_4);
		
		Border border = BorderFactory.createTitledBorder("COPQ-Report");
		panel1.setBorder(border);
		setResizable(false);
		GridLayout grid = new GridLayout();
		grid.setHgap(100);//设置组件之间的水平距离为h（int型）
		grid.setVgap(300);//设置组件之间的垂直距离为v（int型）
		panel12.setLayout(null);
		panel12.add(panel1);
		getContentPane().add(panel12);
		jButton.setBounds(149, 325, 90, 31);
		panel12.add(jButton);
		
		JLabel lblHoneywell = new JLabel("Honeywell");
		lblHoneywell.setForeground(Color.RED);
		lblHoneywell.setFont(new Font("Times New Roman", Font.BOLD, 54));
		lblHoneywell.setBounds(76, 20, 264, 75);
		panel12.add(lblHoneywell);
		jButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String path = txt_1.getText();
				String program = txt_2.getText();
				String function = txt_3.getText();
				String template = txt_4.getText();
				if(StringUtils.isBlank(path)||StringUtils.isBlank(program)||StringUtils.isBlank(function)||StringUtils.isBlank(template)){
					JOptionPane.showMessageDialog(null,"Please inpu the relevant parameters!");
				}else {
					String info = App.mainStart(path, program, function, template);
					JOptionPane.showMessageDialog(null,info);
				}
			}
		}); 
		
		txt_1.addMouseListener(this);
		txt_4.addMouseListener(this);
	}
	public static void main(String[] args) {
		run(new MainFrame(), 410, 410);
	}
	public void mouseClicked(MouseEvent e) {
		JFileChooser jFileChooser = new JFileChooser();
		int val = jFileChooser.showOpenDialog(MainFrame.this);
		if(val==JFileChooser.APPROVE_OPTION){
			JTextField jTextField = (JTextField) e.getComponent();
			jTextField.setText(jFileChooser.getSelectedFile().getAbsolutePath());
		}
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
