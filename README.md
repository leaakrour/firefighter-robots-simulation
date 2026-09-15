# 🔥 Firefighter Robots Simulation

An object-oriented simulation of autonomous firefighting robots navigating a natural terrain to extinguish fires as efficiently as possible. Built in Java as part of the Object-Oriented Programming course at **Grenoble INP - Ensimag**.

## Overview

The simulation models a fleet of firefighter robots operating on a grid map, coordinated by a "fire chief" that assigns fires to robots based on different strategies. The project progressively covers:

- **Object-oriented design**: encapsulation, inheritance, abstraction, polymorphism
- **Discrete-event simulation engine** driving robot movement and interventions
- **Shortest-path algorithms** for robot navigation across varied terrain
- **Strategy pattern** for comparing fire-assignment algorithms (elementary vs. optimized)
- **Java Collections** (List, HashSet, PriorityQueue) for efficient data handling

## Robot types

| Type | Movement | Water capacity |
|---|---|---|
| Drone | Flies over any terrain | 10,000 L |
| Wheeled | Free terrain / habitat only | 5,000 L |
| Tracked | Slowed in forest, avoids water/rock | 2,000 L |
| Legged | Avoids water, uses unlimited powder | Unlimited |

## Tech stack

Java · Gradle · Custom GUI simulator (provided by course staff)

## Project structure

app/ → Main entry point & simulator
app.data/ → Core domain classes (Robot, Carte, Incendie, events...)
app.ai/ → Fire chief strategies & path-finding
app.test/ → Test scenarios
carteParDefaut/ → Predefined map configurations

## Running the project

```bash
./gradlew run
```

## Context

Group project (3 students) completed as part of the Object-Oriented Programming course, Ensimag 2A (2024).
