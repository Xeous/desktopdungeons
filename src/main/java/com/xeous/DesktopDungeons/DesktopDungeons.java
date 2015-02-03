package com.xeous.DesktopDungeons;

import javax.swing.JFrame;

public class DesktopDungeons extends JFrame {
	
	public DesktopDungeons() {
		InitUI();
	}

	public void InitUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0, 0);
		setTitle("Desktop Dungeons");
		setResizable(false);
		Board board = new Board();
		add(board);
		setSize(Board.WIDTH, Board.HEIGHT);
	}

	public static void main(String[] args) {
		DesktopDungeons dd = new DesktopDungeons();
		dd.setVisible(true);
	}

}
