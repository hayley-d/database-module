1. 
```
//author[books/book]/name
```

2. 
```
//book[published_year='1953']/title
```

3.
```
//author[books/book/editions > 5]/books/book/title
```

4.
```
/library/author[name='J.D. Salinger']/books/book[title='The Catcher in the Rye']/editions
```

5. 
```
//author[books/book[number(published_year) > 1950 and number(editions) > 5]]/name
```

6.
```
for $a in /library/author
let $b := $a/books/book
where count($b) > 1
return $a/name
```

7.
```
for $a in /library/author[ contains(concat(' ', normalize-space(name), ' '), ' Lee ') ]
return $a/books/book/title
```

8.
```
for $a in /library/author[ books/book[number(published_year) >= 1950 and number(published_year) <= 1960] ]
order by $a/name ascending
return $a/name
```

9.
```
let $top := /library/author/books/book
            [ not(/library/author/books/book/editions > editions) ]
for $a in $top/ancestor::author
return $a/name
```

10.
```
for $a in /library/author
where some $b in $a/books/book satisfies number($b/published_year) < 1950
  and some $c in $a/books/book satisfies number($c/published_year) > 1960
return $a/name/text()
```

