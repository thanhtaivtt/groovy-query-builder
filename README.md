# Groovy query builder
Repository just for education.

### Usage

Import Query

```groovy
import Core.Query
```

Init Query Object:

```groovy
def connString = "jdbc:oracle:thin:@localhost:1521/db.toidicode.com";
def username = "admin";
def password = "*******";


query = new Query(connString, username, password);

```

Set table

```groovy
query.from(tablename)
```
Select Statement

```groovy
query.select([ArrayColumn])
```

Where Statement

```groovy
query.where(Coulumn, Value, Operater[Eg: =, != , <>, ...])
query.whereOr(Coulumn, Value, Operater[Eg: =, != , <>, ...])
query.whereIn(Coulumn, ListValue)
query.whereNotIn(Coulumn, ListValue)
```

Get result

```groovy
query.run() // get all result
query.run(offset, limit) // get result with paging
```
Get first result

```groovy
query.first() //
```

### Author

[Thanh Tai](https://facebook.com/thanhtai.json)

[Tutorials](https://toidicode.com)