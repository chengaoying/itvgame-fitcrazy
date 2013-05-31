package com.tvgame.game;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;

import com.tvgame.actor.Resources;
import com.tvgame.actor.Scene;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.midlet.FitCrazyMIDlet;
import com.tvgame.ui.Box;
import com.tvgame.ui.Tip;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.TextView;
import com.tvgame.util.Util;

public class Game extends GameCanvasEngine/*Canvas implements Runnable,CommonListener*/ {

    boolean isPause; 					// 应用处于暂停状态
    static boolean isExit; 				// 退出应用
    static boolean isRealLoad;
    public static boolean isNewGame = true;	// 新游戏
    public int lastState2;
    public static PropManager pm;
    public GameRanking[] rankList;
    public Graphics gs;
    
    //音效
    //public MusicMaster  musicMasterPIG = new MusicMaster();
    //public MusicMaster  musicMasterKL = new MusicMaster();
    private static Object o = new Object();
    public static Game getInstance() {
        return instance;
    }
    
    private static Game instance = buildGameEngine();

	private static Game buildGameEngine() {
		if(instance==null){
			return new Game(FitCrazyMIDlet.getInstance());
		}else{
			return instance;
		}
	}
    
    //构造器
    public Game(FitCrazyMIDlet midlet) {
    	super(midlet);
    	setRelease(false);
        lastState2 = ST_MENU;
        pm = new PropManager(this);
        setState(ST_MENU, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    }

    /**
     * 初始一个游戏状态的资源和变量
     *
     * @param state
     */
    public void initState(int state) {
        System.out.println("state:" + state);
        System.gc();
        switch (state) {
        	case ST_LOGO:
        		initLogo();
            break;
            case ST_MENU:
                break;
            case ST_SCENECHANGE:
            	initSceneChange();
            	break;
            case ST_GAME_LOADING:
            	initGameLoading();
            	break;
            case ST_HELP:
                break;
            case ST_GAME:
                initGame();
                break;
        }
    }

    /**
     * 释放一个游戏状态的资源
     *
     * @param rlsState
     */
    public void releaseState(int rlsState) {
        switch (rlsState) {
            case ST_GAME:
                break;
        }
        System.gc();
    }
    public static Box box[];

    public void doPaint(Graphics g) {
        if (g == null) {
            return;
        }
        g.setFont(Const.font);
        g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
        GraphicsUtil.cls(g, 0x004455);
        switch (state) {
        case ST_LOGO:
        	drawLogo(g);
        	break;
        case ST_MENU:
        	drawnMenu(g);
            break;
        case ST_SCENECHANGE:
        	break;
        case ST_GAME_LOADING:
        	drawGameLoading(g);
        	break;
        case ST_GAME:
            drawGame(g);
            if(Dialog.isShowDialog)
        		Dialog.drawGameDialog(g);
            break;
        }
        if(!Dialog.isShowDialog){
        	GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT, GraphicsUtil.HCENTER_BOTTOM, Resources.IMG_ID_BOTBAR);
        	GraphicsUtil.drawImage(g, 20, Const.HEIGHT-1, GraphicsUtil.LEFT_BOTTOM, Resources.IMG_ID_CG_LOGO_02);
        }
		drawUiStack(g);    
    }

    protected void fillScreenWithBlack(Graphics g) {
        g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
        g.setColor(0);
        g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
    }

    public void paint(Graphics g) {
    	gs = g;
        if (isLoading) {
            drawLoading(g);
        } else {
            doPaint(g);
        }

    }
    public static final int FRAME = 20;

    public void GameLogic() {
    	synchronized (o) {
    		if(!updateUiStack()){
                switch (state) {
                case ST_LOGO:
                	updataLogo();
                	break;
                case ST_MENU:
                	updataMenu();
                    break;
                case ST_GAME_LOADING:
                	updataGameLoading();    
                	break;
                case ST_SCENECHANGE:
                	updataSceneChange();   
                	break;
                case ST_GAME:
                    updataGame();
                    break;
                }
    		}
    	}
    }
    public void updataPaint(){
        if (isLoading) {
            updateLoading();
        } else {
            repaint();
            serviceRepaints();
        }
    }
    
    //LOGO部分
    private int loadinglevel;
    private void initLogo(){
    	Resources.loadImage(Resources.IMG_ID_BOTBAR);
    	Resources.loadImage(Resources.IMG_ID_CG_LOGO_01);
    	Resources.loadImage(Resources.IMG_ID_CG_LOGO_02);
    	Resources.loadImage(Resources.IMG_ID_CG_LOGO_03);
    	loadinglevel = 0;
    }
    private void drawLogo(Graphics g){
    	g.setColor(0x004455);
    	g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
    	GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT_HALF, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_CG_LOGO_01);
    	//GraphicsUtil.drawImage(g, Const.WIDTH_HALF, 250, GraphicsUtil.HCENTER_BOTTOM, Resources.IMG_ID_CG_LOGO_02);
    	GraphicsUtil.drawImage(g, Const.WIDTH_HALF, 250+40, GraphicsUtil.HCENTER_TOP, Resources.IMG_ID_CG_LOGO_03);
    	//g.drawString("游戏加载中,请稍候...", Const.WIDTH_HALF, 320, GraphicsUtil.HCENTER_TOP);

    	g.setColor(0x00BDCD);
    	g.fillRect(Const.WIDTH_HALF - 220, 380, loadinglevel, 26);
    	g.setColor(0x00FFFF);
    	g.drawRect(Const.WIDTH_HALF - 220, 380, 440, 26);
    }
    private void updataLogo(){
    	if(loadinglevel<200){
    		loadinglevel+=10;
    		//loadinglevel= loadinglevel>440?440:loadinglevel;
    	}else if(loadinglevel<240){
    		threadSleep(2000);
    		loadinglevel=250;
    	}
    	else if(loadinglevel<440){
    		loadinglevel+=10;
    		loadinglevel= loadinglevel>440?440:loadinglevel;
    	}
    	else{
    		//通知服务器开始游戏(这个地方需要延迟否则是失败)
    		threadSleep(1000);
    		//CommonMain.doGameBegin();
    		
    	Resources.releaseImage(Resources.IMG_ID_CG_LOGO_01);
    	//Resources.releaseImage(Resources.IMG_ID_CG_LOGO_02);
    	Resources.releaseImage(Resources.IMG_ID_CG_LOGO_03);
    	
    	setState(ST_MENU, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    	}
    }
    
    //主菜单部分
    private int selectIndex;
    private void drawnMenu(Graphics g) {
    	GraphicsUtil.drawImage(g, Const.WIDTH_HALF,Const.HEIGHT_HALF, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_MENU_BG);
    	int px = 500, py;
    	for(int i = 0;i<6;i++){
    		py = 70+i*60;
    		GraphicsUtil.drawImage(g, px, py, GraphicsUtil.HCENTER_TOP, Resources.IMG_ID_MAINMENU_ITEM_BUTTON);
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_MAINMENU_ITEM_TEXT), 83*i, 0, 83,26 , 0, px, py+10, GraphicsUtil.HCENTER_TOP);
    	}
    	GraphicsUtil.drawImage(g, px-100+iClock%12, 70+selectIndex*60, GraphicsUtil.HCENTER_TOP, Resources.IMG_ID_MAINMENU_ITEM_HAND);
    }
    
	private void updataMenu() {
		if (keyState.containsAndRemove(KeyCode.DOWN)) {
			selectIndex = selectIndex < 5 ? ++selectIndex : 0;
		} else if (keyState.containsAndRemove(KeyCode.UP)) {
			selectIndex = selectIndex > 0 ? --selectIndex : 5;
		} else if (keyState.containsAndRemove(KeyCode.OK)) {
			switch (selectIndex) {
			case 0:  		//继续游戏
				SaveGameRecord r = new SaveGameRecord(Game.getInstance());
				r.loadAttainment();
				boolean result = r.loadRecord();
				if(!result){
					/*PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					pt.setText("没有游戏记录,请重新开始游戏!");
					pt.popup();*/
					showTip("没有游戏记录,请重新开始游戏!");
					selectIndex = 1;
				}else{
					pm.queryProps();
					isNewGame = false;
					setState(ST_GAME_LOADING, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
					selectIndex = 0;
					clearMenu();
				}
				break;
			case 1:			//开始游戏
				 //加载成就信息
		    	SaveGameRecord sgr = new SaveGameRecord(Game.getInstance());
				sgr.loadAttainment();
				UserData.loadData();
				isNewGame = true;
				pm.queryProps();
				setState(ST_GAME_LOADING, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
				selectIndex = 0;
				clearMenu();
				break;
			case 2:
				// 排行榜信息
				// CommonMain.doMarkLoadList("10",CommonMain.MARK_TYPE_SINGLE);
				ServiceWrapper sw = getServiceWrapper();
				rankList = sw.queryRankingList(0, 10);
				openRank();
				clearMenu();
				break;
			case 3:
				openHelp();
				//pm.queryProps();
				//openShop();
				//new Thread(new Shop(gs)).start();
				break;
			case 4:
				 //加载成就信息
		    	SaveGameRecord gr = new SaveGameRecord(Game.getInstance());
				gr.loadAttainment();
				openAchichment();
				clearMenu();
				break;
			case 5:
				isExit = true;
				exit = true;
				break;
			}
		} else if (keyState.containsAndRemove(KeyCode.NUM0)) {
			//isExit = true;
			//exit = true;
		} else if (keyState.containsAndRemove(KeyCode.NUM9)) {
			//Game.getInstance().pm.sysProps();
			Game.getInstance().openShop();
			clearMenu();
		}
	}
	
	public static void clearMenu(){
		Resources.releaseImage(Resources.IMG_ID_MENU_BG);
		Resources.releaseImage(Resources.IMG_ID_MAINMENU_ITEM_BUTTON);
		Resources.releaseImage(Resources.IMG_ID_MAINMENU_ITEM_TEXT);
		Resources.releaseImage(Resources.IMG_ID_MAINMENU_ITEM_HAND);
	}
	
    /***
     * 绘制框背景
     * @param g
     */
    public void drawBackGround(Graphics g,String title){
    	drawBackGround(g,title,true);
    }
    
    public void drawBackGround(Graphics g,String title,int command_id){
    	drawBoader(g, Const.WIDTH_HALF>>2, Const.HEIGHT_HALF>>2,480,384);
    	Util.draw3DString(g, title, Const.WIDTH_HALF-((Const.FONT_W_CH*title.length())>>1), Const.HEIGHT_HALF/4+5, 20, 0x0, 0xffffff);
    	int command_bg_x =  (Const.WIDTH_HALF/4+480)/2+120;
    	int command_bg_y =  (Const.HEIGHT_HALF/4+384-18);
    	GraphicsUtil.drawImage(g,command_bg_x, command_bg_y, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_COMMAND_BG);
    	drawButton(g, command_bg_x, command_bg_y, command_id);
    }
    public void drawBackGround(Graphics g,String title,boolean flag){
    	drawBoader(g, Const.WIDTH_HALF>>2, Const.HEIGHT_HALF>>2,480,384);
    	Util.draw3DString(g, title, Const.WIDTH_HALF-((Const.FONT_W_CH*title.length())>>1), Const.HEIGHT_HALF/4+5, 20, 0x0, 0xffffff);
    	if(flag){
    	int command_bg_x =  (Const.WIDTH_HALF/4+480)/2+40;
    	int command_bg_y =  (Const.HEIGHT_HALF/4+384-18);
    	GraphicsUtil.drawImage(g,command_bg_x, command_bg_y, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_COMMAND_BG);
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_COMMAND), 0, 0, 30,14, 0, (Const.WIDTH_HALF/4+480)/2+40, (Const.HEIGHT_HALF/4+384-18), GraphicsUtil.HCENTER_VCENTER);
    	}
    }
    
    /***
     * 绘制按键(确定)
     * @param g
     * @param x
     * @param y
     */
    public static void drawButton(Graphics g,int x ,int y){
    	GraphicsUtil.drawImage(g,x, y, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_COMMAND_BG);
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_COMMAND), 0, 0, 30,14, 0, x, y, GraphicsUtil.HCENTER_VCENTER);
    }
    
    public static void drawButton(Graphics g,int x ,int y,int command_id){
    	GraphicsUtil.drawImage(g,x, y, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_COMMAND_BG);
    	if(command_id == Const.COMMAND_OK)
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_COMMAND), 0, 0, 30,14, 0, x, y, GraphicsUtil.HCENTER_VCENTER);
    	else if(command_id == Const.COMMAND_BACK)
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_COMMAND), 30, 0, 30,14, 0, x, y, GraphicsUtil.HCENTER_VCENTER);
    }
    
    //对话框边框展开的效果
    private int open_mode_delata_leftToright = 0;
    private int open_mode_delata_rightToLeft = 0;
    private int open_mode_delata_upToDown = 0;
    private int open_mode_delata_DownToUp = 0;
    public void resetBoaderMode()
    {
    	open_mode_delata_leftToright = 0;
    	open_mode_delata_rightToLeft = 0;
    	open_mode_delata_upToDown = 0;
    	open_mode_delata_DownToUp = 0;
    }
    
    /**
     * 以某种展开形式绘制边框
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param mode 0-从左往右横向展开 1-从右往左横向展开 2-从上至下展开 3-从下至上展开
     */
    public void drawBoaderWithMode(Graphics g,int x,int y,int w,int h,int mode)
    {
    	if(mode == 0)
    	{
    		g.setClip(x, y, open_mode_delata_leftToright, h);
    		if(open_mode_delata_leftToright<=w)
    			open_mode_delata_leftToright +=32;
    		else {
    			open_mode_delata_leftToright = w;
			}
    		drawBoader(g, x, y, w, h);
    	}
    	else if(mode == 1)
    	{
    		g.setClip(x+w-open_mode_delata_rightToLeft, y, open_mode_delata_rightToLeft, h);
    		if(open_mode_delata_rightToLeft<w)
    			open_mode_delata_rightToLeft+=32;
    		else
    			open_mode_delata_rightToLeft = w;
    		drawBoader(g, x, y, w, h);
    	}
    	else if(mode == 2)
    	{
    		g.setClip(x, y, w, open_mode_delata_upToDown);
    		if(open_mode_delata_upToDown<=h)
    			open_mode_delata_upToDown +=32;
    		else {
    			open_mode_delata_upToDown = h;
			}
    		drawBoader(g, x, y, w, h);
    	}
    	else if(mode == 3)
    	{
    		g.setClip(x, y+h-open_mode_delata_DownToUp, w, open_mode_delata_DownToUp);
    		if(open_mode_delata_DownToUp<h)
    			open_mode_delata_DownToUp+=32;
    		else
    			open_mode_delata_DownToUp = h;
    		drawBoader(g, x, y, w, h);
    	
    	}
    }
    
    /**
     * 绘制对话边框
     * @param g
     * @param x
     * @param y
     * @param w 总的宽度[32的整数倍] 至少96
     * @param h 总的高度[32的整数倍] 至少96
     */
    public void drawBoader(Graphics g,int x,int y,int w,int h)
    {
    	//顶部区域
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 0, 0, 32, 32, 0,x , y, GraphicsUtil.LEFT_TOP);
    	int col = (w-64)/32;//除掉2个边角宽度后，中间的正方块儿横向平铺多少列
    	for (int j = 1; j <= col; j++) {
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 32, 0, 32, 32, 0,x+32*j , y, GraphicsUtil.LEFT_TOP);
		}
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 64, 0, 32, 32, 0,x+w-32, y, GraphicsUtil.LEFT_TOP);
    	//中间区域
    	int row = (h-64)/32;//除掉2个边角宽度后，中间的正方块儿纵向平铺多少行
    	for (int i = 0; i < row; i++) {
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 96, 0, 32, 32, 0,x , y+32+32*i, GraphicsUtil.LEFT_TOP);
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 160, 0, 32, 32, 0,x+w-32 , y+32+32*i, GraphicsUtil.LEFT_TOP);
		}
    	for (int i = 1; i <= row; i++) {
    		for (int j = 1; j <= col; j++) {
    			GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 128, 0, 32, 32, 0,x+32*j , y+32*i, GraphicsUtil.LEFT_TOP);
    		}
    	}
    	//底部区域
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 288, 0, 32, 32, 0,x , y+h-32, GraphicsUtil.LEFT_TOP);
    	for (int i = 1; i <=col; i++) {
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 320, 0, 32, 32, 0,x+32*i , y+h-32, GraphicsUtil.LEFT_TOP);
		}
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 352, 0, 32, 32, 0,x+w-32, y+h-32, GraphicsUtil.LEFT_TOP);
    	g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
    }
 
    /**
     * 全局计数器
     */
    public static int iClock = 0;
    private final int ZTE_FRAME = 60;
    public static final boolean bZTE = true;

	public void loop() {
		while (!isExit) {
			if (iClock < 65535)
				iClock++;
			else
				iClock = 0;
			
			//long TIME = System.currentTimeMillis();
			GameLogic();
			updataPaint();
			try {
				if (bZTE) {
					Thread.sleep(ZTE_FRAME);
				} else
					Thread.sleep(FRAME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

    public void threadSleep(int time) {
        long startTime = System.currentTimeMillis();
        do {
            Thread.yield();
        } while ((System.currentTimeMillis() - startTime < time));
    }
    public static long CurSleepTime;

    // 游戏状态
    public static final int ST_LOGO         = 0;  	//游戏LOGO
    public static final int ST_MENU         = 1;  	//主菜单
    public static final int ST_HELP         = 11; 	//帮助
    public static final int ST_GAME_LOADING = 12; 	//游戏加载展示动画
    public static final int ST_SCENECHANGE  = 13; 	//场景选择
    //public static final int ST_TOP          = 14; //排行榜
    public static final int ST_GAME         = 20; 	//新游戏
    public static final int ST_GAMEMENU     = 21; 	//游戏暂停菜单
    //public static final int ST_GAME_ACHIMENT= 22; //游戏中成就界面
    //public static final int ST_GAMEMENU_HELP = 23;//游戏中帮助
    
    public static int state = ST_MENU; 				//游戏的当前状态
    private int prevState; 							//游戏的上一个状态 previously
    private int nextState; 							//游戏的下一个状态
    private Object[] stateArgs;
    static int stateTick;
    public static final int STFLAG_DROW_LOADING = 1;
    public static final int STFLAG_RLES_THIS = 1 << 1;
    public static final int STFLAG_INIT_NEXT = 1 << 2;
    public int stFlag;

    
    /**
     * @param nextState
     *            要前往的游戏状态
     * @param isDrawLoading
     *            是否绘制进度信息
     * @param isRelsCurState
     *            是否释放上个状态(当前状态)的资源
     * @param isInitRes
     *            是否重新初始资源
     * @param args
     *            其它自定义参数
     */
    private void setState(int nextState, int flag, Object[] args) {
        stFlag = flag;
        stateTick = 0;
        this.nextState = nextState;
        this.prevState = state;
        this.isDrawLoading = (flag & STFLAG_DROW_LOADING) != 0;
        this.isReInitRes = (flag & STFLAG_INIT_NEXT) != 0;
        this.stateArgs = args;
        startLoading();
    }
    
    //LOADING
    boolean isLoadingFinish; 			// 资源加载完成
    public static boolean isLoading; 	// 处于资源加载状态
    boolean isDrawLoading; 				// 显示加载界面
    boolean isReInitRes;
    int progress = 0; 					// 资源加载进度
    int progressIncrease = 1; 			// 递增
    static final int MaxProgress = 265; // 估算加载资源所用帧数

    // 初始游戏LOADING状态
    public void startLoading() {
        isLoadingFinish = false;
        isLoading = true;
    }

    // 更新LOADING界面
    public void updateLoading() {
        repaint();
        if (!isLoadingFinish) {
            if ((stFlag & STFLAG_RLES_THIS) != 0) {
                releaseState(prevState);
            }
            System.gc();
            if ((stFlag & STFLAG_INIT_NEXT) != 0) {
                initState(nextState);
            }
            isLoadingFinish = true;
        }

        if (isLoadingFinish) {// 进度显示完毕
            isDrawLoading = false;
            isLoading = false;
            state = nextState;
            progress = MaxProgress;
        }
    }

    // 资源LOADING界面
    public void drawLoading(Graphics g) {
    }
    
    // -------------------------------------------------------------StringUtil.java
    /**
     * 读取TXT文件
     *
     * @param fileName
     *            路径
     * @return 按在TXT中的回车排的字符数组
     */
    public static String[] readTxtUnicodeBigEndian(String fileName) {
        StringBuffer stringbuffer = new StringBuffer();
        java.util.Vector al = new java.util.Vector();
        DataInputStream dis;
        try {
            dis = new DataInputStream(fileName.getClass().getResourceAsStream(
                    fileName));
            dis.skip(2);
            char c1;
            while ((c1 = dis.readChar()) != -1) {
                if (c1 == '\n') {
                    stringbuffer.deleteCharAt(stringbuffer.length() - 1);
                    al.addElement(stringbuffer.toString());
                    stringbuffer.delete(0, stringbuffer.length());

                } else {
                    stringbuffer.append(c1);
                }
            }
            dis.close();
            dis = null;
        } catch (EOFException eof) {
        } catch (IOException ioex) {
        }
        if (stringbuffer.length() > 0) {
            al.addElement(stringbuffer.toString());
        }
        String[] s = new String[al.size()];
        al.copyInto(s);
        return s;
    }

    /**
     * 读取UTF8txt文件
     * @param fileName 路径
     * @return 整个文件的内容
     */
    public static String readTxyByUTF8(String fileName) {
        String strReturn = "";
        InputStream dis = null;
        try {
            dis = "".getClass().getResourceAsStream(fileName);
            dis.skip(3);
            int n;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((n = dis.read()) != -1) {
                bos.write(n);
            }
            strReturn = new String(bos.toByteArray(), "UTF-8");
            dis.close();
        } catch (Exception e) {
        } finally {
            dis = null;
        }
        return strReturn;
    }


    /***************************************************************************
     * UI 栈
     **************************************************************************/
    public static Stack uiStack = new Stack();
    public static boolean drawUiStack(Graphics g) {
        if (uiStack.isEmpty()) {
            return false;
        }
        for (int i = 0; i < uiStack.size(); i++) {
            UiObject obj = (UiObject) uiStack.elementAt(i);
            obj.draw(g);
        }
        return true;
    }

    public /*static*/ boolean updateUiStack() {
        if (uiStack.isEmpty()) {
            return false;
        }
        // 返回栈顶对象但不将其移除
        UiObject obj = (UiObject) uiStack.peek();
        obj.update(keyState);
        return true;
    }

    /**
     * 游戏主场景部分
     */
    private Scene scene = null;

    /**
     * 游戏初始化
     */
    public void initGame() {
        if (scene == null) {
            scene = new Scene();
        }
        //地图剧中处理
        scene.setPostion((Const.WIDTH - scene.getWidth()) >> 1, (Const.HEIGHT - scene.getHeight() >> 1));
    }

    public void drawGame(Graphics g) {
    	g.setColor(0x5C892C);
    	g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
        scene.paint(g);
        
        drawFeeIcon(g);
        
        if(weacher_clipTime_index<=weather_clipTime[weather_clipTime.length-1])
        	weacher_clipTime_index++;
        else 
        	weacher_clipTime_index = 0;
    }

    public void updataGame() {
        scene.updata(keyState);
        //weather.UpdateWeather();
    }
    
    //下雨下雪的区间段；
    private int weacher_clipTime_index = 0;
    private final int []weather_clipTime ={0,1000,1500,2500,3000,4500};
    
    
    private void drawFeeIcon(Graphics g)
    {
    	int px =60;
    	int py = (Const.HEIGHT_HALF>>1)-24;
    	Game.getInstance().setFont(1, false, g);
    	for (int i = 0; i < 4; i++) {
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMD_ID_FEE_ICON), 
    				0, 77*i, 69, 77, 0, px, py+78*i, GraphicsUtil.LEFT_TOP);
    		int propId = Game.pm.props[i].getPropId();
    		int num = Game.getInstance().pm.getPropNumsById(propId);
    		int col = g.getColor();
    		g.setColor(0x000000);
    		TextView.showSingleLineText(g, String.valueOf(num), px+49, py+78*i+44,20,15,1);
    		g.setColor(col);
		}
    	int px2 = Const.WIDTH-130;
    	int py2 = (Const.HEIGHT_HALF>>1)-24;
    	for (int i = 4; i < 8; i++) {
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMD_ID_FEE_ICON), 
    				0, 77*i, 69, 77, 0, px2, py2+78*(i-4), GraphicsUtil.LEFT_TOP);
    		int propId = Game.pm.props[i].getPropId();
    		int num = Game.getInstance().pm.getPropNumsById(propId);
    		int col = g.getColor();
    		g.setColor(0x000000);
    		TextView.showSingleLineText(g, String.valueOf(num), px2+49, py2+78*(i-4)+44,20,15,1);
    		g.setColor(col);
		}
    	Game.getInstance().setDefaultFont(g);
    }
    
    /**
     * 获取场景
     * @return
     */
    public Scene getScene(){
    	return scene;
    } 
    
    public void setScene(Scene s){
    	scene = s;
    }

    /***
     * 返回主菜单
     */
    public void backMainMenu(){
    	//UserData.saveSceneData2Server();
    	
    	//退出游戏清空数据
    	setScene(null); 
    	
    	//上传积分到排行榜
    	
    	setState(ST_MENU, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    }
    /**
     * 
     */
    public void openMall(){
    	//商城界面暂时用Tip演示
    	push2Stack(new Mall());
    }
    /**
     * 打开商城()
     */
    public void openShop(){
    	pm.queryProps();
    	push2Stack(new Shop());
    }
    
    /**
     * 打开帮助
     */
    public void openHelp(){

    	push2Stack(new Help());
    }
    /**
     * 打开排行榜
     */
    public void openRank(){
    	
    	push2Stack(new Rank());
    }
    /**
     * 打开成就
     */
    public void openAchichment(){
    	push2Stack(new Achichment());
    }
    /**
     * 打开系统菜单
     */
    public void openSysMenu(){
    	push2Stack(new SysMenu());
    }
    /**
     *新手教学
     */
    public void openTeach()
    {
    	push2Stack(new Teach());
    }
    
    /**
     * 打开游戏结算对话框
     */
    public void openGameEnd(){
    	push2Stack(new GameEnd());
    }
    /**
     * 显示TIP
     * @param message
     */
    public void showTip(String message){
    	push2Stack(new Tip(message));
    }
    public void showLoading(){
    	push2Stack(new Tip("正在处理，请稍候",-1));
    }
    public static void push2Stack(UiObject ui){
    	synchronized (uiStack) { 
    			uiStack.push(ui);
		}
    }
    public static void popStack(){
    	synchronized (uiStack) {
    		if(uiStack.size()>0){
    		uiStack.pop();
    		}
		}
    }
    
    //==============================游戏家在状态==============
    //追赶动画
    //private SpriteX sprite;
    public void initGameLoading(){
    	//sprite = Resources.getSpritex(Resources.SPRITE_ani_loading);
    	loadinglevel = 0;
    	//加载猪叫
    	//musicMasterPIG.setSound(Const.PIG_WAV);
    	//musicMasterPIG.setLoopCount(1);
    	//加载恐龙叫
    	//musicMasterKL.setSound(Const.DINOSAUR_WAV);
    	//musicMasterKL.setLoopCount(1);
		
    }
    public void drawGameLoading(Graphics g){
    	g.setColor(0x000000);
    	g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
    	//g.drawString("游戏加载状态,点击确定进入场景选择", Const.WIDTH_HALF, Const.HEIGHT_HALF, GraphicsUtil.HCENTER_TOP);
    	g.setColor(0xFFFFFF);
    	g.drawString("载入中...", Const.WIDTH_HALF, 320-50, GraphicsUtil.HCENTER_TOP);
       	g.setColor(0xFFFFFF);
    	g.fillRect(Const.WIDTH_HALF - 220-2, 280-2-50, 440+4, 26+4);
    	g.setColor(0xFF0000);
    	g.fillRect(Const.WIDTH_HALF - 220, 280-50, loadinglevel, 26);
 
  
    	GraphicsUtil.drawImage(g, Const.WIDTH-220, Const.HEIGHT-150, GraphicsUtil.LEFT_TOP,Resources.IMG_ID_LOADING_TITLE);
    	//sprite.paint(g,Const.WIDTH_HALF - 220 +loadinglevel,280-50);
    	//sprite.nextFrame();
    }
    
    public void updataGameLoading(){
    	int loading_time = 440;
    	if(loadinglevel<loading_time){
    		loadinglevel+=15;
    		loadinglevel= loadinglevel>loading_time?loading_time:loadinglevel;
    	}else{
    		//载入数据
    		//CommonMain.doDataLoad();
    		setState(ST_GAME, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    	}
    }
    //==============================游戏场景选择==============
    private static final int postion[][]={{165,125},{365,125},{165,275},{365,275}};
    private int scene_choose_index = 0;
    public void initSceneChange(){
		Resources.loadImage(Resources.IMG_ID_BG);
		Resources.loadImage(Resources.IMG_ID_STATUS_01);
		
    }
    public void drawSceneChange(Graphics g){
    	
    	GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT_HALF, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_MENU_BG);
    	drawBackGround(g, "场景选择",false);
    	GraphicsUtil.drawImage(g, Const.WIDTH-220, Const.HEIGHT-150, GraphicsUtil.LEFT_TOP,Resources.IMG_ID_LOADING_TITLE);
    	int px, py;
    	for(int i = 0;i<4;i++){
    		py = postion[i][1];
    		px = postion[i][0];
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_SEASON), 111*i, 0, 111, 111, 0, px, py, 20);
    	}
    	py = postion[scene_choose_index][1];
		px = postion[scene_choose_index][0];
		GraphicsUtil.drawImage(g, px-50+(iClock%12), py+40, 20, Resources.IMG_ID_MAINMENU_ITEM_HAND);
		
		Util.draw3DString(g, "上下左右选择场景，确定键进入游戏", Const.WIDTH_HALF/8, Const.HEIGHT-55, 20, 0x0, 0xffffff);
  
    }
    
    public void updataSceneChange(){
    	//if(KeyBoard.isAnyKeyPressed()){
    		if(keyState.containsAndRemove(KeyCode.DOWN)){
    			scene_choose_index = scene_choose_index <3?++scene_choose_index:0;
    		}else if(keyState.containsAndRemove(KeyCode.UP)){
    			scene_choose_index = scene_choose_index >0?--scene_choose_index:3;
    		}else if(keyState.containsAndRemove(KeyCode.LEFT)){
    			scene_choose_index = scene_choose_index >0?--scene_choose_index:3;
    		}else if(keyState.containsAndRemove(KeyCode.RIGHT)){
    			scene_choose_index = scene_choose_index <3?++scene_choose_index:0;
    		}else if(keyState.containsAndRemove(KeyCode.OK)){
				// 释放主菜单图片
				Resources.releaseImage(Resources.IMG_ID_MENU_BG);
				// Resources.releaseImage(Resources.IMG_ID_MAINMENU_INTRO);
				Resources.releaseImage(Resources.IMG_ID_SEASON);
	
				setState(ST_GAME, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
	
				if (scene_choose_index == 0)	// 春天（下雨）
					Weather.SetWeather(Weather.Weather_Sunny);
				else if (scene_choose_index == 1)
					Weather.SetWeather(Weather.Weather_Rain);
				else if (scene_choose_index == 2)
					Weather.SetWeather(Weather.Weather_Sunny);
				else if (scene_choose_index == 3)// 冬天(下雪)
					Weather.SetWeather(Weather.Weather_Slow);
    		}else if(keyState.containsAndRemove(KeyCode.NUM0)||keyState.containsAndRemove(KeyCode.BACK))
    			backMainMenu();
    }
    public void setFont(int size, boolean isBold, Graphics g) {
		Font font = null;
		if(!isBold){
			if (size <= smallFontSize) {
				font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
			}
			else if (size <= mediumFontSize) {
				if (size >= smallFontSize+((mediumFontSize-smallFontSize)>>1)) {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
				}
				else {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);
				}
			}
			else if (size <= largeFontSize) {
				if (size >= mediumFontSize+((largeFontSize-mediumFontSize)>>1)) {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE);
				}
				else {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
				}
			}
			else {
				font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE);
			}
		}else{
			if (size <= smallFontSize) {
				font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL);
			}
			else if (size <= mediumFontSize) {
				if (size >= smallFontSize+((mediumFontSize-smallFontSize)>>1)) {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
				}
				else {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL);
				}
			}
			else if (size <= largeFontSize) {
				if (size >= mediumFontSize+((largeFontSize-mediumFontSize)>>1)) {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);
				}
				else {
					font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
				}
			}
			else {
				font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);
			}
		}
		g.setFont(font);
	}

	public void setDefaultFont(Graphics g) {
		setFont(20,false,g);
	}
}
