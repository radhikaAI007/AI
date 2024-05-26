:- initialization(main).
main :- write('Hello, World!').% Facts
parent(john, mary).
parent(john, mike).
parent(susan, mary).
parent(susan, mike).
parent(mary, anna).
parent(mary, tom).
parent(peter, anna).
parent(peter, tom).

% Rules
father(F, C) :- parent(F, C), male(F).
mother(M, C) :- parent(M, C), female(M).

child(C, P) :- parent(P, C).
sibling(X, Y) :- parent(P, X), parent(P, Y), X \= Y.

grandparent(GP, GC) :- parent(GP, P), parent(P, GC).

% Gender facts
male(john).
male(mike).
male(peter).
male(tom).
female(susan).
female(mary).
female(anna).

% Queries for testing
% ?- father(john, mary).
% ?- mother(susan, mike).
% ?- child(mary, john).
% ?- sibling(mike, mary).
% ?- grandparent(john, anna).

% Initialization and main program
:- initialization(main).
main :- 
    write('Family Tree Program'), nl,
    write('Example Queries:'), nl,
    write('father(john, mary).'), nl,
    write('mother(susan, mike).'), nl,
    write('child(mary, john).'), nl,
    write('sibling(mike, mary).'), nl,
    write('grandparent(john, anna).'), nl,
    nl,
    % Running some example queries
    (father(john, mary) -> write('John is the father of Mary.'), nl ; write('John is not the father of Mary.'), nl),
    (mother(susan, mike) -> write('Susan is the mother of Mike.'), nl ; write('Susan is not the mother of Mike.'), nl),
    (child(mary, john) -> write('Mary is the child of John.'), nl ; write('Mary is not the child of John.'), nl),
    (sibling(mike, mary) -> write('Mike is a sibling of Mary.'), nl ; write('Mike is not a sibling of Mary.'), nl),
    (grandparent(john, anna) -> write('John is the grandparent of Anna.'), nl ; write('John is not the grandparent of Anna.'), nl).
