package com.company;

public class Clause {

    private Literal one;
    private Literal two;
    private Literal three;

    public Clause(Literal newOne, Literal newTwo, Literal newThree) {

        one = newOne;
        two = newTwo;
        three = newThree;

    }

    public boolean trySolve(boolean[] truthAssignments) {

       int a =Math.abs(one.indexIntoTruthArray);
       int b=Math.abs(two.indexIntoTruthArray);
       int c=Math.abs(three.indexIntoTruthArray);

       if(one.indexIntoTruthArray >0) {
           if (truthAssignments[one.indexIntoTruthArray]) {
               return true;
           }
       }
       else if(!truthAssignments[a]){
           return true;
       }


        if(two.indexIntoTruthArray>0) {
            if (truthAssignments[two.indexIntoTruthArray]) {
                return true;
            }
            } else if (!truthAssignments[b]) {
                return true;
            }


         if(three.indexIntoTruthArray>0) {
             if (truthAssignments[three.indexIntoTruthArray]) {
                 return true;
             }
             } else if (!truthAssignments[c]) {
                 return true;
             }


          return false;

    }

    public int [] getClauseValue(){
        int [] clauseValue =new int [3];

        clauseValue[0]= Math.abs(one.indexIntoTruthArray);
        clauseValue[1]= Math.abs(two.indexIntoTruthArray);
        clauseValue[2]=Math.abs(three.indexIntoTruthArray);

        return clauseValue;


    }
}