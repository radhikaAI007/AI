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

