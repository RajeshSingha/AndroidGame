package com.sust.cse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Player implements InputProcessor {
	OrthographicCamera cam;
	GameWorld world;
	Vector3 origin, current;
	boolean isTouched, isFlip, isAlive;
	InputGUI gui;
	
	TextureRegion[] frames;
	Animation anim;
	float stateTime, x, y, radius, sc, exitTime;
	TextureRegion cf;
	
	Sprite sprite;
	
	public Player(OrthographicCamera cam, GameWorld g){
		
		this.cam = cam;
		this.world = g;
		isTouched = false;
		isAlive = true;
		
		
		x = Gdx.graphics.getWidth() / 2;
		y = Gdx.graphics.getHeight() / 2;
		
		gui = new InputGUI();
		
		initAnim();
		
		sc = 0.2f;
		exitTime = 0.0f;
		radius = frames[0].getRegionWidth()*sc / 2;
		radius -= radius * 0.1f;
		
		System.out.println("Player: " + radius);
	}
	
	private void initAnim() {
		frames = new TextureRegion[6];
		for(int i = 0; i <= 5; ++i){
			frames[i] = new TextureRegion(new Texture(Gdx.files.internal("player/player_0000" + i + ".png")));
		}
		
		anim = new Animation(0.4f / 6, frames);
		stateTime = 0.0f;
	}

	public void update(){
		
		if(isTouched){
			Vector3 temp = new Vector3(current.x - origin.x, current.y - origin.y, 0);
			if(temp.len() <= Constants.TOUCH_DISTANCE){
				return;
			}
			temp.nor();
			temp.scale(Constants.PLAYER_SPEED, Constants.PLAYER_SPEED, 0 );
			x += temp.x;
			y += temp.y;
			
			if(temp.x < 0)
				isFlip = true;
			else
				isFlip = false;
			
			if(x < -10)
				x = -10;
			if(x >= Gdx.graphics.getWidth() - 10)
				x = Gdx.graphics.getWidth() - 10;
			
			if(y < -10)
				y = -10;
			if(y >= Gdx.graphics.getHeight() - 10)
				y = Gdx.graphics.getHeight() - 10;
		}
		
		if(!isAlive){
			exitTime += Gdx.graphics.getDeltaTime();
			if(exitTime >= Constants.EXIT_DIALOG_TIME)
				Gdx.app.exit();
		}
		
	}
	
	public void render(SpriteBatch batch){
		
		if(!isAlive)
			return;
		
		stateTime += Gdx.graphics.getDeltaTime();
		cf = anim.getKeyFrame(stateTime, true);
		
		sprite = new Sprite(cf);
		sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
		sprite.setScale(sc);
		sprite.flip(isFlip, false);
		
		sprite.draw(batch);
		gui.draw(batch);
	}

	@Override
	public boolean keyDown(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		isTouched = true;
		
		origin = new Vector3(screenX, screenY, 0);
		current = new Vector3(screenX, screenY, 0);
		cam.unproject(current);
		cam.unproject(origin);
		
		if(world.pausebtn.getBoundingRectangle().contains(origin.x, origin.y)){
			world.isPaused = !world.isPaused;
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		isTouched = false;
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		current = new Vector3(screenX, screenY, 0);
		cam.unproject(current); 
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	private class InputGUI{
		Sprite circle, dot;
		
		public InputGUI(){
			Texture t = new Texture(Gdx.files.internal("circle.png"));
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			circle = new Sprite(t);
			circle.setScale(1.6f);
			
			t = new Texture(Gdx.files.internal("dot.png"));
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			dot = new Sprite(t);
			dot.setScale(2.0f);
		}
		
		public void draw(SpriteBatch batch){
			if(!isTouched || world.isPaused)
				return;
			
			circle.setPosition(origin.x - circle.getWidth() / 2.0f, origin.y - circle.getHeight() / 2.0f);
			circle.draw(batch);
			
			Vector3 temp = new Vector3(current.x - origin.x, current.y - origin.y, 0);
			if(temp.len() > Constants.InputDotMaxDis){
				temp.nor();
				temp.scale(Constants.InputDotMaxDis, Constants.InputDotMaxDis, 0);
			}
			
			temp.add(origin);
			dot.setPosition(temp.x - dot.getWidth() / 2.0f, temp.y - dot.getHeight() / 2.0f);
			dot.draw(batch);
		}
	}
}
