#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 2019/10/18 13:04
# @Author: yangjian
# @File  : getRawLabel.py
# this program is used to generate the raw label for the data
import re
from random import randint

import pandas as pd
import numpy as np

Result = ['危险等级0', '危险等级1', '危险等级2', '危险等级3']


def get_info():
    fileP = '../Data/infos_num.csv'
    info = pd.read_csv(fileP, na_values='NAN', index_col=False)
    info['Height'] = info['Height'].fillna(info['Height'].mean())

    array1 = np.array(info['Marital Status'], int)
    array2 = np.array(info['Parent Situation'], int)
    array3 = np.array(info['Video Result'], float)
    array4 = np.array(info['Heart Speed'])
    state = np.zeros_like(array4)
    for i in range(0, np.size(array4)):
        count = 0
        str1 = array4[i]
        strs = re.split(' |\[|\]', str1)
        for s in strs:
            if s is not '':
                st = int(s)
                if st < 80 and count == 0:
                    count = 1
                elif st >= 80 and count == 0:
                    count = 2
                elif st < 80 and count == 1:
                    state[i] = 0
                elif st > 80 and count == 2:
                    state[i] = 1
                elif st > 80 and count == 1:
                    state[i] = 2
                elif st <= 80 and count == 2:
                    state[i] = randint(0, 3)

    array4 = state
    array1 = np.where(array1 == 2, array1, 0)
    array1 = np.where(array1 == 0, array1, 1)
    array2 = np.where(array2 == 0, array2, 1)
    array4 = np.where(array4 == 2, array4, 1)
    array4 = np.where(array4 == 1, array4, 0)
    # result = np.tanh(array1 * 0.1 + array2 * 0.1 + array3 * 0.65 + array4 * 0.15)
    result = array1 * 0.1 + array2 * 0.1 + array3 * 0.65 + array4 * 0.15
    info['Heart Speed'] = array4
    for i in range(0, len(result)):
        if result[i] < 0.25:
            result[i] = 0
        elif 0.25 <= result[i] < 0.5:
            result[i] = 1
        elif 0.5 <= result[i] < 0.75:
            result[i] = 2
        elif result[i] >= 0.75:
            result[i] = 3

    info['Result'] = np.array(result, int)
    print(info['Result'].value_counts())
    # df1 = pd.DataFrame(info, columns=info.columns)
    # df1.to_csv('C:/Users/JiJie/PycharmProjects/EscapePro/Data/infos_labeled_num_classify2.csv', index=False,
    #            encoding='utf-8')
    #
    # info['Result'] = np.array(result, int)
    # info2 = n2w.number2word(info)
    # info2['Result'] = info['Result'].astype('category')
    # info2['Result'].cat.categories = Result
    #
    # df2 = pd.DataFrame(info2, columns=info2.columns)
    # df2.to_csv('C:/Users/JiJie/PycharmProjects/EscapePro/Data/infos_labeled2.csv', index=False, encoding='gbk')


get_info()
