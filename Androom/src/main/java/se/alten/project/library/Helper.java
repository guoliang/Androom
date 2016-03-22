package se.alten.project.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Helper {

    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> collection) {
        List<T> resultList = new ArrayList<T>(collection.size());
        for(Object object: collection)
          resultList.add(clazz.cast(object));
        return resultList;
    }

    public static Date parseStringToDate(String stringDate)
            throws ParseException {
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date parsedDate = format.parse(stringDate);

        return parsedDate;
    }
}
