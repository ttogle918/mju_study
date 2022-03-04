def solution(s):
    if s.isdigit() is True :
        return int(s)
    
    dic_eng_to_num = {'zero' : '0', 'one':'1', 'two':'2', 'three':'3', 'four':'4', 'five':'5', 'six':'6', 'seven':'7', 'eight':'8', 'nine':'9'}
    
    for k in dic_eng_to_num.keys() :
        s = s.replace(k, dic_eng_to_num[k])
    return int(s)