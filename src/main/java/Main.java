import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int mode;
        System.out.println("--------------------------------------------------");
        System.out.println("        Welcome to file editor");

        System.out.println("--------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("--------------------------------------------------");
            System.out.print("         Select application mode: \n - 1 Clean r codes, sort " +
                    "nicknames lexicographically. \n - 2 Copy date and time from another txt file by nickname " +
                    "Note: register will be ignored. \n - 3 Choose all paragraphs above.");
            System.out.println("--------------------------------------------------");
            mode = scanner.nextInt();
            scanner = new Scanner(System.in);
            if (mode == 1 || mode == 3) {
                System.out.println("        Input absolute path ot file...");
                String path = scanner.nextLine();
                long start = System.currentTimeMillis();
                File file = new File(path);
                Main.sort(file);
                long finish = System.currentTimeMillis();
                double res = (double)(finish - start) / 10000;
                System.out.printf("        Execution time: %.2f seconds\n", res);
            }
            if (mode == 2 || mode == 3) {
                copyDate();
            } else if (mode == -1)
                break;
        }
    }

    public static void sort(File file) {
        // Transfer string in String Builder while iterating through string and skip r code
        StringBuilder dataFromFile = new StringBuilder();
        String str;
        String [] strArr;
        StringBuilder sb = new StringBuilder();
        Map<String, String []> addData = new TreeMap<>();
        System.out.println("--------------------------------------------------");
        System.out.println("            Trying edit your file.");
        System.out.println("--------------------------------------------------");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("--------------------------------------------------");
            System.out.println("        Searching for undeleting revocation code...");
            System.out.println("--------------------------------------------------");
            int counter = 0;
            while((str = br.readLine()) != null) {
                strArr = str.split(" ");
//                System.out.println(Arrays.toString(strArr) + " " + counter++);
                if (strArr.length == 6) {
                    counter++;
                    for(int i = 0; i < strArr.length; i++) {
                        if (i == strArr.length - 1)
                            sb.append(strArr[i]);
                        else if (i != 4)
                            sb.append(strArr[i]).append(" ");
                    }
                    str = String.valueOf(sb);
                    sb = new StringBuilder();
                }
                dataFromFile.append(str).append("\n");
            }
            if (counter > 0) {
                System.out.println("--------------------------------------------------");
                System.out.println("        Replacing all r codes...");
                System.out.println("--------------------------------------------------");
                dataFromFile.delete(dataFromFile.length() - 1 ,dataFromFile.length());
                str = String.valueOf(dataFromFile);
                try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(str);
                    System.out.println("--------------------------------------------------");
                    System.out.println("        Rewrite in file without r codes, total deleted: " + counter);
                    System.out.println("--------------------------------------------------");
                }
                try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("        Sort nicknames by alphabet");
                    System.out.println("--------------------------------------------------");
                    String res;
                    StringBuilder currentString = new StringBuilder();
                    StringBuilder stringByString = new StringBuilder();
                    while((str = bufferedReader.readLine()) != null) {
                        strArr = str.split(" ");
                        if (strArr.length > 3)
                            addData.put(strArr[2].toLowerCase(), strArr);
                    }
                    int ordinal = 1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("        Rewrite ordinal number of accounts...");
                    System.out.println("--------------------------------------------------");
                    for(Map.Entry<String, String[]> set : addData.entrySet()) {
                        String [] tmp = set.getValue();
//                    System.out.println(Arrays.toString(tmp));
                        for(int i = 0; i < tmp.length; i++) {
                            if (i == 1)
                                currentString.append(ordinal).append(" ");
                            if (i == 2)
                                currentString.append(set.getKey()).append(" ");
                            if (i == 3 || i == 0)
                                currentString.append(tmp[i]).append(" ");
                            if (i == tmp.length - 1)
                                currentString.append(tmp[i]).append("\n");
                        }
                        stringByString.append(currentString);
                        currentString = new StringBuilder();
                        ordinal++;
                    }
                    stringByString.delete(stringByString.length() - 1, stringByString.length());
                    res = String.valueOf(stringByString);
                    System.out.println("--------------------------------------------------");
                    System.out.println("        Trying to write in file new data...");
                    System.out.println("--------------------------------------------------");
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
                        bw.write(res);
                        System.out.println("--------------------------------------------------");
                        System.out.println("        Successful write in file...");
                        System.out.println("--------------------------------------------------");
                    }
                }
            } else {
                System.out.println("        Nothing to change");
            }

            } catch (IOException ex) {
            System.out.println("--------------------------------------------------");
            System.out.println("        File not found");
            System.out.println("--------------------------------------------------");
        }
    }

    public static void copyDate() {
        Scanner src = new Scanner(System.in);
        System.out.println("        Input -1 if you want exit, or ENTER to continue.");
        String res = src.nextLine();
        while(!(res.equals("-1"))) {
            System.out.print("        Enter absolut path to file where getting DateAndTime\n        Or input -1 to exit from application\n");
            String from = src.nextLine();
            File copy = new File(from);
                String minus1 = copy.toString();
                if (minus1.equals("-1")){
                    break;
                }
            System.out.print("        Enter absolut path to file where paste DateAndTime\n          Or input -1 to exit from application\n");
                String to = src.nextLine();
                File paste = new File(to);
                String minus2 = paste.toString();
                if(minus2.equals("-1")){
                    break;
                }
                String tmpC;
                String [] tmpArrC;
                String nicknameC;
                
                String tmpP;
                String [] tmpArrP;
                String nicknameP;
                boolean match;

                StringBuilder buildCurrentString = new StringBuilder();
                StringBuilder buildAllData = new StringBuilder();
                try(BufferedReader br = new BufferedReader(new FileReader(to))) {

                    while((tmpP = br.readLine()) != null) {
                        match = true;
                        tmpArrP = tmpP.split(" ");
                        if (tmpArrP.length < 2)
                            continue;
                        nicknameP = tmpArrP[2].toLowerCase();

                        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(copy))) {
                            while((tmpC = bufferedReader.readLine()) != null) {
                                tmpArrC = tmpC.split(" ");
                                if (tmpArrC.length < 2)
                                    continue;
                                nicknameC = tmpArrC[2].toLowerCase();
                                if (nicknameP.equals(nicknameC)) {
                                    match = false;
//                                    System.out.println(tmpC);
                                    buildCurrentString.append(tmpArrC[0]).append(" ");
                                    for(int i = 1; i < tmpArrP.length; i++) {
                                        if (i != tmpArrP.length - 1)
                                            buildCurrentString.append(tmpArrP[i]).append(" ");
                                        else
                                            buildCurrentString.append(tmpArrP[i]);
                                    }
                                    buildAllData.append(buildCurrentString).append("\n");
                                    buildCurrentString = new StringBuilder();
                                    break;
                                }
                            }
                        }
                        if (match){
                            buildAllData.append(tmpP).append("\n");
                        }
                    }

                    res = buildAllData.toString();
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter(to))) {
                        System.out.println("            Edit date and time...");
                        bw.write(res);
                        System.out.println("            Success!");
                    }
                } catch (IOException e) {
                    System.out.println("        File not found");
                }
        }
            System.out.println("        Exit to main menu...");
    }
}
