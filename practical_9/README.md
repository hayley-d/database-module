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

### Delete
```
MATCH (n) DETACH DELETE n;
```

### Show Current Nodes
```
MATCH (p)-[r]->(q) RETURN p,r,q;
```

#### Create People and Relationships
```
CREATE
  (thandi:Person {name: "@Thandi", from: "Durban"}),
  (johan:Person {name: "@Johan", from: "Pretoria"}),
  (neo:Person {name: "@Neo", from: "Cape Town", age: 22}),
  (melanie:Person {name: "@Melanie", from: "Joburg", age: 21});

MATCH
  (thandi:Person {name:"@Thandi"}),
  (johan:Person {name:"@Johan"}),
  (neo:Person {name:"@Neo"}),
  (melanie:Person {name:"@Melanie"})
CREATE
  (thandi)-[:FOLLOWS {since: 2014}]->(johan),
  (thandi)-[:FOLLOWS {since: 2011}]->(neo),
  (johan)-[:FOLLOWS {since: 2012}]->(thandi),
  (neo)-[:FOLLOWS {since: 2016}]->(thandi),
  (neo)-[:FOLLOWS {since: 2016}]->(melanie),
  (melanie)-[:FOLLOWS {since: 2012}]->(johan),
  (melanie)-[:FOLLOWS {since: 2012}]->(thandi);
```

#### Create Posts 
```
CREATE
  (trees:Post {hashtag: "#Trees", message: "provide oxygen"}),
  (rhinos:Post {hashtag: "#Rhinos", message: "are innocent"});

MATCH
  (thandi:Person {name:"@Thandi"}),
  (johan:Person {name:"@Johan"}),
  (neo:Person {name:"@Neo"}),
  (melanie:Person {name:"@Melanie"}),
  (trees:Post {hashtag:"#Trees"}),
  (rhinos:Post {hashtag:"#Rhinos"})
CREATE
  (thandi)-[:POSTED {date: date("2023-08-20")}]->(trees),
  (johan)-[:REPOSTED {date: date("2023-08-20")}]->(trees),
  (neo)-[:POSTED {date: date("2023-09-05")}]->(rhinos),
  (melanie)-[:LIKED {date: date("2023-09-05")}]->(rhinos);

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

#### Find the posts that are 1 or 2 links away from the Person Neo
```
MATCH (n:Person {name:"@Neo"})-[:FOLLOWS|POSTED|REPOSTED|LIKED*1..2]->(post:Post)
RETURN DISTINCT post.hashtag AS Hashtag, post.message AS Message;
```

#### Show the nodes in the path from Melanie to Neo
```
MATCH path = shortestPath((melanie:Person {name:"@Melanie"})-[*]-(neo:Person {name:"@Neo"}))
RETURN path;
```

#### For each person who has posted, provide a report to show if thier post has been reposted.
```
MATCH (p:Person)-[:POSTED]->(post:Post)
OPTIONAL MATCH (:Person)-[r:REPOSTED]->(post)
WITH p.name AS Person, post.hashtag AS Hashtag, COUNT(r) > 0 AS HasBeenReposted
RETURN Person, Hashtag, HasBeenReposted
ORDER BY Person;
```

#### Find the shortest follows path from Melanie to Neo
```
MATCH path = shortestPath((melanie:Person {name:"@Melanie"})-[:FOLLOWS*]->(neo:Person {name:"@Neo"}))
RETURN path;
```

#### Count the number of nodes in the network
```
MATCH (n)
RETURN COUNT(n) AS TotalNodes;
```

#### For each person, count the number of persons they follow
```
MATCH (p:Person)-[:FOLLOWS]->(other:Person)
RETURN p.name AS Person, COUNT(other) AS FollowsCount
ORDER BY FollowsCount DESC;
```

#### For each person count the number of people that have been following them since 2014
```
MATCH (follower:Person)-[r:FOLLOWS]->(followed:Person)
WHERE r.since = 2014
RETURN followed.name AS Person, COUNT(follower) AS FollowersSince2014
ORDER BY FollowersSince2014 DESC;
```

#### Show the name and number of followers for the person with the most followers
```
MATCH (follower:Person)-[:FOLLOWS]->(followed:Person)
WITH followed.name AS Person, COUNT(follower) AS Followers
ORDER BY Followers DESC
LIMIT 1
RETURN Person, Followers;
```


