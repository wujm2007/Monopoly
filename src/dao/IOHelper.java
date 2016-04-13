package dao;

import java.util.Scanner;

public class IOHelper {
	static Scanner sc;

	public static boolean InputYN(String msg) {
		sc = new Scanner(System.in);
		System.out.println(msg + " (Y/N)");
		System.out.print("> ");
		String input = sc.next();
		if (input.toUpperCase().equals("Y"))
			return true;
		else if (input.toUpperCase().equals("N"))
			return false;
		else {
			System.out.println("请输入Y或N。");
			return InputYN(msg);
		}
	}

	public static int InputInt(String msg) {
		sc = new Scanner(System.in);
		System.out.println(msg + ": ");
		System.out.print("> ");
		String strInput = sc.next();
		int input;
		try {
			input = Integer.parseInt(strInput);
		} catch (NumberFormatException e) {
			alert("请输入整数。");
			return InputInt(msg);
		}
		return input;
	}

	public static String[] InputStockOp() {
		sc = new Scanner(System.in);
		String[] rtn = new String[3];
		System.out.println("请输入操作码,输入q退出(如：b 1 10 //买入1号股票10股)");
		System.out.print("> ");
		String strInput = sc.next().toLowerCase();
		if (strInput.equals("q"))
			return null;
		if (strInput.equals("b") || strInput.equals("s"))
			rtn[0] = strInput;
		else {
			alert("请按照操作码格式输入。");
			return InputStockOp();
		}
		strInput = sc.next();
		int input = 0;
		try {
			input = Integer.parseInt(strInput);
			rtn[1] = String.valueOf(input);
		} catch (NumberFormatException e) {
			alert("请输入正确的股票代码。");
			return InputStockOp();
		}
		strInput = sc.next();
		try {
			input = Integer.parseInt(strInput);
			if (input <= 0) {
				alert("请输入正确的股数。");
				return InputStockOp();
			}
			rtn[2] = String.valueOf(input);
		} catch (NumberFormatException e) {
			alert("请输入正确的股数。");
			return InputStockOp();
		}
		return rtn;
	}

	public static void alert(String msg) {
		System.out.println(msg);
	}

	public static void showInfo(String msg) {
		System.out.println(msg);
	}
}
