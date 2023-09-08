from associatmemory import AssociatMemory


def main():



    memory = AssociatMemory()
    print('NormForm: ')
    memory.PrintNormalFrom()
    print('\n\n\n')
    
    memory.MakeDiagonal()
    
    print('DiagForm: ')
    print(memory)
    print('\n\n\n')

    
    
    user_input = int(input('Input index of word : '))
    print(' '.join([str(i) for i in memory.ReadWord(user_input)]))
    print('\n\n\n')

    print("---------------------------------------------------")
    
    user_input = int(input('Input index of line: '))
    print(' '.join([str(i) for i in memory.ReceiveLine(user_input)]))
    print('\n\n\n')

    print("---------------------------------------------------")
    
    print('Func: ')
    print(f'Func 1 for 1, 2 and 3 words: {memory.Func1([1, 2, 3])}')
    print(f'Func 3 for 1 word: {memory.Func3([1, 2])}')
    print(f'Func 12 for 4 word: {memory.Func12([4, 2])}')
    print(f'Func 14 for 1, 2 and 5 words: {memory.Func14([1, 2, 5])}')
    print('\n\n\n')
    
    print('DiagForm: ')
    print(memory)
    print('\n\n')
    print('NormForm: ')
    memory.PrintNormalFrom()
    print('\n\n\n')
    
    print("---------------------------------------------------")

    print('Arifmetik operations: ')
    memory.ArifOp([0, 0, 1])
    print('\n')
    
    print('DiagForm: ')
    print(memory)
    print('\n\n')
    print('NormForm: ')
    memory.PrintNormalFrom()
    print('\n\n\n')
    



    print("---------------------------------------------------")

    print('Search: ')
    print(memory.Search([0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0 ,0]))
    
    
    
if __name__ == '__main__':
    main()