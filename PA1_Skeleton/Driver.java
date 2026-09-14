import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Driver {
    public static String filename;
    public static boolean testGS_h;
    public static boolean testGS_s;
    public static boolean testMatching;

    public static void main(String[] args) throws Exception {
        parseArgs(args);

        if (testMatching) {
            Program1 program = new Program1();
            Matching problem = parseMatchingProblemWithExample(filename);

            boolean isStable = program.isStableMatching(problem);

            if (isStable)
                System.out.println("Matching provided is stable");
            else
                System.out.println("Matching provided is not stable");

            testRun(problem);
        } else {
            Matching problem = parseMatchingProblem(filename);
            testRun(problem);
        }
    }

    private static void usage() {
        System.err.println("usage: java Driver [-h] [-s] [-m] <filename>");
        System.err.println("\t-h\tTest Gale-Shapley school optimal implementation");
        System.err.println("\t-s\tTest Gale-Shapley student optimal implementation");
        System.err.println("\t-m\tCheck if input matching is stable");
        System.exit(1);
    }

    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            usage();
        }

        filename = "";
        testGS_h = false;
        testGS_s = false;
        testMatching = false;

        boolean flagsPresent = false;

        for (String s : args) {
            if (s.equals("-h")) {
                flagsPresent = true;
                testGS_h = true;
            } else if (s.equals("-s")) {
                flagsPresent = true;
                testGS_s = true;
            } else if (s.equals("-m")) {
                flagsPresent = true;
                testMatching = true;
            } else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testGS_h = true;
            testGS_s = true;
        }
    }

    public static Matching parseMatchingProblemWithExample(String inputFile)
            throws FileNotFoundException {

        int m = 0;
        int n = 0;

        ArrayList<ArrayList<Integer>> schoolPrefs;
        ArrayList<ArrayList<Integer>> studentPrefs;
        ArrayList<Integer> schoolOpenings;
        ArrayList<Integer> exampleMatching;

        Scanner sc = new Scanner(new File(inputFile));

        String[] inputSizes = sc.nextLine().split(" ");

        m = Integer.parseInt(inputSizes[0]);
        n = Integer.parseInt(inputSizes[1]);

        schoolOpenings = readOpeningsList(sc, m);
        schoolPrefs = readPreferenceLists(sc, m);
        studentPrefs = readPreferenceLists(sc, n);

        Matching problem = new Matching(
                m,
                n,
                schoolPrefs,
                studentPrefs,
                schoolOpenings);

        exampleMatching = readOpeningsList(sc, n);
        problem.setStudentMatching(exampleMatching);

        return problem;
    }

    public static Matching parseMatchingProblem(String inputFile)
            throws FileNotFoundException {

        int m = 0;
        int n = 0;

        ArrayList<ArrayList<Integer>> schoolPrefs;
        ArrayList<ArrayList<Integer>> studentPrefs;
        ArrayList<Integer> schoolOpenings;

        Scanner sc = new Scanner(new File(inputFile));

        String[] inputSizes = sc.nextLine().split(" ");

        m = Integer.parseInt(inputSizes[0]);
        n = Integer.parseInt(inputSizes[1]);

        schoolOpenings = readOpeningsList(sc, m);
        schoolPrefs = readPreferenceLists(sc, m);
        studentPrefs = readPreferenceLists(sc, n);

        Matching problem = new Matching(
                m,
                n,
                schoolPrefs,
                studentPrefs,
                schoolOpenings);

        return problem;
    }

    private static ArrayList<Integer> readOpeningsList(Scanner sc, int count) {
        ArrayList<Integer> openings = new ArrayList<Integer>(0);

        String[] values = sc.nextLine().split(" ");

        for (int i = 0; i < count; i++) {
            openings.add(Integer.parseInt(values[i]));
        }

        return openings;
    }

    private static ArrayList<ArrayList<Integer>> readPreferenceLists(
            Scanner sc, int count) {

        ArrayList<ArrayList<Integer>> preferenceLists =
                new ArrayList<ArrayList<Integer>>(0);

        for (int i = 0; i < count; i++) {
            String line = sc.nextLine();
            String[] preferences = line.split(" ");

            ArrayList<Integer> preferenceList =
                    new ArrayList<Integer>(0);

            for (Integer j = 0; j < preferences.length; j++) {
                preferenceList.add(Integer.parseInt(preferences[j]));
            }

            preferenceLists.add(preferenceList);
        }

        return preferenceLists;
    }

    public static void testRun(Matching problem) {
        Program1 program = new Program1();
        boolean isStable;

        if (testGS_h) {
            Matching GSMatching =
                    program.stableMatchingGaleShapley_schooloptimal(problem);

            System.out.println(GSMatching);

            isStable = program.isStableMatching(GSMatching);

            System.out.printf(
                    "%s: stable? %s\n",
                    "Gale-Shapley School Optimal",
                    isStable);

            System.out.println();
        }

        if (testGS_s) {
            Matching GSMatching =
                    program.stableMatchingGaleShapley_studentoptimal(problem);

            System.out.println(GSMatching);

            isStable = program.isStableMatching(GSMatching);

            System.out.printf(
                    "%s: stable? %s\n",
                    "Gale-Shapley Student Optimal",
                    isStable);

            System.out.println();
        }
    }
}