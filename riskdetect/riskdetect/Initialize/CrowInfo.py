#!/usr/bin/env python
# -*- coding:utf-8 -*-
#@Time  : 2019/10/15 15:21
#@Author: yangjian
#@File  : CrowInfo.py#!/usr/bin/env python

import os
import re
import urllib.error
import urllib.request
import xlrd
from bs4 import BeautifulSoup
import csv

def downloadPhoto(url,name):
    if not os.path.exists("./photo"):
        print("not exit")
        os.makedirs("./photo")

    else:
        # print("exit")
        os.chmod("./photo", 777)
        with urllib.request.urlopen(url, timeout=30) as response, open("./photo/"+name+".jpg" , 'wb') as f_save:
            f_save.write(response.read())
            f_save.flush()
            f_save.close()
            print("成功")
def reMatch (patteren, content)->(str,str):
    str1 = ''
    str2 = ''
    matchs = re.findall(patteren, content)
    # print(matchs  )
    if matchs != []:
        strs = re.split('\n',str(matchs.__getitem__(0)))
        # print(strs)
        str1 = strs.__getitem__(0)
        str2 = strs.__getitem__(1)
    return str1,str2
def crowl_infos(url):
    info = dict()
    req = urllib.request.Request(url)
    try:
        urllib.request.urlopen(req)
        req.add_header('User-Agent',
                       'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36')
        data = urllib.request.urlopen(req)
        str = data.read()

        soup = BeautifulSoup(str, 'html.parser')
        content = soup.find("table", class_="table_deathrow indent")
        # print(content)
        if content != None:
            content = content.text
            nameP = "Name\n.*?\n"
            ageP = 'Received\)\n.*?\n'
            eduP = 'Education Level \(Highest Grade Completed\)\n.*?\n'
            genderP = 'Gender\n.*?\n'
            hightP = 'Height \(in Feet and Inches\)\n.*?′ .*?″\n'
            weightP = 'Weight \(in Pounds\)\n.*?\n'

            key1, name = reMatch(nameP, content)
            info.setdefault(key1, name)
            key2, age = reMatch(ageP, content)
            info.setdefault('Age', age)
            key3, edu = reMatch(eduP, content)
            info.setdefault('Education Level', edu)
            key4, gender = reMatch(genderP, content)
            info.setdefault(key4, gender)
            key5, height = reMatch(hightP, content)
            if height!= '':
                heights = [x for x in re.split('′ |″', height) if x != '']
                height = '%.1f' % (int(heights.__getitem__(0)) * 30.48 + int(heights.__getitem__(1)) * 2.54)
            info.setdefault('Height', height)  # cm
            key6, weight = reMatch(weightP, content)
            if weight!='':
                weight = '%.1f' % (int(re.findall('\d+',weight).__getitem__(0)) * 0.4535924)
            info.setdefault('Weight', weight)  # kg

            print(info.__str__())

            return info
        else:
            return None
    except urllib.error.HTTPError as err:
        print(err.code)
        if err.code == '404':
            return None


def getUrl():
    path ='C:\\Users\\JiJie\\PycharmProjects\\EscapePro\\Crow\\OffenderInfo.xlsx'
    data = xlrd.open_workbook(path)
    table = data.sheets()[0]
    Names = list()
    firstNames = table.col_values(0,start_rowx=1,end_rowx=None)
    lastNames = table.col_values(1,start_rowx=1,end_rowx=None)
    urls = table.col_values(2, start_rowx=1, end_rowx=None)

    for fn in firstNames:
        Names.append(fn+lastNames.__getitem__(firstNames.index(fn)))
    return Names,urls

def getInformation():
    dataFilePath = 'C:\\Users\\JiJie\\PycharmProjects\\EscapePro\\Data'
    names, urls = getUrl()
    infos = list()
    for url in urls:
        print(url)
        if url[-5:] == '.html':
            info = crowl_infos(url)
            if info!= None:
                infos.append(info)
        # else:
        #     downloadPhoto(url, names.__getitem__(urls.index(url)))
    csvFile = open('offenferInfo.csv','w')
    writer = csv.writer(csvFile)
    writer.writerow(infos.__getitem__(0).keys())
    for item in infos:
        writer.writerow(item.values())
    csvFile.close()
# crowl_infos('https://www.tdcj.texas.gov/death_row/dr_info/wilkinschristopher.html')
getInformation()