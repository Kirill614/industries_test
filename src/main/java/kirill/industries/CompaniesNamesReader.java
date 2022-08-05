package kirill.industries;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompaniesNamesReader {
    public List<String> readCompaniesNames() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader("C:\\Users\\User\\companies.txt");
            int i;
            while ((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] companiesArray = stringBuilder.toString().split(",");
        return Arrays.asList(companiesArray);
    }

}
