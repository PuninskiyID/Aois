import pandas as pd
from truthTable import *
import numpy as np
from math import log2
import re

def karnaugh_table(n):
    if n == 1:
        table = ['0', '1']
    elif n == 2:
        table = ['00', '01', '11', '10']
    else:
        table = ['000', '001', '011',	'010', '110',	'111', '101', '100']

    return table


def create_karnaugh_map(logical_expression: str, variable_names: list):
    num_variables = len(variable_names) // 2
    column_variables = variable_names[num_variables:]
    row_variables = variable_names[:num_variables]
    variable_assignments = generate_truth_table_variables(variable_names)
    truth_table = calculate_truth_table(logical_expression[:], variable_assignments)
    column_headers, row_headers = karnaugh_table(len(column_variables)), karnaugh_table(len(row_variables))
    karnaugh_map = pd.DataFrame(columns=column_headers, index=row_headers)
    for key, value in truth_table.items():
        variable_assignment = variable_assignments[key]
        column_key = ''.join(column_variables)
        row_key = ''.join(row_variables)
        for letter in column_key:
            value_replacement = '1' if variable_assignment[letter] == 'True' else '0'
            column_key = column_key.replace(letter, value_replacement)
        for letter in row_key:
            value_replacement = '1' if variable_assignment[letter] == 'True' else '0'
            row_key = row_key.replace(letter, value_replacement)
        karnaugh_map.loc[row_key, column_key] = value
    return karnaugh_map


def find_largest_power_of_2(karnaugh_map):
    largest_area = 0
    for i in range(karnaugh_map.shape[0]):
        for j in range(i, karnaugh_map.shape[0]):
            for k in range(karnaugh_map.shape[1]):
                for l in range(k, karnaugh_map.shape[1]):
                    if karnaugh_map.iloc[i:j + 1, k:l + 1].all().all():
                        area = (j - i + 1) * (l - k + 1)
                        if area > largest_area and (area & (area - 1)) == 0:
                            largest_area = area
    return largest_area


def select_max_rectangles(karnaugh_map, largest_area):
    array = karnaugh_map.to_numpy()
    max_rectangles = []
    for i in range(array.shape[0]):
        for j in range(array.shape[1]):
            for k in range(i+1, array.shape[0]+1):
                for l in range(j+1, array.shape[1]+1):
                    sub_array = array[i:k, j:l]
                    if np.all(sub_array == 1) and sub_array.size == largest_area:
                        max_rectangles.append(pd.DataFrame(sub_array, columns=karnaugh_map.columns[j:l], index=karnaugh_map.index[i:k]))

    return max_rectangles


def delete_duplicate_rectangles(rectangle_list: list):
    new_list = []
    for i in range(len(rectangle_list)):
        is_duplicate = False
        for j in range(i + 1, len(rectangle_list)):
            if rectangle_list[i].columns.to_list() == rectangle_list[j].columns.to_list() and \
                    rectangle_list[i].index.to_list() == rectangle_list[j].index.to_list():
                is_duplicate = True
                break
        if not is_duplicate:
            new_list.append(rectangle_list[i])
    return new_list


def get_sub_areas_of_ones(sub_map, rectangles):
    for power in range(int(log2(sub_map.size)), -1, -1):
        max_area = 2 ** power
        selected_rectangles = select_max_rectangles(sub_map, max_area)
        rectangles.extend(selected_rectangles)
        if len(rectangles) == sub_map.size:
            break
    return rectangles


def get_areas_of_ones(karnaugh_map: pd.DataFrame):
    rectangles = []
    for power in range(int(log2(karnaugh_map.size)), -1, -1):
        max_area = 2 ** power
        selected_rectangles = select_max_rectangles(karnaugh_map, max_area)
        rectangles.extend(selected_rectangles)
        if len(rectangles) == karnaugh_map.size:
            break
    sub_map = karnaugh_map.copy().iloc[:, [karnaugh_map.shape[1] - 1, *list(range(karnaugh_map.shape[1] - 1))]]
    rectangles = get_sub_areas_of_ones(sub_map, rectangles)
    sub_map_2 = karnaugh_map.copy().iloc[[karnaugh_map.shape[0] - 1, *list(range(karnaugh_map.shape[0] - 1))], :]
    rectangles = get_sub_areas_of_ones(sub_map_2, rectangles)
    rectangles = delete_duplicate_rectangles(rectangles)
    rectangles.sort(key=lambda x: x.shape[0]*x.shape[1], reverse=True)
    return rectangles


def cover_karnaugh_map(karnaugh_map: pd.DataFrame, rectangles: list) -> list:
    covered_areas = []
    while karnaugh_map.sum().sum() > 0:
        for rect in rectangles:
            rect_columns = rect.columns
            rect_rows = rect.index
            karnaugh_map_rect = karnaugh_map.loc[rect_rows, rect_columns]
            if karnaugh_map_rect.sum().sum() > 0:
                covered_areas.append(rect.copy())
                karnaugh_map.loc[rect_rows, rect_columns] = 0

    return covered_areas


def get_same_position_letters(words):
    common_letters = {}

    for i, letter in enumerate(words[0]):
        is_same = all([word[i] == letter for word in words])
        if is_same:
            common_letters[i] = letter
    return common_letters


def print_karnaugh_form_dnf(areas: list, variables: list):
    is_even, len_number, dnf_expression = True if len(variables) % 2 == 0 else False, len(variables) // 2, ''
    if is_even:
        columns = variables[len_number:]
        index = variables[:len_number]
    else:
        columns = variables[len_number:]
        index = variables[:len_number]
    for rect in areas:
        rect_index = rect.index.to_list()
        rect_column = rect.columns.to_list()
        gluing_index = get_same_position_letters(rect_index)
        gluing_column = get_same_position_letters(rect_column)
        gluing_index = {index[key]: value for key, value in gluing_index.items()}
        gluing_column = {columns[key]: value for key, value in gluing_column.items()}
        dnf_expression += ''.join([f'!{i}' if j == '0' else i for i, j in gluing_index.items()])
        dnf_expression += ''.join([f'!{i}' if j == '0' else i for i, j in gluing_column.items()])
        dnf_expression += ' * '
    return dnf_expression.strip(' * ')


def invert_kmap_for_cnf(karnaugh_map: pd.DataFrame):
    karnaugh_map = karnaugh_map.applymap(lambda x: 0 if x == 1 else 1)
    return karnaugh_map


def print_karnaugh_form_cnf(areas: list, variables: list):
    is_even, len_number, cnf_expression = True if len(variables) % 2 == 0 else False, len(variables) // 2, ''
    columns = variables[len_number:]
    index = variables[:len_number]
    for rect in areas:
        cnf_expression += '('
        rect_index = rect.index.to_list()
        rect_column = rect.columns.to_list()
        gluing_index = get_same_position_letters(rect_index)
        gluing_column = get_same_position_letters(rect_column)
        gluing_index = {index[key]: value for key, value in gluing_index.items()}
        gluing_column = {columns[key]: value for key, value in gluing_column.items()}
        cnf_expression += ''.join([f'{i}+' if j == '0' else f'!{i}+' for i, j in gluing_index.items()])
        cnf_expression += ''.join([f'{i}+' if j == '0' else f'!{i}+' for i, j in gluing_column.items()])
        cnf_expression = cnf_expression.strip('+')
        cnf_expression += ')'
        cnf_expression += ' * '
    return cnf_expression.strip(' * ')


def create_karnaugh_form_dnf(infunc: str):
    infunc = infunc.replace('x1', 'a').replace('x2', 'b').replace('x3', 'c').replace('x4', 'd').replace('!', '-')
    infunc = infunc.replace("=", "==")
    variables = sorted(set(re.findall(r"[A-Za-z]", infunc)))
    karnaugh_map = create_karnaugh_map(infunc, variables)
    rectangles = get_areas_of_ones(karnaugh_map)
    cover_map = cover_karnaugh_map(karnaugh_map.copy(), rectangles)
    dnf_expression = print_karnaugh_form_dnf(cover_map, variables)
    dnf_expression = dnf_expression.replace('a', 'x1').replace('b', 'x2').replace('c', 'x3').replace('d', 'x4')
    return dnf_expression
