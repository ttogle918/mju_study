import re

def solution(new_id):
    # 1단계 : 대문자 -> 소문자
    new_id = new_id.lower()
    
    # 2단계 : 쓸 수 없는 문자 지우기
    if new_id.isalnum() == False :
        new_id = re.sub(r'[^0-9a-z-_.]', '', new_id)
    
    # 3단계 : 2개 이상의 .을 1개로 바꿈
    new_id = re.sub('[.]+', '.', new_id)
    
    # 4단계 : 처음이나 끝 . 제거
    if len(new_id) > 0 and new_id[0] == '.' :
        new_id = new_id[1:]
    if len(new_id) > 0 and new_id[-1] == '.' :
        new_id = new_id[:-1]
        
    # 5단계 : 빈 문자열이라면 a 대입
    if len(new_id) == 0 :
        new_id = 'a'
        
    # 6단계 : 16자 이상이라면 15번째 이상부터 제거. 제거 후 . 존재 시, 마침표 . 제거
    if len(new_id) > 15 :
        new_id = new_id[:15]
    if new_id[-1] == '.' : # .이 2개 이상은 아니다. 3단계에서 제거하였다.
        new_id = new_id[:-1]
    
    # 7단계 : 글자수가 3자 미만일 때 3자 만들기
    if len(new_id) <= 2 :
        while len(new_id) < 3 :
            new_id += new_id[-1]
    
    return new_id