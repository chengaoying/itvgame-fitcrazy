package com.tvgame.actor;

import com.tvgame.constant.Const;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author liushiming
 */
public class Resources {
    //������Դ�б� (data.spx|image.png)

	public  static final byte SPRITE_item_01 = 0;
	public  static final byte SPRITE_item_02 = 1;
	public  static final byte SPRITE_item_03 = 2;
	public  static final byte SPRITE_item_04 = 3;
	public  static final byte SPRITE_item_05 = 4;
	public  static final byte SPRITE_item_06 = 5;
	public  static final byte SPRITE_item_07 = 6;
	public  static final byte SPRITE_item_08 = 7;
	public  static final byte SPRITE_item_09 = 8;
	public  static final byte SPRITE_item_10 = 9;
	public  static final byte SPRITE_item_11 = 10;
	public  static final byte SPRITE_item_12 = 11;
	public  static final byte SPRITE_item_13 = 12;
	public  static final byte SPRITE_item_14 = 13;
	public  static final byte SPRITE_item_15 = 14;
	public  static final byte SPRITE_item_16 = 15;
	public  static final byte SPRITE_explode  = 16;
	public  static final byte SPRITE_BOMB    = 17;
	public  static final byte SPRITE_fire    = 18;
	public  static final byte SPRITE_jewel   = 19;
	public  static final byte SPRITE_pig     = 20;
	public  static final byte SPRITE_daoju_lock  = 21;
	public  static final byte SPRITE_ani_propflash  = 22;//ˢ�µ��ߵĵط�����
	/*
	//�����󶯻� 
	public static final byte SPRITE_big_ani_bomb = 21;//�󶯻�ը��Ч��
	public static final byte SPRITE_big_ani_jewel = 22;//�󶯻�ħ����Ч��
	public static final byte SPRITE_big_ani_box = 23;//�󶯻�������Ч��
	*/
    private static final String spriteResInfo[] = {
    	"item_01",
    	"item_02",
    	"item_03",
    	"item_04",
    	"item_05",
    	"item_06",
    	"item_07"//6
    	,"item_08",
    	"item_09",
    	"item_10",
    	"item_11"//10
    	,"item_12",
    	"item_13",
    	"item_14",
    	"item_15",
    	"item_16"//15
    	,"ani_explode",
    	"ani_bomb",
    	"ani_fire",
    	"ani_jewel",
    	"ani_pig"//20
    	,"daoju_lock",
    	"ani_propflash",
    	/*"big_effect/ani_big_bomb","big_effect/ani_big_jewel","big_effect/ani_box"*/
    };

    private static final Image spriteImagesCache[] = new Image[spriteResInfo.length];
    private static final SpriteX[] spriteArry = new SpriteX[spriteImagesCache.length];
    public static String getDataName(int index) {
        return "/item/"+spriteResInfo[index]+".sprite";
    }

    public static String getImageName(int index) {
        return "/item/"+spriteResInfo[index]+".png";
    }

    public static Image getImage(int index) {
        if (spriteImagesCache[index] == null) {
            spriteImagesCache[index] = createImage(getImageName(index));
        }
        return spriteImagesCache[index];
    }

    public static Image createImage(String imageName) {
        try {
        	return Image.createImage(imageName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static SpriteX getSpritex(int type) {
    	if(spriteArry[type]==null){
    		spriteArry[type] = new SpriteX(getDataName(type), getImage(type));
    	}
        return new SpriteX(spriteArry[type]);
    }
    public static Actor getActor(int type) {
        return null;
    }
    public static void drawRegion(Graphics g, Image image, int x, int y, int width, int height, int sx, int sy) {
        int clipx = g.getClipX();
        int clipy = g.getClipY();
        int clipw = g.getClipWidth();
        int cliph = g.getClipHeight();
        g.setClip(x, y, width, height);
        g.drawImage(image, x - sx, y - sy, Graphics.LEFT | Graphics.TOP);
        g.setClip(clipx, clipy, clipw, cliph);

    }
    //=============================================================================================
    // =============================================ͼƬ��Դ����
    public static final int IMG_ID_BOTBAR = 0;
    public static final int IMG_ID_CG_LOGO_01 = 1;
    public static final int IMG_ID_CG_LOGO_02 = 2;
    public static final int IMG_ID_MENU_BG = 3;
    public static final int IMG_ID_SELECTED = 4;
    public static final int IMG_ID_CELL0 = 5;
    public static final int IMG_ID_BUTTON_01 = 6;  
//    public static final int IMG_ID_BUTTON_TEXT = 7;
    public static final int IMG_ID_STATUS_01 = 8;
    public static final int IMG_ID_STATUS_02 = 9;
    public static final int IMG_ID_SYMBOL = 10;
    public static final int IMG_ID_NO = 11;
    public static final int IMG_ID_STATUS_ICON = 12;
    public static final int IMG_ID_UI_TEXT = 13;
    public static final int IMG_ID_BG = 14;
    public static final int IMG_ID_SHADOW = 15;
    public static final int IMG_ID_TUOPAN = 16;
    
//    public static final int IMG_ID_MAINMENU_INTRO =17;//���˵��ϵ�ͼƬ����Ϊ�����
    public static final int IMG_ID_MAINMENU_ITEM_TEXT = 18;//���˵�ѡ������
    public static final int IMG_ID_MAINMENU_ITEM_HAND = 19;//���˵���ָͼ��
    public static final int IMG_ID_MAINMENU_ITEM_BUTTON = 20;//���˵���BUTTON����
    public static final int IMG_ID_DIALOG = 21; //�Ի�����ͼ
    public static final int IMG_ID_DIALOG_BAR = 22;//�Ի���С��ͼ
    public static final int IMG_ID_COMMAND = 23;//ȷ���ͷ���
    public static final int IMG_ID_COMMAND_BG = 24;//COMMAND �ı���
    public static final int IMG_ID_LOADING_TITLE = 25;//����ѡ������title[��Ϊ������ͼƬ��]
    public static final int IMG_ID_SEASON = 26;//����ѡ���ͼƬ
    public static final int IMG_ID_LOCK = 27;//������
    
    public static final int IMG_ID_UI_KUANG_02_CORNER_PNG = 28;//����Ի���ͼƬ
    public static final int IMG_ID_UI_KUANG_02_SIDE_1_PNG = 29;//����Ի���ͼƬ
    public static final int IMG_ID_UI_KUANG_02_SIDE_2_PNG = 30;//����Ի���ͼƬ
    public static final int IMG_ID_UI_NAME_BG_PNG = 31;//�Ի�ʱ�������ֱ�����
    public static final int IMG_ID_DIALOG_ROLE = 32;//�Ի�ʱ����ͷ��
    public static final int IMG_ID_DIALOG_ROLE2 = 33;//�Ի�ʱ������һ������ͷ��
    public static final int IMG_ID_NO_01 = 34;//����ͼƬ1
    public static final int IMG_ID_NO_02 = 35;//����ͼƬ2
    public static final int IMG_ID_S_ITEM_ICON_ALL = 36;//Сicon
    public static final int IMG_ID_NO_SYMBO_01 = 37;//�����01
    public static final int IMG_ID_NO_SYMBO_02 = 38;//�����02
    public static final int IMG_ID_S_ITEM_ICON = 39;
    
    public static final int IMG_ID_ACHI_ITEMBG = 40;//�ɾ�ѡ��ı���ͼ
    public static final int IMG_ID_ACHI_OFF = 41;//�ɾʹ�ɱ�ǣ�����
    public static final int IMG_ID_ACHI_ON = 42;//�ɾ�δ��ɱ�ǣ�X��
    
    public static final int IMG_ID_LIGHT = 43;//����
    public static final int IMG_ID_MALLTAB = 44;//�̳��л��ı�ǩ
    public static final int IMG_ID_MALL_MONEY_CHANGE_TEXT = 45;//��ֵ�һ�������ͼ
    public static final int IMG_ID_MALL_BUTTON_BG = 46; //��ֵ�һ�button�ı���ͼ
    public static final int IMG_ID_ACHI_GAME_SHOWTIP = 47;//��Ϸ�е����ĳɾͽ�������
    
    public static final int IMG_ID_GAME_BG1 = 48;
    public static final int IMG_ID_GAME_BG2 = 49;
    public static final int IMG_ID_GAME_BG3 = 50;
    public static final int IMG_ID_GAME_BG4 = 51;
    
    public static final int IMG_ID_TEACH_1 = 52;//����ָ��ͼƬ1
    public static final int IMG_ID_TEACH_2 = 53;
    public static final int IMG_ID_TEACH_3 = 54;
    public static final int IMG_ID_TEACH_4 = 55;
    
    public static final int IMD_ID_FEE_ICON = 56;//�շѵ��ߴ�ͼ��
    public static final int IMG_ID_CG_LOGO_03 = 57;//�շѵ��ߴ�ͼ��
    
    public static final int IMG_ID_SHOP = 58;
    public static final String[] img_file_name = {
    	"botbar.png", 
    	"cg_logo01.png",
    	"cg_logo02.png",
    	"menubg.png",
    	"selected.png",
    	"cell0.png",
    	"button_01.png",
    	"",
    	"status_01.png",
    	"status_02.png",
    	"symbol.png",
    	"no.png",
    	"status_icon.png",
    	"ui_text.png",
    	"bg.png",
    	"item_shadow.png",
    	"tuopan.png",
    	"",
    	"mainmenu_item_text.png",
    	"mainmenu_item_hand.png",
    	"mainmenu_button.png",
    	"dialog.png",
    	"dialog_bar.png",
    	"button_02_text.png",
    	"button_02.png",
    	"loading_title.png",
    	"season.png",
    	"lock.png",
    	"ui_kuang_02_corner.png",
    	"ui_kuang_02_side_1.png",
    	"ui_kuang_02_side_2.png",
    	"ui_name_bg.png",
    	"char_01.png",
    	"char_02.png",
    	"no_01.png",
    	"no_02.png",
    	"s_item_icon_all.png",
    	"no_symbo_01.png",
    	"no_symbo_02.png",
    	"s_item_icon.png",
    	"achi_itembg.png",
    	"achi_off.png",
    	"achi_on.png",
    	"light.png",
    	"malltab.png",
    	"mall_money_change_text.png",
    	"mall_button_bg.png",
    	"achi_game_showTip.png",
    	"gamebg_1.png",
    	"gamebg_2.png",
    	"gamebg_3.png",
    	"gamebg_4.png",
    	"teach_01.png",
    	"teach_02.png",
    	"teach_03.png",
    	"teach_04.png",
    	"fee_icon.png",
    	"cg_logo03.png",
    	
    	"shop/shop.png",
    };
    
    static final Image[] resList = new Image[100];

    public static Image loadDialogImage(int resId) {
        if (resList[resId] == null) {
            try {
                resList[resId] = Image.createImage("/dialog/"+img_file_name[resId]);
            } catch (IOException e) {
                resList[resId] = Image.createImage(1, 1);
            }
        }
        return (Image) resList[resId];
    }
    
    public static Image loadImage(int resId) {
        if (resList[resId] == null) {
            try {
                resList[resId] = Image.createImage("/"+img_file_name[resId]);
            } catch (IOException e) {
                resList[resId] = Image.createImage(1, 1);
            }
        }
        return (Image) resList[resId];
    }

    public static void loadImage(int[] list) {
        for (int i = 0; i < list.length; i++) {
            loadImage(list[i]);
        }
    }

    public static void releaseImage(int[] list) {
        for (int i = 0; i < list.length; i++) {
            resList[list[i]] = null;
        }
    }

    public static void releaseImage(Image img) {
        if (img != null) {
            for (int i = 0; i < resList.length; i++) {
                if (img.equals(resList[i])) {
                    resList[i] = null;
                    break;
                }
            }
        }
    }

    public static void releaseImage(int resId) {
        resList[resId] = null;
    }

    public static void releaseAllImage() {
        for (int i = 0; i < resList.length; i++) {
            resList[i] = null;
        }
    }
}
