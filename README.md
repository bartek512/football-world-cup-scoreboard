# Football World Cup ScoreboardFootball

Football World Cup Scoreboard - simple in-memory app to manage football matches and display scores,
during the World Cup.

## Goal

The goal of the project is to build a simple and clean solution for managing football scores with a focus on
Test-Driven Development (TDD), adhering to SOLID principles and maintaining a high level of code quality.

## Requirements

- The scoreboard should allow the following actions:
    1. Start a game - Capture teams and initialize the score to 0-0.
    2. Finish a game - Remove a match from the scoreboard.
    3. Update score - Update the score for a specific match.
    4. Get a summary - Display all matches ordered by their total score.

## Assumptions

- Each game has a home team and an away team.
- The scoreboard is in-memory and no persistence (database) is required.
- The project will be implemented in Java.

## Structure

- `Game`: Represents a single match, including teams and their scores.
- `ScoreBoard`: Manages multiple games, and provides methods to start a game, finish a game, update the score, and get a
  summary of games.

## Technologies

- Java (JDK 17)
- JUnit (for unit testing)
- Maven (for dependency management)

## How to Run

1. Clone the repository:
   git clone https://github.com/bartek512/scoreboard.git

## Commit Strategy

- Commits will be structured as follows:
    - `[FEAT]`: New features (e.g., added a new method or class).
    - `[TEST]`: Changes or additions to tests.
    - `[FIX]`: Bug fixes or adjustments.
    - `[REFACTOR]`: Refactor or code improvements.
    - `[DOCS]` : Add docs or comments

## Project Design

While this project could be implemented using various architectural patterns or tools (such as layered architecture),
for the purposes of this task, which emphasizes simplicity and quick implementation, I chose to keep the solution as
simple as possible. The decision was made to avoid unnecessary abstractions, which would introduce additional
complexity.
