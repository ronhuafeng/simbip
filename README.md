# simbip

A simulator writtern in Clojure for the simulation of BIP modeling language.

## What is BIP?

BIP (Behavior, Interaction, Priority) is a general framework encompassing rigorous design. It uses the BIP language and an associated toolset supporting the design flow. The BIP language is a notation which allows building complex systems by coordinating the behavior of a set of atomic components. Behavior is described as a Petri net extended with data and functions described in C.

This is the page of BIP modeling language which defines the semantics of the language.
http://www-verimag.imag.fr/Rigorous-Design-of-Component-Based.html

## Why needs a simulator?
- To alleviate the pain when designers build formal models.
- To integrate with Eclipse modeling framework to build a graphical modeling environment
- To generate other codes such as C, Java via Clojure codes- 

## Usage

Rewrite models into a JSON form as the structure in tests. Then use method 'Fire' in 'src/simbip/structure.clj'
to fire a model.


## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
