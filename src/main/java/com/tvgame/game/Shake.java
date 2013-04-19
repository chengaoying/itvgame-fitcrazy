package com.tvgame.game;

public class Shake {
	// --------------------------------------------------------------屏幕震动
	public static byte shake_indexT;// 计数=T

	public static byte shake_state;// 状态

	public static byte shake_mode = -1;// 模式

	public static byte shake_loop = 0;// -1为无限次数

	public static boolean vobble_isOver;//

	/**
	 * 
	 * @param mode
	 *            1左右 2上下 3滚动
	 * @param loop
	 * @param force
	 */
	public static void shake_start(int mode, int loop, boolean force) {
		if (loop < -1) {
			return;
		}

		if (vobble_isOver || (force && shake_mode != SHAKE_TYPE_NORMAL_ATTACK)) {
			shake_mode = (byte) mode;
			shake_loop = (byte) loop;
			shake_indexT = 0;
			vobble_isOver = false;
		}
	}

	public static void shake_start(int ShakMode) {
		// if(true){
		// camera.openShake(4, 0);
		// return;
		// }
		shake_start(SHAKE_TYPE_SMALL, 1, true);
	}

	public static void shake_setLoop(byte i) {
		shake_loop = i;
	}

	public static void shake_stop() {
		shake_loop = 0;
		shake_indexT = 0;
		vobble_isOver = false;
	}

	public static void shake_reset() {
		shake_stop();
		shake_mode = -1;
	}

	public static boolean shake_working() {
		return shake_mode != -1 && shake_loop != 0 && shake_indexT > 0;
	}

	public static int shakeX;

	public static int shakeY;

	// public static final byte[] SHAKE_DATE = { Game.WIDTH >= 240 ? 14 : 10,
	// Game.WIDTH >= 240 ? -12 : -8, Game.WIDTH >= 240 ? 8 : 5,
	// Game.WIDTH >= 240 ? -8 : -5, Game.WIDTH >= 240 ? 3 : 2,
	// Game.WIDTH >= 240 ? -3 : -2};

	// 震动数据
	public static final byte[] SHAKE_NORMAL_ATTACK = { 10, -8, 5, -5, 2, -2 };

	public static final byte[] SHAKE_NORMAL_SMALL = { 1, -1, 1, -1, };

	public static final int SHAKE_TYPE_NORMAL_ATTACK = 0;

	public static final int SHAKE_TYPE_SMALL = 1;

	public static void shake_draw(/* Graphics g */) {
		if (shake_loop != 0) {
			byte array_tmpX = 0;
			byte array_tmpY = 0;
			byte[] x = null;
			switch (shake_mode) {
			case -1:
				break;
			case SHAKE_TYPE_SMALL:
				x = SHAKE_NORMAL_SMALL;
				array_tmpX = x[shake_indexT];
				array_tmpY = x[shake_indexT];
				break;
			case SHAKE_TYPE_NORMAL_ATTACK:// 上下
				x = SHAKE_NORMAL_ATTACK;
				array_tmpX = x[shake_indexT];
				array_tmpY = x[shake_indexT];
				break;
			case 3:// 滚
				x = new byte[] { 1, -1, -1, 0, 0, 0 };
				array_tmpX = x[shake_indexT];
				array_tmpY = x[shake_indexT];
				break;
			case 4:// 简单左右
				x = new byte[] { 1, -1 };
				// cameraOffseX = x[shake_indexT];
				break;
			case 5:// 简单上下
				x = new byte[] { 1, 0 };
				// cameraOffseY = x[shake_indexT];
				break;// 简单上下
			}
			// cameraX+=array_tmpX;
			// cameraY+=array_tmpY;
			shakeX = array_tmpX;
			shakeY = array_tmpY;

			if (shake_indexT++ > x.length - 2) {
				shake_indexT = 0;
				if (shake_loop == -1) {
					vobble_isOver = false;
				} else {
					shake_loop--;
					vobble_isOver = false;
					if (shake_loop == 0) {
						// shake_mode = -1;//
						shake_loop = 0;
						vobble_isOver = true;
					}
				}

				// cameraX =CAMERA_X;
				// cameraY =CAMERA_Y;
				shakeX = 0;
				shakeY = 0;
				// g.translate(0, 0);
			}
		}
	}

}
