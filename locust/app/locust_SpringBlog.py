#!/usr/bin/python3
# -*- encoding: utf-8 -*-
'''
@File    :   locustfile_SpringBlog.py
@Time    :   2023/05/24
@Author  :   Yuehao.Xu
'''

import sys,os
os.chdir(sys.path[0])
sys.path.append("../")

import common.config
import locust_plugins
from locust import HttpUser, task, constant_throughput


class User(HttpUser):
    wait_time = constant_throughput(0.1)

    # @task
    # def hello(self):
    #     self.client.get(common.config.getUrlPrefix("abstract") +
    #                     "/AbstractAction/hello")

    modelInfo = {""}
    modelJson = json.dumps()

    @task
    def adminIndex(self):
        self.client.get(common.config.getUrlPrefix("admin") +
                        "/admin")

    @task
    def adminSetting(self):
        self.client.get(common.config.getUrlPrefix("admin") +
                        "/admin/settings")

    @task
    def adminUpdateSetting(self):
        self.client.post(common.config.getUrlPrefix("admin") +
                        "/admin/settings")

    @task
    def cart(self):
        self.client.get(common.config.getUrlPrefix("Cart") +
                        "/CartAction/testAllFunction")

    @task
    def order(self):
        self.client.get(common.config.getUrlPrefix("Order") +
                        "/OrderAction/testAllFunction")
