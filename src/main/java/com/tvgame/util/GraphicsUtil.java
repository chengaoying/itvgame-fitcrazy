package com.tvgame.util;

import com.tvgame.actor.Logger;
import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author liushiming
 */
public class GraphicsUtil {

    /** �������κβ��� */
    public static final int TRANS_NONE = 0; // 000
    /** ˳ʱ��ת90�� */
    public static final int TRANS_ROT90 = 5; // 101
    /** ˳ʱ��ת180�� */
    public static final int TRANS_ROT180 = 3; // 011
    /** ˳ʱ��ת270�� */
    public static final int TRANS_ROT270 = 6; // 110
    /** ���ҷ�ת */
    public static final int TRANS_MIRROR = 2;// �� //010
    /** ���ҷ�ת����˳ʱ��ת90�� */
    public static final int TRANS_MIRROR_ROT90 = 7; // 111
    /** ���ҷ�ת����˳ʱ��ת180�� */
    public static final int TRANS_MIRROR_ROT180 = 1; // 001
    /** ���ҷ�ת����˳ʱ��ת270�� */
    public static final int TRANS_MIRROR_ROT270 = 4; // 100
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int LEFT_TOP = Graphics.LEFT | Graphics.TOP;
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int LEFT_VCENTER = Graphics.LEFT | Graphics.VCENTER;
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int LEFT_BOTTOM = Graphics.LEFT | Graphics.BOTTOM;
    /**
     * ê�� {@link javax.microedition.lcdui.Graphics}<br>
     * ���ֲ�����ʹ��
     */
    public static final int HCENTER_VCENTER = Graphics.HCENTER | Graphics.VCENTER;
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int HCENTER_BOTTOM = Graphics.HCENTER | Graphics.BOTTOM;
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int HCENTER_TOP = Graphics.HCENTER | Graphics.TOP;
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int RIGHT_TOP = Graphics.RIGHT | Graphics.TOP;
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int RIGHT_VCENTER = Graphics.RIGHT | Graphics.VCENTER;
    /** ê�� {@link javax.microedition.lcdui.Graphics} */
    public static final int RIGHT_BOTTOM = Graphics.RIGHT | Graphics.BOTTOM;

    // #if NOKIA_API
    // # public static void drawRegion(Graphics g, Image image_src, int x_src,
    // # int y_src, int width, int height, int transform, int x_dest,
    // # int y_dest, int anchor) {
    // # com.nokia.mid.ui.DirectGraphics dg = DirectUtils.getDirectGraphics(g);
    // # boolean isRot = (transform & TRANS_MIRROR_ROT270) != 0;// operation
    // # // once
    // # int offsetXY = getAnchorOffset(anchor, transform, width, height);
    // # int getClipW = g.getClipWidth();
    // # int getClipH = g.getClipHeight();
    // # x_dest -= (short) ((offsetXY >> 16) & 0xffff);
    // # y_dest -= (short) (offsetXY & 0xffff);
    // # if (isRot) {// bit 00000100 rot
    // # int tW = width;
    // # width = height;
    // # height = tW;
    // # }
    // # g.setClip(x_dest, y_dest, width, height);
    // # switch (transform) {
    // # case TRANS_NONE:// 000
    // # dg.drawImage(image_src, x_dest - x_src, y_dest - y_src, 0, 0);
    // # break;
    // # case TRANS_MIRROR_ROT270:// 100
    // # dg.drawImage(image_src, x_dest - y_src, y_dest - x_src, 0,
    // # DirectGraphics.ROTATE_270 | DirectGraphics.FLIP_HORIZONTAL);
    // # break;
    // # case TRANS_ROT270:// 110
    // # dg.drawImage(image_src, x_dest - y_src, y_dest
    // # - (image_src.getWidth() - x_src - height), 0,
    // # DirectGraphics.ROTATE_90);
    // # break;
    // # case TRANS_MIRROR_ROT180:// 001
    // # dg.drawImage(image_src, x_dest - (x_src), y_dest
    // # - (image_src.getHeight() - y_src - height), 0,
    // # DirectGraphics.FLIP_VERTICAL);
    // # break;
    // # case TRANS_ROT90:// 101
    // # dg.drawImage(image_src, x_dest
    // # - (image_src.getHeight() - y_src - width),
    // # y_dest - (x_src), 0, DirectGraphics.ROTATE_270);
    // # break;
    // # case TRANS_MIRROR:// 010
    // # dg.drawImage(image_src, x_dest
    // # - (image_src.getWidth() - x_src - width), y_dest - y_src,
    // # 0, DirectGraphics.FLIP_HORIZONTAL);
    // # break;
    // # case TRANS_MIRROR_ROT90:// 111
    // # dg.drawImage(image_src, x_dest
    // # - (image_src.getHeight() - y_src - width), y_dest
    // # - (image_src.getWidth() - x_src - height), 0,
    // # DirectGraphics.ROTATE_90 | DirectGraphics.FLIP_HORIZONTAL);
    // # break;
    // # case TRANS_ROT180:// 011
    // # dg.drawImage(image_src, x_dest
    // # - (image_src.getWidth() - x_src - width), y_dest
    // # - (image_src.getHeight() - y_src - height), 0,
    // # DirectGraphics.ROTATE_180);
    // # break;
    // # default:
    // # throw new java.lang.IllegalArgumentException();
    // # }
    // # g.setClip(0, 0, getClipW, getClipH);
    // # }
    // #else
    /**
     * ���Ƶ�ǰ���õ�ָ��ͼ���ָ�����򣬲�ָ��������תָ������
     *
     * @param g
     * @param image
     *            ָ��ͼ��
     * @param clipX
     *            ͼ���ָ������� x����
     * @param clipY
     *            ͼ���ָ������� y����
     * @param clipW
     *            ͼ���ָ������� ���
     * @param clipH
     *            ͼ���ָ������� �߶�
     * @param flipFlag
     *            ָ���ඨ��ķ�ת����
     * @param x
     *            ���Ƶ�x����
     * @param y
     *            ���Ƶ�y����
     * @param anchor
     *            ָ���ඨ���ê�㳣��
     */
    public static void drawRegion(Graphics g, Image image, int clipX,
            int clipY, int clipW, int clipH, int flipFlag, int x, int y,
            int anchor) {
        int offset = getAnchorOffset(anchor, flipFlag, clipW, clipH);
        x -= (short) (offset >> 16) & 0xffff;
        y -= (short) (offset & 0xffff);
        // if(image!=null)
        try {
            g.drawRegion(image, clipX, clipY, clipW, clipH, flipFlag, x, y,
                    LEFT_TOP);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    // #endif
    // public static void drawRegion_(Graphics g,Image image, int x_src, int
    // y_src, int width,
    // int height, int transform, int x_dest, int y_dest, int anchor) {
    // int clipX = g.getClipX();
    // int clipY = g.getClipY();
    // int clipWidth = g.getClipWidth();
    // int clipHeight = g.getClipHeight();
    // boolean isRot = (transform & TRANS_MIRROR_ROT270) != 0;// operation
    // // once
    // int offsetXY = getAnchorOffset(anchor, transform, width, height);
    // x_dest -= (short) ((offsetXY >> 16) & 0xffff);
    // y_dest -= (short) (offsetXY & 0xffff);
    // if (isRot) {// bit 00000100 rot
    // int tW = width;
    // width = height;
    // height = tW;
    // }
    // g.clipRect(x_dest, y_dest, width, height);
    // switch (transform) {
    // case TRANS_NONE:
    // g.drawImage(image, x_dest - x_src, y_dest - y_src, g.LEFT | g.TOP);
    // g.setClip(clipX, clipY, clipWidth, clipHeight);
    // break;
    // case TRANS_ROT180:
    // for (int xi = 0; xi < height; xi++) {
    // for (int yj = 0; yj < width; yj++) {
    // g.setClip(x_dest + yj, y_dest + xi, 1, 1);
    // g.drawImage(image, (x_dest - x_src) + ((yj << 1) - width + 1),
    // (y_dest - y_src) + ((xi << 1) - height + 1), 0);
    // }
    // }
    // break;
    // case TRANS_MIRROR_ROT180:
    // for (int xi = 0; xi < height; xi++) {
    // g.setClip(x_dest, y_dest + xi, width, 1);
    // g.drawImage(image, (x_dest - x_src), (y_dest - y_src)
    // + (2 * xi - height + 1), 0);
    // }
    // break;
    // case TRANS_ROT270:
    // for (int xi = 0; xi < height; xi++) {
    // g.setClip(x_dest, y_dest + xi, width, 1);
    // g.drawImage(image, (x_dest - x_src), (y_dest - y_src)
    // + ((xi << 1) - height + 1), 0);
    // }
    // break;
    // case TRANS_MIRROR:
    // for (int xi = 0; xi < width; xi++) {
    // g.setClip(x_dest + xi, y_dest + 0, 1, height);
    // g.drawImage(image, (x_dest - x_src) + ((xi << 1) - width + 1),
    // y_dest - y_src, 0);
    // }
    // break;
    // }
    // g.setClip(clipX, clipY, clipWidth, clipHeight);
    // }
    /**
     * ����ָ��ͼ���һ��֡.<br>
     *
     * @param g
     *            ֡��ָ���Ĵ�С��Ȼ�ָ�
     * @param indxe
     *            ͼƬ���±�(֡)
     * @param image
     *            ָ��ͼ��
     * @param x
     *            ���Ƶ�xλ��
     * @param y
     *            ���Ƶ�yλ��
     * @param flipFlag
     *            ��ָ���ķ�ת����
     * @param anchor
     *            ��ָ����ê�㳣��
     * @param clipW
     *            ��֡�Ŀ�
     * @param clipH
     *            ��֡�ĸ�
     */
    public static void drawFrame(Graphics g, int indxe, Image image, int x,
            int y, int flipFlag, int anchor, int clipW, int clipH) {
        if (indxe == -1) {
            return;
        }
        int imgCol = image.getWidth() / clipW;//
        int clipX = (indxe % imgCol) * clipW;
        int cilpY = (indxe / imgCol) * clipH;
        drawRegion(g, image, clipX, cilpY, clipW, clipH, flipFlag, x, y, anchor);
    }

    /**
     * ���Զ���ͼ�ֿ��������
     *
     * @param g
     * @param img
     *            �ֿ�ͼ
     * @param str
     *            Ҫ���Ƶ��ַ���
     * @param fontStoreroom
     *            �ֿ��ַ���
     * @param x
     *            ���Ƶ�xλ��
     * @param y
     *            ���Ƶ�yλ��
     * @param clipW
     *            ͼ�Ͳü����(ÿ����)
     * @param clipH
     *            ͼ�Ͳü��߶�(ÿ����)
     * @param anchor
     *            �Ե�ǰһ�����ֽ���ê��
     * @param space
     *            ÿ�����ֵĿ�϶
     * @param numDigit
     *            �����ֽ��в�0 ,�����Ϊ6����Ϊ1��ôӦ�û���Ϊ000001
     */
    public static void drawString(Graphics g, Image img, String str,
            String fontStoreroom, int x, int y, int clipW, int clipH,
            int anchor, int space, int numDigit,String digit) {
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        for (int i = sb.length(); i < numDigit; i++) {
            sb.insert(0, digit);// maxNumDigit �������Ļ���0 ��1 == 001
        }
        int charId;
        str = sb.toString();
        int strLength = str.length();
        int offset = getAnchorOffset(anchor, 0, strLength * clipW + space,
                clipH);
        x -= (offset & 0xffff0000) >> 16;
        y -= (offset & 0xffff);
        for (int i = 0; i < strLength; i++, x += clipW + space) {
            charId = fontStoreroom.indexOf(str.charAt(i));
            if (charId == -1) {
                continue;
            } else {
                drawRegion(g, img, charId * clipW, 0, clipW, clipH, 0, x, y, 0);
            }
        }
    }
    public static void drawString(Graphics g, Image img, String str,
            String fontStoreroom, int x, int y, int clipW, int clipH,
            int anchor, int space, int numDigit) {
    	drawString(g, img, str, fontStoreroom, x, y, clipW, clipH, anchor, space, numDigit,"0");
    	
    }
    /**
     * ָ����ɫ����
     *
     * @param g
     *            ����
     * @param color
     *            ʮ��������ɫֵ
     */
    public static void cls(Graphics g, int color) {
        g.setColor(color);
        g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
    }

    /**
     * �Զ����ê�㷽��. ��Ϊ����ͼ�ͺ����ֽ���ê�� �˷�����֧��Graphics.BASELINE��Ϊ transform���� �÷�int
     * offsetXY = getAnchorOffset(anchor,transform,width,height); x_dest -=
     * (short)((offsetXY >> 16) & 0xffff); y_dest -= (short)(offsetXY & 0xffff);
     *
     * @param anchor
     *            Graphics���ê��ֵ
     * @param transform
     *            �ඨ��ķ�תͼ�ͳ���
     * @param w
     *            ����Ŀ��
     * @param h
     *            ����ĸ߶�
     * @return һ��intֵ�������ê����λ��ƫ������
     */
    public static int getAnchorOffset(int anchor, int transform, int w, int h) {
        int offX = 0;
        int offY = 0;
        if ((transform & TRANS_MIRROR_ROT270) != 0) {// bit 00000101
            int tW = w;
            w = h;
            h = tW;
        }
        if ((anchor & Graphics.BASELINE) != 0) {
            throw new java.lang.IllegalArgumentException();
        }
        if ((anchor & Graphics.HCENTER) != 0) {
            offX = w >> 1;
        } else if ((anchor & Graphics.RIGHT) != 0) {
            offX = w;
        }
        if ((anchor & Graphics.VCENTER) != 0) {
            offY = h >> 1;
        } else if ((anchor & Graphics.BOTTOM) != 0) {
            offY = h;
        }
        return (offX << 16) | offY;//
    }// :~QN

    public static void drawRoundFrame(Graphics g, int x, int y, int w, int h) {
        final int arc = 40;
        g.setColor(0x030504);
        g.drawRoundRect(x, y, w, h, arc, arc);
//        g.setColor(0xcbba9b);
        g.setColor(0xDDD3D4);
        g.fillRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
        g.setColor(0x2c4031);
        g.drawRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
        g.drawRoundRect(x + 2, y + 2, w - 4, h - 4, arc, arc);
    }
    public static void drawRoundFrame(Graphics g, int x, int y, int w, int h, int fillcolor,int arc) {
        g.setColor(0x030504);
        g.drawRoundRect(x, y, w, h, arc, arc);
        g.setColor(fillcolor);
        g.fillRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
        g.setColor(0x2c4031);
        g.drawRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
        g.drawRoundRect(x + 2, y + 2, w - 4, h - 4, arc, arc);
    }
    public static void drawFrame(Graphics g, int x, int y, int w, int h) {
        final int arc = 4;
        g.setColor(0x030504);
        g.drawRoundRect(x, y, w, h, arc, arc);
//        g.setColor(0xcbba9b);
        g.setColor(0xDDD3D4);
        g.fillRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
        g.setColor(0x2c4031);
        g.drawRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
        g.drawRoundRect(x + 2, y + 2, w - 4, h - 4, arc, arc);
    }
    public static void drawImage(Graphics g,int x,int y,int anchor,int resID){
    	Image image = Resources.loadImage(resID);
    	if(image != null){
    		g.drawImage(image, x, y, anchor);
    	}else{
    		Logger.debug("erro Image "+resID+" is null !");
    	}
    }
    public static void drawImage(Graphics g,int x,int y,int anchor,Image image){
    	if(image != null){
    		g.drawImage(image, x, y, anchor);
    	}else{
    		Logger.debug("erro Image  is null !");
    	}
    }
    public static  void fillImage(Image image,Graphics g,int x,int y,int fillwidth,int fillheight,int reswidth,int resheight,int index,int fx){
    	if(fx==0){
    		int count = fillwidth/reswidth;
        	for(int i = 0;i<count;i++){
        		drawFrame(g, index, image, x + i*reswidth, y, TRANS_NONE, LEFT_TOP, reswidth, resheight);
        	}
    	}else{
    		int count = fillheight/resheight;
        	for(int i = 0;i<count;i++){
        		drawFrame(g, index, image, x, y+i*resheight, TRANS_NONE, LEFT_TOP, reswidth, resheight);
        	}
    	}

    }
}
