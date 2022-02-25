package Hashcode;

// HashCode 2022
// Solution by Anmol Sharma

import java.util.*;
import java.lang.*;
import java.io.*;


public class Main {
    public static long mod = 1000000007;
    public static long mod2 = 998244353;

    public static void main(String[] args) throws java.lang.Exception {

        String[] input = {
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/in/a.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/in/b.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/in/c.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/in/d.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/in/e.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/in/f.txt"
        };
        String[] output = {
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/out/a.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/out/b.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/out/c.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/out/d.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/out/e.txt",
                "/Users/anmolsharma/Desktop/Projects/CP/src/Hashcode/out/f.txt"
        };

        for (int files = 0; files <= 5; files++) {
            // Write solution here!!

            System.out.println("Running on file " + files);

            BufferedReader br = new BufferedReader(new FileReader(input[files]));
            Writer out = new Writer(new FileWriter(output[files]));

            String[] inp = br.readLine().split(" ");
            int n = Integer.parseInt(inp[0]);
            int m = Integer.parseInt(inp[1]);

            ArrayList<String> outputList = new ArrayList<>();
            HashMap<String, ArrayList<String>> outputMap = new HashMap<>();

            HashMap<String, HashSet<String>> skillsToContributors = new HashMap<>();

            Contributor[] contributors = new Contributor[n];
            HashMap<String, Contributor> contributorMap = new HashMap<>();

            for(int i = 0; i < n; i++) {
                inp = br.readLine().split(" ");
                String name = inp[0];
                int numberOfSkills = Integer.parseInt(inp[1]);
                HashMap<String, Integer> skills = new HashMap<>();
                for(int j = 0; j < numberOfSkills; j++) {
                    inp = br.readLine().split(" ");
                    skills.put(inp[0], Integer.parseInt(inp[1]));
                    if(skillsToContributors.containsKey(inp[0])) {
                        skillsToContributors.get(inp[0]).add(name);
                    } else {
                        HashSet<String> set = new HashSet<>();
                        set.add(name);
                        skillsToContributors.put(inp[0], set);
                    }
                }
                contributorMap.put(name, new Contributor(name, skills));
                contributors[i] = new Contributor(name, skills);
            }

            Project[] projects = new Project[m];
            for(int i = 0; i < m; i++) {
                inp = br.readLine().split(" ");
                String name = inp[0];
                int days = Integer.parseInt(inp[1]);
                int score = Integer.parseInt(inp[2]);
                int bestBefore = Integer.parseInt(inp[3]);
                int numberOfSkills = Integer.parseInt(inp[4]);
                HashMap<String, Integer> skills = new HashMap<>();
                ArrayList<String> req = new ArrayList<>();
                ArrayList<SkillPair> reqPair = new ArrayList<>();
                for(int j = 0; j < numberOfSkills; j++) {
                    inp = br.readLine().split(" ");
                    req.add(inp[0]);
                    reqPair.add(new SkillPair(inp[0], Integer.parseInt(inp[1])));
//                    if(name.equals("StadiaZv2")) System.out.println(inp[0] + " " + inp[1]);
                    skills.put(inp[0], Integer.parseInt(inp[1]));
                }
                projects[i] = new Project(name, skills, days, bestBefore, numberOfSkills, score, req, reqPair);
            }

            Arrays.sort(projects, new Comparator<Project>() {
                @Override
                public int compare(Project o1, Project o2) {
                    // Jai Shree Ram
//                    if (o1.score == o2.score) {
//                        if(o1.bestBefore == o2.bestBefore) {
//                            return o1.days - o2.days;
//                        }
//                        return o2.bestBefore - o1.bestBefore;
//                    }
//                    return o2.score - o1.score;


                    if(o1.days == o2.days) {
                        if(o1.score == o2.score) {
                            return o1.bestBefore - o2.bestBefore;
                        }
                        return o2.score - o1.score;
                    }
                    return o1.days - o2.days;


//                    if(o1.bestBefore == o2.bestBefore) {
//                        if(o1.days == o2.days) {
//                            return o2.score - o1.score;
//                        } return o1.days - o2.days;
//                    } else return o1.bestBefore - o2.bestBefore;
                }
            });

            Queue<Long> queue = new PriorityQueue<>();
            queue.add(1L);

            while (!queue.isEmpty()) {
                long currDay = queue.poll();
                for (int i = 0; i < m; i++) {

                    // ith project
                    Project project = projects[i];

                    // can be completed
                    if (project.isCompleted) continue;

                    int nSkills = project.numOfSkills;
                    HashMap<String, Integer> skills = project.requiredSkillMap;
                    boolean isPossible = true;

                    // for output
                    ArrayList<String> contributorsList = new ArrayList<>();
                    HashSet<String> contributorsSet = new HashSet<>();

                    HashMap<String, String> store = new HashMap<>();

                    // iterate over all required skills
                    for (SkillPair skillP : project.requiredSkillPair) {

                        String skill = skillP.skill;
                        int skillLevel = skillP.level;

                        // if skill is not present in any contributor
                        if (skillsToContributors.containsKey(skill)) {
                            boolean found = false;
                            String cName = "";

                            // iterate over all contributors with that skill
                            for (String contributor : skillsToContributors.get(skill)) {
                                Contributor c = contributorMap.get(contributor);

                                // if contributor is already in the project
                                if(contributorsSet.contains(c.name)) continue;



                                // if contributor is not working and has the skill and matches the project requirements
                                if (c.willBeAvailableAfter > currDay || c.skillMap.get(skill) < skillLevel) {
                                    continue;
                                }
                                if (c.skillMap.get(skill) == skillLevel) {

                                    store.put(c.name, skill);

                                    c.skillMap.put(skill, c.skillMap.get(skill) + 1);
                                }
                                found = true;
                                cName = contributor;
                                c.willBeAvailableAfter = currDay + project.days;
                            }

                            // if contributor found
                            if (found) {
                                contributorsList.add(cName);
                                contributorsSet.add(cName);
                            } else {
                                // banda h but level ka nahi h
                                isPossible = false;
                                break;
                            }
                        } else {
                            // banda hi nahi h
                            isPossible = false;
//                            project.canBeCompleted = false;
                            break;
                        }
                    }
                    if (isPossible) {
                        project.isCompleted = true;
                        queue.add(project.days + currDay);
                        outputList.add(project.name);
                        outputMap.put(project.name, contributorsList);
                    } else {
                        for(String contributor : contributorsList) {
                            contributorMap.get(contributor).willBeAvailableAfter = currDay - project.days;
                        }
                        for(String contributor : store.keySet()) {
                            String skill = store.get(contributor);
                            contributorMap.get(contributor).skillMap.put(skill, contributorMap.get(contributor).skillMap.get(skill) - 1);
                        }
                    }
                }
            }

            // print output
            out.println(outputMap.size());
            for (String project : outputList) {
                out.println(project);
                for (String contributor : outputMap.get(project)) {
                    out.print(contributor + " ");
                }
                out.println();
            }


            System.out.println("Done with test case " + files);





            out.flush();
        }


    }


    static class Contributor {
        String name;
        HashMap<String, Integer> skillMap;
        long willBeAvailableAfter;
        boolean isWorking;
        public Contributor(String name, HashMap<String, Integer> skillMap) {
            this.name = name;
            this.skillMap = skillMap;
            this.isWorking = false;
            willBeAvailableAfter = 0;
        }
    }

    static class Project {
        String name;
        ArrayList<SkillPair> requiredSkillPair;
        HashMap<String, Integer> requiredSkillMap;
        ArrayList<String> requiredSkillList;
        boolean isCompleted;
        boolean canBeCompleted;
        int days;
        int score;
        int bestBefore;
        int numOfSkills;

        public Project(String name, HashMap<String, Integer> skillMap, int days, int bestBefore, int numOfSkills, int score, ArrayList<String> requiredSkillList, ArrayList<SkillPair> requiredSkillPair) {
            this.name = name;
            this.requiredSkillMap = skillMap;
            this.isCompleted = false;
            this.days = days;
            this.bestBefore = bestBefore;
            this.numOfSkills = numOfSkills;
            this.score = score;
            this.canBeCompleted = true;
            this.requiredSkillList = requiredSkillList;
            this.requiredSkillPair = requiredSkillPair;
        }
    }



    static class SkillPair {
        String skill;
        int level;
        public SkillPair(String s, int l) {
            skill = s;
            level = l;
        }
        @Override
        public int hashCode() {
            return skill.hashCode() * level;
        }
    }


    static long modSum(long p, long q) {
        if (q > 0) {
            return (p + q) % mod;
        } else {
            return (p + q + mod) % mod;
        }
    }

    static long invMod(long p, long q, long m) {
        long expo = m - 2;
        while (expo != 0) {
            if ((expo & 1) == 1) {
                p = (p * q) % m;
            }
            q = (q * q) % m;
            expo >>= 1;
        }
        return p;
    }

    public static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static long power(long a, long b) {
        if (b == 0)
            return 1;
        long answer = power(a, b / 2) % mod;
        answer = (answer * answer) % mod;
        if (b % 2 != 0)
            answer = (answer * a) % mod;
        return answer;
    }

    public static void swap(int x, int y) {
        int t = x;
        x = y;
        y = t;
    }

    public static long min(long a, long b) {
        if (a < b) return a;
        return b;
    }

    public static long divide(long a, long b) {
        return (a % mod * (power(b, mod - 2) % mod)) % mod;
    }

    public static long nCr(long n, long r) {
        long answer = 1;
        long k = min(r, n - r);
        for (long i = 0; i < k; i++) {
            answer = (answer % mod * (n - i) % mod) % mod;
            answer = divide(answer, i + 1);
        }
        return answer % mod;
    }

    public static boolean plaindrome(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return (str.equals((sb.reverse()).toString()));
    }

    public static class Pair {
        int a;
        int b;

        Pair(int s, int e) {
            a = s;
            b = e;
        }

        static void sort(Pair[] a) {
            Arrays.sort(a, (o1, o2) -> {
                if (o1.a == o2.a) {
                    return o1.b - o2.b;
                } else {
                    return o1.a - o2.a;
                }
            });
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Pair pair = (Pair) o;
            return a == pair.a && b == pair.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    static class Writer extends PrintWriter {

        public Writer(java.io.Writer out) {
            super(out);
        }

        public Writer(java.io.Writer out, boolean autoFlush) {
            super(out, autoFlush);
        }

        public Writer(OutputStream out) {
            super(out);
        }

        public Writer(OutputStream out, boolean autoFlush) {
            super(out, autoFlush);
        }

        public Writer(FileWriter fileName) throws java.io.FileNotFoundException { super(fileName);}

        public void printArray(int[] arr) {
            for (int j : arr) {
                print(j);
                print(' ');
            }
            println();
        }

        public void printArray(long[] arr) {
            for (long j : arr) {
                print(j);
                print(' ');
            }
            println();
        }

    }


}
