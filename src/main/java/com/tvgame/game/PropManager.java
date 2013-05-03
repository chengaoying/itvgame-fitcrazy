package com.tvgame.game;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.itvgame.model.Prop;
import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;

public class PropManager{
	
	private Game engine;
	public PlayerProp[] props ;
	
	public PropManager(Game e){
		this.engine = e;
	}
	
	/*查询玩家道具*/
	public void queryProps(){
		initProps(props);
		ServiceWrapper sw = engine.getServiceWrapper();
		OwnProp[] pps = sw.queryOwnPropList();
		if(pps==null){
			return;
		}
		for(int i=0;i<props.length;i++){
			for(int j=0;j<pps.length;j++){
				if(pps[j].getPropId()==props[i].getPropId()){
					props[i].setNums(pps[j].getCount());
				}
			}
		}
		
		printInfo();
	}
	
	public void printInfo(){
		for(int i=0;i<props.length;i++){
			System.out.println("道具ID=="+props[i].getPropId());
			System.out.println("道具数量=="+props[i].getNums());
		}
	}

	private void initProps(PlayerProp[] props2) {
		ServiceWrapper sw = engine.getServiceWrapper();
		Prop[] ps = sw.queryGamePropList();
		if(ps == null){
			return;
		}
		props = new PlayerProp[ps.length];
		System.out.println("创建道具数据并初始化道具信息,size:"+props.length);
		for(int i=0;i<ps.length;i++){
			for(int j=0;j<props.length;j++){
				PlayerProp prop = new PlayerProp();
				if(i==j){
					prop.setPropId(ps[i].getPropId());
					prop.setName(ps[i].getPropName());
					prop.setPrice(ps[i].getPrice());
					prop.setId(j);
					prop.setNums(0);
					prop.setDesc(ps[i].getDescription());
					prop.setFeeCode(ps[i].getFeeCode());
					props[j] = prop;
				}
			}
		}
		
		for(int m=0;m<props.length;m++){
			System.out.println("propId:"+props[m].getPropId()+", price:"+props[m].getPrice()+", name:"+props[m].getName());
		}
	}
	
	/*根据道具ID查询该道具数量*/
	public PlayerProp getPropById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i];
			}
		}
		return null;
	}
	
	public int getPropNumsById(int id){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==id){
				return props[i].getNums();
			}
		}
		return 0;
	}
	
	public int getPriceById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i].getPrice();
			}
		}
		return 0;
	}
	
	public String getNameById(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				return props[i].getName();
			}
		}
		return null;
	}
	
	public void addPropNum(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				props[i].setNums(props[i].getNums()+1);
			}
		}
	}
	public void reducePropNum(int propId){
		int len = props.length;
		for(int i=len-1;i>=0;i--){
			if(props[i].getPropId()==propId){
				props[i].setNums(props[i].getNums()-1);
			}
		}
	}
	
	public boolean buyProp(int propId, int propCount){
		PlayerProp pp = getPropById(propId);
		if (engine.getEngineService().getBalance() >= pp.getPrice()) {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.purchaseProp(propId, 1, "购买"+pp.getName());
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("购买"+pp.getName()+"成功");
				//Game.getInstance().showTip("购买"+pp.getName()+"成功");
				addPropNum(propId);
			}
			else {
			//	Game.getInstance().showTip("购买"+pp.getName()+"失败, 原因: "+sw.getServiceMessage());
				pt.setText("购买"+pp.getName()+"失败, 原因: "+sw.getServiceMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}else {
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText(engine.getEngineService().getExpendAmountUnit()+"不足,是否充值");
			if (pc.popup() == 0) {
			//Game.getInstance().showTip(engine.getEngineService().getExpendAmountUnit()+"不足,请充值");
				Recharge recharge = new Recharge(engine);
				recharge.recharge();
			}
			return false;
		}
	}
	
	/*同步道具*/
	public void sysProps(){
		int[] ids = new int[8];
		int[] counts = new int[8];
		for(int i=0;i<props.length;i++){
			ids[i] = props[i].getPropId();
			counts[i] = props[i].getNums();
		}
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.synProps(ids, counts);
		System.out.println("同步道具:"+sw.isServiceSuccessful());
		for(int i=0;i<props.length;i++){
			System.out.println("道具ID=="+props[i].getPropId());
			System.out.println("道具数量=="+props[i].getNums());
		}
	}
}
