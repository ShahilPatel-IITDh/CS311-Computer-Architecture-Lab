from tkinter import Y
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits import mplot3d

#Creating Numpy arrays of columns of Output.txt
width = np.loadtxt("Output.txt",usecols=0)
probability = np.loadtxt("Output.txt",usecols = 1)
time = np.loadtxt("Output.txt",usecols = 2)

ax = plt.axes(projection="3d")

ax.plot3D(width,probability,time)

plt.show()