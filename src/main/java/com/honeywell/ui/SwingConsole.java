package com.honeywell.ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SwingConsole {
	public static void run(final JFrame f, final int width, final int height){
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				Image image;
				try {
					image = ImageIO.read(this.getClass().getResource("/favicon.jpg"));
					f.setIconImage(image);          //设置图标
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				image = Toolkit.getDefaultToolkit().getImage("/3.jpg");
				f.setTitle("COPQ Report");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(width, height);
				f.setVisible(true);
			}
		});
	}
}
