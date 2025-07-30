#!/bin/bash
docker build -t java-runner .
docker run --rm -v "$PWD":/app java-runner javac Main.java
docker run --rm -v "$PWD":/app java-runner java Main

