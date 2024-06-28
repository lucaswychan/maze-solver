# Maze Solver

## Abstract
Maze Solver is a Java repository that provides efficient implementations of popular pathfinding algorithms such as Depth-First Search (DFS), Breadth-First Search (BFS), and A* (A-Star). Designed to solve mazes of various sizes and complexities, this repository offers an intuitive and flexible solution for finding the shortest path through a maze. Whether you're interested in maze exploration, game development, or algorithmic problem-solving, MazeSolverAI provides an easy-to-use framework to navigate and discover optimal routes within mazes.

<hr/>

## Instruction
The algorithm are located at [MazeSPT.java](src/MazeSPT.java). A*, DFS, and BFS are provided to get the shortest path, and the default algorithm is set to A*.

```java
private void AStar(int[] start, int[] end)
private void dfs(int[] currVertex, int[] end)
private void bfs(int[] start, int[] end)
```

The constructor of `MazeSPT` takes a 2D graph with binary values, which can be obtained from [Map](Map). [CsvToArray.java](src/CsvToArray.java) provides a convenient way to convert the csv file into a binary graph. 
