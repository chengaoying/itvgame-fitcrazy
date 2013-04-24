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
    //动画资源列表 (data.spx|image.png)

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
	public  static final byte SPRITE_ani_propflash  = 22;//刷新道具的地方闪光
	/*
	//新增大动画 
	public static final byte SPRITE_big_ani_bomb = 21;//大动画炸弹效果
	public static final byte SPRITE_big_ani_jewel = 22;//大动画魔法珠效果
	public static final byte SPRITE_big_ani_box = 23;//大动画开宝箱效果
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
    // =============================================图片资源管理
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
    
//    public static final int IMG_ID_MAINMENU_INTRO =17;//主菜单上的图片【我为合体狂】
    public static final int IMG_ID_MAINMENU_ITEM_TEXT = 18;//主菜单选项文字
    public static final int IMG_ID_MAINMENU_ITEM_HAND = 19;//主菜单手指图标
    public static final int IMG_ID_MAINMENU_ITEM_BUTTON = 20;//主菜单的BUTTON背景
    public static final int IMG_ID_DIALOG = 21; //对话框切图
    public static final int IMG_ID_DIALOG_BAR = 22;//对话框小切图
    public static final int IMG_ID_COMMAND = 23;//确定和返回
    public static final int IMG_ID_COMMAND_BG = 24;//COMMAND 的背景
    public static final int IMG_ID_LOADING_TITLE = 25;//场景选择界面的title[我为合体狂的图片字]
    public static final int IMG_ID_SEASON = 26;//场景选择的图片
    public static final int IMG_ID_LOCK = 27;//场景锁
    
    public static final int IMG_ID_UI_KUANG_02_CORNER_PNG = 28;//人物对话框图片
    public static final int IMG_ID_UI_KUANG_02_SIDE_1_PNG = 29;//人物对话框图片
    public static final int IMG_ID_UI_KUANG_02_SIDE_2_PNG = 30;//人物对话框图片
    public static final int IMG_ID_UI_NAME_BG_PNG = 31;//对话时人物名字背景框
    public static final int IMG_ID_DIALOG_ROLE = 32;//对话时主角头像
    public static final int IMG_ID_DIALOG_ROLE2 = 33;//对话时候另外一个主角头像
    public static final int IMG_ID_NO_01 = 34;//数字图片1
    public static final int IMG_ID_NO_02 = 35;//数字图片2
    public static final int IMG_ID_S_ITEM_ICON_ALL = 36;//小icon
    public static final int IMG_ID_NO_SYMBO_01 = 37;//运算符01
    public static final int IMG_ID_NO_SYMBO_02 = 38;//运算符02
    public static final int IMG_ID_S_ITEM_ICON = 39;
    
    public static final int IMG_ID_ACHI_ITEMBG = 40;//成就选项的背景图
    public static final int IMG_ID_ACHI_OFF = 41;//成就达成标记（勾）
    public static final int IMG_ID_ACHI_ON = 42;//成就未达成标记（X）
    
    public static final int IMG_ID_LIGHT = 43;//阳光
    public static final int IMG_ID_MALLTAB = 44;//商城切换的标签
    public static final int IMG_ID_MALL_MONEY_CHANGE_TEXT = 45;//充值兑换的文字图
    public static final int IMG_ID_MALL_BUTTON_BG = 46; //充值兑换button的背景图
    public static final int IMG_ID_ACHI_GAME_SHOWTIP = 47;//游戏中弹出的成就界面文字
    
    public static final int IMG_ID_GAME_BG1 = 48;
    public static final int IMG_ID_GAME_BG2 = 49;
    public static final int IMG_ID_GAME_BG3 = 50;
    public static final int IMG_ID_GAME_BG4 = 51;
    
    public static final int IMG_ID_TEACH_1 = 52;//新手指导图片1
    public static final int IMG_ID_TEACH_2 = 53;
    public static final int IMG_ID_TEACH_3 = 54;
    public static final int IMG_ID_TEACH_4 = 55;
    
    public static final int IMD_ID_FEE_ICON = 56;//收费道具大图标
    public static final int IMG_ID_CG_LOGO_03 = 57;//收费道具大图标
    
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
