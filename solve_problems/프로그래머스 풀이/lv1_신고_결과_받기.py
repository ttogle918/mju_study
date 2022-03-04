def solution(id_list, report, k):
    # initialize
    dic_reported_id = dict([[id, []] for id in id_list])
    result = [0 for i in id_list]
    answer= [0 for i in id_list]
    
    for r in report :
        r = r.split(' ')
        
        # 해당 user가 신고한 user의 이름 append
        dic_reported_id[r[0]].append(r[1])

    for key, v in dic_reported_id.items() : 
        # 중복 신고 제거 (1회만 남기기)
        v = list(set(v))
        for reported_user in v :
            # 신고받은 횟수 더하기
            result[id_list.index(reported_user)] += 1
    
    # k번 이상 신고받아 정지된 id 내역
    paused_id = []
    [paused_id.append(id_list[i]) for i, v in enumerate(result) if v >= k]
            
    for key, v in dic_reported_id.items() : 
        # 정지된 id를 신고한 이력이 있는가?
        for id in paused_id :
            if id in v :
                answer[id_list.index(key)] += 1
            
            
    return answer