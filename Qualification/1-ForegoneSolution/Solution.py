import re

t = int(input()) 
for i in range(0, t):
    chars = input()
    m1, m2 = [None] * len(chars), [None] * len(chars)
    for j in range(0, len(chars)):
        m1[j] = "3" if chars[j] == "4" else chars[j]
        m2[j] = "1" if chars[j] == "4" else "0"
    s1 = ''.join(m1)
    s2 = ''.join(m2)
    
    print("Case #{}: {} {}".format(i + 1, s1, re.sub(r'^0*', '', s2)))