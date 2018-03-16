package MBPackage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


public class JSONInitializer {

    private static JSONArray dealers;
    private static JSONArray bookings;
    private static JSONObject jsonObject;

    public static void init()
    {
        try
        {
            Path datapath = FileSystems.getDefault().getPath("dataset.json.txt");
            String content = new String(Files.readAllBytes(datapath));
            jsonObject = new JSONObject(content);
            dealers = (JSONArray) jsonObject.get("dealers");
            bookings = (JSONArray) jsonObject.get("bookings");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static JSONArray getDealers()
    {
        return dealers;
    }

    public static void setDealers(JSONArray dealers)
    {
        JSONInitializer.dealers = dealers;
    }

    public static JSONArray getBookings()
    {
        return bookings;
    }

    public static void setBookings(JSONArray bookings)
    {
        JSONInitializer.bookings = bookings;
    }

    public static JSONObject getData()
    {
        return jsonObject;
    }

}
