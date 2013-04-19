package com.tvgame.ui;



import java.util.Vector;

/**
 * 实现对字符串表达式进行简单的包括函数的运算。
 * 
 * @author 时锐
 */ 
public class AnalyzeCalculate {
	/**  
   * 定义运算符  
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
   * 四则运算表达式处理.  
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
  	
     /* gs = gs + "+0"; // 因为下面的计算是遇到符号才进行,所以多加入一个计算符号,不影响值.   
      String c1 = "";// 第一个运算符号   
      String c2 = "";// 第二个运算符号   
      String s1 = "";// 第一个运算数   
      String s2 = "";// 第二个运算数   
      String s3 = "";// 第三个运算数   

      int len = gs.length();   
      for (int i = 0; i < len; i++) {   
          String s = String.valueOf(gs.charAt(i));// 获得该位置字符并转换成字符串做比较   

          if (lc.contains(s)) { // 如果是运算符号   
              if (c1.length() == 0)// 如果第一个运算符号为空,加入   
                  c1 = s;   
              else if (c2.length() == 0) {// 否则,如果第二个运算符号为空,加入   
                  c2 = s;// 第二个运算符号   
                  if ("+".equals(c2) || "-".equals(c2)) {// 如果第二个运算符号级别低,那么进行计算   
                      s1 = _4zys(s1, c1, s2);// 第一个和第二个数计算   
                      c1 = c2;// 保存第二个运算符,其他为空   
                      c2 = "";   
                      s2 = "";   
                  }   
              } else {// 上述都保存过   
                  if ("+".equals(s) || "-".equals(s)) {// 如果第三个运算符级别低,进行运算   
                      s2 = _4zys(s2, c2, s3);// 先算第二三个数,保存至第二个   
                      s1 = _4zys(s1, c1, s2);// 再算第一二个,保存至第一个   
                      c1 = s;// 保存当前运算符,其他为空   
                      s2 = "";   
                      c2 = "";   
                      s3 = "";   
                  } else {// 如果第三个运算符级别高   
                      s2 = _4zys(s2, c2, s3);// 先算第二三个数,保存至第二个   
                      c2 = s;// 前面不动,保存运算符   
                      s3 = "";   
                  }   
              }   
          } else if (s1.length() > 0 && c1.length() > 0 && c2.length() == 0) {// 如果第一个数,第一个运算符已保存,第二个运算符未保存,保存第二个数   
              s2 += s;   
          } else if (c1.length() == 0) {// 如果没有运算符,保存第一个数   
              s1 += s;   
          } else if (s1.length() > 0 && s2.length() > 0 && c1.length() > 0  
                  && c2.length() > 0) {// 如果第一二个数和运算符都有,保存第三个数   
              s3 += s;   
          }   
      }        
      return Integer.valueOf(s1).intValue();  */ 
  }   

  /**  
   * 基本四则运算.  
   * @param c1  
   * 运算数1.  
   * @param s1  
   * 运算符(加减乘除).  
   * @param c2  
   * 运算数2.  
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
   * 公式计算.  
   * @param price  
   * @param expressions  
   *  传入参数票价以及计算公式.  
   * @return   
   * 返回票价.  
   */  
//  public Integer count(String price, String expressions) {   
//      // 验证表达式是否符合规范.   
//      String temp = "";   
//      // 获取票价后面字符.   
//      if (expressions.length() != (expressions.indexOf("X") + 1)) {   
//          temp = String.valueOf(expressions   
//                  .charAt(expressions.indexOf("X") + 1));   
//      }   
//      if (expressions.length() == (expressions.indexOf("X") + 1)   
//              || "+".equals(temp) || "-".equals(temp) || "*".equals(temp)   
//              || "/".equals(temp)) {   
//          // 将票价丢到表达式中去.   
//          expressions = expressions.replace("X", price);   
//          // 调用计算方法.   
//          Double b = szys(expressions);   
//          // 进行四舍五入.   
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

