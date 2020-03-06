import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsonSimpleTest1 {

    public static void main(String[] args) throws IOException {

        JsonTestStaff staff = createStaff();

        //Java objects toJSON String
        String json = Jsoner.serialize(staff);

        //pretty print
        json = Jsoner.prettyPrint(json);

        System.out.println(json);

        //Java objects to JSON file
        try (FileWriter fileWriter = new FileWriter("staffTest.json")) {
            Jsoner.serialize(staff, fileWriter);
        }

    }

    private static JsonTestStaff createStaff(){

        JsonTestStaff staff = new JsonTestStaff();

        staff.setName("Ian");
        staff.setAge(38);
        staff.setPosition(new String[]{"Founder", "CTO", "Writer"});
        Map<String, BigDecimal> salary = new HashMap() {{
            put("2010", new BigDecimal(10000));
            put("2012", new BigDecimal(12000));
            put("2018", new BigDecimal(14000));
        }};
        staff.setSalary(salary);
        staff.setSkills(Arrays.asList("java", "python", "node", "kotlin"));

        return staff;

    }

}
