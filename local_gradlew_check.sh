#!/bin/bash
./gradlew clean checkstyleMain checkstyleTest headless allTests coverage coveralls asciidoctor
