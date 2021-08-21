import matplotlib.pyplot as plt
import pandas as pd

# Read the file made in the java Program (Write.java)
data = pd.read_csv("E:\\EclipseCodes\\CO_ASSIGNMENT_2\\src\\simulator\\filename.txt", delimiter="\t")

# Accessing through variables
x = data['a']
y = data['b']

# Add title
plt.title("Memory Address vs Cycle")

# Add labels
plt.xlabel('Cycle')
plt.ylabel('Address')

# Scatter Plot :- Plot only points
plt.scatter(x, y, )

# Save the plot
plt.savefig('Graph.png')

# Show the plot
plt.show()
