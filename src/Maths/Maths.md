# Maths

- Modulo Maths
- GCD
- Prime Number
- 


int dist = (int) 1e9;

// 1e9 = 10^9;
// used as a sentinal value(placeholder value) in CP (leetcode, codechef and other) as a safe large value.. but always look at constraints before using that largest possible value for variable is less than 1e9 or not.. if not then use Integer.MAX_VALUE;

## Cartesian Movement:

X-axis & Y-axis
```
(x-1, y+1)     (x, y+1)       (x+1, y+1)
     ↖             ↑            ↗
                  +y
                   ↑ 
 (x-1, y) ← -x ← (x, y) → +x → (x+1, y)  
                   ↓ 
                   -y   
     ↙             ↓             ↘
(x-1, y-1)      (x, y-1)      (x+1, y-1)
```

