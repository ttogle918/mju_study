# 질문 보고 했음..
from math import gcd
def solution(w,h):
    total = w * h
    a = gcd(w, h)
    mini_h = h // a
    mini_w = w // a
    return total - (mini_h+mini_w-1)*a