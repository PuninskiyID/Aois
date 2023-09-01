package binCalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

abstract public class Minimalizer {
	
	static public String PDNFminByCalc(ArrayList<String> PDNF, HashSet<String> UnicVariables)
	{
		String variables = "";
		for(String variable : UnicVariables)
			variables += variable;
		ArrayList<ArrayList<String>> gluedPDNF = new ArrayList<>();
		ArrayList<ArrayList<String>> prevGluedPDNF = new ArrayList<>();
		gluedPDNF = Gluing(CreateLettersPDNFTable(PDNF, variables));
		do{
			prevGluedPDNF =  gluedPDNF;
			gluedPDNF = Gluing(gluedPDNF);
		}while(!prevGluedPDNF.equals(gluedPDNF));
		gluedPDNF = DeleteUnusefulParts(gluedPDNF);
		return CreateStringPDNF(gluedPDNF);
	}
	
	static public String PKNFminByCalc(ArrayList<String> PKNF, HashSet<String> UnicVariables)
	{
		String variables = "";
		for(String variable : UnicVariables)
			variables += variable;
		ArrayList<ArrayList<String>> gluedPKNF = new ArrayList<>();
		ArrayList<ArrayList<String>> prevGluedPKNF = new ArrayList<>();
		gluedPKNF = GluingPKNF(CreateLettersPKNFTable(PKNF, variables));
		do{
			prevGluedPKNF =  gluedPKNF;
			gluedPKNF = GluingPKNF(gluedPKNF);
		}while(!prevGluedPKNF.equals(gluedPKNF));
		gluedPKNF = DeleteUnusefulPartsPKNF(gluedPKNF);
		return CreateStringPKNF(gluedPKNF);
	}
	
	static public String PDNFminByTableCalc(ArrayList<String> PDNF, HashSet<String> UnicVariables) 
	{
		String variables = "";
		for(String variable : UnicVariables)
			variables += variable;
		ArrayList<ArrayList<String>> gluedPDNF = new ArrayList<>();
		ArrayList<ArrayList<String>> prevGluedPDNF = new ArrayList<>();
		gluedPDNF = Gluing(CreateLettersPDNFTable(PDNF, variables));
		do{
			prevGluedPDNF =  gluedPDNF;
			gluedPDNF = Gluing(gluedPDNF);
		}while(!prevGluedPDNF.equals(gluedPDNF));
		gluedPDNF = QuineMcclasskyTable(gluedPDNF, CreateLettersPDNFTable(PDNF, variables));
		return CreateStringPDNF(gluedPDNF);
	}
	
	static public String PKNFminByTableCalc(ArrayList<String> PKNF, HashSet<String> UnicVariables) 
	{
		String variables = "";
		for(String variable : UnicVariables)
			variables += variable;
		ArrayList<ArrayList<String>> gluedPKNF = new ArrayList<>();
		ArrayList<ArrayList<String>> prevGluedPKNF = new ArrayList<>();
		gluedPKNF = Gluing(CreateLettersPKNFTable(PKNF, variables));
		do{
			prevGluedPKNF =  gluedPKNF;
			gluedPKNF = Gluing(gluedPKNF);
		}while(!prevGluedPKNF.equals(gluedPKNF));
		gluedPKNF = QuineMcclasskyTable(gluedPKNF, CreateLettersPKNFTable(PKNF, variables));
		return CreateStringPKNF(gluedPKNF);
	}
	
	static public String PDNFminByTable(ArrayList<String> PDNF, HashSet<String> UnicVariables) 
	{
		String variables = "";
		for(String variable : UnicVariables)
			variables += variable;
		ArrayList<ArrayList<String>> modifiedPDNF = new ArrayList<>();
		modifiedPDNF = CreateLettersPDNFTable(PDNF, variables);
		modifiedPDNF = TableMethod(modifiedPDNF);
		return CreateStringPDNF(modifiedPDNF);
	}
	
	static public String PKNFminByTable(ArrayList<String> PKNF, HashSet<String> UnicVariables) 
	{
		String variables = "";
		for(String variable : UnicVariables)
			variables += variable;
		ArrayList<ArrayList<String>> modifiedPKNF = new ArrayList<>();
		modifiedPKNF = CreateLettersPKNFTable(PKNF, variables);
		modifiedPKNF = TableMethod(modifiedPKNF);
		return CreateStringPKNF(modifiedPKNF);
	}
	
	static private ArrayList<ArrayList<String>> CreateLettersPDNFTable(ArrayList<String> PDNF, String variables)
	{
		ArrayList<ArrayList<String>> LettersPDNFTable = new ArrayList<>();
		for(String bracket : PDNF) 
		{
			ArrayList<String> LetteredStr = new ArrayList<>(); 
			for(int i = 0; i < bracket.length(); i++) 
			{
				if(bracket.charAt(i) == '0')
					LetteredStr.add("" + '-' + variables.charAt(i));
				else
					LetteredStr.add("" + variables.charAt(i));
			}
			
			LettersPDNFTable.add(LetteredStr);
		}
		return LettersPDNFTable;
	}
	
	static private ArrayList<ArrayList<String>> CreateLettersPKNFTable(ArrayList<String> PKNF, String variables)
	{
		ArrayList<ArrayList<String>> LettersPKNFTable = new ArrayList<>();
		for(String bracket : PKNF) 
		{
			ArrayList<String> LetteredStr = new ArrayList<>(); 
			for(int i = 0; i < bracket.length(); i++) 
			{
				if(bracket.charAt(i) == '1')
					LetteredStr.add("" + '-' + variables.charAt(i));
				else
					LetteredStr.add("" + variables.charAt(i));
			}
			
			LettersPKNFTable.add(LetteredStr);
		}
		return LettersPKNFTable;
	}
	
	static private ArrayList<ArrayList<String>> Gluing(ArrayList<ArrayList<String>> LettersPDNFTable) 
	{
		ArrayList<ArrayList<String>> GluedPDNFTable = new ArrayList<>(); 
		for(int i = 0; i < LettersPDNFTable.size(); i++) {
			int capOfNonGlueble = 0;
			for(int j = 0; j < LettersPDNFTable.size(); j++)
				if(j!=i) 
					if(isGlueble(LettersPDNFTable.get(i), LettersPDNFTable.get(j))) 
					{
						GluedPDNFTable.add(GlueingTwoElements(LettersPDNFTable.get(i), LettersPDNFTable.get(j)));
						if(i < LettersPDNFTable.size() - 1) 
						{
							j = 0;
							i++;
							capOfNonGlueble = 0;
						}
						else
							break;
					}
					else
						capOfNonGlueble++;
			if(capOfNonGlueble >= LettersPDNFTable.size() - 2)
				GluedPDNFTable.add(LettersPDNFTable.get(i));
		}
		return CopyCheck(GluedPDNFTable); 
	}
	
	static private ArrayList<ArrayList<String>> GluingPKNF(ArrayList<ArrayList<String>> LettersPKNFTable) 
	{
		ArrayList<ArrayList<String>> GluedPKNFTable = new ArrayList<>(); 
		for(int i = 0; i < LettersPKNFTable.size(); i++) {
			int capOfNonGlueble = 0;
			for(int j = 0; j < LettersPKNFTable.size(); j++)
				if(j!=i) 
					if(isGlueble(LettersPKNFTable.get(i), LettersPKNFTable.get(j))) 
					{
						GluedPKNFTable.add(GlueingTwoElements(LettersPKNFTable.get(i), LettersPKNFTable.get(j)));
						if(i < LettersPKNFTable.size() - 1) 
						{
							j = 0;
							i++;
							capOfNonGlueble = 0;
						}
						else
							break;
					}
					else
						capOfNonGlueble++;
			if(capOfNonGlueble >= LettersPKNFTable.size() - 2)
				GluedPKNFTable.add(LettersPKNFTable.get(i));
		}
		return CopyCheck(GluedPKNFTable); 
	}
	
	static private boolean isGlueble(ArrayList<String> firstElements, ArrayList<String> secondElements) 
	{
		if(firstElements.size() != secondElements.size())
			return false;
		for(int i = 0; i < firstElements.size(); i++)
		{
			if(firstElements.get(i).charAt(firstElements.get(i).length() - 1) != secondElements.get(i).charAt(secondElements.get(i).length() - 1))
					return false;
		}
		int coincidences = 0;
		for(int i = 0; i < firstElements.size(); i++)
		{
			if(firstElements.get(i).equals(secondElements.get(i)))
					coincidences++;
		}
		if(coincidences >= firstElements.size() - 1)
			return true;
		else
			return false;
	} 
	
	static private ArrayList<String> GlueingTwoElements(ArrayList<String> firstElements, ArrayList<String> secondElements)
	{
		ArrayList<String> output = new ArrayList<>();
		for(int i = 0; i < firstElements.size(); i++)
			if(firstElements.get(i).equals(secondElements.get(i))) 
				output.add(firstElements.get(i));
		return output;
	}
	
	static private ArrayList<ArrayList<String>> CopyCheck(ArrayList<ArrayList<String>> LetteredPDNF)
	{
		HashSet<ArrayList<String>> SetBuffer = new HashSet<>();
		for(ArrayList<String> brackets : LetteredPDNF) 
			SetBuffer.add(brackets);
		ArrayList<ArrayList<String>> output = new ArrayList<>();
		for(ArrayList<String> brackets : SetBuffer)  
			output.add(brackets);
		return output;
	}
	
	static private String CreateStringPDNF(ArrayList<ArrayList<String>> LetteredPDNF) 
	{
		String output = "";
		int indexOfPDNFSize = 0;
		for(ArrayList<String> bracket: LetteredPDNF) 
		{
			if(LetteredPDNF.size() > 1)
				output += "(";
			int indexOfBracketSize = 0;
			for(String variable : bracket) 
			{
				if(variable.charAt(0) == '-')
					output += "(" + variable + ")";
				else
					output += variable;
				if(++indexOfBracketSize < bracket.size())
					output += "/\\";
			}
			if(LetteredPDNF.size() > 1)
				output += ")";
			if(++indexOfPDNFSize < LetteredPDNF.size())
				output += "\\/";
		}
		return output;
	}
	
	static private String CreateStringPKNF(ArrayList<ArrayList<String>> LetteredPKNF) 
	{
		String output = "";
		int indexOfPKNFSize = 0;
		for(ArrayList<String> bracket: LetteredPKNF) 
		{
			if(LetteredPKNF.size() > 1)
				output += "(";
			int indexOfBracketSize = 0;
			for(String variable : bracket) 
			{
				if(variable.charAt(0) == '-')
					output += "(" + variable + ")";
				else
					output += variable;
				if(++indexOfBracketSize < bracket.size())
					output += "\\/";
			}
			if(LetteredPKNF.size() > 1)
				output += ")";
			if(++indexOfPKNFSize < LetteredPKNF.size())
				output += "/\\";
		}
		return output;
	}
	
	static private ArrayList<ArrayList<String>> DeleteUnusefulParts(ArrayList<ArrayList<String>> gluedPDNF) 
	{
		ArrayList<ArrayList<String>> output = new ArrayList<>();
		for(int i = 0; i < gluedPDNF.size(); i++) 
		{
			ArrayList<ArrayList<String>> gluedPDNFwithNoElement = new ArrayList<>();
			ArrayList<ArrayList<String>> gluedPDNFcopy = CopyArrayOfArrays(gluedPDNF);
			for(int j = 0; j < gluedPDNFcopy.size(); j++) 
				if(i != j)
					gluedPDNFwithNoElement.add(gluedPDNFcopy.get(j));
			if(CheckPartImmutability(gluedPDNFcopy.get(i), gluedPDNFwithNoElement)) 	
				output.add(gluedPDNFcopy.get(i));	
			gluedPDNFwithNoElement = new ArrayList<>();
		}
		
		return output;
	}
	
	static private ArrayList<ArrayList<String>> DeleteUnusefulPartsPKNF(ArrayList<ArrayList<String>> gluedPKNF) 
	{
		ArrayList<ArrayList<String>> output = new ArrayList<>();
		for(int i = 0; i < gluedPKNF.size(); i++) 
		{
			ArrayList<ArrayList<String>> gluedPKNFwithNoElement = new ArrayList<>();
			ArrayList<ArrayList<String>> gluedPKNFcopy = CopyArrayOfArrays(gluedPKNF);
			for(int j = 0; j < gluedPKNFcopy.size(); j++) 
				if(i != j)
					gluedPKNFwithNoElement.add(gluedPKNFcopy.get(j));
			if(CheckPartImmutabilityPKNF(gluedPKNFcopy.get(i), gluedPKNFwithNoElement)) 	
				output.add(gluedPKNFcopy.get(i));	
			gluedPKNFwithNoElement = new ArrayList<>();
		}
		
		return output;
	}
	
	static private Boolean CheckPartImmutability(ArrayList<String> elementToCheck,ArrayList<ArrayList<String>> PDNFwithNoCheckEl)
	{
		String answer = "";
		BinaryNum BinIterator = new BinaryNum(0);
		ArrayList<ArrayList<String>> ArrayedElementToCheck = new ArrayList<>();
		ArrayedElementToCheck.add(elementToCheck);
		while(!answer.contains("1")) 
		{
			String numExpression = MakeNumExpression(BinIterator, CreateStringPDNF(ArrayedElementToCheck));
			answer = BinaryCalculator.calc(numExpression);
			if(!answer.contains("1"))
				BinIterator = BinaryNum.Sum(new BinaryNum (1), BinIterator);
		}
		PDNFwithNoCheckEl = ChangeKnownVarsToNums(BinIterator, elementToCheck, PDNFwithNoCheckEl);
		BinIterator = new BinaryNum(0);
		String answerOfFirst = BinaryCalculator.calc(MakeNumExpression(BinIterator, CreateStringPDNF(PDNFwithNoCheckEl)));
		answer = answerOfFirst;
		while(answerOfFirst.equals(answer) && BinIterator.dec < Math.pow(2, PDNFwithNoCheckEl.get(0).size())) 
		{
			String numExpression = MakeNumExpression(BinIterator, CreateStringPDNF(PDNFwithNoCheckEl));
			answer = BinaryCalculator.calc(numExpression);
			BinIterator = BinaryNum.Sum(new BinaryNum (1), BinIterator);
		}
		if(!isExpressionContainsLetters(PDNFwithNoCheckEl))
			return true;
		if(answerOfFirst.equals(answer))
			return false;
		else
			return true;
	}
	
	static private Boolean CheckPartImmutabilityPKNF(ArrayList<String> elementToCheck,ArrayList<ArrayList<String>> PKNFwithNoCheckEl)
	{
		String answer = "";
		BinaryNum BinIterator = new BinaryNum(0);
		ArrayList<ArrayList<String>> ArrayedElementToCheck = new ArrayList<>();
		ArrayedElementToCheck.add(elementToCheck);
		while(!answer.contains("0")) 
		{
			String numExpression = MakeNumExpression(BinIterator, CreateStringPKNF(ArrayedElementToCheck));
			answer = BinaryCalculator.calc(numExpression);
			if(!answer.contains("0"))
				BinIterator = BinaryNum.Sum(new BinaryNum (1), BinIterator);
		}
		PKNFwithNoCheckEl = ChangeKnownVarsToNums(BinIterator, elementToCheck, PKNFwithNoCheckEl);
		BinIterator = new BinaryNum(0);
		String answerOfFirst = BinaryCalculator.calc(MakeNumExpression(BinIterator, CreateStringPKNF(PKNFwithNoCheckEl)));
		answer = answerOfFirst;
		while(answerOfFirst.equals(answer) && BinIterator.dec < Math.pow(2, PKNFwithNoCheckEl.get(0).size())) 
		{
			String numExpression = MakeNumExpression(BinIterator, CreateStringPKNF(PKNFwithNoCheckEl));
			answer = BinaryCalculator.calc(numExpression);
			BinIterator = BinaryNum.Sum(new BinaryNum (1), BinIterator);
		}
		if(!isExpressionContainsLetters(PKNFwithNoCheckEl))
			return true;
		if(answerOfFirst.equals(answer))
			return false;
		else
			return true;
	}
	
	static private ArrayList<ArrayList<String>> ChangeKnownVarsToNums(BinaryNum iterator,ArrayList<String> elementToCheck,ArrayList<ArrayList<String>> PDNFwithNoCheckEl)
	{
		ArrayList<String> bracket = new ArrayList<>();
		String variable = "";
		for(int i = 0; i < PDNFwithNoCheckEl.size(); i++) 
		{
			bracket = PDNFwithNoCheckEl.get(i);
			for(int j = 0; j < bracket.size(); j++) 
			{
				variable = bracket.get(j);
				for(int k = 0; k < elementToCheck.size(); k++) 
					if(variable.charAt(variable.length() - 1) == elementToCheck.get(k).charAt(elementToCheck.get(k).length() - 1)) {
						variable = variable.replace(elementToCheck.get(k).charAt(elementToCheck.get(k).length() - 1),
								iterator.bin.charAt(iterator.bin.length() - 1 - k));
					}
				bracket.set(j, variable);
			}
			PDNFwithNoCheckEl.set(i, bracket);
		}			
			
		return PDNFwithNoCheckEl; 		
	}
	static private String MakeNumExpression(BinaryNum values, String expressionWithVariables) 
	{
		HashSet<Character> unicVariables = new HashSet<>();
		for(int i = 0; i < expressionWithVariables.length(); i++) 
			if(Character.isLetter(expressionWithVariables.charAt(i)))
				unicVariables.add(expressionWithVariables.charAt(i));
		int indexOfVariable = 1;
		for(Character variable : unicVariables) 
		{
			expressionWithVariables = expressionWithVariables.replace(variable, values.bin.charAt(values.bin.length() - indexOfVariable));
			indexOfVariable++;
		}
		return expressionWithVariables;
	}
	
	static private ArrayList<ArrayList<String>> CopyArrayOfArrays(ArrayList<ArrayList<String>> arrOfArrs)
	{
		ArrayList<ArrayList<String>> newArrayOfArrays = new ArrayList<>();
		for(ArrayList<String> ArrayToAdd : arrOfArrs) {
			ArrayList<String> newFstLvlArr = (ArrayList<String>)ArrayToAdd.clone();
			newArrayOfArrays.add(newFstLvlArr);
		}
		
		return newArrayOfArrays;
	}
	
	static private boolean isExpressionContainsLetters(ArrayList<ArrayList<String>> expression) 
	{
		for(ArrayList<String> line : expression) 
			for(String row : line) 
			{
				if(row.matches("^[a-zA-Z]*$"))
					return true;
			}
		return false;
		
	}
	
	static private ArrayList<ArrayList<String>> QuineMcclasskyTable(ArrayList<ArrayList<String>> gluedPDNF, ArrayList<ArrayList<String>> PDNF)
	{
		ArrayList<ArrayList<String>> TableOfInclusion = new ArrayList<>();
		for(ArrayList<String> gluedConstituent: gluedPDNF) 
		{
			ArrayList<String> lineOfTable = new ArrayList<>();
			for(ArrayList<String> constituent: PDNF) 
			{
				if(isIncluded(constituent, gluedConstituent)) 
					lineOfTable.add("1");
				else	
					lineOfTable.add("0");
			}
			TableOfInclusion.add(lineOfTable);
		}
		ArrayList<ArrayList<String>> output = FindCore(gluedPDNF, TableOfInclusion);	
		return output;
	}
	
	static private boolean isIncluded(ArrayList<String> constituent, ArrayList<String>  gluedConstituent) 
	{
		int capOfVariables = 0;
		for(String variable : gluedConstituent) 
		{
			if(constituent.contains(variable))
				capOfVariables++;
		}
		if(capOfVariables == gluedConstituent.size())
			return true;
		else
			return false;
	}
	
	static private ArrayList<ArrayList<String>> FindCore(ArrayList<ArrayList<String>> gluedPDNF, ArrayList<ArrayList<String>> tableOfInclusion)
	{
		ArrayList<ArrayList<String>> output = new ArrayList<>();
		for(int line = 0; line < tableOfInclusion.size(); line++) 
			for(int row = 0; row < tableOfInclusion.get(line).size(); row++) 
				if(tableOfInclusion.get(line).get(row) == "1" && isCore(tableOfInclusion, line, row)) 
				{
					 output.add(gluedPDNF.get(line));
					 break;
				}
		
		return output;
	}
	
	static private boolean isCore(ArrayList<ArrayList<String>> tableOfInclusion, int currentLine, int currentRow) 
	{
		int capOfZeros = 0;
		for(int line = 0; line < tableOfInclusion.size(); line++) 
		{
			if(tableOfInclusion.get(line).get(currentRow) == "0" && line != currentLine) 
				capOfZeros++;
		}
		if(capOfZeros != tableOfInclusion.size() - 1)
			return false;
		else
			return true;
	}
	
	static private ArrayList<ArrayList<String>> TableMethod (ArrayList<ArrayList<String>> PDNF)
	{
		ArrayList<ArrayList<String>> modifiedPDNF = PDNF;
		ArrayList<ArrayList<String>> prevModifiedPDNF = modifiedPDNF;
		do{
			prevModifiedPDNF =  modifiedPDNF;
			modifiedPDNF = Gluing(modifiedPDNF);
		}while(!prevModifiedPDNF.equals(modifiedPDNF));
		modifiedPDNF = DeleteUnusefulParts(modifiedPDNF);
		return modifiedPDNF;
	}
	
	
}
