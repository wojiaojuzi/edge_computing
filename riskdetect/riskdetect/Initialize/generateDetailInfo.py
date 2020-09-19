#!/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 2019/10/13 17:27
# @Author: yangjian
# @File  : generateDetailInfo.py
import re
import numpy as np
import pandas as pd
import Initialize.num_w as n2w


def getInfo():
    data = pd.read_csv('C:/Users/JiJie/PycharmProjects/EscapePro/Crow/offenferInfo.csv')
    data1 = data
    data2 = data1.append(data1, ignore_index=True)
    data2 = data2.append(data2, ignore_index=True)
    # data2 = data2.append(data2, ignore_index=True)
    data2 = data2.append(data, ignore_index=True)

    data2.loc[data2['Education Level'].astype(int) <= 6, 'Education Level'] = 0
    data2.loc[
        (6 < data2['Education Level'].astype(int)) & (data2['Education Level'].astype(int) < 11), 'Education Level'] = 1
    data2.loc[data2['Education Level'].astype(int) >= 11, 'Education Level'] = 2
    data2.loc[data2['Gender'] == 'Male', 'Gender'] = 0
    data2.loc[data2['Gender'] == 'male', 'Gender'] = 0
    data2.loc[data2['Gender'] == 'Female', 'Gender'] = 1
    size = data2.shape[0]
    data2['Criminal Level'] = np.random.randint(0, 3, size=size)
    data2['Marital Status'] = np.random.randint(0, 2, size=size)
    data2['Parent Situation'] = np.random.randint(0, 2, size=size)
    data2['Child Situation'] = np.random.randint(0, 3, size=size)
    data2.loc[data2['Marital Status'] == 0, 'Child Situation'] = 0
    data2['Terrain'] = np.random.randint(0, 2, size=size)
    data2['Road Condition'] = np.random.randint(0, 2, size=size)
    data2['Wind Speed'] = np.random.randint(0, 13, size=size)
    data2['Weather'] = np.random.randint(0, len(n2w.weather), size=size)
    data2['Human'] = np.random.randint(0, 3, size=size)
    res = np.around(np.random.random(size=size)/2, decimals=3)
    for j in range(0,size):
        if res[j] >1:
            res[j] = res[j]/2
    data2['Video Result'] = res
    hs= np.random.randint(60, 90, size=(2,size))
    heart_s = list()
    for i in range(0,size):
        heart_s.insert(i, str(hs[:, i]))
    data2['Heart Speed'] = np.array(heart_s)

    data2 = pd.DataFrame.drop_duplicates(data2, subset=None, keep='first', inplace=False)
    data2.insert(0, column='index', value=data2.index)

    df1 = pd.DataFrame(data2, columns=data2.columns)
    df1.to_csv('C:/Users/JiJie/PycharmProjects/EscapePro/Data/infos_num.csv',  mode='a',index=False, encoding='utf-8')

    # data3 = n2w.number2word(data2)
    # df2 = pd.DataFrame(data3, columns=data3.columns)
    # df2.to_csv('C:/Users/JiJie/PycharmProjects/EscapePro/Data/infos_d.csv', mode='a', index=False, encoding='gbk')


getInfo()
