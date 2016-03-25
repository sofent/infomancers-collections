# Introduction #

These are a few examples of simple iterators created using the _yield_ feature. These are implemented by having a static method in a utility class, which supplies iterators for different needs.

## Filtering Iterator ##

```

public interface Predicate<T> {
  boolean evaluate(T item);
}

public static <T> Iterable<T> filteredIterable(Iterable<T> it, Predicate<T> pred) {
  return new Yielder<T>() {
    public void yieldNextCore() {
      for (T item : it) {
        if (pred.evaluate(item)) {
          yieldReturn(item);
        }
      }
  };
}
```

## Transforming Iterator ##

```

public interface Transformation<T, K> {
  K transform(T item);
}

public static <T, K> Iterable<K> transformedIterable(Iterable<T> it, Transformation<T, K> trans) {
  return new Yielder<T>() {
    public void yieldNextCore() {
      for (T item : it) {
        yieldReturn(trans.transform(item));
      }
  };
}
```

## DFS on a Graph ##

```
public interface GraphNode {
  Iterable<GraphNode> getNeighbours();
}

public static Iterable<GraphNode> depthFirstSearch(GraphNode graph) {
  return dfsCore(graph, new HashSet<GraphNode>());
}

private static Iterable<GraphNode> dfsCore(GraphNode node, Collection<GraphNode> visited) {
  return new Yielder<GraphNode>() {
            protected void yieldNextCore() {
                if (visited.contains(node)) {
                    yieldBreak();
                }
                
                yieldReturn(node);

                visited.add(node);

                // Now, let's iterate the neighbours and recursively call DFS.
                for (Object neighbour :node.getNeighbours()) {
                    for (Object yieldedNeighbour : dfsCore(neighbour, visited)) {
                        yieldReturn(yieldedNeighbour);
                    }
                }
            }
  };
}
```