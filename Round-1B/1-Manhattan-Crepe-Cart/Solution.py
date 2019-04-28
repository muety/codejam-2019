import operator
from itertools import product

def make_grid(q):
    grid = {}
    for i in range(0, q + 1):
        for j in range(0, q + 1):
            grid[(i,j)] = 0
    return grid

def get_candidates(person):
    if (person[2] == 'N'):
        x = range(0, q + 1)
        y = range(person[1] + 1, q + 1)
    elif (person[2] == 'W'):
        x = range(0, person[0])
        y = range(0, q + 1)
    elif (person[2] == 'S'):
        x = range(0, q + 1)
        y = range(0, person[1])
    elif (person[2] == 'E'):
        x = range(person[0] + 1, q + 1)
        y = range(0, q + 1)
    return list(product(x, y))

if __name__ == '__main__':
    t = int(input())

    for i in range(0, t):
        chars = input().split(' ')
        p, q = int(chars[0]), int(chars[1])
        grid = make_grid(q)

        for j in range(0, p):
            chars = input().split(' ')
            person = (int(chars[0]), int(chars[1]), chars[2])
            candidates = get_candidates(person)
            
            for c in candidates:
                grid[c] += 1

        maxval = max(grid.items(), key=operator.itemgetter(1))[1]
        candidates = [e[0] for e in grid.items() if e[1] == maxval]
        best = sorted(candidates, key=lambda c: c[0] + c[1])[0]
        
        print('Case #{}: {} {}'.format(i+1, best[0], best[1]))