package com.sust.cse;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Fish";
		cfg.useGL20 = false;
		cfg.width = Constants.WINDOW_WIDTH;
		cfg.height = Constants.WINDOW_HEIGHT;
		
		new LwjglApplication(new Game(true), cfg);
	}
}
