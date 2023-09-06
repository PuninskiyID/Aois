#include<vector>
#include<string>
#include<random>
#include<iostream>
//#include "Truthtable.h"
#include <set>
#include <sstream>
#include <stack>
using namespace std;

class TruthTable
{
	vector < string > truthTable = {};
	set <char> plentyOfVariables;

	void FindVariables(set <char>& plentyOfVariables, string logicalFunc);
	bool CalcTruthTable(string logicalFunc);
	void Inverse(stack <char>& StakOfVariables, stack <char>& StakOfOperations);
	void Sum(stack <char>& StakOfVariables, stack <char>& StakOfOperations);
	void Mult(stack <char>& StakOfVariables, stack <char>& StakOfOperations);
	void Implication(stack <char>& StakOfVariables, stack <char>& StakOfOperations);
	void Equialense(stack <char>& StakOfVariables, stack <char>& StakOfOperations);
	bool CheckPriority(stack <char> StakOfOperations, char newOperator);
	void CalcBracket(stack <char>& StakOfVariables, stack <char>& StakOfOperations);
	bool getBool(stack <char>& StakOfVariables);
	void DoPrevOperation(stack <char>& StakOfVariables, stack <char>& StakOfOperations);
	void AddOperation(stack <char>& StakOfVariables, stack <char>& StakOfOperations, char operation, string logicalFunc, int index);
	string ReplaceVariables(string stringToReplace, string stringOfMeanings);
	string BinaryPlusOne(string inString);
	int calcInDec(string binNum);

public:
	string GetTruthtable();
	TruthTable();
	TruthTable(string logicalFunc);
	void SKNF();
	void SKNFinNumForm();
	void SDNF();
	void SDNFinNumForm();
};

string TruthTable::GetTruthtable()
{
	string output = "";
	for (int i = 0; i < this->truthTable.size(); i++)
		output += this->truthTable[i][this->truthTable[i].length() - 1];
	cout << output << endl;
	return output;
}

TruthTable::TruthTable()
{
}
TruthTable::TruthTable(string logicalFunc)
{

	string varOfMeaning = "";
	FindVariables(this->plentyOfVariables, logicalFunc);
	for (int i = 0; i < plentyOfVariables.size(); i++)
		varOfMeaning.push_back('0');
	for (int i = 0; i < pow(2, plentyOfVariables.size()); i++)
	{
		this->truthTable.push_back(varOfMeaning);
		varOfMeaning = BinaryPlusOne(varOfMeaning);
	}
	for (int i = 0; i < truthTable.size(); i++)
	{
		bool tmp = CalcTruthTable(ReplaceVariables(logicalFunc, truthTable[i]));
		if (tmp == 0)
			truthTable[i].push_back('0');
		else if (tmp == 1)
			truthTable[i].push_back('1');
	}

}
void TruthTable::FindVariables(set <char>& plentyOfVariables, string logicalFunc)
{
	for (int i = 0; i < logicalFunc.size(); i++)
		if (logicalFunc[i] > 64 && logicalFunc[i] < 91)
			plentyOfVariables.insert(logicalFunc[i]);
}
bool TruthTable::CheckPriority(stack<char> StakOfOperations, char newOperator)
{
	if (StakOfOperations.top() == '(')
		return true;
	if (newOperator == '+')
		if (StakOfOperations.top() != '-')
			return false;
	if (newOperator == '*')
		if (StakOfOperations.top() != '+' || StakOfOperations.top() != '-')
			return false;
	if (newOperator == '>')
		if (StakOfOperations.top() != '+' || StakOfOperations.top() != '*' || StakOfOperations.top() != '-')
			return false;
	if (newOperator == '=')
		if (StakOfOperations.top() != '+' || StakOfOperations.top() != '*' || StakOfOperations.top() != '>' || StakOfOperations.top() != '-')
			return false;
	return true;
}
void TruthTable::Inverse(stack<char>& StakOfVariables, stack<char>& StakOfOperations)
{
	bool variable = getBool(StakOfVariables);
	if (variable == true)
		StakOfVariables.push('f');
	else
		StakOfVariables.push('t');
	StakOfOperations.pop();
	return;
}
void TruthTable::Sum(stack <char>& StakOfVariables, stack <char>& StakOfOperations)
{
	bool firstVariable = getBool(StakOfVariables);
	bool secondVariable = getBool(StakOfVariables);
	if (firstVariable == true && secondVariable == true)
		StakOfVariables.push('t');
	else
		StakOfVariables.push('f');
	StakOfOperations.pop();
	return;
}
void TruthTable::Mult(stack<char>& StakOfVariables, stack<char>& StakOfOperations)
{
	bool firstVariable = getBool(StakOfVariables);
	bool secondVariable = getBool(StakOfVariables);
	if (firstVariable == false && secondVariable == false)
		StakOfVariables.push('f');
	else
		StakOfVariables.push('t');
	StakOfOperations.pop();
	return;
}
void TruthTable::Implication(stack<char>& StakOfVariables, stack<char>& StakOfOperations)
{
	bool secondVariable = getBool(StakOfVariables);
	bool firstVariable = getBool(StakOfVariables);
	if (firstVariable == true && secondVariable == false)
		StakOfVariables.push('f');
	else
		StakOfVariables.push('t');
	StakOfOperations.pop();
	return;
}
void TruthTable::Equialense(stack<char>& StakOfVariables, stack<char>& StakOfOperations)
{
	bool firstVariable = getBool(StakOfVariables);
	bool secondVariable = getBool(StakOfVariables);
	if (firstVariable == secondVariable)
		StakOfVariables.push('t');
	else
		StakOfVariables.push('f');
	StakOfOperations.pop();
	return;
}
void TruthTable::CalcBracket(stack<char>& StakOfVariables, stack<char>& StakOfOperations)
{
	while (StakOfOperations.top() != '(')
	{
		if (StakOfOperations.top() == '-')
			Inverse(StakOfVariables, StakOfOperations);
		else if (StakOfOperations.top() == '+')
			Sum(StakOfVariables, StakOfOperations);
		else if (StakOfOperations.top() == '*')
			Mult(StakOfVariables, StakOfOperations);
		else if (StakOfOperations.top() == '>')
			Implication(StakOfVariables, StakOfOperations);
		else if (StakOfOperations.top() == '=')
			Equialense(StakOfVariables, StakOfOperations);
	}
	StakOfOperations.pop();
}
bool TruthTable::CalcTruthTable(string logicalFunc)
{
	stack <char> StakOfVariables;
	stack <char> StakOfOperations;
	for (int i = 0; i < logicalFunc.size(); i++)
	{
		if (logicalFunc[i] == '(')
			StakOfOperations.push(logicalFunc[i]);
		else if (logicalFunc[i] == 't' || logicalFunc[i] == 'f')
			StakOfVariables.push(logicalFunc[i]);
		else if (logicalFunc[i] == '-' || logicalFunc[i] == '+' || logicalFunc[i] == '*' || logicalFunc[i] == '>' || logicalFunc[i] == '=')
			AddOperation(StakOfVariables, StakOfOperations, logicalFunc[i], logicalFunc, i);
		else if (logicalFunc[i] == ')')
			CalcBracket(StakOfVariables, StakOfOperations);
	}
	return getBool(StakOfVariables);
}
bool TruthTable::getBool(stack <char>& StakOfVariables)
{
	if (StakOfVariables.top() == 't')
	{
		StakOfVariables.pop();
		return true;
	}
	else
	{
		StakOfVariables.pop();
		return false;
	}
}
void TruthTable::DoPrevOperation(stack<char>& StakOfVariables, stack<char>& StakOfOperations)
{
	if (StakOfOperations.top() == '-')
		Inverse(StakOfVariables, StakOfOperations);
	else if (StakOfOperations.top() == '+')
		Sum(StakOfVariables, StakOfOperations);
	else if (StakOfOperations.top() == '*')
		Mult(StakOfVariables, StakOfOperations);
	else if (StakOfOperations.top() == '>')
		Implication(StakOfVariables, StakOfOperations);
	else if (StakOfOperations.top() == '=')
		Equialense(StakOfVariables, StakOfOperations);
}
void TruthTable::AddOperation(stack<char>& StakOfVariables, stack<char>& StakOfOperations, char operation, string logicalFunc, int i)
{
	if (CheckPriority(StakOfOperations, operation) == false)
	{
		DoPrevOperation(StakOfVariables, StakOfOperations);
		StakOfOperations.push(logicalFunc[i]);
	}
	else
		StakOfOperations.push(logicalFunc[i]);
}
string TruthTable::ReplaceVariables(string stringToReplace, string stringOfMeanings)
{
	vector <char> Variables;
	int numOfMeaning = 0;
	for (std::set<char>::iterator i = this->plentyOfVariables.begin(); i != this->plentyOfVariables.end(); ++i)
	{
		for (int j = 0; j < stringToReplace.size(); j++)
			if (stringToReplace[j] == *i)
			{
				if (stringOfMeanings[numOfMeaning] == '0')
					stringToReplace[j] = 'f';
				else if (stringOfMeanings[numOfMeaning] == '1')
					stringToReplace[j] = 't';
			}
		numOfMeaning++;
	}
	return stringToReplace;
}
string TruthTable::BinaryPlusOne(string inString)
{
	string output;
	bool addOne = 1;
	for (int i = inString.size() - 1; i >= 0; i--)
	{
		if (addOne == 1 && inString[i] == '1')
			inString[i] = '0';
		else if (addOne == 1 && inString[i] == '0')
		{
			inString[i] = '1';
			addOne = 0;
		}
	}
	output = inString;
	return output;
}
void TruthTable::SKNF()
{
	string output = "";
	for (int i = 0; i < truthTable.size(); i++)
	{
		if (truthTable[i].back() == '1')
		{
			if (output != "")
				output.push_back('*');
			output.push_back('(');
			int numOfMeaning = 0;
			for (std::set<char>::iterator j = this->plentyOfVariables.begin(); j != this->plentyOfVariables.end(); ++j)
			{
				if (truthTable[i][numOfMeaning] == '1')
					output.push_back(*j);
				else if (truthTable[i][numOfMeaning] == '0')
					output = output + "(-" + *j + ')';
				if (numOfMeaning < truthTable[i].size() - 2)
					output.push_back('+');
				numOfMeaning++;
			}
			output.push_back(')');
		}
	}
	cout << output << endl;
}
void TruthTable::SKNFinNumForm()
{
	string output = "";
	for (int i = 0; i < truthTable.size(); i++)
	{
		if (output == "")
			output = "*(";
		if (truthTable[i].back() == '1')
		{
			if (output != "*(")
				output += ',';
			output += to_string(calcInDec(truthTable[i]));

		}
		if (i == truthTable.size() - 1)
			output += ')';
	}
	cout << output << endl;
}
void TruthTable::SDNF()
{
	string output = "";
	for (int i = 0; i < truthTable.size(); i++)
	{
		if (truthTable[i].back() == '0')
		{
			if (output != "")
				output.push_back('+');
			output.push_back('(');
			int numOfMeaning = 0;
			for (std::set<char>::iterator j = this->plentyOfVariables.begin(); j != this->plentyOfVariables.end(); ++j)
			{
				if (truthTable[i][numOfMeaning] == '0')
					output.push_back(*j);
				else if (truthTable[i][numOfMeaning] == '1')
					output = output + "(-" + *j + ')';
				if (numOfMeaning < truthTable[i].size() - 2)
					output.push_back('*');
				numOfMeaning++;
			}
			output.push_back(')');
		}
	}
	cout << output << endl;
}
void TruthTable::SDNFinNumForm()
{
	string output = "";
	for (int i = 0; i < truthTable.size(); i++)
	{
		if (output == "")
			output = "+(";
		if (truthTable[i].back() == '0')
		{
			if (output != "+(")
				output += ',';
			output += to_string(calcInDec(truthTable[i]));

		}
		if (i == truthTable.size() - 1)
			output += ')';
	}
	cout << output << endl;
}
int TruthTable::calcInDec(string binNum)
{
	float koeff = 1;
	float buffer = 0;
	float output = 0;
	for (int i = binNum.size() - 2; i >= 0; i--)
	{
		buffer = binNum[i] - '0';
		output += buffer * koeff;
		koeff *= 2;
	}
	return output;
}





class Processor
{
private:
    vector<string> Memory;
    int _bitsCount;
    int _wordsCount;

    void FillMemory()
    {
        srand(time(NULL));
        for (int i = 0; i < _wordsCount; i++)
        {
            string newValue;
            for (int index = 0; index < _bitsCount; index++)
            {
                newValue += char(rand() % 2 + 48);
            }

            Memory.push_back(newValue);
        }
    }

    char Inverse(char incomeValue)
    {
        return incomeValue == '1' ? '0' : '1';
    }

    char CompareDigits(char first, char second)
    {
        if (first == second)
        {
            if (first == '1')
            {
                return '1';
            }
        }
        return '0';
    }

    int GetInt(char value)
    {
        return value == '1' ? 1 : 0;
    }

    char GreaterTriggerValue(char prevGreaterValue, char aValue, char sValue, char prevLowerValue)
    {
        char firstComparing = CompareDigits(Inverse(aValue), sValue);
        char secondComparing = CompareDigits(firstComparing, Inverse(prevLowerValue));
        if (prevGreaterValue == '1' || secondComparing == '1')
        {
            return '1';
        }
        else
        {
            return '0';
        }
    }

    char LowerTriggerValue(char prevLowerValue, char aValue, char sValue, char prevGreaterValue)
    {
        char firstComparing = CompareDigits(aValue, Inverse(sValue));
        char secondComparing = CompareDigits(firstComparing, Inverse(prevGreaterValue));
        if (prevLowerValue == '1' || secondComparing == '1')
        {
            return '1';
        }
        else
        {
            return '0';
        }
    }

    int Compare(string word, string compareWith)
    {
        char prevLowerValue = '0', prevGreaterValue = '0';
        for (int i = 0; i < _bitsCount; i++)
        {
            char greaterValue = GreaterTriggerValue(prevGreaterValue, compareWith[i], word[i], prevLowerValue);
            char lowerValue = LowerTriggerValue(prevLowerValue, compareWith[i], word[i], prevGreaterValue);
            prevLowerValue = lowerValue;
            prevGreaterValue = greaterValue;
        }

        if (GetInt(prevGreaterValue) > GetInt(prevLowerValue))
        {
            return 1;
        }
        else
        {
            if (GetInt(prevGreaterValue) < GetInt(prevLowerValue))
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }

    int FindMinWordPosition(vector<string> memory)
    {
        int iteration = memory.size() - 1;
        string minimalWord = memory[iteration];
        int minimalIndex = iteration;
        while (iteration >= 0)
        {
            int compareResult = Compare(memory[iteration], minimalWord);
            if (compareResult >= 0)
            {
                iteration -= 1;
                continue;
            }

            minimalWord = memory[iteration];
            minimalIndex = iteration;
            iteration -= 1;
        }

        return minimalIndex;
    }

    int FindMaxWordPosition(vector<string> memory)
    {
        int iteration = memory.size() - 1;
        string maximalWord = memory[iteration];
        int minimalIndex = iteration;
        while (iteration >= 0)
        {
            int compareResult = Compare(memory[iteration], maximalWord);
            if (compareResult <= 0)
            {
                iteration -= 1;
                continue;
            }

            maximalWord = memory[iteration];
            minimalIndex = iteration;
            iteration -= 1;
        }

        return minimalIndex;
    }

public:
    Processor(int wordsCount, int bitsCount)
    {
        this->_bitsCount = bitsCount;
        this->_wordsCount = wordsCount;
        FillMemory();
    }

    void DisplayMemory()
    {
        for (int index = 0; index < Memory.size(); index++)
        {
            cout << "Word number " << index + 1 << ':' << Memory[index] << endl;
        }
    }

    void SortMinToMax()
    {
        vector<string> newMemory = vector<string>(Memory.size());
        vector<string> existingMemory = Memory;
        for (int round = 0; round < Memory.size(); round++)
        {
            int index = FindMinWordPosition(existingMemory);
            string minimal = existingMemory[index];
            newMemory[round] = minimal;
            existingMemory.erase(existingMemory.begin() + index);
        }

        Memory = newMemory;
    }

    void SortMaxToMin()
    {
        SortMinToMax();
        vector<string> reversedMemory = Memory;
        reverse(reversedMemory.begin(), reversedMemory.end());
        Memory = reversedMemory;
    }

    void Sort()
    {
        SortMinToMax();
        DisplayMemory();
        SortMaxToMin();
    }

    void FindBetween(string minimal, string maximal) {
        vector<string> foundedWords;
        for (int i = 0; i < Memory.size(); i++) {
            string thisWord = Memory[i];
            if (Compare(thisWord, minimal) > 0 && Compare(thisWord, maximal) < 0)
            {
                foundedWords.push_back(thisWord);
            }
        }
        if (foundedWords.size() > 0) {
            cout << "Founded words: " << foundedWords[0] << endl;
            for (int index = 1; index < foundedWords.size(); index++) {
                cout << "               " << foundedWords[index] << endl;
            }
        }
        else
            cout << "No words founded!" << endl;
    }
    
     string BooleanFunctionSearch(string function)
     {
		 TruthTable tb(function);
         string comparingTo = tb.GetTruthtable();
         if (comparingTo.size() == _bitsCount)
         {
             for(int i = 0; i < Memory.size(); i++)
             {
                 int compareResult = Compare(Memory[i], comparingTo);
                 if (compareResult == 0)
                 {
					 cout << "               " << Memory[i] << endl;
                     return Memory[i];
                 }
             }
         }
		 cout << "No words founded!" << endl;
		 return"";
     }
};

int main() {

    Processor processor = Processor(8, 8);
    processor.DisplayMemory();
	cout << "----------------------" << endl;
    processor.Sort();
	cout << "----------------------" << endl;
    processor.DisplayMemory();
	cout << "----------------------" << endl;
    processor.FindBetween("00001111", "1111111");
	cout << "----------------------" << endl;
	processor.BooleanFunctionSearch("(A*(B*C))");
	processor.BooleanFunctionSearch("(A*(B+(!C)))");
	processor.BooleanFunctionSearch("(A+((!B)*(!C)))");
	processor.BooleanFunctionSearch("((!A)+((!B)+(!C)))");
	
	
    return 0;
}