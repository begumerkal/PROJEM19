package tuxkids.tuxblocks.core.defense.tower;

import playn.core.Image;
import tuxkids.tuxblocks.core.defense.projectile.Projectile;

public class HorizontalWall extends Tower {

	@Override
	public int rows() {
		return 1;
	}

	@Override
	public int cols() {
		return 3;
	}

	@Override
	public int damage() {
		return 0;
	}

	@Override
	public int fireRate() {
		return -1;
	}

	@Override
	public float range() {
		return 0;
	}

	@Override
	public Projectile createProjectile() {
		return null;
	}

	@Override
	public Tower copy() {
		return new HorizontalWall();
	}

	@Override
	public String name() {
		return "Horizontal Wall";
	}

}
