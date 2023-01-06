import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/** This class takes a file and runs it through different methods to clean it up. */
public class cleanWordsFile {
    private String languageFileName, upDatedName;

    public cleanWordsFile(String languageFileName, String upDatedName) {
       this.languageFileName = languageFileName;
       this.upDatedName = upDatedName;

       cleanLabels();
    }

    /**
     * This method is done only once to remove the individual letters such as b/c/d that are not words. It adds
     * the words in a new textFile called upDated_words.txt
     * */
    public void cleanLabels(){
        try{
            BufferedReader input = new BufferedReader(new FileReader(languageFileName));

            BufferedWriter textUpdate = new BufferedWriter(new FileWriter(upDatedName));

            String word;
            while((word = input.readLine()) != null){
                if(word.compareTo("i") == 0 || word.compareTo("a") == 0 || word.length() != 1){
                    textUpdate.write(word+ "\n");
                }
            }

            input.close();
            textUpdate.close();

        } catch(Exception e){
            System.out.println("Incorrect path has been entered.");
        }
    }
}
