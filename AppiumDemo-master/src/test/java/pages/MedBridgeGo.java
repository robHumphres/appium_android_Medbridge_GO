package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MedBridgeGo extends BasePage {

    //Member Variables
    private static final String nonPatientAccessCode = "76X9G37Z";
    private static final String patientAccessCode = "";
    private static String
            _currTotalExerciseTimeToday = null,
            _currCurrentStreak = null,
            _currLongestStreak = null;

    public MedBridgeGo(WebDriver driver){
        super(driver);
    }

    public void mainExecution(){

        //Login
        System.out.println("Able to login: " + logIn());

        //Wait for element to load
        waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/label_minute_session"));

        System.out.println("Was able to find the element");

        //Make Sure Exercise Tab works correctly portrait....
        exerciseTab();



    }

    private boolean exerciseTabElementsVisible(){


        try{
            //Make sure elements are visible
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/label_minute_session"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/logo"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/title"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/trophy"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/go_button"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/label_minute_session"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/button_skip_and_mark"));

            //Test Exercise NOTE: could integrate this test with HEP Smoke, need a way to pass exercises it picks
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/end_Session"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/exercise_name"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/surface_view"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/circularProgress"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/slide_duration"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/prev"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/play_button"));
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/next"));


            //TODO: Need to figure out what to do about Information Tab
            //driver.findElement(By.id("com.sqor.droid.dev:id/textContent")).getText();



        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;

    }

    private void grabCurrentDataFromActivities(){

        driver.findElement(By.id("my_activity_button")).click();
        _currTotalExerciseTimeToday = driver.findElement(By.id("com.medbridgeed.hep.go:id/total_exercises_value")).getText();
        _currCurrentStreak = driver.findElement(By.id("com.medbridgeed.hep.go:id/current_streak")).getText();
        _currLongestStreak = driver.findElement(By.id("com.medbridgeed.hep.go:id/longest_streak_value")).getText();
        driver.findElement(By.id("exercise_button")).click();

    }


    private boolean exerciseTab(){


        try{
            //Make sure all elements are there.
            System.out.println("Exercise Elements are all visible: " + exerciseTabElementsVisible());

            //Grab Current Sessions Records.. *Need to implement CRUD for truly fresh accounts*
            grabCurrentDataFromActivities();

            //Start exercise program
            driver.findElement(By.id("go_button")).click();
            driver.findElement(By.id("com.medbridgeed.hep.go:id/end_Session")).click();
            //sweet naming... button2 is discard session fyi.
            driver.findElement(By.id("android:id/button2")).click();

            //Home Activity look for session element
            waitForVisibilityOf(By.id("com.medbridgeed.hep.go:id/label_minute_session"));

            //Validate session that there's no changes
            if(!validActivity())
                return false;

            //Validate Session make sure there's a +1 change
            driver.findElement(By.id("exercise_button")).click();
            driver.findElement(By.id("go_button")).click();
            driver.findElement(By.id("com.medbridgeed.hep.go:id/end_Session")).click();
            driver.findElement(By.id("android:id/button1")).click();

            //Good Job page, hitting done
            driver.findElement(By.id("com.medbridgeed.hep.go:id/done_button")).click();

            //update curret strings
            updateCurrStreaks();
            //Verify the ++ worked
            validActivity();



        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private void updateCurrStreaks(){

        int temp = 0;
        temp = Integer.getInteger(_currCurrentStreak);
        temp ++;
        _currCurrentStreak = Integer.toString(temp);

        temp = Integer.getInteger(_currLongestStreak);
        temp ++;
        _currLongestStreak = Integer.toString(temp);

    }

    private boolean validActivity(){

        //Check My Activity for results
        driver.findElement(By.id("my_activity_button")).click();
        if(!(driver.findElement(By.id("com.medbridgeed.hep.go:id/total_exercises_value")).getText().equalsIgnoreCase(_currTotalExerciseTimeToday))){
            System.out.print("Curr Total time has changed... which it shouldn't");
            return false;
        }

        if(!(driver.findElement(By.id("com.medbridgeed.hep.go:id/current_streak")).getText().equalsIgnoreCase(_currCurrentStreak)))
        {System.out.print("Curr streak has changed.... which it shouldn't"); return false;}


        if(!(driver.findElement(By.id("com.medbridgeed.hep.go:id/longest_streak_value")).getText().equalsIgnoreCase(_currLongestStreak)))
        {System.out.print("Longest streak has changed.... which it shouldn't"); return false;}

        driver.findElement(By.id("exercise_button")).click();

        return true;

    }


    public boolean logIn(){
        driver.findElement(By.id("button_start")).click();

        waitForVisibilityOf(By.id("terms_checkbox"));
        driver.findElement(By.id("terms_checkbox")).click();
        try {
            char[] tempc = nonPatientAccessCode.toCharArray();

            for (int x = 1; x < 9; x++) {
                driver.findElement(By.id("token_char_" + x)).sendKeys(Character.toString(tempc[x - 1]));
            }

            driver.findElement(By.id("button_next")).click();

        }catch(Exception e) {

            System.out.println(e.getMessage());
            return false;
        }

        return true;

    }





}
