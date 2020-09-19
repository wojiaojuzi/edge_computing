# !/usr/bin/env python
# -*- coding:utf-8 -*-
# @Time  : 2019/11/11 20:24
# @Author: yangjian
# @File  : test.py

import numpy as np
import lightgbm as lgb
import re
import sys

def test(x_test: 'np.array') -> float:
    clf = lgb.Booster(model_file='model_r.txt')

    # 模型预测
    y_pred = clf.predict(x_test, num_iteration=clf.best_iteration)

    return y_pred


ar = sys.argv[1]
pattern = ","
result = re.split(pattern,ar)
print(result)
ar = list(map(lambda x:float(x), result))
ar = np.array(ar).reshape(1,-1)
print(ar)
print(test(ar))
