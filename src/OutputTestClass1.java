import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Deprecated
public class OutputTestClass1 {
    private final File file;
    public OutputTestClass1(String fileName){
        this.file=new File(fileName);
    }
    public void output(){

        try{
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            String testOutputData = "testdata1";
            fileOutputStream.write(testOutputData.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
