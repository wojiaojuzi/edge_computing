#!/usr/bin/env python
# -*- coding:utf-8 -*-
#@Time  : 2019/11/11 19:25
#@Author: yangjian
#@File  : normalize_regression.py


import pandas as pd
import numpy as np
import lightgbm as lgb
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import train_test_split, KFold
from sklearn import metrics

def lgbm_fold(test_x, test_y, train_x, train_y):
    X_train = train_x.astype(float)
    X_test = test_x.astype(float)
    y_train = train_y.astype(float)
    y_test = test_y.astype(float)
    param = {
        'boosting_type': 'gbdt',
        'objective':  'regression',
        'metric': 'rmse',
        'colsample_bytree': 0.1,
        'feature_fraction': 0.5,
        'lambda_l1': 0.0,
        'lambda_l2': 0.0,
        'learning_rate': 0.005000001090491338,
        'max_depth': 139,
        'min_data_in_leaf': 50,
        'min_gain_to_split': 0.0,
        'min_sum_hessian_in_leaf': 1e-05,
        'num_leaves': 17,
        'subsample': 1.0,
        'bagging_fraction': 0.4,
        'bagging_freq': 5,
        'verbose': -1,
        'num_threads': 4,
    }


    # 五折交叉验证
    folds = KFold(n_splits=5, shuffle=False, random_state=600)

    predictions = np.zeros([len(X_test)])
    oof_lgb = np.zeros(len(train_x))

    print(np.shape(X_train))
    print(np.shape(y_train))
    for fold_, (trn_idx, val_idx) in enumerate(folds.split(X_train, y_train)):
        print("fold n°{}".format(fold_ + 1))
        trn_data = lgb.Dataset(X_train[trn_idx], y_train[trn_idx])
        val_data = lgb.Dataset(X_train[val_idx], y_train[val_idx])

        num_round = 1000
        clf = lgb.train(param,
                        trn_data,
                        num_round,
                        valid_sets=[trn_data, val_data],
                        verbose_eval=100,
                        early_stopping_rounds=100)
        # oof[val_idx] = clf.predict(X_train[val_idx], num_iteration=clf.best_iteration)
        oof_lgb[val_idx] = clf.predict(train_x[val_idx], num_iteration=clf.best_iteration)

        predictions += clf.predict(X_test, num_iteration=clf.best_iteration) / folds.n_splits
        #
        # print("Best RMSE: ", np.sqrt(mean_squared_error(oof_lgb, train_y)))
    clf.save_model('model_r.txt')

    # 模型加载
    clf = lgb.Booster(model_file='model_r.txt')

    # 模型预测
    y_pred = clf.predict(X_test, num_iteration=clf.best_iteration)


    true_value = np.array(y_test)
    pred_value = np.array(y_pred)
    print(true_value.T)
    print(pred_value.T)
    print("Best RMSE: ", np.sqrt(mean_squared_error(y_pred, y_test)))
    for i in range(0, len(true_value)):
        if true_value[i] < 0.25:
            true_value[i] = 0
        elif 0.25 <= true_value[i] < 0.5:
            true_value[i] = 1
        elif 0.5 <= true_value[i] < 0.75:
            true_value[i] = 2
        elif true_value[i] >= 0.75:
            true_value[i] = 3

    for i in range(0, len(pred_value)):
        if pred_value[i] < 0.28:
            pred_value[i] = 0
        elif 0.28 <= pred_value[i] < 0.502:
            pred_value[i] = 1
        elif 0.502 <= pred_value[i] < 0.72:
            pred_value[i] = 2
        elif pred_value[i] >= 0.72:
            pred_value[i] = 3

    score = metrics.accuracy_score(true_value,pred_value)
    print("AUC score: {:<8.5f}".format(score))
    print(true_value)
    print(pred_value)
    precision = metrics.precision_score(y_true=true_value, y_pred= pred_value, average=None)
    recall = metrics.recall_score(y_true=true_value, y_pred= pred_value, average=None)
    matrix = metrics.confusion_matrix(y_true=true_value, y_pred=pred_value)
    FAR = [(matrix[1, 0] + matrix[2, 0] + matrix[3, 0]) / (
                matrix[1, 0] + matrix[2, 0] + matrix[3, 0] + matrix[1, 1] + matrix[2, 2] + matrix[3, 3]),
           (matrix[0, 1] + matrix[2, 1] + matrix[3, 1]) / (
                       matrix[0, 1] + matrix[2, 1] + matrix[3, 1] + matrix[0, 0] + matrix[2, 2] + matrix[3, 3]),
           (matrix[1, 2] + matrix[0, 2] + matrix[3, 2]) / (
                       matrix[1, 2] + matrix[0, 2] + matrix[3, 2] + matrix[1, 1] + matrix[0, 0] + matrix[3, 3]),
           (matrix[1, 3] + matrix[0, 3] + matrix[2, 3]) / (
                   matrix[1, 3] + matrix[0, 3] + matrix[2, 3] + matrix[1, 1] + matrix[0, 0] + matrix[2, 2]),
           ]  # FP/(FP+TN)
    print(matrix)
    print("AUC score: {:<8.5f}".format(score))
    print('precision' + str(precision))
    print('recall' + str(recall))
    print(" FAR" + str(FAR))

    return score, X_test, y_test, X_train, y_train


def get_data():
    file_tr = '../Data/train.csv'
    file_te = '../Data/test.csv'
    test = pd.read_csv(file_te, na_values='NAN', index_col=False)
    test['Height'] = test['Height'].fillna(test['Height'].mean())
    test_x = np.array(test.iloc[:, 0:16])
    test_y = np.array(test.iloc[:, 16])
    train = pd.read_csv(file_tr, na_values='NAN', index_col=False)
    train['Height'] = train['Height'].fillna(train['Height'].mean())
    train_x = np.array(train.iloc[:, 0:16])
    train_y = np.array(train.iloc[:, 16])

    return test_x, test_y, train_x, train_y


def start():
    test_x, test_y, train_x, train_y = get_data()
    lgbm_fold(test_x, test_y, train_x, train_y)


start()
