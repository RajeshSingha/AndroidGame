package com.sust.cse;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyFish {

	Sprite s;
	float yScale;
	Random rand;
	boolean isFlipX;
	
	String[] prefix;
	TextureRegion[] frames;
	Animation anim;
	float stateTime, x, y, radius, sc;

	public EnemyFish() {
		rand = new Random();
		
		loadAnims();		
		initFish();
		
		
		initScale();
		
		radius = frames[0].getRegionHeight()*sc / 2;
		radius -= radius * 0.1f;
		System.out.println("Enemy: " + radius);
	}

	

	private void initScale() {
		int temp = rand.nextInt(8) * 5 + 10;
		sc = (float)temp / 100;
	}

	private void loadAnims() {
		prefix = new String[4];
		for(int i = 1; i <= 4; ++i)
			prefix[i-1] = "enemy/" + i + "/";
		
		frames = new TextureRegion[6];
		
		int cnt = rand.nextInt(4);
		
		switch (cnt) {
		case 0:
			yScale = 2f;
			break;
			
		case 1:
			yScale = 1.9f;
			break;
			
		case 2:
			yScale = 0.9f;
			break;
			
		case 3:
			yScale = 1.2f;
			break;

		default:
			yScale = 1.0f;
			break;
		}
		
		for(int i = 0; i <= 5; ++i){
			frames[i] = new TextureRegion(new Texture(Gdx.files.internal(prefix[cnt] + "enemy_0000" + i + ".png")));
		}
		s = new Sprite(frames[0]);
		
		stateTime = 0;
		
		anim = new Animation(0.2f / 6, frames);
	}

	private void initFish() {
		isFlipX = rand.nextBoolean();
		
		if (isFlipX) {
			x = Gdx.graphics.getWidth();
		} else {
			x = 0 - frames[0].getRegionWidth();
		}

		y = rand.nextInt((int) Gdx.graphics.getHeight() - 50);
	}

	public void render(SpriteBatch batch) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		s = new Sprite(anim.getKeyFrame(stateTime, true));
		s.setPosition(x - s.getWidth() / 2, y - s.getHeight() / 2);
		s.setScale(sc, sc*yScale);
		s.flip(isFlipX, false);
		
		s.draw(batch);
	}

	public void update() {
		if (isFlipX) {
			x -= Constants.ENEMY_SPEED;
		} else {
			x += Constants.ENEMY_SPEED;
		}
	}

}
