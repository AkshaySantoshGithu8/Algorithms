/*
 * Name: Akshay Santosh
 * EID: as235628
 */

import java.util.*;

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
        {
            if (schoolMatch.get(x) != -1) 
            studentsAt.get(schoolMatch.get(x)).add(x);

        }

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
    public Matching stableMatchingGaleShapley_schooloptimal(Matching problem) 
    {
        //program starts with only a list of school's preferences and a list of student's preferences
        ArrayList<ArrayList<Integer>> studentsRanked = new ArrayList<>();

        for (ArrayList<Integer> pList : problem.getSchoolPreference())
        studentsRanked.add(new ArrayList<>(pList));

        ArrayList<ArrayList<Integer>> schoolsRanked = problem.getStudentPreference();
        //make a queue of queues to keep track of schools, once a school is done recruiting, poll. and once a students has been proposed to, poll
        ArrayList<Integer> schoolOpenings = new ArrayList<>(problem.getSchoolOpenings());
        //HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>(); //keeps track of students
        //in this version, schools propose
        
        boolean loopCond = true;

        ArrayList<Integer> studentMatches = new ArrayList<>(Collections.nCopies(problem.getStudentCount(), -1)); //initialize each school to -1 and predefined size shortcut
        while (loopCond) //poll schools' pref-list queues as you propose, poll student from sub-q u propose to
        {
            loopCond = false;
            for (int schoolIndex = 0; schoolIndex < studentsRanked.size(); schoolIndex++)
            {
                ArrayList<Integer> school = studentsRanked.get(schoolIndex); //get list of students for this school
                if (school.isEmpty() || schoolOpenings.get(schoolIndex) == 0) //current school doesnt have an opening or is empty
                    continue;

                loopCond = true;
                Integer student = school.remove(0); //pick the first student from the list not proposed
                
                if (studentMatches.get(student) == -1) //student is open
                {
                    studentMatches.set(student, schoolIndex);
                    schoolOpenings.set(schoolIndex, schoolOpenings.get(schoolIndex)-1);
                }
                else  //student is taken but likes school better
                {
                    //problem.getStudentMatching().get(student) is the student that is matched
                    //get matched students' pref list
                    //if student's match is higher index than this school
                    // current school = problem.getStudentMatching().get(student)
                    // new school = schoolIndex
                    // compare using problem.getStudentPreference().get(student)
                    if (schoolsRanked.get(student).indexOf(schoolIndex) < schoolsRanked.get(student).indexOf(studentMatches.get(student)))
                    {
                        int oldSchool = studentMatches.get(student);
                        studentMatches.set(student, schoolIndex);
                        schoolOpenings.set(schoolIndex, schoolOpenings.get(schoolIndex)-1);
                        schoolOpenings.set(oldSchool, schoolOpenings.get(oldSchool)+1);
                    }
                }
            //else skip
            }
            
            
            
           
        }
        //problem.setStudentMatching(studentMatches);
        return new Matching(problem, studentMatches);
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