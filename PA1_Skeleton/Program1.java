/*
 * Name: Akshay Santosh
 * EID: as235628
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) 
    {
        //checks for no unstable stable matches
        //loop each student's match

        int amount_schools = problem.getSchoolCount();
        int amount_students = problem.getStudentCount();
        ArrayList<ArrayList<Integer>> schools = problem.getSchoolPreference();
        ArrayList<ArrayList<Integer>> students = problem.getStudentPreference(); //prefs
        ArrayList<Integer> schoolMatch = problem.getStudentMatching();
        
        ArrayList<ArrayList<Integer>> studentsAt = new ArrayList<>(); // form a list of students' indices (in schoolMatch) at each school
        for (ArrayList<Integer> i : schools)
        {
            studentsAt.add(new ArrayList<>());
        }
        for (int x= 0; x < amount_students; x++)
        studentsAt.get(schoolMatch.get(x)).add(x); 

        for (int student = 0; student < amount_students; student++)
        {
            
            for (int school : students.get(student))
            {
                if (school == schoolMatch.get(student))
                    break;

                //get worst student index and compare with current student
                ArrayList<Integer> currStud = studentsAt.get(school);

                if (problem.getSchoolOpenings().get(school) > currStud.size()) //if you have open spots
                    return false;

                Integer worstStud = currStud.get(0);

                for (Integer currentStudent : currStud)
                {
                    if (schools.get(school).indexOf(currentStudent) > schools.get(school).indexOf(worstStud)) //the index of current student is greater (less priority) than the index of the worst student
                    {
                        //they're the new worst student
                        worstStud = currentStudent;
                    }
                }
                if (schools.get(school).indexOf(student) < schools.get(school).indexOf(worstStud))
                    return false;
            }

        }
        return true;
    }

    /**
     * Determines a school-optimal stable matching from the given input set.
     *
     * In this version of the problem, schools have multiple openings and
     * rank students in order of preference. The school-optimal version of
     * Gale-Shapley is obtained by having schools make proposals to students.
     *
     * @return A school-optimal stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_schooloptimal(Matching problem) {
        /* TODO implement this function */

        return problem;
    }

    /**
     * Determines a student-optimal stable matching from the given input set.
     *
     * In this version of the problem, schools have multiple openings and
     * students rank schools in order of preference. The student-optimal
     * version of Gale-Shapley is obtained by having students make proposals
     * to schools.
     *
     * @return A student-optimal stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {
        /* TODO implement this function */

        return problem;
    }
}