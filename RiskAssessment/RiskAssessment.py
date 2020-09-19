import random
a = random.randrange(1,100)
if a > 94 and a <= 97:
    b = random.randrange(20,70)
    print(b)
elif a > 97:
    b = random.randrange(40,95)
    print(b)
else:
    print(random.randrange(1,60))