# Java Adaptive Quiz

A console based Java quiz application with **progressive scoring**, **two player gameplay**, and **file based question sets**.

The first question is worth 1 point. If a player answers correctly, the next question becomes worth one additional point. If the player answers incorrectly, the next question resets to 1 point.

## Features

- Two player console quiz
- Progressive / streak based scoring
- Question sets loaded from text files
- Create and save new question sets from the console
- Built in sample question sets
- Input validation for question counts and yes/no responses
- Case insensitive answer checking
- Final score summary and winner/draw detection
- Uses Java file I/O, arrays, loops, methods, records, and exception handling

## Tech Stack

- Java
- Java NIO file handling
- Console / CLI interface

## Project Structure

```text
java-adaptive-quiz/
├── src/
│   └── Quiz.java
├── data/
│   ├── months.txt
│   └── weekdays.txt
├── .gitignore
└── README.md
```

## How the Scoring Works

Each player starts with a question value of **1 point**.

```text
Correct answer -> add current question value to score
                  increase next question value by 1

Wrong answer   -> add 0 points
                  reset next question value to 1
```

Example:

```text
Question 1: correct -> +1 point -> next question worth 2
Question 2: correct -> +2 points -> next question worth 3
Question 3: wrong   -> +0 points -> next question worth 1
```

## Question Set File Format

A question set file uses a simple line based format:

```text
<number of questions>
<question 1>
<answer 1>
<question 2>
<answer 2>
...
```

Example:

```text
2
What is the capital of France?
Paris
What is 5 + 5?
10
```

## Requirements

Use **JDK 17 or later**.

Check your Java installation:

```bash
java -version
javac -version
```

## Run the Project

Clone the repository:

```bash
git clone https://github.com/MikaGunn/java-adaptive-quiz.git
cd java-adaptive-quiz
```

Compile:

```bash
javac -d out src/Quiz.java
```

Run:

```bash
java -cp out Quiz
```

When asked for a question-set file, try:

```text
data/months.txt
```

or:

```text
data/weekdays.txt
```

## Example Gameplay

```text
=== Adaptive Quiz ===
Rules:
- The first question is worth 1 point.
- After a correct answer, the next question is worth 1 more point.
- After a wrong answer, the next question resets to 1 point.
- The quiz is played by 2 players.

Player 1
Worth 1 point(s).
What is the first month of a year?
Your answer: January
Correct!
Current score: 1
Next question value: 2
```

## Programming Concepts Demonstrated

- Variables and expressions
- Conditional statements
- Nested loops
- Arrays
- Methods with parameters and return values
- Java records
- Keyboard input with `Scanner`
- File input and output
- Exception handling
- Modular program structure

## Background

This repository is a cleaned and refactored GitHub version of a university mini project originally developed as a short answer quiz program. The core project requirement was to accept typed answers and implement adaptive scoring where correct answers increase the value of the next question and incorrect answers reset that value to one point.

## Possible Future Improvements

- Configurable number of players
- Randomised question order
- Categories and difficulty levels
- Timer for each question
- Persistent high scores
- Unit tests with JUnit
- GUI or web based interface

## Repository Topics

Suggested GitHub topics:

`java` `quiz-game` `console-application` `file-io` `cli` `beginner-java` `java-project`

## License

No license is included by default. Add a license only if you want to specify how other people may reuse your code.
