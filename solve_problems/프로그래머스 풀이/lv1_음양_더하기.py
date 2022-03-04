def solution(absolutes, signs):
    return sum([v if signs[i]==True else -v for i,v in enumerate(absolutes)])