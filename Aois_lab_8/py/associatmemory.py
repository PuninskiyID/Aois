from random import choice
import typing
import copy



class AssociatMemory:
    def __init__(self) -> None:
        self.size = 16
        self.memory = [[choice([0, 1]) for i in range(self.size)] for j in range(self.size)]
        self.memoryDiagonal = self.memory
        
    def PrintNormalFrom(self) -> None:
        if self.memoryDiagonal == self.memory:
            for i in self.memory:
                memoryRow = ' '.join([str(j) for j in i])
                print(memoryRow)
        else:
            memory = self.Normalize()
            for i in memory:
                memoryRow = ' '.join([str(j) for j in i])
                print(memoryRow)
        
    def Func1(self, columns: typing.List[int]) -> str:
        firstWord = self.ReadWord(columns[0])
        secondWord = self.ReadWord(columns[1])
        newWord = []
        for i in range(self.size):
            newWord.append(firstWord[i] and secondWord[i])
        self.SetWord(newWord, columns[2])
        return ' '.join([str(j) for j in newWord])
    
    def Func3(self, columns: typing.List[int]) -> str:
        newWord = self.ReadWord(columns[0])
        return ' '.join([str(j) for j in newWord])
    
    def Func12(self, columns: typing.List[int]) -> str:
        newWord = self.ReadWord(columns[0])
        newWord = list(map(lambda x: 0 if x==1 else 1, newWord))
        self.SetWord(newWord, columns[0])
        return ' '.join([str(j) for j in newWord])
    
    def Func14(self, columns: typing.List[int]) -> str:
        firstWord = self.ReadWord(columns[0])
        secondWord = self.ReadWord(columns[1])
        newWord = []
        for i in range(self.size):
            newWord.append(firstWord[i] and secondWord[i])
        newWord = list(map(lambda x: 0 if x==1 else 1, newWord))
        self.SetWord(newWord, columns[2])
        return ' '.join([str(j) for j in newWord])
    
    def SetWord(self, word: typing.List[int], column: int) -> None:
        self.Reverse()
        self.memoryDiagonal[column] = word[self.size-column:] + word[:self.size-column]
        self.Reverse()
    
    def ReadWord(self, column: int) -> typing.List[int]:
        self.Reverse()
        word = self.memoryDiagonal[column][column:] + self.memoryDiagonal[column][:column]
        self.Reverse()
        return word
    
    def ReceiveLine(self, column: int) -> typing.List[int]:
        memory = self.Normalize()
        return memory[column]
    
    def __str__(self) -> str:
        for i in self.memoryDiagonal:
            memoryRow = ' '.join([str(j) for j in i])
            print(memoryRow)   
        return ''
    
    def Reverse(self) -> None:
        self.memoryDiagonal = [[self.memoryDiagonal[i][j] for i in range(self.size)] for j in range(self.size)]
    
    def MakeDiagonal(self) -> None:
        self.Reverse()
        for i in range(self.size):
            self.memoryDiagonal[i] = self.memoryDiagonal[i][self.size-i:] + self.memoryDiagonal[i][:self.size-i]
        self.Reverse()
        
    def CompareWords(self, words: typing.List[int], flag: bool) -> typing.List[int]:
        for i in words:
            print(' '.join([str(j) for j in i]))
            word = i
            for j in words:
                if flag:
                    if self.Comparison(word, j):
                        word = j
                else:
                    if not self.Comparison(word, j):
                        word = j
        return word
    
    def Search(self, word: typing.List[int]) -> str:
        memory = self.Normalize()
        memory = [[memory[i][j] for i in range(self.size)] for j in range(self.size)]
        print(f'Inputed word: {word}')
        print('\n')
        lessThenInputWord = []
        moreThenInputWord = []

        for i in memory:
            if self.Comparison(word, i):
                moreThenInputWord.append(i)
            else:
                lessThenInputWord.append(i)

        print('Smaller then inputed word')
        biggestWord = self.CompareWords(lessThenInputWord, True)
        print('\n\n')
        print('Larger then inputed word')
        smallestWord = self.CompareWords(moreThenInputWord, False)
        print('Result: ')
        return ' '.join([str(i) for i in biggestWord]) + '\n' + ' '.join([str(i) for i in smallestWord])
    
    def Comparison(self, firstWord: typing.List[int], secondWord: typing.List[int]) -> bool:
        gVariable, lVariable = 0, 0
        prev_g_Variable, prev_l_Variable = 0, 0
        
        for i in range(len(firstWord)):
            gVariable = prev_g_Variable or (not firstWord[i] and secondWord[i] and not prev_l_Variable)
            lVariable = prev_l_Variable or (firstWord[i] and not secondWord[i] and not prev_g_Variable)
            prev_g_Variable, prev_l_Variable = gVariable, lVariable
            
        return gVariable

    def Remove(self, key):
        skey = self.FindHash(key)
        for idx, item in enumerate(self.table[skey]):
            if item[0] == key:
                del self.table[skey][idx]
                return
    
    def Normalize(self) -> typing.List[int]:
        normalForm = []
        self.Reverse()
        matrix = copy.deepcopy(self.memoryDiagonal)
        self.Reverse()
        for i in range(len(matrix)):
            normalForm.append(self.ReadWord(i))
        return [[normalForm[i][j] for i in range(self.size)] for j in range(self.size)]
    
    def ArifOp(self, mask: typing.List[int]) -> None:
        memory = self.Normalize()
        memory = [[memory[i][j] for i in range(self.size)] for j in range(self.size)]
        passValidationByMask = list(filter(lambda x: x[:3] == mask, memory))
        print(f'Mask: {"".join(str(i) for i in mask)}')
        print('Passed validation: ')
        newWords = []
        for i in passValidationByMask:
            memoryRow = ' '.join(str(j) for j in i)
            print(f'{memoryRow}, index - {memory.index(i)}')
            newWords.append((memory.index(i), self.Addict(i)))
        
        print('Words moded by arifmetic operation: ')
        for i in newWords:
            memoryRow = ' '.join(str(j) for j in i[1])
            print(memoryRow)
            self.SetWord(i[1], i[0])
    
    def Addict(self, word: typing.List[int]) -> typing.List[int]:
        A = word[3:7]
        B = word[7:11]
        S, dopBit=[], 0
        for j in range(1, 5):
            S.append(A[-j]+B[-j]+dopBit)
            if S[-1] == 3:
                S[-1] = 1
                dopBit = 1
            elif S[-1] == 2:
                S[-1] = 0
                dopBit = 1
            elif S[-1] == 1:
                S[-1] = 1
                dopBit = 0
            else:
                S[-1] = 0
        S.append(dopBit)
        S = list(reversed(S))
        return word[:11] + S