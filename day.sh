#!/bin/bash

# Check if a parameter is provided
if [ -z "$1" ]; then
  echo "Usage: $0 <parameter>"
  exit 1
fi

PARAMETER="$1"

# Create the folder structure
mkdir -p ./src/main/groovy/"$PARAMETER"
mkdir -p ./src/main/resources/"$PARAMETER"

# Create the parameter.groovy file
GROOVY_FILE=./src/main/groovy/"$PARAMETER"/"$PARAMETER".groovy
if [ ! -f "$GROOVY_FILE" ]; then
  echo "package $PARAMETER" > "$GROOVY_FILE"
  echo ""  >> "$GROOVY_FILE"
  echo "def inputFile = new File(\"../../resources/$PARAMETER/input\")" >> "$GROOVY_FILE"
  echo "def testInputFile = new File(\"../../resources/$PARAMETER/input-test\")" >> "$GROOVY_FILE"

  echo "// Groovy file for $PARAMETER" > "$GROOVY_FILE"
  echo "Created $GROOVY_FILE"
else
  echo "$GROOVY_FILE already exists."
fi

# Create the input file
INPUT_FILE=./src/main/resources/"$PARAMETER"/input
if [ ! -f "$INPUT_FILE" ]; then
  echo "Placeholder content for input" > "$INPUT_FILE"
  echo "Created $INPUT_FILE"
else
  echo "$INPUT_FILE already exists."
fi

# Create the input-test file
INPUT_TEST_FILE=./src/main/resources/"$PARAMETER"/input-test
if [ ! -f "$INPUT_TEST_FILE" ]; then
  echo "Placeholder content for input-test" > "$INPUT_TEST_FILE"
  echo "Created $INPUT_TEST_FILE"
else
  echo "$INPUT_TEST_FILE already exists."
fi

echo "Folders and files created successfully!"