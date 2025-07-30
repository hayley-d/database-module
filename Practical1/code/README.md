## Instructions

```bash
docker build -t java-runner .
```

```bash
# Compile your Java file (e.g., Main.java)
docker run --rm -v "$PWD":/app java-runner javac Main.java

# Run it
docker run --rm -v "$PWD":/app java-runner java Main
```
