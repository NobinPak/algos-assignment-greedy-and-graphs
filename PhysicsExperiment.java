/**
 /**
 * Physics Experiment
 * Author: Your Name and Carolyn Yao
 * Does this compile or finish running within 5 seconds? Y
 */



/**
 * This class implements a greedy scheduler to assign students
 * to steps in a physics experiment. There are three test cases in the main
 * method. Please read through the whole file before attempting to code the
 * solution.
 *
 * You will only be graded on code you add to the scheduleExperiments method.
 * Do not mess with the existing formatting and identation.
 * You don't need to use the helper methods, but if they come in handy setting
 * up a custom test case, feel free to use them.
 */
public class PhysicsExperiment {

    /**
     * The actual greedy scheduler you will be implementing!
     * @param "numStudents The number of students who can participate, m
     * @param "numSteps The number of steps in the experiment, n
     * @param "signUpTable An easy lookup tool, signUpTable[x][Y] = student X signed up or did not sign up for step Y.
     *      Example:
    signUpTable[1][3] = 1 if Student 1 signed up for Step 3
    signUpTable[1][3] = 0 if Student 1 didn't sign up for Step 3
     * @return scheduleTable: a table similar to the signUpTable where scheduleTable[X][Y] = 1 means
     *     student X is assigned to step Y in an optimal schedule
     */
    public void printArray(int [] array){
        for(int i=0;i<array.length;i++)
        {
            System.out.print(array[i]+", ");
        }
        System.out.println("");
    }

    public void print2DArray(int [][] array){
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[0].length;j++){
                System.out.print(array[i][j]+", ");
            }
            System.out.println();
        }
    }
    public int[][] normalize_signUp_table(int [][] signUpTable){
        int[][] new_table = new int[signUpTable.length-1][signUpTable[0].length-1];
        for(int i=1;i<signUpTable.length;i++){
            for(int j=1;j<signUpTable[0].length;j++){
                if(signUpTable[i][j]==0){
                    new_table[i-1][j-1]=0;
                }else if(signUpTable[i][j]==1){
                    new_table[i-1][j-1] = j;
                }
            }
        }
        return new_table;
    }

    public boolean element_is_present(int [] arr, int val){
        for (int element : arr) {
            if (element == val) {
                return true;
            }
        }
        return false;
    }
    public int find_longest(int [] lista, int k ) {
        int  j = k;

        while(element_is_present(lista,j)){
            j+=1;
        }

        return j-1;
    }
    public int[][] scheduleExperiments(
            int numStudents,
            int numSteps,
            int[][] signUpTable
    ) {
        // Your scheduleTable is initialized as all 0's so far. Your code will put 1's
        // in the table in the right places based on the return description
        int[][] scheduleTable = new int[numStudents][numSteps + 1];

        // Your code goes here

        signUpTable = normalize_signUp_table(signUpTable);

        int maxn = 0;
        int n = signUpTable.length;
        for (int i=0;i<n;i++) {
            for (int j=0; j<signUpTable[0].length; j++){
                if ( maxn < signUpTable[i][j] ) {
                    maxn = signUpTable[i][j];
                }
            }
        }

        int i    = 1;
        int ind = 0;
        int k  = 0;
        int VOID = -1;
        String chk = "";
        while(i<=maxn){
            int kmax = 0;
            int j0 = 0;
            for(int j=0;j<n;j++){
                if(element_is_present(signUpTable[j],i)){
                    k = find_longest(signUpTable[j],i);
                    if(k>kmax){
                        kmax = k;
                        j0=j;
                    }
                }
            }
            chk+=String.valueOf(j0+1);
            if(j0!=VOID){
                int [] one_path = new int [numSteps+1];
                one_path[0] = j0+1;
                for(int x = i;x<=kmax; x++){
                    one_path[x] = x;
                }
                scheduleTable[ind] = one_path;
                i=kmax+1;
                ind+=1;
            }
        }
        if(chk.length() != numStudents){
            int s1 = 0;
            int s2 = 0;
            int temp = numStudents;
            for(int h=0;h<chk.length();h++){
                s1+=Integer.parseInt(String.valueOf(chk.charAt(h)));
            }
            while(temp>0){
                s2+=temp;
                temp--;
            }
            int ans = s2-s1;
            for(int h=0;h<numStudents;h++)
            {
                if(scheduleTable[h][0]==0){
                    scheduleTable[h][0] = ans;
                    break;
                }
            }
        }

        //removing duplicate student
        for(int ii=0;ii<scheduleTable.length;ii++){
            for(int jj=ii+1;jj<scheduleTable.length;jj++){
                if(scheduleTable[ii][0]==scheduleTable[jj][0]){
                    scheduleTable[ii][0]=0;
                    for(int kk=1;kk<scheduleTable[0].length;kk++){
                        if(scheduleTable[ii][kk]!=0){
                            for(int ll=1;ll<scheduleTable[0].length;ll++){
                                if(scheduleTable[jj][ll]==0){
                                    scheduleTable[jj][ll]=scheduleTable[ii][kk];
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return scheduleTable;
    }

    /**
     * Makes the convenient lookup table based on the steps each student says they can do
     * @param numSteps the number of steps in the experiment
     * @param studentSignUps student sign ups ex: {{1, 2, 4}, {3, 5}, {6, 7}}
     * @return a lookup table so if we want to know if student x can do step y,
    lookupTable[x][y] = 1 if student x can do step y
    lookupTable[x][y] = 0 if student x cannot do step y
     */
    public int[][] makeSignUpLookup(int numSteps, int[][] studentSignUps) {
        int numStudents = studentSignUps.length;
        int[][] lookupTable = new int[numStudents+1][numSteps + 1];
        for (int student = 1; student <= numStudents; student++) {
            int[] signedUpSteps = studentSignUps[student-1];
            for (int i = 0; i < signedUpSteps.length; i++) {
                lookupTable[student][signedUpSteps[i]] = 1;
            }
        }
        return lookupTable;
    }

    public boolean chk_if_arrays_is_full_of_zeroes(int [] arr){
        for(int ele : arr){
            if(ele!=0)return false;
        }
        return true;
    }

    /**
     * Prints the optimal schedule by listing which steps each student will do
     * Example output is Student 1: 1, 3, 4
     * @param schedule The table of 0's and 1's of the optimal schedule, where
     *   schedule[x][y] means whether in the optimal schedule student x is doing step y
     */
    public void printResults(int[][] schedule){   
    	int student = 1;
        while(student<schedule.length+1){
            for(int i=0;i<schedule.length;i++){
                if(schedule[i][0]==student){
                    System.out.print("Student "+student+": --> ");
                    for(int step = 1;step<schedule[i].length;step++){
                        if(schedule[i][step]==0)continue;
                        System.out.print(schedule[i][step]+" ");
                    }
                    System.out.println();
                    break;
                }
            }
            student++;
        }
//        boolean full_of_zeroes = false;
//        for(int i=0;i<schedule.length;i++){
//            if(schedule[i][0]==0)continue;
//            if(chk_if_arrays_is_full_of_zeroes(schedule[i])){
//                full_of_zeroes = true;
//            }
//            for(int step = 0;step<schedule[0].length;step++){
//
//
//                if(step ==0 || full_of_zeroes){
//                    System.out.print("Student "+schedule[i][step]+" -> ");
//                    full_of_zeroes = false;
//                }else{
//                    if(schedule[i][step]==0)continue;
//                    System.out.print(schedule[i][step]+", ");
//                }
//            }
//            System.out.println();
//        }
    }

    /**
     * This validates the input data about the experiment step sign-ups.
     * @param numStudents the number of students
     * @param numSteps the number of steps
     * @param signUps the data given about which steps each student can do
     * @return true or false whether the input sign-ups match the given number of
     *    students and steps, and whether all the steps are guaranteed at least
     *    one student.
     */
    public boolean inputsValid(int numStudents, int numSteps, int signUps[][]) {
        int studentSignUps = signUps.length;

        // Check if there are any students or signups
        if (numStudents < 1 || studentSignUps < 1) {
            System.out.println("You either did not put in any student or any signups");
            return false;
        }

        // Check if the number of students and sign-up rows matches
        if (numStudents != studentSignUps) {
            System.out.println("You input " + numStudents + " students but your signup suggests " + signUps.length);
            return false;
        }

        // Check that all steps are guaranteed in the signups
        int[] stepsGuaranteed = new int[numSteps + 1];
        for (int i = 0; i < studentSignUps; i++) {
            for (int j = 0; j < signUps[i].length; j++) {
                stepsGuaranteed[signUps[i][j]] = 1;
            }
        }
        for (int step = 1; step <= numSteps; step++) {
            if (stepsGuaranteed[step] != 1) {
                System.out.println("Your signup is incomplete because not all steps are guaranteed.");
                return false;
            }
        }

        return true;
    }

    /**
     * This sets up the scheduling test case and calls the scheduling method.
     * @param numStudents the number of students
     * @param numSteps the number of steps
     * @param signUps which steps each student can do, in order of students and steps
     */
    public void makeExperimentAndSchedule(int experimentNum, int numStudents, int numSteps, int[][] signUps) {
        System.out.println("-------  Experiment " + experimentNum + " -------");
        if (!inputsValid(numStudents, numSteps, signUps)) {
            System.out.println("Experiment signup info is invalid");
            return;
        }
        int[][] signUpsLookup = makeSignUpLookup(numSteps, signUps);
        int[][] schedule = scheduleExperiments(numStudents, numSteps, signUpsLookup);
        printResults(schedule);
        System.out.println("");
    }

    /**
     * You can make additional test cases using the same format. In fact the helper functions
     * I've provided will even check your test case is set up correctly. Do not touch any of
     * of the existing lines. Just make sure to comment out or delete any of your own test cases
     * when you submit. The three experiment test cases existing in this main method should be
     * the only output when running this file.
     */
    public static void main(String args[]){
        PhysicsExperiment pe = new PhysicsExperiment();

        // Experiment 1: Example 1 from README, 3 students, 6 steps:
        int[][] signUpsExperiment1 = {{1, 2, 3, 5}, {2, 3, 4}, {1, 4, 5, 6}};
        pe.makeExperimentAndSchedule(1, 3, 6, signUpsExperiment1);

        // Experiment 2: Example 2 from README, 4 students, 8 steps
        int[][] signUpsExperiment2 = {{5, 7, 8}, {2, 3, 4, 5, 6}, {1, 5, 7, 8}, {1, 3, 4, 8}};
        pe.makeExperimentAndSchedule(2, 4, 8, signUpsExperiment2);

        // Experiment 3: Another test case, 5 students, 11 steps
        int[][] signUpsExperiment3 = {{7, 10, 11}, {8, 9, 10}, {2, 3, 4, 5, 7}, {1, 5, 6, 7, 8}, {1, 3, 4, 8}};
        pe.makeExperimentAndSchedule(3, 5, 11, signUpsExperiment3);

    }
}
