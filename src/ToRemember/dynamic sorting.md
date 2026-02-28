Data Structures for Dynamic Sorting & Updates
The Scenario
You need to store objects (e.g., `User(name, age)`) and efficiently support:

1. Fetch Top: Get user with highest age (tie-break: lexicographically smallest name).

2. Arbitrary Updates: Update a specific user's age or remove them entirely.

The "Twist": Updates & Removals
While `PriorityQueue` is great for fetching the top element ($O(1)$), it fails at arbitrary updates because finding an element to remove takes $O(N)$ (linear scan).

Complexity Comparison

The "Mutability Trap"
Rule: Never modify a field used in sorting logic (e.g., age) while the object is inside the data structure. The collection will not re-sort automatically, leading to corruption.

Correct Update Pattern:

1. Remove the object.

2. Update the fields.

3. Add the object back.

Strategy A: TreeSet + HashMap (Recommended)
Use a `HashMap<ID, User>` to hold references for easy access, and `TreeSet` for sorting.

• Logic: `map.get()` -> `set.remove()` -> `update` -> `set.add()`

• Pros: Clean, strict $O(\log N)$ for all ops.

• Note: `TreeSet` uses `compareTo` for equality. If two objects have same Name/Age, they are treated as duplicates. Always add a unique ID to the Comparator tie-breaker.

```

// Comparator: Descending Age, Ascending Name, ID tie-breaker

Comparator<User> comp = (a, b) -> {

    if (a.age != b.age) return Integer.compare(b.age, a.age);

    if (!a.name.equals(b.name)) return a.name.compareTo(b.name);

    return Integer.compare(a.id, b.id);

};

```

Strategy B: PriorityQueue + HashMap ("Lazy Removal")
If `TreeSet` is unavailable or memory is tight, use "Lazy Removal".

• Logic:

  • Update: Do not remove old item. Mark it as "stale" in Map (or `visited` array) and add the new version to PQ.

  • Fetch: When polling, check if the top item is valid (matches current state in Map). If stale, discard and poll again.

• Pros: Faster constants than TreeSet.

• Cons: PQ grows larger than $N$. Requires cleanup logic.
