import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JsonSimpleTest3 {

    public static void main(String[] args) throws IOException, JsonException {

        // The file `user4.json` is generated from above example 3.2
        try (FileReader fileReader = new FileReader(("staffTest.json"))) {

            JsonArray objects = Jsoner.deserializeMany(fileReader);
            for (Object obj : objects) {
                if (obj instanceof JsonTestStaff) {

                }
            }

            /*
            Mapper mapper = new DozerBeanMapper();

            JsonArray o = (JsonArray) objects.get(0);
            List<JsonTestStaff> collect = o.stream()
                    .map(x -> mapper.map(x, JsonTestStaff.class)).collect(Collectors.toList());
            collect.forEach(x -> System.out.println(x));
            */
        }

    }

}
