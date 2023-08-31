package binCalculator;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

 abstract class BinaryCalculator 
{
	
	private static Stack<String> stackOfNumbers = new Stack<>();
	private static Stack<String> stackOfOperations = new Stack<>();
	
	private static Map<String,Integer> Operators;
	static {
		Map<String, Integer> Op = new HashMap<>(); 
		Op.put("/\\", 3);
		Op.put("\\/", 2);
		Op.put("^", 4);
		Op.put("->", 5);
		Op.put("-", 1);
		Op.put("=", 6);
		Op.put("(", 0);
		Op.put(")", 100);
		Operators = Collections.unmodifiableMap(Op);
	}
	
	private static ArrayList<String> ParseString (String str) 
	{
		ArrayList<String> parsedStrings = new ArrayList<>();
		String stringToAddString = "";
		int i = 0;
		while(i < str.length()) 
		{			
			while((i < str.length()) && Character.isDigit(str.charAt(i))) 
			{
				stringToAddString += str.charAt(i);
				i++;
			}
			if(stringToAddString != "")
				parsedStrings.add(stringToAddString);
			stringToAddString = "";
			while((i < str.length()) && (str.charAt(i) == ')' || str.charAt(i) == '(')) 
			{
				stringToAddString += str.charAt(i);
				i++;
				parsedStrings.add(stringToAddString);
				stringToAddString = "";	
			}
			while((i < str.length()) && (!Character.isDigit(str.charAt(i))) && (str.charAt(i) != '(') && (str.charAt(i) != ')')) 
			{
				stringToAddString += str.charAt(i);
				i++;
			}
			if(stringToAddString != "")
				parsedStrings.add(stringToAddString);
			stringToAddString = "";	
		}
		//System.out.println(parsedStrings);
		return parsedStrings;
	}; 
	
	public static String calc(String str) 
	{
		ArrayList<String> parsedStrings = new ArrayList<>();
		parsedStrings = ParseString(str);
	
		//System.out.println(parsedStrings);		
		
		String answer = Solver(parsedStrings);
		//System.out.println(answer);
		return answer;
	}
	
	private static String Solver(ArrayList<String> parsedStrings) 
	{
		
		for(int i = 0; i < parsedStrings.size(); i++) 
		{
			if(Character.isDigit(parsedStrings.get(i).charAt(0)))
				stackOfNumbers.add(parsedStrings.get(i));
			else if(parsedStrings.get(i).equals("(") || stackOfOperations.size() == 0 || CheckPriority(parsedStrings.get(i)))
					stackOfOperations.add(parsedStrings.get(i));
				else {
						SolveOperation();
						stackOfOperations.add(parsedStrings.get(i));
				}
					
		}
		while(stackOfOperations.size() != 0)
			SolveOperation();
		return stackOfNumbers.lastElement();
	}
	
	private static boolean CheckPriority(String operation) 
	{
		if(stackOfOperations.lastElement().equals(")"))       
			return false;
		if(Operators.get(operation) > Operators.get(stackOfOperations.lastElement()))
			return true;
		else
			return false;
	}

	private static void SolveOperation() 
	{
		String TopOperation = stackOfOperations.pop();
		if(TopOperation.equals(")")) {
			while(!stackOfOperations.lastElement().equals("("))
				SolveOperation();
			stackOfOperations.pop();
		}
		else if(TopOperation.equals("/\\")) 
			stackOfNumbers.add(""+Сonjunction(stackOfNumbers.pop(),stackOfNumbers.pop()));
		else if(TopOperation.equals("\\/")) 
			stackOfNumbers.add(""+Disjunction(stackOfNumbers.pop(),stackOfNumbers.pop()));
		else if(TopOperation.equals("^")) 
			stackOfNumbers.add(""+Xor(stackOfNumbers.pop(),stackOfNumbers.pop()));
		else if(TopOperation.equals("->")) 
			stackOfNumbers.add(""+Implication(stackOfNumbers.pop(),stackOfNumbers.pop()));
		else if(TopOperation.equals("=")) 
			stackOfNumbers.add(""+Equivalence(stackOfNumbers.pop(),stackOfNumbers.pop()));
		else if(TopOperation.equals("-")) 
			stackOfNumbers.add(""+Negation(stackOfNumbers.pop()));
		
		
	}
	private static String Implication(String secNum, String fstNum) 
	{
		if(fstNum.equals("1") && secNum.equals("0"))
			return "0";
		else
			return "1";	
	}
	
	private static String Сonjunction(String fstNum, String secNum) //***************
	{
		if(fstNum.equals("1") && secNum.equals("1"))
			return "1";
		else
			return "0";	
	}
	
	private static String Disjunction(String fstNum, String secNum) 
	{
		if(fstNum.equals("1") || secNum.equals("1"))
			return "1";
		else
			return "0";		
	}
	
	private static String Equivalence(String fstNum, String secNum) 
	{
		if(fstNum.equals(secNum))
			return "1";
		else
			return "0";				
	}
	
	private static String Negation(String fstNum) 
	{
		if(fstNum.equals("1"))
			return "0";
		else
			return "1";
	}
	
	private static String Xor(String fstNum, String secNum) 
	{
		if(fstNum.equals("1") && secNum.equals("0")||fstNum.equals("0") && secNum.equals("1"))
			return "1";
		else
			return "0";
	}
	
}
