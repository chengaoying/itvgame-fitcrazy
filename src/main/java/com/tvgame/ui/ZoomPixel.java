package com.tvgame.ui;

/**
 * 
 * <p>Title: </p>
 *
 * <p>Description: ˫���Բ�ֵ����ͼƬ</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author An Zhiqiang 
 * 			QQ:343887157
 * @version 1.0 (����03:45:59 2010)
 */
public class ZoomPixel {
	
	public static int interpolation(int[] imageData, int width, int heigth,
			float x, float y) {		
		// �ĸ����ٽ����ص�����(i1, j1), (i2, j1), (i1, j2), (i2, j2)
		int i1, i2;
		int j1, j2;

		int f1, f2, f3, f4; // �ĸ����ٽ�����ֵ
		int f12, f34; // ������ֵ�м�ֵ

		f1 = f2 = f3 = f4 = 0;

		// ����һ��ֵ���������������С�ڸ�ֵʱ��Ϊ������ͬ
		int EXP = 0;

		// �����ĸ����ٽ����ص�����
		i1 = (int) x;
		i2 = i1 +1;
		j1 = (int) y;
		j2 = j1 +1;

		// ���ݲ�ͬ����ֱ���
		if ((x < 0) || (x > width - 1) || (y < 0) || (y > heigth - 1)) {
			return 0x00ffffff; // Ҫ����ĵ㲻��Դͼ��Χ�ڣ�ֱ�ӷ���255��
		} else {
			if (Math.abs(x - width + 1) <= EXP) {
				// Ҫ����ĵ���ͼ���ұ�Ե��
				if (Math.abs(y - heigth + 1) <= EXP) {
					// Ҫ����ĵ�������ͼ�������½���һ�����أ�ֱ�ӷ��ظõ�����ֵ
					f1 = (int)imageData[width * j1 + i1];
					return (int)f1;
				} else {
					// ��ͼ���ұ�Ե���Ҳ������һ�㣬ֱ��һ�β�ֵ����
					f1 = (int)imageData[width * j1 + i1];
					f3 = (int)imageData[width * j1 + i2];

					// ���ز�ֵ���
					return (int) (f1 + (y - j1) * (f3 - f1));
				}
			} else if (Math.abs(y - heigth + 1) <= EXP) {
				// Ҫ����ĵ���ͼ���±�Ե���Ҳ������һ�㣬ֱ��һ�β�ֵ����
				f1 = (int)imageData[width * j1 + i1];
				f2 = (int)imageData[width * j2 + i1];

				// ���ز�ֵ���
				return (int) (f1 + (x - i1) * (f2 - f1));
			} else {
				// �����ĸ����ٽ�����ֵ
				f1 = imageData[width * j1 + i1];
				f2 = imageData[width * j1 + i2];
				f3 = imageData[width * j2 + i1];
				f4 = imageData[width * j2 + i2];
				


//				 ��ֵ1
				f12 = (int)(f1 + (x - i1) * (f2 - f1));
				// ��ֵ2
				f34 = (int)((f3 + (x - i1) * (f4 - f3)));
				// ��ֵ3
				return  (int)(f12 + (y - j1) * (f34 - f12));
			}
		}

	}	
}
