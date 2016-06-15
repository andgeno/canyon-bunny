
package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.packtpub.libgdx.canyonbunny.game.Assets;

public class Help extends AbstractGameObject {

	public boolean collected;

	public Animation animNormal;
	public Animation animTilt;
	public Animation animTouch;

	private Vector2 moveSrc = new Vector2();
	private Vector2 moveDst = new Vector2();
	private float moveTime;
	private float timeMoveLeft;
	private Interpolation moveEasing;

	private boolean visible;
	private boolean oscillate;

	public Help () {
		init();
	}

	private void init () {
		dimension.set(3.5f, 2.0f);

		animNormal = Assets.instance.book.animHelpIdle;
		animTilt = Assets.instance.book.animHelpTilt;
		animTouch = Assets.instance.book.animHelpTouch;

		visible = false;
		oscillate = false;
	}

	@Override
	public void update (float deltaTime) {
		super.update(deltaTime);

		if (timeMoveLeft > 0) {
			timeMoveLeft = Math.max(0, timeMoveLeft - deltaTime);
			float alpha = 1 - timeMoveLeft / moveTime;
			position.x = moveSrc.x + (moveDst.x) * moveEasing.apply(alpha);
			position.y = moveSrc.y + (moveDst.y) * moveEasing.apply(alpha);
		} else if (visible) {
			if (!oscillate) {
				oscillate = true;
				stateTime = 0;// MathUtils.random();
			}
			float oscX = 0;
			float oscY = 0;
// oscX = MathUtils.cosDeg((stateTime * 10) % 360.0f * 25) * 0.05f;
// oscY = MathUtils.sinDeg((stateTime * 10) % 360.0f * 25) * 0.05f;
			position.x = moveSrc.x + moveDst.x + oscX;
			position.y = moveSrc.y + moveDst.y + oscY;
		}

	}

	public void render (SpriteBatch batch) {
		TextureRegion reg = null;

		reg = animation.getKeyFrame(stateTime, true);
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
			rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}

	public void moveBy (float xSrc, float ySrc, float xDst, float yDst, float duration, Interpolation easing) {
		if (visible) return;
		position.x = xSrc;
		position.y = ySrc;
		moveBy(xDst, yDst, duration, easing);
	}

	public void moveBy (float x, float y, float duration, Interpolation easing) {
		if (visible) return;
		visible = true;
		moveSrc.set(position);
		moveDst.set(x, y);
		moveTime = duration;
		timeMoveLeft = duration;
		moveEasing = easing;
	}

}
