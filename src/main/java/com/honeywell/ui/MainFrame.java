package com.honeywell.ui;

import static com.honeywell.ui.SwingConsole.run;

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
		panel1.setBounds(10, 10, 414, 210);
		panel1.setLayout(null);
		label_1.setBounds(6, 17, 115, 23);
		panel1.add(label_1);
		txt_1.setBounds(163, 17, 230, 23);
		panel1.add(txt_1);
		label_2.setBounds(6, 56, 115, 23);
		panel1.add(label_2);
		txt_2.setBounds(163, 56, 230, 23);
		panel1.add(txt_2);
		label_3.setBounds(6, 101, 115, 23);
		panel1.add(label_3);
		txt_3.setBounds(163, 101, 230, 23);
		panel1.add(txt_3);
		label_4.setBounds(6, 149, 156, 23);
		panel1.add(label_4);
		txt_4.setBounds(163, 149, 230, 23);
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
		jButton.setBounds(167, 230, 90, 31);
		panel12.add(jButton);
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
		run(new MainFrame(), 450, 300);
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
