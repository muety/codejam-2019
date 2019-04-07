t = int(input())
for i in range(0, t):
    n = int(input())
    result = input().replace('E', 'X').replace('S', 'E').replace('X', 'S')
    print('Case #{}: {}'.format(i + 1, result))