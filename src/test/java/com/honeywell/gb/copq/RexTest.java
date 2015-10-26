package com.honeywell.gb.copq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RexTest {
	public static void main(String[] args) {
	  Pattern pattern = Pattern.compile("[0-1][0-9][0-3][0-9]-[0-1][0-9][0-3][0-9]-sh");
	  Matcher matcher = pattern.matcher("0727-0731-SH");
	  boolean b= matcher.matches();
	  //当条件满足时，将返回true，否则返回false
	  System.out.println(b);
	}
}
