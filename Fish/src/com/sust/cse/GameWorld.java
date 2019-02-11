package com.sust.cse;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class GameWorld {

	Sprite background, pausebtn, startbtn, scoreSprite;
	Player player;
	ArrayList<EnemyFish> enemys;
	ArrayList<Bubble> bbls;
	boolean moveRight = false, isPaused, isMusicOn;
	long start_time;
	int score;
	BitmapFont font;

	Music bMusic;
	Sound gulp, die;

	Background back;

	public GameWorld(OrthographicCamera c, boolean isM) {

		start_time = System.currentTimeMillis();
		score = 0;

		isMusicOn = isM;

		font = new BitmapFont();
		font.setColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		font.setScale(2);

		player = new Player(c, this);
		Gdx.input.setInputProcessor(player);
		enemys = new ArrayList<EnemyFish>();
		
		bbls = new ArrayList<Bubble>();

		initBackground();

		if (isMusicOn) {
			bMusic = Gdx.audio.newMusic(Gdx.files.internal("bmusic.mp3"));
			bMusic.setLooping(true);
			bMusic.play();
		}

		gulp = Gdx.audio.newSound(Gdx.files.internal("gulp.ogg"));
		die = Gdx.audio.newSound(Gdx.files.internal("fish_dead.ogg"));
	}

	private void initBackground() {
		background = new Sprite(new Texture(
				Gdx.files.internal("background.png")));
		background.setScale(1.5f);
		background.setPosition(0, 0);
		background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		pausebtn = new Sprite(new Texture(Gdx.files.internal("pause.png")));
		pausebtn.setBounds(Gdx.graphics.getWidth() - 100,
				Gdx.graphics.getHeight() - 80, 70, 70);

		startbtn = new Sprite(new Texture(Gdx.files.internal("start.png")));
		startbtn.setBounds(Gdx.graphics.getWidth() - 100,
				Gdx.graphics.getHeight() - 80, 70, 70);

		scoreSprite = new Sprite(new Texture(Gdx.files.internal("score.png")));
		scoreSprite.scale(1.2f);
		scoreSprite.setPosition(
				(Gdx.graphics.getWidth() - scoreSprite.getWidth()) / 2,
				(Gdx.graphics.getHeight() - scoreSprite.getHeight()) / 2);
		
		back = new Background();
	}

	public void input() {

	}

	public void update() {

		if (isPaused)
			return;

		player.update();
		back.update();
		for(int i = bbls.size() - 1; i >= 0; --i){
			bbls.get(i).update();
			if(bbls.get(i).b1.getY() > Gdx.graphics.getHeight())
				bbls.remove(i);
		}

		checkForNewEnemy();

		for (int i = enemys.size() - 1; i >= 0; --i) {
			EnemyFish temp = enemys.get(i);

			temp.update();

			if (temp.x > Gdx.graphics.getWidth()
					|| temp.x < -(temp.frames[0].getRegionWidth() + 5)) {
				enemys.remove(i);
				System.out.println("Remove Enemy");
			}
		}

		checkForCollision();

		// water.translate(2, 0);
		// if(water.getX() > 0)
		// water.translate(-200f, 0f);
	}

	private void checkForCollision() {

		if (!player.isAlive)
			return;

		Vector2 v = new Vector2(player.x, player.y);

		for (int i = enemys.size() - 1; i >= 0; --i) {
			EnemyFish temp = enemys.get(i);

			Vector2 v1 = new Vector2(temp.x - v.x, temp.y - v.y);
			if (v1.len() <= temp.radius + player.radius) {
				enemys.remove(i);
				if (temp.sc <= player.sc) {
					// Eat'em
					score += 10;
					if (isMusicOn)
						gulp.play();
					if (score % 40 == 0 && player.sc < 0.45) {
						player.sc += 0.05;
					}
				} else {
					player.isAlive = false;
					if (isMusicOn)
						die.play();
				}
				
				bbls.add(new Bubble(player.x, player.y));
			}
		}
	}

	private void checkForNewEnemy() {
		long current = System.currentTimeMillis();

		if (current - start_time >= Constants.SPWAN_INTRVAL) {
			enemys.add(new EnemyFish());
			start_time = current;
		}
	}

}
