package binCalculator;
import java.util.*;

public class Truthtable 
{
	protected String expression = "";
	protected ArrayList<String> mainTable = new ArrayList<>();
	protected ArrayList<String> PDNFTable = new ArrayList<>();
	protected ArrayList<String> PKNFTable = new ArrayList<>();
	protected String answers = "";
	protected HashSet<String> unicVariables = new HashSet<>();
	protected String PDNF = "";
	protected String PKNF = "";
	
	public void Clear() 
	{
		this.mainTable.clear();
		this.PDNFTable.clear();
		this.PKNFTable.clear();
		this.answers = "";
		this.expression = "";
		this.unicVariables.clear();
		this.PDNF = "";
		this.PKNF = "";
	}
	
	
	public void byAnswers(String answers, String vars) {
		Clear();
		for(int i = 0; i < vars.length(); i++)
			this.unicVariables.add(vars.charAt(i) + "");
		this.answers = answers;
		BinaryNum allVars = new BinaryNum(0);
		this.mainTable.add(allVars.ConvertToSizedString((int)GeneralFunc.log2(answers.length()))); 
		for(int i = 0; i < answers.length() - 1; i++) {			
			allVars = BinaryNum.Sum(allVars, new BinaryNum(1)); 
			this.mainTable.add(allVars.ConvertToSizedString((int)GeneralFunc.log2(answers.length())));
		}
		calcPDNF();
		calcPKNF();

	}
	
	public void calcTruthTable(String logicalExpression) 
	{
		Clear();
		this.expression = logicalExpression;
		for(int i = 0; i < logicalExpression.length(); i++) 
			if(Character.isLetter(logicalExpression.charAt(i)))
				unicVariables.add(logicalExpression.charAt(i) + "");
		BinaryNum allVars = new BinaryNum(0);
		this.mainTable.add(allVars.ConvertToSizedString(unicVariables.size())); 
		for(int i = 0; i < Math.pow(2,unicVariables.size()) - 1; i++) {			
			allVars = BinaryNum.Sum(allVars, new BinaryNum(1)); 
			this.mainTable.add(allVars.ConvertToSizedString(unicVariables.size()));
		}
		calcAnswers(logicalExpression);
		calcPDNF();
		calcPKNF();
	}
	
	private void calcAnswers(String logicalExpression) 
	{
		String baseExpression = logicalExpression;
		for (int i = 0; i < mainTable.size() ; i++) 
		{
			int substitutedNumber = 0;
			for (String variable : unicVariables) 
			{		
				logicalExpression = logicalExpression.replace(variable.charAt(0), mainTable.get(i).charAt(substitutedNumber));
				substitutedNumber++;
			}
			this.answers = this.answers + BinaryCalculator.calc(logicalExpression);
			logicalExpression = baseExpression;
		}
	}
	
	private void calcPDNF() 
	{
		for(int i = 0; i < this.answers.length(); i++) 
		{
			if(answers.charAt(i) == '1') 
			{
				PDNFTable.add(this.mainTable.get(i));
				if(i > 1 && PDNF.length() != 0)
					this.PDNF = this.PDNF + "\\/";
				this.PDNF = this.PDNF + '(';
				int indexOfVariable = 0;
				for(String variable : unicVariables) 
				{
					if(this.mainTable.get(i).charAt(indexOfVariable) == '0') 
						this.PDNF = this.PDNF + "(-" + variable + ")";
					else
						this.PDNF = this.PDNF + variable;
					indexOfVariable++;
					if(indexOfVariable < mainTable.get(0).length())
						this.PDNF = this.PDNF + "/\\";
				}
				this.PDNF = this.PDNF + ')';
			}	
		}
	}
	
	
	private void calcPKNF() 
	{
		for(int i = 0; i < this.answers.length(); i++) 
		{
			if(answers.charAt(i) == '0') 
			{
				PKNFTable.add(this.mainTable.get(i));
				if(i > 1)
					this.PKNF = this.PKNF + "/\\";
				this.PKNF = this.PKNF + '(';
				int indexOfVariable = 0;
				for(String variable : unicVariables) 
				{
					if(this.mainTable.get(i).charAt(indexOfVariable) == '1') 
						this.PKNF = this.PKNF + "(-" + variable + ")";
					else
						this.PKNF = this.PKNF + variable;
					indexOfVariable++;
					if(indexOfVariable < mainTable.get(0).length())
						this.PKNF = this.PKNF + "\\/";
				}
				this.PKNF = this.PKNF + ')';
			}
		}
	}
		
}
