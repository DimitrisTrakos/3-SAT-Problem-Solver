package com.company;
import java.util.ArrayList;
import java.util.Iterator;



public class CNF {
    private ArrayList<Clause> clauses;
    private int variableCount;

    public CNF(ArrayList<Clause> newClauses, int newVariableCount)
    {
        clauses = newClauses;
        variableCount = newVariableCount;
    }

    public int numberOfClauses()
    {
        return clauses.size();
    }

    public int numberOfVariables()
    {
        return variableCount;
    }

public int Cost(boolean[] truthAssignments) {

    int numberSolved = 0;
    Iterator clauseList = clauses.iterator();

    while (clauseList.hasNext()) {
        Clause currentClause = (Clause) clauseList.next();

        if (currentClause.trySolve(truthAssignments)) {
            numberSolved++;
        }

    }
    return numberSolved;
}
    public int NumberofFalseClauses(boolean[] truthAssignments){

        int numberFalse = 0;
        Iterator clauseList = clauses.iterator();

        while (clauseList.hasNext())
        {
            Clause currentClause = (Clause)clauseList.next();

            if(!currentClause.trySolve(truthAssignments))
            {
                numberFalse++;
            }

        }

    return numberFalse;
}

    public ArrayList<Clause> getfalseClauses(boolean[] truthAssignments) {

        ArrayList<Clause> falseclauses = new ArrayList<Clause>();
        Iterator clauseList = clauses.iterator();

        while (clauseList.hasNext())
        {
            Clause currentClause = (Clause)clauseList.next();

            if(!currentClause.trySolve(truthAssignments))
            {
               falseclauses.add(currentClause);
            }


        }

return falseclauses;

    }
}
