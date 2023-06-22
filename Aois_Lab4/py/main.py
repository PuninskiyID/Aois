from karno import *

def Summator(A, B, C):
    Sum = (not A and not B and C) or (not A and B and not C) or (A and not B and not C) or (A and B and C)
    Plus = (not A and B and C) or (A and not B and C) or (A and B and not C) or (A and B and C)
    return Sum, Plus

def SummatorSDNF(make_func=False):
    Sum_SDNF = []
    Plus_SDNF = []
    for A in range(2):
        for B in range(2):
            for C in range(2):
                Sum, Plus = Summator(A, B, C)
                if Sum:
                    Sum_SDNF.append( f"({' !' if not A else ''}A{'!' if not B else ''}B{' not ' if not C else ''}C) or ".replace('not ', '!').replace(' !', '!'))

                if Plus:
                    Plus_SDNF.append(f"({' not ' if not A else ''}A{' not ' if not B else ''}B{' not ' if not C else ''}C) or ".replace('not ', '!').replace(' !', '!'))

    return Sum_SDNF, Plus_SDNF


def unsigned_binary_sum(num1, num2):
    length = max(len(num1), len(num2))
    tmp = 0
    output = ''
    for i in range(length - 1, -1, -1):
        binSum = int(num1[i]) + int(num2[i]) + tmp
        if binSum == 0:
            output = '0' + output
            tmp = 0
        elif binSum == 1:
            output = '1' + output
            tmp = 0
        elif binSum == 2:
            output = '0' + output
            tmp = 1
        elif binSum == 3:
            output = '1' + output
            tmp = 1
        elif num1[i] == '.':
            output = '.' + output
    # if tmp == 1:
    #     output = "1" + output
    return output


def TruthTable():
    print(f"x1 x2 x3 x4 y1 y2 y3 y4")
    for i in range(16):
        bin = '{0:04b}'.format(i)  
        if i > 9:
            print(f"{bin[0]}  {bin[1]}  {bin[2]}  {bin[3]}  -  -  -  -")
        else:
            y = '{0:04b}'.format(i + 4) 
            print(f"{bin[0]}  {bin[1]}  {bin[2]}  {bin[3]}  {y[0]}  {y[1]}  {y[2]}  {y[3]} ")


def print_formulas():
    print("Formuly y1, y2, y3, y4 v SDNF:")
    y1 = "!x1*!x2*x3*!x4 + !x1*!x2*x3*x4 + !x1*x2*!x3*!x4 + !x1*x2*!x3*x4 + !x1*x2*x3*!x4 + !x1*x2*x3*x4 + x1*!x2*!x3*!x4 + x1*!x2*!x3*x4"
    y2 = "!x1*!x2*!x3*!x4+!x1*!x2*!x3*x4+!x1*x2*x3*!x4+!x1*x2*x3*x4+x1*!x2*!x3*!x4+x1*!x2*!x3*x4"
    y3 = "!x1*!x2*!x3*!x4+!x1*!x2*!x3*x4+!x1*x2*!x3*!x4+!x1*x2*!x3*x4+x1*!x2*!x3*!x4+x1*!x2*!x3*x4"
    y4 = "!x1*!x2*!x3*x4 + !x1*!x2*x3*x4+!x1*x2*!x3*x4+!x1*x2*x3*x4+x1*!x2*!x3*x4 "
    print(y1, y2, y3, y4, sep='\n')
    print("Minimalizirovannie formuli y1, y2, y3, y4 v SDNF:")
    Minimalization_y1 = '(((((((((((!x1)*(!x2))*x3)*(!x4)) + ((((!x1)*(!x2))*x3)*x4)) + ((((!x1)*x2)*(!x3))*(!x4))) + ((((!x1)*x2)*(!x3))*x4)) + ((((!x1)*x2)*x3)*(!x4))) + ((((!x1)*x2)*x3)*x4)) + (((x1*(!x2))*(!x3))*(!x4))) + (((x1*(!x2))*(!x3))*x4))'
    Minimalization_y2 = "(((((((((!x1)*(!x2))*(!x3))*(!x4)) + ((((!x1)*(!x2))*(!x3))*x4)) + ((((!x1)*x2)*x3)*(!x4))) + ((((!x1)*x2)*x3)*x4)) + (((x1*(!x2))*(!x3))*(!x4))) + (((x1*(!x2))*(!x3))*x4))"
    Minimalization_y3 = '(((((((((!x1)*(!x2))*(!x3))*(!x4)) + ((((!x1)*(!x2))*(!x3))*x4)) + ((((!x1)*x2)*(!x3))*(!x4))) + ((((!x1)*x2)*(!x3))*x4)) + (((x1*(!x2))*(!x3))*(!x4))) + (((x1*(!x2))*(!x3))*x4))'
    Minimalization_y4 = '((((((((!x1)*(!x2))*(!x3))*x4) + ((((!x1)*(!x2))*x3)*x4)) + ((((!x1)*x2)*(!x3))*x4)) + ((((!x1)*x2)*x3)*x4)) + (((x1*(!x2))*(!x3))*x4))'

    y1 = create_karnaugh_form_dnf(Minimalization_y1)
    y2 = create_karnaugh_form_dnf(Minimalization_y2)
    y3 = create_karnaugh_form_dnf(Minimalization_y3)
    y4 = create_karnaugh_form_dnf(Minimalization_y4)
    print(y1, y2, y3, y4, sep='\n')


if __name__ == "__main__":
    print("A\tB\tC\tSum\tPlus")
    for A in range(2):
        for B in range(2):
            for C in range(2):
                Sum, Plus = Summator(A, B, C)
                print(f"{A}\t{B}\t{C}\t{int(Sum)}\t{int(Plus)}")

    Sum_SDNF, Plus_SDNF = SummatorSDNF()
    print("Sum = ", ''.join(Sum_SDNF).strip('or '))
    print("Plus = ", ''.join(Plus_SDNF).strip('or '))
    print()
    print('Tablitsa istinisti D8421 -> D8421+4:')
    TruthTable()
    print_formulas()
