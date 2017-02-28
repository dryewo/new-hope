# new-hope

Training camp for the new hope of Clojure.
This is a pre-configured project that you can use to do exercises from the book:

![Living Clojure book cover](http://akamaicovers.oreilly.com/images/0636920034292/cat.gif)

[Living Clojure](http://shop.oreilly.com/product/0636920034292.do) by Carin Meier

Exercises are given in a weekly plan in Chapter 10.


## Prerequisites

Please have the following installed:

* [Leiningen](http://leiningen.org/) build tool
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) + [Cursive plugin](https://cursive-ide.com/userguide/)

## Installation

Fork this repo and clone.

## Usage

The workflow is called "REPL Driven Development". REPL stands for Read-Evaluate-Print-Loop and means interpreter command line mode.

The goal of the workflow is to reduce the turnaround time (aka feedback loop) in the development microcycle:

```
Edit the code -> Run it -> Repeat
```

To prepare for work, do the following steps:

1. Open the cloned folder in IntelliJ, it should automatically recognize the project type as Leiningen and load it.
2. Right-click on the project name in the project pane and select "Run 'REPL for new-hope'"
3. After the REPL loads, run `(refresh)` in it once.

This is your initial position. You have a code editor, you have a JVM instance running and a REPL session attached to it. All the project source code is loaded into the JVM.

### Evaluation

Most of the time with REPL Driven Development you evaluate expressions and look at their results.

#### Direct evaluation

You can type commands into the REPL and press Ctrl-Enter (Cmd-Enter) to evaluate them in the context of the `user` namespace.

#### `comment` code blocks

```clj
(comment
  (conj [1 2 3] 4)
  )
```

It's just a scratchpad, a comment block containing code snippets. To evaluate a snippet, set the caret to the closing parenthesis and use REPL -> Send '...' to REPL context menu action. The expression will be evaluated in the context of the current source file namespace (e.g. `new-hope.week1`).

New lines don't matter, any expression can be evaluated this way.

* If you set the caret after `4`, only `4` will be evaluated, yielding `4`.
* If you set the caret on `]`, the vector `[1 2 3]` will be evaluated, yielding `[1 2 3]`.
* If you set the caret on `)`, the entire `(conj [1 2 3] 4)` form will be evaluated, yielding `[1 2 3 4]`.
* If you set the caret on the closing `)` of the comment block, the comment block will be evaluated (by doing nothing), yielding `nil`.

#### Unit test expressions

In real projects unit tests are kept separately from the production code, but for simplicity's sake here we put them right next to it.

Unit tests are expressions too, like any other Clojure code. When you evaluate a unit test, it returns true or false, and prints error messages if there are any issues.

```clj
(facts "about first"
  (first [1 2 3]) => 1)
```

To execute a unit test, use REPL -> Send '...' to REPL context menu action.

The unit testing library is called [Midje](https://github.com/marick/Midje).

#### Definitions

```clj
(def second-to-last
  (fn [coll]
    (second (reverse coll))))
```

Definitions are also expressions, when evaluated, they create a binding in the JVM between the name and the meaning, so that the name can be used afterwards in the code. Every time the definition is evaluated, the binding in the JVM is updated. Also can be executed with REPL -> Send '...' to REPL.

#### Shortcuts

In order to avoid mouse clicking it's handy to set a key combination, like Ctrl-Shift-Cmd-R for the "REPL -> Send '...' to REPL" action.

#### Evaluating all the source code at once (reloading)

There are actions in the Tools -> REPL menu or in the REPL context menu to evaluate every expression in the current file (Load file) or in every file that has changed since last reload (Sync files).

The same effect is achieved by running `(refresh)` function in the REPL directly (in the `user` namespace).

Contents of the `comment` blocks are ignored.

When a file is reloaded, all the unit tests in it are automatically run.

### Doing an exercise

Starting from Week 1 Day 4, the goal of every exercise is to write a function.

Correctness of the implementation can be checked on the exercise webpage, and the checker there expects it to be in the form of anonymous function:

```clj
(fn [<args>] <body>)
```

In order to keep like this, the recommended way of working on an exercise is:

```clj
;; Name that we use for unit-testing
(def second-to-last
  ;; The actual implementation that can be checked on 4clojure.org
  (fn [coll]
    (second (reverse coll))))

(comment
  ;; Some relevant snippets
  (reverse [1 2 3 4])
  )

(facts "about second-to-last"
  ;; Some unit-tests
  (second-to-last [1 2 3 4]) => 3)
```

1. Create an empty definition of the function.
2. Use `comment` block to play around and experiment.
3. Write unit tests that check correctness of your implementation once you figure it out.

### Looking up the implementation

Press and hold Ctrl (Cmd) and hover your mouse cursor over a symbol — it will show the function signature.

Ctrl (Cmd) and click — it will go to definition.

### Running all the tests

To run all the unit tests without starting the REPL, run

```
$ lein midje
```

from the project directory.

## Style guide

Read this: https://github.com/bbatsov/clojure-style-guide and try to follow. Always format your code before checking in.

## License

Copyright © 2016 Dmitrii Balakhonskii

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
