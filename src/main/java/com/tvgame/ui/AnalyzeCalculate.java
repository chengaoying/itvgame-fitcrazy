package com.tvgame.ui;



import java.util.Vector;

/**
 * ʵ�ֶ��ַ������ʽ���м򵥵İ������������㡣
 * 
 * @author ʱ��
 */ 
public class AnalyzeCalculate {
	/**  
   * ���������  
   */  
	private static Parser p = new Parser();
  public static Vector lc = new Vector();   
  static {   
      lc.addElement("+");   
      lc.addElement("-");   
      lc.addElement("*");   
      lc.addElement("/");   
  }   

 
  
  public int compute2(String gs){
//  	try {
  		return compute(gs);
//		}
//		catch (Exception e) {
//			System.err.println(gs+e);
//			return -1;
//		}
  	
  }
  
  /**  
   * ����������ʽ����.  
   * @param str  
   * @return String  
   */  
  public int compute(String gs) { 
  	try {
  		return (int)p.entry(gs);
		}
		catch (Exception e) {
			return -1;
		}
  	
     /* gs = gs + "+0"; // ��Ϊ����ļ������������ŲŽ���,���Զ����һ���������,��Ӱ��ֵ.   
      String c1 = "";// ��һ���������   
      String c2 = "";// �ڶ����������   
      String s1 = "";// ��һ��������   
      String s2 = "";// �ڶ���������   
      String s3 = "";// ������������   

      int len = gs.length();   
      for (int i = 0; i < len; i++) {   
          String s = String.valueOf(gs.charAt(i));// ��ø�λ���ַ���ת�����ַ������Ƚ�   

          if (lc.contains(s)) { // ������������   
              if (c1.length() == 0)// �����һ���������Ϊ��,����   
                  c1 = s;   
              else if (c2.length() == 0) {// ����,����ڶ����������Ϊ��,����   
                  c2 = s;// �ڶ����������   
                  if ("+".equals(c2) || "-".equals(c2)) {// ����ڶ���������ż����,��ô���м���   
                      s1 = _4zys(s1, c1, s2);// ��һ���͵ڶ���������   
                      c1 = c2;// ����ڶ��������,����Ϊ��   
                      c2 = "";   
                      s2 = "";   
                  }   
              } else {// �����������   
                  if ("+".equals(s) || "-".equals(s)) {// �������������������,��������   
                      s2 = _4zys(s2, c2, s3);// ����ڶ�������,�������ڶ���   
                      s1 = _4zys(s1, c1, s2);// �����һ����,��������һ��   
                      c1 = s;// ���浱ǰ�����,����Ϊ��   
                      s2 = "";   
                      c2 = "";   
                      s3 = "";   
                  } else {// �������������������   
                      s2 = _4zys(s2, c2, s3);// ����ڶ�������,�������ڶ���   
                      c2 = s;// ǰ�治��,���������   
                      s3 = "";   
                  }   
              }   
          } else if (s1.length() > 0 && c1.length() > 0 && c2.length() == 0) {// �����һ����,��һ��������ѱ���,�ڶ��������δ����,����ڶ�����   
              s2 += s;   
          } else if (c1.length() == 0) {// ���û�������,�����һ����   
              s1 += s;   
          } else if (s1.length() > 0 && s2.length() > 0 && c1.length() > 0  
                  && c2.length() > 0) {// �����һ�����������������,�����������   
              s3 += s;   
          }   
      }        
      return Integer.valueOf(s1).intValue();  */ 
  }   

  /**  
   * ������������.  
   * @param c1  
   * ������1.  
   * @param s1  
   * �����(�Ӽ��˳�).  
   * @param c2  
   * ������2.  
   * @return String  
   */  
  public String _4zys(String c1, String s1, String c2) {   
      String reval = "0";   
      try {   
          int ln = Integer.valueOf(c1).intValue();   
          int rn = Double.valueOf(c2).intValue();   
          if ("+".equals(s1)) {   
              return (ln + rn) + "";   
          } else if ("-".equals(s1)) {   
              return (ln - rn) + "";   
          } else if ("*".equals(s1)) {   
              return (ln * rn) + "";   
          } else if ("/".equals(s1)) {   
              if (rn == 0)   
                  return reval;   
              else  
                  return (ln / rn) + "";   
          }   
      } catch (Exception e) {   
      } finally {   
      }   

      return reval;   
  }   

  /**  
   * ��ʽ����.  
   * @param price  
   * @param expressions  
   *  �������Ʊ���Լ����㹫ʽ.  
   * @return   
   * ����Ʊ��.  
   */  
//  public Integer count(String price, String expressions) {   
//      // ��֤���ʽ�Ƿ���Ϲ淶.   
//      String temp = "";   
//      // ��ȡƱ�ۺ����ַ�.   
//      if (expressions.length() != (expressions.indexOf("X") + 1)) {   
//          temp = String.valueOf(expressions   
//                  .charAt(expressions.indexOf("X") + 1));   
//      }   
//      if (expressions.length() == (expressions.indexOf("X") + 1)   
//              || "+".equals(temp) || "-".equals(temp) || "*".equals(temp)   
//              || "/".equals(temp)) {   
//          // ��Ʊ�۶������ʽ��ȥ.   
//          expressions = expressions.replace("X", price);   
//          // ���ü��㷽��.   
//          Double b = szys(expressions);   
//          // ������������.   
//          int c = Integer.parseInt(b.toString().substring(0,   
//                  b.toString().indexOf(".")));   
//          int e = (Integer.parseInt(b.toString().substring(   
//                  b.toString().indexOf(".") + 1,   
//                  b.toString().indexOf(".") + 2)) > 4) ? 1 : 0;   
//          return c + e;   
//      } else {   
//
//      }   
//      return new Integer(0);   
//  }
	
}

