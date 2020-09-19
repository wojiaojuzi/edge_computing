#!/usr/bin/env python
# -*- coding:utf-8 -*-
#@Time  : 2019/10/15 15:15
#@Author: yangjian
#@File  : num_w.py
import pandas as pd

education_level = ['小学及以下', '初中', '高中及以上']
gender = ['男', '女']
criminal_level = ['轻', '中', '重']
maritalSt = ['未婚', '已婚', '离异', '丧偶']
parentSitu = ['父母健在', '有一父或一母', '父母双亡']
# maritalSt = ['未婚', '已婚']
# parentSitu = ['父母健在']
childSitu = ['没有孩子', '有一子女', '有多个子女']
terrain = ['有山', '平原']
roadCond = ['堵车', '通畅']
windSpeed = ['无风', '软风', '轻风', '微风', '和风', '劲风', '强风', '疾风', '大风', '烈风', '狂风', '暴风', '台风']
weather = ['晴', '多云', '阴天', '雾', '小雨', '中雨', '大雨', '暴雨', '雷阵雨', '冰雹', '冻雨', '雨夹雪', '小雪', '中雪', '大到暴雪', '霜冻']
humanT = ['小', '中', '大']

def number2word(data2):

    data3 = data2
    data3['Education Level'] = data2['Education Level'].astype('category')
    data3['Education Level'].cat.categories = education_level

    data3['Gender'] = data2['Gender'].astype('category')
    data3['Gender'].cat.categories = gender

    data3['Criminal Level'] = data2['Criminal Level'].astype('category')
    data3['Criminal Level'].cat.categories = criminal_level

    data3['Marital Status'] = data2['Marital Status'].astype('category')
    data3['Marital Status'].cat.categories = maritalSt

    data3['Parent Situation'] = data2['Parent Situation'].astype('category')
    data3['Parent Situation'].cat.categories = parentSitu

    data3['Child Situation'] = data2['Child Situation'].astype('category')
    data3['Child Situation'].cat.categories = childSitu

    data3['Terrain'] = data2['Terrain'].astype('category')
    data3['Terrain'].cat.categories = terrain

    data3['Road Condition'] = data2['Road Condition'].astype('category')
    data3['Road Condition'].cat.categories = roadCond

    data3['Wind Speed'] = data2['Wind Speed'].astype('category')
    data3['Wind Speed'].cat.categories = windSpeed

    data3['Weather'] = data2['Weather'].astype('category')
    data3['Weather'].cat.categories = weather

    data3['Human'] = data2['Human'].astype('category')
    data3['Human'].cat.categories = humanT

    return data3