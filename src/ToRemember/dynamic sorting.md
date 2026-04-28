# Data Structures for Dynamic Sorting & Updates

---

## The Scenario

You need to store objects (e.g., `User(name, age)`) and efficiently support:

1. **Fetch Top**: Get user with highest age (tie-break: lexicographically smallest name).
2. **Arbitrary Updates**: Update a specific user's age or remove them entirely.

```java
class User {
    int id;
    String name;
    int age;

    User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
```

---

## The "Twist": Updates & Removals

While `PriorityQueue` is great for fetching the top element O(1), it **fails at arbitrary updates** because finding an element to remove takes O(N) (linear scan).

### Complexity Comparison

| Operation        | PriorityQueue | TreeSet      |
| ---------------- | ------------- | ------------ |
| Fetch Top        | O(1)          | O(log N)     |
| Insert           | O(log N)      | O(log N)    |
| Remove (by ref)  | O(N)          | O(log N)    |
| Search           | O(N)          | O(log N)    |

---

## The "Mutability Trap"

> **Rule**: Never modify a field used in sorting logic (e.g., `age`) while the object is inside the data structure. The collection will **not** re-sort automatically, leading to corruption.

### Correct Update Pattern

1. **Remove** the object.
2. **Update** the fields.
3. **Add** the object back.

```java
// WRONG - modifying while inside the set
user.age = 30; // TreeSet is now corrupted!

// CORRECT
set.remove(user);
user.age = 30;
set.add(user);
```

---

## Strategy A: TreeSet + HashMap (Recommended)

Use a `HashMap<ID, User>` to hold references for easy access, and `TreeSet` for sorting.

- **Logic**: `map.get()` → `set.remove()` → `update` → `set.add()`
- **Pros**: Clean, strict O(log N) for all ops.
- **Note**: `TreeSet` does **not** use `equals()` for equality — it uses the `Comparator.compare()` you provide. If `compare()` returns `0` for two objects, TreeSet treats them as **the same element** and silently drops one. So always add a **unique ID** as a final tie-breaker to ensure two distinct objects never compare as `0`.

### Comparator

```java
// Comparator: Descending Age, Ascending Name, ID tie-breaker
Comparator<User> comp = (a, b) -> {
    if (a.age != b.age) return Integer.compare(b.age, a.age);
    if (!a.name.equals(b.name)) return a.name.compareTo(b.name);
    return Integer.compare(a.id, b.id); // WITHOUT this: two users with same name+age → compare returns 0 → one gets dropped!
};
```

```java
// Example of the problem WITHOUT the ID tie-breaker:
TreeSet<User> set = new TreeSet<>((a, b) -> {
    if (a.age != b.age) return Integer.compare(b.age, a.age);
    return a.name.compareTo(b.name);
});
set.add(new User(1, "Alice", 25));
set.add(new User(2, "Alice", 25)); // compare() returns 0 → SILENTLY DROPPED!
System.out.println(set.size()); // 1, not 2!
```

### Full Example

```java
TreeSet<User> set = new TreeSet<>(comp);
HashMap<Integer, User> map = new HashMap<>();

// Insert
User u1 = new User(1, "Alice", 25);
User u2 = new User(2, "Bob", 30);
map.put(u1.id, u1); set.add(u1);
map.put(u2.id, u2); set.add(u2);

// Fetch Top
User top = set.first(); // Bob (age 30)

// Update user 1's age to 35
User target = map.get(1);
set.remove(target);
target.age = 35;
set.add(target);

// Fetch Top again
top = set.first(); // Alice (age 35 now)

// Remove user 2
User toRemove = map.get(2);
set.remove(toRemove);
map.remove(2);
```

---

## Strategy B: PriorityQueue + HashMap ("Lazy Removal")

If `TreeSet` is unavailable or memory is tight, use "Lazy Removal".

- **Logic**:
  - **Update**: Do not remove old item. Mark it as "stale" in Map (or `visited` array) and add the new version to PQ.
  - **Fetch**: When polling, check if the top item is valid (matches current state in Map). If stale, discard and poll again.
- **Pros**: Faster constants than TreeSet.
- **Cons**: PQ grows larger than $N$. Requires cleanup logic.

### Full Example

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
    return b[1] - a[1]; // max-heap by age
});
HashMap<Integer, Integer> currentAge = new HashMap<>(); // id -> latest age

// Insert
pq.offer(new int[]{1, 25}); // {id, age}
currentAge.put(1, 25);
pq.offer(new int[]{2, 30});
currentAge.put(2, 30);

// Update user 1's age to 35 (lazy: don't remove old entry)
pq.offer(new int[]{1, 35});
currentAge.put(1, 35);

// Fetch Top (skip stale entries)
while (!pq.isEmpty()) {
    int[] top = pq.peek();
    int id = top[0], age = top[1];
    if (!currentAge.containsKey(id) || currentAge.get(id) != age) {
        pq.poll(); // stale entry, discard
        continue;
    }
    // top is valid
    break;
}
// pq.peek() -> {1, 35} (Alice, age 35)

// Remove user 2 (lazy: just remove from map)
currentAge.remove(2);
// Next poll will skip user 2's entries automatically
```

---

## When to Use Which?

| Criteria                     | TreeSet + HashMap | PQ + Lazy Removal |
| ---------------------------- | ----------------- | ----------------- |
| Need strict ordering at all times | ✅                | ❌                |
| Frequent updates/removals    | ✅                | ⚠️ PQ bloats      |
| Only care about top element  | Overkill          | ✅                |
| Simplicity                   | Moderate          | Simple            |
