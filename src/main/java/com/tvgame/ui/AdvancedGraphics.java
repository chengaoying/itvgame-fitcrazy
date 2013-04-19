package com.tvgame.ui;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.tvgame.constant.Const;
import com.tvgame.game.Game;

/**
 *
 * @author Administrator
 */
public class AdvancedGraphics {

    private Graphics g = null;
    
    private static AdvancedGraphics ag = null;
    
    private int clipX,clipY,clipW,clipH;

    private AdvancedGraphics(Graphics g) {
        this.g = g;
    }

    public static AdvancedGraphics getInstance(Graphics g) {
//        if (ag == null) {
            ag = new AdvancedGraphics(g);
//        }
        return ag;
    }
    
    public void drawString(String str,int x,int y,int anchor){
        Font font = Const.font;
        int w = font.stringWidth(str);
        int h = Const.FONT_H;
       
        if(checkClip(x,y,w,h,anchor)){
            g.drawString(str, x, y, anchor);
        }
    }
    
    public void drawLine(int x1,int y1,int x2,int y2){
       g.drawLine(x1, y1, x2, y2);
    }
    
    public void drawImage(Image img,int x,int y,int anchor){
        g.drawImage(img, x, y, anchor);
    }
    
    /*
     * 检测绘制的内容是否在指定区域内
     */  
    private boolean checkClip(int x, int y, int w, int h, int anchor) {
//                int anchorHC = anchor & Graphics.HCENTER;
//        int anchorVC = anchor & anchor & Graphics.VCENTER;
        int anchorL = anchor & Graphics.LEFT;
        //        int anchorR  = anchor & Graphics.RIGHT;
        int anchorT = anchor & Graphics.TOP;
//                int anchorB  = anchor & Graphics.BOTTOM;
        int xl,
         yt,
         xr,
         yb;
        if (anchorL == Graphics.LEFT) {
            xl = x;
            xr = x + w;
        } else {
            xr = x;
            xl = xr - w;
        }
        if (anchorT == Graphics.TOP) {
            yt = y;
            yb = y + h;
        } else {
            yb = y;
            yt = y - h;
        }
        
        clipX = g.getClipX();
        clipY = g.getClipY();
        clipW = g.getClipWidth();
        clipH = g.getClipHeight();
        
        return true;
    }
    
    
}
