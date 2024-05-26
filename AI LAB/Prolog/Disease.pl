symptom(fever,flu).
symptom(cough,flu).
symptom(sneezing,cold).

diagnose(Disease):-
    write('Do You have fever (yes/no)'), read(Fever),
    write('Do You have cough (yes/no)'), read(Cough),
    write('Do You have sneezing (yes/no)'), read(Sneezing),
    identifyDisease(Fever,Cough,Sneezing,Disease).


identifyDisease(yes,yes,no,flu):- !.
identifyDisease(no,no,yes,cold):- !.
identifyDisease(no,no,no,unknown).

start:-
    diagnose(Disease),
    write('You may have '),write(Disease), nl.



% Facts
// symptom(fever, flu).
// symptom(cough, flu).
// symptom(sneezing, cold).

// % Rules
// diagnose(Disease) :-
//     % Directly setting the symptom values
//     Fever = no,    % change to no to test different scenarios
//     Cough = no,    % change to no to test different scenarios
//     Sneezing = yes,  % change to yes to test different scenarios
//     identifyDisease(Fever, Cough, Sneezing, Disease).

// identifyDisease(yes, yes, no, flu) :- !.
// identifyDisease(no, no, yes, cold) :- !.
// identifyDisease(_, _, _, unknown).

// start :-
//     diagnose(Disease),
//     write('You may have '), write(Disease), nl.

// :- initialization(start).

// % Entry point
// main :- start.
