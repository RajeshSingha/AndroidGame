package com.sust.cse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	GameWorld world;
	Renderer renderer;
	
	boolean m;
	
	public Game(boolean b){
		m = b;
	}
	
	@Override
	public void create() {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		
		world = new GameWorld(camera, m);
		world.isPaused = false;
		
		renderer = new Renderer(world, batch);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		world.input();
		world.update();
		
		renderer.render();
		
		batch.end();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int width, int height) {
		
	}
}
