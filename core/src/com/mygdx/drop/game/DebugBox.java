package com.mygdx.drop.game;

import java.util.function.Supplier;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.drop.Constants;
import com.mygdx.drop.Drop;
import com.mygdx.drop.etc.ClickListener;
import com.mygdx.drop.etc.InputEvent;

public class DebugBox extends BoxEntity {
	private final AtlasRegion texture;

	/**
	 * Creates a DebugBox.
	 * @param x Measured in meters
	 * @param y Measured in meters
	 * @param width Measured in meters
	 * @param height Measured in meters
	 */
	protected DebugBox(World world, float x, float y, float width, float height) {
		super(world, width, height, 
		((Supplier<BodyDef>) (() -> {
			BodyDef body = new BodyDef();
			body.type = BodyType.DynamicBody;
			body.position.set(x, y + height / 2);
			return body;
		})).get(), 
		((Supplier<FixtureDef>) (() -> {
			FixtureDef fixture = new FixtureDef();
			fixture.density = 0.5f;
			fixture.filter.categoryBits = (short) (Constants.Category.PLAYER_COLLIDABLE.value);
			return fixture;
		})).get());

		this.texture = game.assets.get(com.mygdx.drop.Assets.TextureId.DebugBox_bucket);
		
		addListener(new ClickListener(Input.Buttons.RIGHT) {
			@Override
			public void clicked(InputEvent event, float x, float y) { 
				super.clicked(event, x, y);
				System.out.println("clicked debugbox");
			}
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Entity fromEntity) {
				// TODO Auto-generated method stub
				super.enter(event, x, y, pointer, fromEntity);
				System.out.println("entered");
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Entity toEntity) {
				// TODO Auto-generated method stub		
				super.exit(event, x, y, pointer, toEntity);
				System.out.println("exit");
			}
		});
	}

	@Override
	public void dispose() {}

	@Override
	public boolean update(Camera camera) { return false; }
	
	public void clicked() { System.out.println("clicked debug box"); };

	@Override
	public void draw(Camera camera) {
		Vector2 coords = getDrawingCoordinates();
		game.batch.draw(texture, coords.x, coords.y, 0, 0, getWidth(), getHeight(), 1, 1, self.getAngle() * MathUtils.radiansToDegrees);
	}

	public static class Definition extends Entity.EntityDefinition<DebugBox> {
		public float width, height;

		/**
		 * Defines the properties of a DebugBox. See {@link DebugBox#DebugBox(World, float, float, float, float)}
		 */
		public Definition(float x, float y, float width, float height) {
			super(x, y);
			this.width = width;
			this.height = height;
		}

		@Override
		protected DebugBox createEntity(World world) { return new DebugBox(world, x, y, width, height); }

	}

}
