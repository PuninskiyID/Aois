def find_parenthesis_indices(func: str):
    first_parenthesis = None
    last_parenthesis = func.find(')')
    index = 0
    for i in func[:last_parenthesis]:
        if i == '(':
            first_parenthesis = int(index)
        index += 1
    if first_parenthesis is None:
        raise Exception('Parse error: could not find first parenthesis')
    return first_parenthesis, last_parenthesis


def evaluate_subexpression(subexpression: str):
    if '-' in subexpression:
        answer = 'True' if 'False' in subexpression else 'False'
    elif '==' in subexpression:
        answer = 'True' if subexpression.count('False') == 2 or subexpression.count('True') == 2 else 'False'
    elif '+' in subexpression:
        answer = 'True' if 'True' in subexpression else 'False'
    elif '*' in subexpression:
        answer = 'True' if subexpression.count('True') == 2 else 'False'
    elif '>' in subexpression:
        first_symbol, second_symbol = subexpression.split('>')
        if 'True' in second_symbol:
            answer = 'True'
        elif 'False' in first_symbol:
            answer = 'True'
        else:
            answer = 'False'
    else:
        raise Exception('Parse error')
    return answer


def parse_function(func: str):
    while '(' in func:
        first_parenthesis, last_parenthesis = find_parenthesis_indices(func)
        subexpression = func[first_parenthesis: last_parenthesis + 1]
        answer = evaluate_subexpression(subexpression)
        func = func.replace(subexpression, answer, 1)
    return 1 if func == 'True' else 0


def generate_truth_table_variables(variables):
    truth_table_variables = {}
    for variant in range(1 << len(variables)):
        truth_table_variables[variant] = {}
        for i, key in reversed(list(enumerate(reversed(variables)))):
            truth_table_variables[variant].update({key: str(bool(variant & (1 << i)))})
    return truth_table_variables


def replace_symbols_with_values(func: str, truth_table_variables: dict):
    for variable, is_true in truth_table_variables.items():
        func = func.replace(variable, f' {variable} ')

    for variable, is_true in truth_table_variables.items():
        func = func.replace(f' {variable} ', is_true)

    if func.count('(') != func.count(')'):
        raise Exception("Wrong number of parentheses")
    return func


def calculate_truth_table(func: str, truth_table_variables: dict):
    truth_table = {}
    for variant in truth_table_variables:
        tmp_func = replace_symbols_with_values(func[:], truth_table_variables[variant])
        answer = parse_function(tmp_func[:])
        truth_table[variant] = answer

    return truth_table


def print_truth_table(truth_table_variables: dict, truth_table: dict, variables: list):
    for variable in variables:
        print(f' {variable} ', end='')
    print('  Result')
    for variant in truth_table_variables:
        for variable, value in truth_table_variables[variant].items():
            value = 1 if value == 'True' else 0
            print(f' {value} ', end='')
        print(f'  {truth_table[variant]}')


def print_disjunctive_normal_form(vars_for_table: dict, truth_table_answer: dict):
    disjunctive_normal_form = []
    for variant, mean in truth_table_answer.items():
        if mean == 1:
            disjunctive_normal_form.append(vars_for_table[variant])

    answer = ''
    for form in disjunctive_normal_form:
        for variable, is_true in form.items():
            print(' ' if is_true == 'True' else '-', end='')
            answer += variable
        print('   ', end='')
        answer += ' * '
    print()
    print(''.join(answer[:-2]))


def get_disjunctive_normal_form(vars_for_table: dict, truth_table_answer: dict):
    disjunctive_normal_form = []
    for variant, mean in truth_table_answer.items():
        if mean == 1:
            disjunctive_normal_form.append(vars_for_table[variant])

    answer = ''
    for form in disjunctive_normal_form:
        for variable, is_true in form.items():
            answer += f'!{variable}' if is_true == 'False' else variable

        answer += ' + '

    return answer.strip(' + ')


def get_conjunctive_normal_form(vars_for_table: dict, truth_table_answer: dict):
    disjunctive_normal_form = []
    for variant, mean in truth_table_answer.items():
        if mean == 0:
            disjunctive_normal_form.append(vars_for_table[variant])

    answer = ''
    for form in disjunctive_normal_form:
        for variable, is_true in form.items():
            answer += f'!{variable}' if is_true == 'True' else variable

        answer += ' + '

    return answer.strip(' + ')
