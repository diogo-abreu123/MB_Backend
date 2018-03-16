package MBPackage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MBController {

    //Allows user to book a test drive
    @RequestMapping(value = "/bookTestDrive", method= RequestMethod.GET)
    public String bookTestDrive(@RequestParam(value="vehicleID") String vehicle_id,
                                @RequestParam(value="firstName") String first_name,
                                @RequestParam(value="lastName") String last_name,
                                @RequestParam(value="pickupDate") String pickup_date)
    {
        String result = "hey";
        JSONArray bookings = JSONInitializer.getBookings();
        boolean canAdd = true;
        for (Object booking:bookings)
        {
            JSONObject jsonbooking = (JSONObject) booking;

            if(jsonbooking.get("vehicleId").equals(vehicle_id) && (LocalDateTime.parse(pickup_date).isBefore(LocalDateTime.parse((String)jsonbooking.get("pickupDate")).minusMinutes(15)) || LocalDateTime.parse(pickup_date).isAfter(LocalDateTime.parse((String)jsonbooking.get("pickupDate")).plusMinutes(15))))
            {
                result = "Can't add booking";
                canAdd = false;
            }
        }

        result = "Booking added successfully";
        if(canAdd)
        {
            JSONObject jsonbook = new JSONObject();
            jsonbook.put("id",UUID.randomUUID().toString());
            jsonbook.put("firstName",first_name);
            jsonbook.put("lastName",last_name);
            jsonbook.put("vehicleId",vehicle_id);
            jsonbook.put("pickupDate",pickup_date);
            jsonbook.put("createdAt",LocalDateTime.now());
            bookings.put(jsonbook);
        }

        return result+bookings.getJSONObject(bookings.length()-1).toString();
    }

    //Method that lists vehicles according to selected criterion
    @RequestMapping(value = "/filter", method= RequestMethod.GET)
    public List<Vehicle> filter(@RequestParam(value="type") String type,@RequestParam(value="value") String value) {
        List<Vehicle> result = new ArrayList<>();
        JSONArray dealers = JSONInitializer.getDealers();

        for(Object dealer : dealers)
        {
            if (dealer instanceof JSONObject)
            {
                JSONObject dealerJson = (JSONObject) dealer;
                JSONArray vehicles = (JSONArray) dealerJson.get("vehicles");
                for (Object vehicle : vehicles)
                {
                    if (vehicle instanceof JSONObject)
                    {
                        JSONObject iter_vehicle = (JSONObject) vehicle;
                        String type2 = type.equals("dealer") ? dealerJson.getString("name"): iter_vehicle.getString(type);
                        if (type2.equals(value))
                        {
                            if(type.equals("model"))
                            {
                                result.add(new Vehicle(iter_vehicle.get("id").toString(), type2, iter_vehicle.get("fuel").toString(), iter_vehicle.get("transmission").toString()));
                            }
                            else if(type.equals("fuel"))
                            {
                                result.add(new Vehicle(iter_vehicle.get("id").toString(),iter_vehicle.get("model").toString() ,type2 , iter_vehicle.get("transmission").toString()));
                            }
                            else if(type.equals("transmission"))
                            {
                                result.add(new Vehicle(iter_vehicle.get("id").toString(),iter_vehicle.get("model").toString() ,iter_vehicle.get("fuel").toString() , type2));
                            }
                            else if(type.equals("dealer"))
                            {
                                if(dealerJson.getString("name").equals(value))result.add(new Vehicle(iter_vehicle.get("id").toString(),iter_vehicle.get("model").toString() ,iter_vehicle.get("fuel").toString() , iter_vehicle.get("transmission").toString()));
                            }
                        }
                    }
                }
            }
        }
        return result;
    }


    @RequestMapping(value = "/closestDealer", method= RequestMethod.GET)
    public Dealer getClosestDealer(@RequestParam(value="model") String model,
                                   @RequestParam(value="fuel") String fuel,
                                   @RequestParam(value="transmission") String transmission,
                                   @RequestParam(value="lat") BigDecimal latitude,
                                   @RequestParam(value="long") BigDecimal longitude)
    {
        JSONArray dealers = JSONInitializer.getDealers();
        dealers = sortDealers(dealers,new Point(latitude.intValue(),longitude.intValue()));
        List<String> closed = new ArrayList<>();
        JSONArray closed_days = (JSONArray) dealers.getJSONObject(0).get("closed");
        for(int i = 0;i<closed_days.length();i++)
        {
            closed.add(closed_days.get(i).toString());
        }

        int valid_dealer = -1;
        int dealer_iter = 0;
        for(Object dealer:dealers)
        {
            JSONObject jsondealer = (JSONObject) dealer;
            JSONArray vehicles = jsondealer.getJSONArray("vehicles");

            for(Object vehicle: vehicles)
            {
                JSONObject jsonvehicle = (JSONObject) vehicle;
                if(jsonvehicle.get("model").equals(model)
                        && jsonvehicle.get("fuel").equals(fuel)
                        && jsonvehicle.get("transmission").equals(transmission))
                {
                    valid_dealer = dealer_iter;
                }
            }
            dealer_iter++;
        }

        if(valid_dealer>-1)
        {
            return new Dealer(dealers.getJSONObject(valid_dealer).getString("id"),
                    dealers.getJSONObject(valid_dealer).getString("name"),
                    dealers.getJSONObject(valid_dealer).getNumber("latitude").longValue(),
                    dealers.getJSONObject(valid_dealer).getNumber("longitude").longValue(),
                    closed);
        }
        else
        {
            return null;
        }
    }

    public JSONArray sortDealers(JSONArray init_array, Point user_pos)
    {
        ArrayList<JSONObject> sorted_array = new ArrayList<>();
        double dist = 99999999;

        for(Object init: init_array)
        {
            JSONObject init_obj = (JSONObject) init;
            Point init_pos = new Point(init_obj.getNumber("latitude").intValue(),init_obj.getNumber("longitude").intValue());

            if(sorted_array.size() == 0)
            {
                sorted_array.add(init_obj);
            }
            else
            {
                int sorted_index = 0;
                boolean hasInserted = false;
                ArrayList<JSONObject> aux_array = new ArrayList<>();
                for (Object sorted: sorted_array)
                {
                    JSONObject sortedObj = (JSONObject) sorted;
                    Point sorted_pos = new Point(sortedObj.getNumber("latitude").intValue(),sortedObj.getNumber("latitude").intValue());
                    if((sorted_pos.distance(user_pos))>(init_pos.distance(user_pos)))
                    {
                        aux_array.add(sorted_index,init_obj);
                        hasInserted = true;
                    }
                    sorted_index++;
                }
                if(!hasInserted)
                {
                    aux_array.add(sorted_array.size()-1,init_obj);
                }
                sorted_array.addAll(aux_array);
            }
        }
        return new JSONArray(sorted_array);
    }
}
