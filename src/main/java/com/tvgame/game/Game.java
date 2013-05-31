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

    boolean isPause; 					// Ӧ�ô�����ͣ״̬
    static boolean isExit; 				// �˳�Ӧ��
    static boolean isRealLoad;
    public static boolean isNewGame = true;	// ����Ϸ
    public int lastState2;
    public static PropManager pm;
    public GameRanking[] rankList;
    public Graphics gs;
    
    //��Ч
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
    
    //������
    public Game(FitCrazyMIDlet midlet) {
    	super(midlet);
    	setRelease(false);
        lastState2 = ST_MENU;
        pm = new PropManager(this);
        setState(ST_MENU, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    }

    /**
     * ��ʼһ����Ϸ״̬����Դ�ͱ���
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
     * �ͷ�һ����Ϸ״̬����Դ
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
    
    //LOGO����
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
    	//g.drawString("��Ϸ������,���Ժ�...", Const.WIDTH_HALF, 320, GraphicsUtil.HCENTER_TOP);

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
    		//֪ͨ��������ʼ��Ϸ(����ط���Ҫ�ӳٷ�����ʧ��)
    		threadSleep(1000);
    		//CommonMain.doGameBegin();
    		
    	Resources.releaseImage(Resources.IMG_ID_CG_LOGO_01);
    	//Resources.releaseImage(Resources.IMG_ID_CG_LOGO_02);
    	Resources.releaseImage(Resources.IMG_ID_CG_LOGO_03);
    	
    	setState(ST_MENU, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    	}
    }
    
    //���˵�����
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
			case 0:  		//������Ϸ
				SaveGameRecord r = new SaveGameRecord(Game.getInstance());
				r.loadAttainment();
				boolean result = r.loadRecord();
				if(!result){
					/*PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					pt.setText("û����Ϸ��¼,�����¿�ʼ��Ϸ!");
					pt.popup();*/
					showTip("û����Ϸ��¼,�����¿�ʼ��Ϸ!");
					selectIndex = 1;
				}else{
					pm.queryProps();
					isNewGame = false;
					setState(ST_GAME_LOADING, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
					selectIndex = 0;
					clearMenu();
				}
				break;
			case 1:			//��ʼ��Ϸ
				 //���سɾ���Ϣ
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
				// ���а���Ϣ
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
				 //���سɾ���Ϣ
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
     * ���ƿ򱳾�
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
     * ���ư���(ȷ��)
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
    
    //�Ի���߿�չ����Ч��
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
     * ��ĳ��չ����ʽ���Ʊ߿�
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param mode 0-�������Һ���չ�� 1-�����������չ�� 2-��������չ�� 3-��������չ��
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
     * ���ƶԻ��߿�
     * @param g
     * @param x
     * @param y
     * @param w �ܵĿ��[32��������] ����96
     * @param h �ܵĸ߶�[32��������] ����96
     */
    public void drawBoader(Graphics g,int x,int y,int w,int h)
    {
    	//��������
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 0, 0, 32, 32, 0,x , y, GraphicsUtil.LEFT_TOP);
    	int col = (w-64)/32;//����2���߽ǿ�Ⱥ��м�������������ƽ�̶�����
    	for (int j = 1; j <= col; j++) {
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 32, 0, 32, 32, 0,x+32*j , y, GraphicsUtil.LEFT_TOP);
		}
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 64, 0, 32, 32, 0,x+w-32, y, GraphicsUtil.LEFT_TOP);
    	//�м�����
    	int row = (h-64)/32;//����2���߽ǿ�Ⱥ��м�������������ƽ�̶�����
    	for (int i = 0; i < row; i++) {
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 96, 0, 32, 32, 0,x , y+32+32*i, GraphicsUtil.LEFT_TOP);
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 160, 0, 32, 32, 0,x+w-32 , y+32+32*i, GraphicsUtil.LEFT_TOP);
		}
    	for (int i = 1; i <= row; i++) {
    		for (int j = 1; j <= col; j++) {
    			GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 128, 0, 32, 32, 0,x+32*j , y+32*i, GraphicsUtil.LEFT_TOP);
    		}
    	}
    	//�ײ�����
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 288, 0, 32, 32, 0,x , y+h-32, GraphicsUtil.LEFT_TOP);
    	for (int i = 1; i <=col; i++) {
    		GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 320, 0, 32, 32, 0,x+32*i , y+h-32, GraphicsUtil.LEFT_TOP);
		}
    	GraphicsUtil.drawRegion(g, Resources.loadImage(Resources.IMG_ID_DIALOG), 352, 0, 32, 32, 0,x+w-32, y+h-32, GraphicsUtil.LEFT_TOP);
    	g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
    }
 
    /**
     * ȫ�ּ�����
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

    // ��Ϸ״̬
    public static final int ST_LOGO         = 0;  	//��ϷLOGO
    public static final int ST_MENU         = 1;  	//���˵�
    public static final int ST_HELP         = 11; 	//����
    public static final int ST_GAME_LOADING = 12; 	//��Ϸ����չʾ����
    public static final int ST_SCENECHANGE  = 13; 	//����ѡ��
    //public static final int ST_TOP          = 14; //���а�
    public static final int ST_GAME         = 20; 	//����Ϸ
    public static final int ST_GAMEMENU     = 21; 	//��Ϸ��ͣ�˵�
    //public static final int ST_GAME_ACHIMENT= 22; //��Ϸ�гɾͽ���
    //public static final int ST_GAMEMENU_HELP = 23;//��Ϸ�а���
    
    public static int state = ST_MENU; 				//��Ϸ�ĵ�ǰ״̬
    private int prevState; 							//��Ϸ����һ��״̬ previously
    private int nextState; 							//��Ϸ����һ��״̬
    private Object[] stateArgs;
    static int stateTick;
    public static final int STFLAG_DROW_LOADING = 1;
    public static final int STFLAG_RLES_THIS = 1 << 1;
    public static final int STFLAG_INIT_NEXT = 1 << 2;
    public int stFlag;

    
    /**
     * @param nextState
     *            Ҫǰ������Ϸ״̬
     * @param isDrawLoading
     *            �Ƿ���ƽ�����Ϣ
     * @param isRelsCurState
     *            �Ƿ��ͷ��ϸ�״̬(��ǰ״̬)����Դ
     * @param isInitRes
     *            �Ƿ����³�ʼ��Դ
     * @param args
     *            �����Զ������
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
    boolean isLoadingFinish; 			// ��Դ�������
    public static boolean isLoading; 	// ������Դ����״̬
    boolean isDrawLoading; 				// ��ʾ���ؽ���
    boolean isReInitRes;
    int progress = 0; 					// ��Դ���ؽ���
    int progressIncrease = 1; 			// ����
    static final int MaxProgress = 265; // ���������Դ����֡��

    // ��ʼ��ϷLOADING״̬
    public void startLoading() {
        isLoadingFinish = false;
        isLoading = true;
    }

    // ����LOADING����
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

        if (isLoadingFinish) {// ������ʾ���
            isDrawLoading = false;
            isLoading = false;
            state = nextState;
            progress = MaxProgress;
        }
    }

    // ��ԴLOADING����
    public void drawLoading(Graphics g) {
    }
    
    // -------------------------------------------------------------StringUtil.java
    /**
     * ��ȡTXT�ļ�
     *
     * @param fileName
     *            ·��
     * @return ����TXT�еĻس��ŵ��ַ�����
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
     * ��ȡUTF8txt�ļ�
     * @param fileName ·��
     * @return �����ļ�������
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
     * UI ջ
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
        // ����ջ�����󵫲������Ƴ�
        UiObject obj = (UiObject) uiStack.peek();
        obj.update(keyState);
        return true;
    }

    /**
     * ��Ϸ����������
     */
    private Scene scene = null;

    /**
     * ��Ϸ��ʼ��
     */
    public void initGame() {
        if (scene == null) {
            scene = new Scene();
        }
        //��ͼ���д���
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
    
    //������ѩ������Σ�
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
     * ��ȡ����
     * @return
     */
    public Scene getScene(){
    	return scene;
    } 
    
    public void setScene(Scene s){
    	scene = s;
    }

    /***
     * �������˵�
     */
    public void backMainMenu(){
    	//UserData.saveSceneData2Server();
    	
    	//�˳���Ϸ�������
    	setScene(null); 
    	
    	//�ϴ����ֵ����а�
    	
    	setState(ST_MENU, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    }
    /**
     * 
     */
    public void openMall(){
    	//�̳ǽ�����ʱ��Tip��ʾ
    	push2Stack(new Mall());
    }
    /**
     * ���̳�()
     */
    public void openShop(){
    	pm.queryProps();
    	push2Stack(new Shop());
    }
    
    /**
     * �򿪰���
     */
    public void openHelp(){

    	push2Stack(new Help());
    }
    /**
     * �����а�
     */
    public void openRank(){
    	
    	push2Stack(new Rank());
    }
    /**
     * �򿪳ɾ�
     */
    public void openAchichment(){
    	push2Stack(new Achichment());
    }
    /**
     * ��ϵͳ�˵�
     */
    public void openSysMenu(){
    	push2Stack(new SysMenu());
    }
    /**
     *���ֽ�ѧ
     */
    public void openTeach()
    {
    	push2Stack(new Teach());
    }
    
    /**
     * ����Ϸ����Ի���
     */
    public void openGameEnd(){
    	push2Stack(new GameEnd());
    }
    /**
     * ��ʾTIP
     * @param message
     */
    public void showTip(String message){
    	push2Stack(new Tip(message));
    }
    public void showLoading(){
    	push2Stack(new Tip("���ڴ������Ժ�",-1));
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
    
    //==============================��Ϸ����״̬==============
    //׷�϶���
    //private SpriteX sprite;
    public void initGameLoading(){
    	//sprite = Resources.getSpritex(Resources.SPRITE_ani_loading);
    	loadinglevel = 0;
    	//�������
    	//musicMasterPIG.setSound(Const.PIG_WAV);
    	//musicMasterPIG.setLoopCount(1);
    	//���ؿ�����
    	//musicMasterKL.setSound(Const.DINOSAUR_WAV);
    	//musicMasterKL.setLoopCount(1);
		
    }
    public void drawGameLoading(Graphics g){
    	g.setColor(0x000000);
    	g.fillRect(0, 0, Const.WIDTH, Const.HEIGHT);
    	//g.drawString("��Ϸ����״̬,���ȷ�����볡��ѡ��", Const.WIDTH_HALF, Const.HEIGHT_HALF, GraphicsUtil.HCENTER_TOP);
    	g.setColor(0xFFFFFF);
    	g.drawString("������...", Const.WIDTH_HALF, 320-50, GraphicsUtil.HCENTER_TOP);
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
    		//��������
    		//CommonMain.doDataLoad();
    		setState(ST_GAME, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
    	}
    }
    //==============================��Ϸ����ѡ��==============
    private static final int postion[][]={{165,125},{365,125},{165,275},{365,275}};
    private int scene_choose_index = 0;
    public void initSceneChange(){
		Resources.loadImage(Resources.IMG_ID_BG);
		Resources.loadImage(Resources.IMG_ID_STATUS_01);
		
    }
    public void drawSceneChange(Graphics g){
    	
    	GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT_HALF, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_MENU_BG);
    	drawBackGround(g, "����ѡ��",false);
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
		
		Util.draw3DString(g, "��������ѡ�񳡾���ȷ����������Ϸ", Const.WIDTH_HALF/8, Const.HEIGHT-55, 20, 0x0, 0xffffff);
  
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
				// �ͷ����˵�ͼƬ
				Resources.releaseImage(Resources.IMG_ID_MENU_BG);
				// Resources.releaseImage(Resources.IMG_ID_MAINMENU_INTRO);
				Resources.releaseImage(Resources.IMG_ID_SEASON);
	
				setState(ST_GAME, STFLAG_INIT_NEXT | STFLAG_RLES_THIS, null);
	
				if (scene_choose_index == 0)	// ���죨���꣩
					Weather.SetWeather(Weather.Weather_Sunny);
				else if (scene_choose_index == 1)
					Weather.SetWeather(Weather.Weather_Rain);
				else if (scene_choose_index == 2)
					Weather.SetWeather(Weather.Weather_Sunny);
				else if (scene_choose_index == 3)// ����(��ѩ)
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
