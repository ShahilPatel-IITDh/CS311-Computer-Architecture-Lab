import pandas as pd
import matplotlib.pyplot as plt

# our data
# data = [["Australia", 2500, 2021],["Bangladesh", 1000, 2021],["England", 2000, 2021],["India", 3000, 2021],["Srilanka", 1500, 2021]]
data_q1 =[["16B",0.025002256,0.02238806, 0.022807017, 0.030265596, 0.03243848],
        ["128B",0.11878216,0.021978023,0.053682037, 0.07174231, 0.045383412],
        ["512B",0.10448887,0.021582734,0.04797048,0.063885264,0.04347826],
        ["1024B",0.09750088,0.021201413,0.046044864,0.0601227,0.04172662]
        ]
# dataframe
dataFrame = pd.DataFrame(data_q1, columns=["Sizes","Descending","EvenOdd","Fibonacci","Palindrome","Prime"])

# plotting the dataframe
dataFrame.plot(x="Sizes", y=["Descending","EvenOdd","Fibonacci","Palindrome","Prime"], kind="line", figsize=(10, 9))

# displaying line graph
plt.show()