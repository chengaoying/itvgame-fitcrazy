package com.tvgame.ui;

/**
 * 
 * <p>Title: </p>
 *
 * <p>Description: 双线性插值缩放图片</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author An Zhiqiang 
 * 			QQ:343887157
 * @version 1.0 (下午03:45:59 2010)
 */
public class ZoomPixel {
	
	public static int interpolation(int[] imageData, int width, int heigth,
			float x, float y) {		
		// 四个最临近象素的坐标(i1, j1), (i2, j1), (i1, j2), (i2, j2)
		int i1, i2;
		int j1, j2;

		int f1, f2, f3, f4; // 四个最临近象素值
		int f12, f34; // 二个插值中间值

		f1 = f2 = f3 = f4 = 0;

		// 定义一个值，当象素坐标相差小于改值时认为坐标相同
		int EXP = 0;

		// 计算四个最临近象素的坐标
		i1 = (int) x;
		i2 = i1 +1;
		j1 = (int) y;
		j2 = j1 +1;

		// 根据不同情况分别处理
		if ((x < 0) || (x > width - 1) || (y < 0) || (y > heigth - 1)) {
			return 0x00ffffff; // 要计算的点不在源图范围内，直接返回255。
		} else {
			if (Math.abs(x - width + 1) <= EXP) {
				// 要计算的点在图像右边缘上
				if (Math.abs(y - heigth + 1) <= EXP) {
					// 要计算的点正好是图像最右下角那一个象素，直接返回该点象素值
					f1 = (int)imageData[width * j1 + i1];
					return (int)f1;
				} else {
					// 在图像右边缘上且不是最后一点，直接一次插值即可
					f1 = (int)imageData[width * j1 + i1];
					f3 = (int)imageData[width * j1 + i2];

					// 返回插值结果
					return (int) (f1 + (y - j1) * (f3 - f1));
				}
			} else if (Math.abs(y - heigth + 1) <= EXP) {
				// 要计算的点在图像下边缘上且不是最后一点，直接一次插值即可
				f1 = (int)imageData[width * j1 + i1];
				f2 = (int)imageData[width * j2 + i1];

				// 返回插值结果
				return (int) (f1 + (x - i1) * (f2 - f1));
			} else {
				// 计算四个最临近象素值
				f1 = imageData[width * j1 + i1];
				f2 = imageData[width * j1 + i2];
				f3 = imageData[width * j2 + i1];
				f4 = imageData[width * j2 + i2];
				


//				 插值1
				f12 = (int)(f1 + (x - i1) * (f2 - f1));
				// 插值2
				f34 = (int)((f3 + (x - i1) * (f4 - f3)));
				// 插值3
				return  (int)(f12 + (y - j1) * (f34 - f12));
			}
		}

	}	
}
