### View In Browser
```bash
http://localhost:7474
```

### Database Name
```
Prac9Neo4j.graphdb
```

### Username
```
neo4j
```

### Password
```
testpassword
```

### Graph View
```bash
MATCH (n) RETURN n;
```

### Create Clauses
```
CREATE
  (thandi:Person {name: "@Thandi", from: "Durban"}),
  (johan:Person {name: "@Johan", from: "Pretoria"}),
  (neo:Person {name: "@Neo", from: "Cape Town", age: 22}),
  (melanie:Person {name: "@Melanie", from: "Joburg", age: 21}),
  (trees:Post {hashtag: "#Trees", message: "provide oxygen"}),
  (rhinos:Post {hashtag: "#Rhinos", message: "are innocent"})
WITH thandi, johan, neo, melanie, trees, rhinos

CREATE
  (thandi)-[:FOLLOWS {since: 2014}]->(johan),
  (thandi)-[:FOLLOWS {since: 2011}]->(neo),
  (thandi)-[:POSTED {date: date("2023-08-20")}]->(trees),
  (johan)-[:FOLLOWS {since: 2012}]->(thandi),
  (johan)-[:REPOSTED {date: date("2023-08-20")}]->(trees),
  (neo)-[:FOLLOWS {since: 2016}]->(thandi),
  (neo)-[:FOLLOWS {since: 2016}]->(melanie),
  (neo)-[:POSTED {date: date("2023-09-05")}]->(rhinos),
  (melanie)-[:FOLLOWS {since: 2012}]->(johan),
  (melanie)-[:FOLLOWS {since: 2012}]->(thandi),
  (melanie)-[:LIKED {date: date("2023-09-05")}]->(rhinos);
```

### Show Current Nodes
```
MATCH (p)-[r]->(q) RETURN p,r,q;
```

#### List all the node labels:
```
MATCH (n)
RETURN DISTINCT labels(n) AS labels;
```

#### List names of  the poeple in alphabetical order
```
MATCH (p:Person)
RETURN p.name AS Name
ORDER BY p.name ASC;
```

#### List all the hashtags for the posts in alphabetical order
```
MATCH (post:Post)
RETURN post.hashtag AS Hashtag
ORDER BY post.hashtag ASC;
```

#### List all the relationship types with no duplicates
```
MATCH ()-[r]->()
RETURN DISTINCT type(r) AS RelationshipType;
```

#### List names of the people who have posted and thier posts
```
MATCH (p:Person)-[:POSTED]->(post:Post)
RETURN p.name AS Person, post.hashtag AS Hashtag, post.message AS Message
ORDER BY p.name;
```

#### List all the hashtags and messages for the posts that have been reposted
```
MATCH (:Person)-[:REPOSTED]->(post:Post)
RETURN DISTINCT post.hashtag AS Hashtag, post.message AS Message
ORDER BY post.hashtag;
```

#### List all the hashtags and messages for the posts which have been liked
```
MATCH (:Person)-[:LIKED]->(post:Post)
RETURN DISTINCT post.hashtag AS Hashtag, post.message AS Message
ORDER BY post.hashtag;
```


