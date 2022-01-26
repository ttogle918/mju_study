def isNumber(s):
    try:
        float(s)
        return True
    except ValueError:
        return False
def solution(program, flag_rules, commands):
    line = len(commands)
    answer = [True]*line
    flag = dict()
    for i in range(len(flag_rules)) :
        index = flag_rules[i].index(' ')
        flag[flag_rules[i][0:index]] = flag_rules[i][index+1 : len(flag_rules[i])]
    
    
    
    data = []
    for i in range(line) :
        data.append(commands[i].split(' '))
    print(data)
    
    
    for i in range(line) :
        # 1
        if program != data[i][0] :
            answer[i] = False
            continue
        # 2 4 6
        j = 1
        while j < len(data[i]) :
            if data[i][j] not in flag.keys() :
                answer[i] = False
                break
            if data[i][j] == '-e' :
                if data[i][j+1] != NULL or data[i][j+1] not in flag.keys():
                    answer[i] = False
                    break
            elif isNumber(data[i][j+1]) and data[i][j] != '-n':
                answer[i] = False
                break
            j=j+2            
    print(answer)
    return answer
  
program = "line"
flag_rules = ["-s STRING", "-n NUMBER", "-e NULL"]
commands =["line -n 100 -s hi -e", "lien -s Bye"]
solution(program, flag_rules, commands)
