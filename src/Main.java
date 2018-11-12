import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.util.Collections.swap;

public class Main {

    public static void main(String[] args) {
        //Instantiate big bottle and small bottle
        Bottle bigBottle = new Bottle(5);
        Bottle smallBottle = new Bottle(3);

        //Input the target volume
        int targetVolume = getValidNumberInput();

        //To store result
        List<String> results = searchSolutions(bigBottle,smallBottle,targetVolume);

        System.out.println("Solutions are:");
        for(int i = 0; i < results.size()-1; i++) {
            for(int j = results.size()-1;j > i;j--){
                if(results.get(j-1).length() >results.get(j).length()){
                    swap(results,j-1,j);
                }
            }
        }
        for(String str : results)
            System.out.println(str);
        System.out.println();
        System.out.println("Simplest solution would be:");

        System.out.println(results.get(0));
    }

    private static List<String> searchSolutions(Bottle bigBottle, Bottle smallBottle,int targetVolume){
        List<String> results = new ArrayList<>();
        int round = 100;
        System.out.println("Check would be "+round+" times!");
        while(round > 0) {
            round--;
            System.out.println("Under checking and "+round+" times left.");
            resetBottles(bigBottle, smallBottle);
            while ((bigBottle.getCurrentVolume() != targetVolume) && (smallBottle.getCurrentVolume() != targetVolume)) {
                //To record result
                StringBuilder resultDetail = new StringBuilder();
                resetBottles(bigBottle, smallBottle);
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        bigBottle.fillBottle();
                        resultDetail.append("Begin with filling big bottle:  [" + bigBottle.getCurrentVolume() + "," + smallBottle.getCurrentVolume() + "]" + "-->");
                        String str1 = tryAllPossibilities(bigBottle, smallBottle, targetVolume);
                        if (str1 != null) {
                            resultDetail.append(str1);
                            if (!results.contains(resultDetail.toString())) {
                                results.add(resultDetail.toString());
                            }
                        }
                        break;

                    case 1:
                        smallBottle.fillBottle();
                        resultDetail.append("Begin with filling small bottle:[" + bigBottle.getCurrentVolume() + "," + smallBottle.getCurrentVolume() + "]" + "-->");
                        String str2 = tryAllPossibilities(bigBottle, smallBottle, targetVolume);
                        if (str2 != null) {
                            resultDetail.append(str2);
                            if (!results.contains(resultDetail.toString())) {
                                results.add(resultDetail.toString());
                            }
                        }
                        break;
                }

            }

        }
        return results;
    }

    //Rule 1 : To the same bottle, behavior can not just same as the previous one
    //Rule 2 : To the same bottle, can not empty after it just filled up
    //Rule 3 : To the same bottle, if there is no water then empty behavior can not happen
    //Rule 4 : To the same bottle, if there is no water then transfer behavior can not happen
    //Rule 5 : Between two bottles, if one bottle just got water transferred in, it can not transfer back on next behavior

    private static String tryAllPossibilities(Bottle bigBottle,Bottle smallBottle,int targetVolume){
        int counter = 1;
        StringBuilder resultDetail = new StringBuilder();
        while ((bigBottle.getCurrentVolume() != targetVolume) && (smallBottle.getCurrentVolume() != targetVolume) && counter < 10) {
            switch ((int) (Math.random() * 5)) {
                case 0:
                    if(bigBottle.isJustFilled()) //Rule 1
                        continue;
                    bigBottle.fillBottle();
                    resultDetail.append("Fill big bottle:["+bigBottle.getCurrentVolume()+","+smallBottle.getCurrentVolume()+"]"+"-->");
                    counter++;
                    break;

                case 1:
                    if(smallBottle.isJustFilled()) //Rule 1
                        continue;
                    smallBottle.fillBottle();
                    resultDetail.append("Fill small bottle:["+bigBottle.getCurrentVolume()+","+smallBottle.getCurrentVolume()+"]"+"-->");
                    counter++;
                    break;

                case 2:
                    if(bigBottle.isJustGotTransferred() || bigBottle.getCurrentVolume() == 0) //Rule 1 and Rule 4
                        continue;
                    bigBottle.transferVolume(smallBottle);
                    resultDetail.append("Pour from big to small:["+bigBottle.getCurrentVolume()+","+smallBottle.getCurrentVolume()+"]"+"-->");
                    counter++;
                    break;

                case 3:
                    if(smallBottle.isJustGotTransferred() || smallBottle.getCurrentVolume() == 0) //Rule 1 and Rule 4
                        continue;
                    smallBottle.transferVolume(bigBottle);
                    resultDetail.append("Pour from small to big:["+bigBottle.getCurrentVolume()+","+smallBottle.getCurrentVolume()+"]"+"-->");
                    counter++;
                    break;

                case 4:
                    if(smallBottle.isJustEmptied() || smallBottle.getCurrentVolume() == 0 || smallBottle.isJustFilled()) //Rule 1 and Rule 2 and Rule 3
                        continue;
                    smallBottle.emptyBottle();
                    resultDetail.append("Empty small bottle:["+bigBottle.getCurrentVolume()+","+smallBottle.getCurrentVolume()+"]"+"-->");
                    counter++;
                    break;

                case 5:
                    if(bigBottle.isJustEmptied() ||bigBottle.getCurrentVolume() == 0 || bigBottle.isJustFilled()) //Rule 1 and Rule 2 and Rule 3
                        continue;
                    bigBottle.emptyBottle();
                    resultDetail.append("Empty big bottle:["+bigBottle.getCurrentVolume()+","+smallBottle.getCurrentVolume()+"]"+"-->");
                    counter++;
                    break;
            }

            if ((bigBottle.getCurrentVolume() == targetVolume) || (smallBottle.getCurrentVolume() == targetVolume)) {
                resultDetail.append("Steps: "+counter);
                //System.out.println(resultDetail.toString());
                return resultDetail.toString();
            }
        }
        return null;
    }

    private static void resetBottles(Bottle bigBottle,Bottle smallBottle){
        bigBottle.resetBottle();
        smallBottle.resetBottle();
    }

    private static int getValidNumberInput(){
        while(true) {
            try {
                System.out.println("Input how much liter water you would like to have, please.(1/4)");
                Scanner scanner = new Scanner(System.in);
                int targetVolume = scanner.nextInt();
                if (targetVolume == 1 || targetVolume == 4){
                    System.out.println("Target volume is: "+ targetVolume+" Liter.");
                    return targetVolume;
                }
                else
                    throw new Exception();
            }catch (InputMismatchException e) {
                System.out.println("Your input is not a number");
            } catch (Exception e) {
                System.out.println("Please input the number 1 or 4.");
            }
        }
    }

}

