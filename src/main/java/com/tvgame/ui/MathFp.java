package com.tvgame.ui;


public class MathFp {
	public static int _fbits = 24;

	private static int _digits = 8;

	private static long _one = 0x1000000L;;

	public static long _fmask = 0xffffffL;

	public static int MATH_mul = 100000;//100000000

	private static long _flt = 0L;

	private static long e[] = { _one, 0x2b7e151L, 0x763992eL, 0x1415e5bfL,
		0x3699205cL };

	public static long PI = 0x3243f6aL;

	public static long E = e[1];

	public static final long MAX_VALUE = 0x7fffffffffffffffL;

	public static final long MIN_VALUE = 0x8000000000000001L;

	public static long toFP(long l) {
		return l << _fbits;
	}

	public static long max(long l, long l1) {
		return l >= l1 ? l : l1;
	}

	public static long min(long l, long l1) {
		return l1 >= l ? l : l1;
	}

	public static long sin(long FPradian) {
		long l1 = mul(FPradian, div(toFP(180L), PI));
		l1 %= toFP(360L);
		if (l1 < 0L)
			l1 = toFP(360L) + l1;
		long l2 = l1;
		if (l1 >= toFP(90L) && l1 < toFP(270L))
			l2 = toFP(180L) - l1;
		else if (l1 >= toFP(270L) && l1 < toFP(360L))
			l2 = -(toFP(360L) - l1);
		long l3 = l2 / 90L;
		long l4 = mul(l3, l3);
		long l5 = mul(mul(mul(mul(0xfffffffffffee21aL >> (int) _flt, l4)
				+ (0x14594dL >> (int) _flt), l4)
				- (0xa55b13L >> (int) _flt), l4)
				+ (long) (0x1921f9c >> (int) _flt), l3);
		return l5;
	}

	public static long getFPRadian(long FPangle) {//角度转弧度
		return div(mul(FPangle, PI), toFP(180));
	}

	public static long cos(long FPradian) {
		return sin(PI / 2L - FPradian);
	}

	public static long tan(long l) {
		return div(sin(l), cos(l));
	}

	public static long round(long l, int i) {
		long l1 = 10L;
		for (int j = 0; j < i; j++)
			l1 *= 10L;
		l1 = div(toFP(5L), toFP(l1));
		if (l < 0L)
			l1 = -l1;
		return l + l1;
	}

	//  x/=y 
	public static long div(long x, long y) {
		boolean flag = false;
		int i = _fbits;
		if (y == _one)
			return x;
		if ((y & _fmask) == 0L)
			return x / (y >> i);
		if (x < 0L && y > 0L || x > 0L && y < 0L)
			flag = true;
		if (x < 0L)
			x = -x;
		if (y < 0L)
			y = -y;
		for (; max(x, y) >= 1L << 63 - i; i--) {
			x >>= 1;
			y >>= 1;
		}
		long l2 = (x << i) / y << _fbits - i;
		return flag ? -l2 : l2;
	}

	//  x+=y
	public static long add(long x, long y) {
		return x + y;
	}

	//    x-=y
	public static long sub(long x, long y) {
		return x - y;
	}

	//    |x|
	public static long abs(long x) {
		if (x < 0L)
			return -x;
		else
			return x;
	}

	//  x*=y 
	public static long mul(long x, long y) {
		boolean flag = false;
		int i = _fbits;
		long l2 = _fmask;
		if ((x & l2) == 0L)
			return (x >> i) * y;
		if ((y & l2) == 0L)
			return x * (y >> i);
		if (x < 0L && y > 0L || x > 0L && y < 0L)
			flag = true;
		if (x < 0L)
			x = -x;
		if (y < 0L)
			y = -y;
		for (; max(x, y) >= 1L << 63 - i; i--) {
			x >>= 1;
			y >>= 1;
			l2 >>= 1;
		}
		long l3 = (x >> i) * (y >> i) << i;
		long l4 = (x & l2) * (y & l2) >> i;
		l4 += (x & ~l2) * (y & l2) >> i;
		l3 = l3 + l4 + ((x & l2) * (y & ~l2) >> i) << _fbits - i;
		if (l3 < 0L)
			throw new ArithmeticException("Overflow");
		else
			return flag ? -l3 : l3;
	}

	public static long toFP(String s) {
		int i = 0;
		if (s.charAt(0) == '-')
			i = 1;
		String s1 = "-1";
		int j = s.indexOf(46);
		if (j >= 0) {
			for (s1 = s.substring(j + 1, s.length()); s1.length() < _digits; s1 = s1
			+ "0")
				;
			if (s1.length() > _digits)
				s1 = s1.substring(0, _digits);
		} else {
			j = s.length();
		}
		long l = 0L;
		if (i != j)
			l = Long.parseLong(s.substring(i, j));
		long l1 = Long.parseLong(s1) + 1L;
		long l2 = (l << _fbits) + (l1 << _fbits) / MATH_mul;
		if (i == 1)
			l2 = -l2;
		return l2;
	}

	public static String toString(long x) {
		boolean flag = false;
		if (x < 0L) {
			flag = true;
			x = -x;
		}
		long l1 = x >> _fbits;
		long l2 = MATH_mul * (x & _fmask) >> _fbits;
		String s;
		for (s = Long.toString(l2); s.length() < _digits; s = "0" + s)
			;
		return (flag ? "-" : "") + Long.toString(l1) + "." + s;
	}

	public static int toLong(long x) {
		if (x < 0L)
			return -(int) (round(-x, 0) >> _fbits);
		else
			return (int) round(x, 0) >> _fbits;
	}

	public static int toInt(long x) {
		return (int) toLong(x);
	}

	public static int toIntMul(long x) {//小数被放大后值
		long r = mul(x, MATH_mul);
		return (int) r;
	}
}
