symptom(high_fever, flu).
symptom(cough, flu).
symptom(difficulty_breathing, flu).
symptom(chills, flu).
symptom(fatigue, flu).
doctor(flu, dr_smith, floor_1).


symptom(cough, common_cold).
symptom(sneezing, common_cold).
symptom(runny_nose, common_cold).
symptom(sore_throat, common_cold).
symptom(headache, common_cold).
doctor(common_cold, dr_jones, floor_1).


symptom(fever, pneumonia).
symptom(cough, pneumonia).
symptom(chest_pain, pneumonia).
symptom(shortness_of_breath, pneumonia).
doctor(pneumonia, dr_clark, floor_2).


symptom(abdominal_pain, gastritis).
symptom(nausea, gastritis).
symptom(vomiting, gastritis).
symptom(indigestion, gastritis).
doctor(gastritis, dr_adams, floor_2).


symptom(joint_pain, arthritis).
symptom(swelling, arthritis).
symptom(stiffness, arthritis).
symptom(reduced_range_of_motion, arthritis).
doctor(arthritis, dr_baker, floor_3).


symptom(skin_rash, measles).
symptom(fever, measles).
symptom(conjunctivitis, measles).
symptom(cough, measles).
doctor(measles, dr_davis, floor_3).


% Collect symptoms from user
collect_symptoms(Symptoms) :-
    write('Enter a symptom (or type "done" to finish): '),
    read(Symptom),
    (Symptom == done -> Symptoms = []; collect_symptoms(Rest), Symptoms = [Symptom|Rest]).


% Diagnose disease based on collected symptoms
diagnose(Disease, Doctor, Floor) :-
    collect_symptoms(Symptoms),
    find_disease(Symptoms, Disease),
    doctor(Disease, Doctor, Floor).


% Find disease based on symptoms
find_disease(Symptoms, Disease) :-
    member(Symptom, Symptoms),
    symptom(Symptom, Disease),
    forall(member(S, Symptoms), symptom(S, Disease)).


% Start diagnosis
start :-
    diagnose(Disease, Doctor, Floor),
    format('Based on your symptoms, you may have ~w. Please see ~w on ~w.', [Disease, Doctor, Floor]), nl.
