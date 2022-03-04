def solution(record):
    answer = []
    dic_id = {}     # id : name
    for v in record :
        t_list = v.split()
        tid = t_list[1]
        isEnter = False
        
        if t_list[0] == 'Change' :
            dic_id[tid] = t_list[2]
            continue
            
        elif t_list[0] == 'Enter' :
            isEnter = True
            dic_id[tid] = t_list[2]
            
        answer.append([tid, isEnter])
        
        
    return [dic_id[v[0]]+'님이 들어왔습니다.' if v[1]==True else dic_id[v[0]]+'님이 나갔습니다.' for v in answer]