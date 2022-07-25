package com.company;


import java.util.*;


public class GSATWALKSAT {

    public GSATWALKSAT(){

    }

    public static void main(String[] args) {

        Scanner myObj= new Scanner(System.in);

        Scanner myObj2 =new Scanner(System.in);

        Scanner myObj3 =new Scanner(System.in);

        Scanner myObj4= new Scanner(System.in);

        Scanner myObj5 = new Scanner(System.in);

        Scanner myObj6= new Scanner(System.in);

        System.out.println("Give the number of Variables :");
        int numberOfVariables=myObj.nextInt() + 1;

        System.out.println("Give the number of Clauses: ");
        int numberOfClauses=myObj2.nextInt();

        System.out.println("Give the number of maxRestarts: ");
        int maxRestarts=myObj3.nextInt();

        System.out.println("Give the number of maxClimbs");
        int maxClimbs= myObj4.nextInt();

        System.out.println("For WalkSat give the double number(0-1) for the heuristic :");
        double p=myObj5.nextDouble();


        System.out.println("Give the number of the 3-SAT problems :");
        int problemNumber=myObj6.nextInt();

        for(int i=0; i<problemNumber; i++) {
            CNF generatedExample = generateCNF(numberOfVariables, numberOfClauses);
            boolean[] solved = GSATAlgorithm(generatedExample, maxRestarts, maxClimbs);

            printSolved(solved);

            boolean[] solved2 = WALKSATALGORITHM(generatedExample, maxRestarts, maxClimbs, p);
            printSolved(solved2);

            System.out.println("END PROBLEM :"+ (i+1) +"\n");
            System.out.println("---------------------------------------------------------------------------");

        }


    }

    public static CNF generateCNF(int numberOfVariables,int numberOfClauses)
    {

        ArrayList<Clause> clauses = new ArrayList<Clause>();


    while(clauses.size()!=numberOfClauses){

        Literal literalOne;
        Literal literalTwo;
        Literal literalThree;

        int literalOneIndex=0;
        int literalTwoIndex=0 ;
        int literalThreeIndex=0;

        double d=1/(numberOfVariables * 1.0);

        while(literalOneIndex==0 || literalTwoIndex==0 || literalThreeIndex==0 ||literalOneIndex==literalTwoIndex || literalOneIndex==literalThreeIndex || literalTwoIndex==literalThreeIndex || literalOneIndex==-literalOneIndex || literalTwoIndex==-literalTwoIndex || literalThreeIndex== -literalThreeIndex) {
                literalOneIndex = (int) (Math.random() * numberOfVariables );

                literalTwoIndex = (int) (Math.random() * numberOfVariables );

                literalThreeIndex = (int) (Math.random() * numberOfVariables);
            }


        if (Math.random() > d) {
            literalOne = new Literal(literalOneIndex, true);
        } else {
            literalOneIndex = -literalOneIndex;
            literalOne = new Literal(literalOneIndex, false);
        }

        if (Math.random() > d) {
            literalTwo = new Literal(literalTwoIndex, true);
        } else {
            literalTwoIndex = -literalTwoIndex;
            literalTwo = new Literal(literalTwoIndex, false);
        }
        if (Math.random() > d) {
            literalThree = new Literal(literalThreeIndex, true);
        } else {
            literalThreeIndex = -literalThreeIndex;
            literalThree = new Literal(literalThreeIndex, false);
        }
        clauses.add(new Clause(literalOne, literalTwo, literalThree));

        for(int i=0; i<clauses.size(); i++){
            for(int j=i+1; j<clauses.size();j++){
                if(clauses.get(i)==clauses.get(j)){
                    clauses.remove(i);
                }
            }
        }




        System.out.println(literalOneIndex + " " + literalTwoIndex + " " + literalThreeIndex);
        System.out.println(" ");


    }

        return new CNF(clauses, numberOfVariables);
        }


   public static boolean[] GSATAlgorithm(CNF cnfToSolve, int maxRestarts, int maxClimbs)
    {

        boolean[] truthAssignments = new boolean[cnfToSolve.numberOfVariables()];
    for(int i=1; i<=maxRestarts; i++){

        randomlyGenerateTruthAssignments(truthAssignments);

        for(int j=1; j<=maxClimbs; j++)
        {

            int clausesSolved =cnfToSolve.Cost(truthAssignments);

            if(clausesSolved == cnfToSolve.numberOfClauses()){
                truthAssignments[0]=true;

                int cost=cnfToSolve.numberOfClauses()-clausesSolved;

                System.out.println("The sum of FALSE Clauses for GSAT is:"+ cost+"\n");

                return truthAssignments;

            }
            truthAssignments=randomBestSuccesor(truthAssignments,cnfToSolve);


        }
    }

    int cost= cnfToSolve.numberOfClauses()- cnfToSolve.Cost(truthAssignments);
    System.out.println("The sum of FALSE Clauses for GSAT is:"+ cost+"\n");


    truthAssignments[0] = false;
    return truthAssignments;
    }




    public static void randomlyGenerateTruthAssignments(boolean[] truthAssignments)
    {
        for (int i = 1; i <truthAssignments.length; i++)
        {
            Random rd = new Random();
            truthAssignments[i]= rd.nextBoolean();
        }
    }

    public static boolean [] randomBestSuccesor(boolean[] oldTruthAssignments, CNF cnfToSolve)
    {
        boolean[] potentialTruthAssignments=oldTruthAssignments.clone();

        int[] numberValue = new int[oldTruthAssignments.length];

       for(int i=0; i< cnfToSolve.numberOfVariables(); i++) {

            numberValue[i]=i+1 ;

       }

        Collections.shuffle(Arrays.asList(numberValue));
       for(int i =0; i< cnfToSolve.numberOfVariables(); i++) {


           potentialTruthAssignments[numberValue[i]] =!potentialTruthAssignments[numberValue[i]];

           int potentialAssignment = cnfToSolve.Cost(potentialTruthAssignments);
           int currentAssignment = cnfToSolve.Cost(oldTruthAssignments);

           if (potentialAssignment >= currentAssignment) {

               return potentialTruthAssignments;
           }

           potentialTruthAssignments[numberValue[i]] =!potentialTruthAssignments[numberValue[i]];
       }
        return oldTruthAssignments;
    }

    public static boolean[] WALKSATALGORITHM(CNF cnfToSolve, int maxRestarts, int maxClimbs, double p)
    {

        boolean[] truthAssignments = new boolean[cnfToSolve.numberOfVariables()];
        for(int i=1; i<=maxRestarts; i++){

            randomlyGenerateTruthAssignments(truthAssignments);

            for(int j=1; j<=maxClimbs; j++)
            {

                int clausesSolved =cnfToSolve.Cost(truthAssignments);

                if(clausesSolved == cnfToSolve.numberOfClauses()){
                    truthAssignments[0]=true;

                    int cost=cnfToSolve.numberOfClauses()-clausesSolved;

                    System.out.println("The sum of FALSE Clauses for WALKSAT is :"+ cost+"\n");

                    return truthAssignments;

                }
                truthAssignments=randomBestClause(truthAssignments,cnfToSolve,p);


            }
        }

        int cost= cnfToSolve.numberOfClauses()- cnfToSolve.Cost(truthAssignments);
        System.out.println("The sum of FALSE Clauses for WALKSAT is:"+ cost+"\n");

        truthAssignments[0] = false;
        return truthAssignments;
    }

    public static boolean [] randomBestClause(boolean[] oldTruthAssignments, CNF cnfToSolve,double p)
    {
        boolean[] potentialTruthAssignments=oldTruthAssignments.clone();

        ArrayList<Clause> falseClauses= cnfToSolve.getfalseClauses(oldTruthAssignments);
        Random rand = new Random();

        Clause falseClause=falseClauses.get(rand.nextInt(falseClauses.size()));

        int []valueClause=falseClause.getClauseValue();


        ArrayList<Integer> flipValue= new ArrayList<Integer>();

        for(int i=0; i< valueClause.length; i++){
           int currentcost= cnfToSolve.Cost(oldTruthAssignments);

           potentialTruthAssignments[valueClause[i]] =!potentialTruthAssignments[valueClause[i]];

           int cost= cnfToSolve.Cost(potentialTruthAssignments);

           if(currentcost<cost){
               flipValue.add(valueClause[i]);

           }
           potentialTruthAssignments[valueClause[i]] =!potentialTruthAssignments[valueClause[i]];
       }
       if(flipValue.size()>0) {
           int flipIndex = rand.nextInt(flipValue.size());

           potentialTruthAssignments[flipIndex]=!potentialTruthAssignments[flipIndex];

           return potentialTruthAssignments;
       }

       else{
            boolean randBoolean=Math.random() <p;

            if(randBoolean){

                int flipIndex =rand.nextInt(valueClause.length);
                potentialTruthAssignments[valueClause[flipIndex]] =!potentialTruthAssignments[valueClause[flipIndex]];
                return potentialTruthAssignments;
            }
            else{
                int index=0;
                int currentFalseCost= cnfToSolve.NumberofFalseClauses(oldTruthAssignments);
                for(int i=0; i<valueClause.length; i++) {

                    potentialTruthAssignments[valueClause[i]] = !potentialTruthAssignments[valueClause[i]];

                    int falseCost = cnfToSolve.NumberofFalseClauses(potentialTruthAssignments);

                    if (falseCost < currentFalseCost) {

                        currentFalseCost = cnfToSolve.NumberofFalseClauses(potentialTruthAssignments);

                        potentialTruthAssignments[valueClause[i]] = !potentialTruthAssignments[valueClause[i]];

                        index = valueClause[i];

                    }
                }

                    potentialTruthAssignments[index]=!potentialTruthAssignments[index];

                  return potentialTruthAssignments;


            }

            }

       }











    public static void printSolved(boolean[] solved)
    {

            if(solved[0]){
            System.out.println("An answer was found, it is as follows :");}
            else
            {
                System.out.println("There is not found an answer :");

            }
            for (int i = 1; i <solved.length; i++)
            {
                System.out.println("Variable " + i + " is " + solved[i]);

            }
        System.out.println("\n");


    }
}









