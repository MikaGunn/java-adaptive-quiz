# Project Notes

## Recommended GitHub repository name

`java-adaptive-quiz`

Alternative names:

- `adaptive-quiz-java`
- `progressive-score-quiz`
- `java-console-quiz`

## Recommended GitHub description

> Console-based Java quiz game with two-player gameplay, file-based question sets, custom question creation, and progressive scoring that rewards consecutive correct answers.

## What was cleaned up for GitHub

The original academic implementation was preserved conceptually, while this repository version was refactored to be easier to read, compile, run, and present in a portfolio.

Changes include:

- clearer Java naming conventions
- one reusable question loader rather than repeatedly opening files for each question/answer
- Java `record` for question data
- try-with-resources for safe file handling
- input validation
- case-insensitive answer comparison
- final winner/draw output
- cleaner console messages
- sample files moved into a `data/` directory
- project documentation suitable for GitHub

## Suggested one-line CV/portfolio description

Developed a Java console-based two-player quiz application featuring adaptive scoring, file-based question management, custom question-set creation, and modular file I/O logic.

## Before publishing

1. Replace `YOUR-USERNAME` in `README.md` with your GitHub username.
2. Test the project locally.
3. Do not upload university documents containing student IDs unless you intentionally want them public.
4. Add screenshots only if they improve the repository.
5. Add a license only after deciding how you want others to reuse the code.
